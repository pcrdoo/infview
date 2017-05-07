package model.files;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.event.EventListenerList;

import model.Attribute;
import model.CharType;
import model.Entity;
import model.InfResource;
import model.InvalidLengthException;
import model.Record;
import model.VarCharType;

public abstract class File extends Entity {
	private ArrayList<UpdateBlockListener> updateBlockListeners;
	
	// Velicina bloka u slogovima, menja korisnik.
	protected int blockFactor = 20;
	
	// Velicina sloga u bajtovima, setuje se kad se dodaje atribut, \r\n ruzan hak
	protected int recordSize = 2; 

	// Broj slogova u datoteci
	protected int numRecords = 0;
	
	// Broj blokova u datoteci, prikazati u view!
	protected int numBlocks = 0; 
	
	// Pokazivac dokle smo stigli
	protected int filePointer = 0; 

	// Full path do entiteta, constructor TODO
	protected String path; 
	
	 // Trenutno ucitan blok
	protected ArrayList<Record> currentBlock;

	protected RandomAccessFile file;
	
	public File(String name, String path, InfResource parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
		currentBlock = new ArrayList<Record>();
		updateBlockListeners = new ArrayList<UpdateBlockListener>();
		this.path = path;
	}
	
	private void lazyOpenFile() throws FileNotFoundException {
		if (file == null) {
			file = new RandomAccessFile(path, "r");
		}
	}
	
	private void closeFile() {
		if (file != null) {
			try {
				file.close();
			} catch (IOException e) {
				System.err.println("File: Failed closing file, ignoring as there's nothing we can do");
			}
		}
		
		file = null;
	}
	
	public boolean fetchNextBlock() throws IOException, InvalidRecordException {
		System.out.println("Fetching next block!");
		lazyOpenFile();
		
		numRecords = (int) Math.ceil(((double) file.length()) / recordSize);
		numBlocks = (int) Math.ceil(((double) numRecords) / blockFactor);

		System.out.println(numRecords + " " + filePointer + " " + recordSize);
		if(filePointer + 2 == recordSize * numRecords) {
			// opet glup hak za \r\n
			closeFile();
			file = null;
			return false;
		}
		int recordsLeft = numRecords - filePointer / recordSize;
		int recordsToRead = Math.min(blockFactor, recordsLeft);
		int bufferSize = recordsToRead * recordSize;
		byte[] buffer = new byte[bufferSize];
		file.seek(filePointer);
		file.read(buffer);
		String contents = new String(buffer);
		// poravnanje?!

		currentBlock.clear();
		for (int i = 0; i < recordsToRead; i++) {
			// svaki slog predstavlja jednu liniju teksta
			String line = contents.substring(i * recordSize, (i+1) * recordSize);
			int linePosition = 0;
			Record record = new Record(this);
			for(Attribute attr: this.attributes) {
				String field = line.substring(linePosition, linePosition + attr.getLength());
				field = field.trim();
				linePosition += attr.getLength();
				Class<?> cls = attr.getValueClass();
				
				if(cls == CharType.class) {
					CharType str = new CharType(field.length());
					try {
						str.set(field);
						record.addAttribute(attr, str);
					} catch(InvalidLengthException e) {
						closeFile();
						throw new InvalidRecordException("CharType", field);
					}
				} else if(cls == VarCharType.class) {
					VarCharType str = new VarCharType(field.length());
					try {
						str.set(field);
						record.addAttribute(attr, str);
					} catch(InvalidLengthException e) {
						closeFile();
						throw new InvalidRecordException("VarCharType", field);
					}
				} else if(cls == Date.class) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					try {
						Date date = sdf.parse(field);
						System.out.println(date);
						record.addAttribute(attr, date);
					} catch(ParseException e) {
						closeFile();
						throw new InvalidRecordException("Date", field);
					}
				} else if(cls == Boolean.class) {
					if(field.equals("true")) {
						record.addAttribute(attr, true);
					} else if(field.equals("false")) {
						record.addAttribute(attr, false);
					} else {
						closeFile();
						throw new InvalidRecordException("Boolean", field);
					}
				} else if(cls == Integer.class) {
					try {
						if(attr.getName().equals("GodinaStudija") && field.equals("A")) {
							field = "1";
							// ISPRAVKA GRESKE U VELIKOM SETU PODATAKA
						}
						Integer num = Integer.parseInt(field);
						record.addAttribute(attr, num);
					} catch(NumberFormatException e) {
						closeFile();
						throw new InvalidRecordException("Integer", field);
					}
				} else {
					System.out.println(cls + "!!");
				}
			}
			currentBlock.add(record);
		}

		// pozicioniramo file pointer tamo gde smo stali sa citanjem
		filePointer = (int)file.getFilePointer();
		return true;
	}

	public abstract boolean addRecord(ArrayList<String> record) throws IOException;

	public abstract boolean updateRecord(ArrayList<String> record) throws IOException;

	public abstract List<Record> findRecord(String[] terms, boolean all);

	public abstract boolean deleteRecord(ArrayList<String> record);
	
	public int getFileSize() {
		return this.numRecords * this.recordSize;
	}

	public ArrayList<Record> getCurrentBlock() {
		return currentBlock;
	}

	public int getBlockFactor() {
		return blockFactor;
	}

	public void setBlockFactor(int blockFactor) {
		this.blockFactor = blockFactor;
	}

	@Override
	public void addAttribute(Attribute newAttribute) {
		if (newAttribute == null)
			return;
		if (this.attributes == null)
			this.attributes = new ArrayList<Attribute>();
		if (!this.attributes.contains(newAttribute)) {
			recordSize += newAttribute.getLength(); // povecaj record size!
			this.attributes.add(newAttribute);
		}
	}

	@Override
	public void removeAttribute(Attribute oldAttribute) {
		if (oldAttribute == null)
			return;
		if (this.attributes != null)
			if (this.attributes.contains(oldAttribute)) {
				recordSize -= oldAttribute.getLength(); // smanjio record size!
				this.attributes.remove(oldAttribute);
			}
	}

	@Override
	public void removeAllAttributes() {
		if (attributes != null) {
			recordSize = 2;

			attributes.clear();
		}
	}

	/*
	 * // lista sluÅ¡aÄ�a koja se koristi da se osveÅ¾i prikaz tabele u klasi
	 * FileView // prilikom uÄ�itavanja novog bloka iz datoteke
	 * 
	 * EventListenerList listenerBlockList = new EventListenerList();
	 * UpdateBlockEvent updateBlockEvent = null;
	 * 
	 */
	public void addUpdateBlockListener(UpdateBlockListener l) {
		updateBlockListeners.add(l);
	}
	
	public void removeUpdateBlockListener(UpdateBlockListener l) {
		updateBlockListeners.remove(l);
	}

	public void fireUpdateBlockPerformed() {
		for (UpdateBlockListener listener : updateBlockListeners) {
			listener.blockUpdated();
		}
	}

	// ;

	// TODO: listeners IZNAD
	// TODO: ucitavanje iz serial ispod
}

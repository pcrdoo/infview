package model.files;

import java.io.IOException;
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
	
	public File(String name, String path, InfResource parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
		currentBlock = new ArrayList<Record>();
		updateBlockListeners = new ArrayList<UpdateBlockListener>();
		this.path = path;
	}
	
	public boolean fetchNextBlock() throws IOException, InvalidRecordException {
		System.out.println("Fetching next block!");
		RandomAccessFile file = new RandomAccessFile(path, "r");
		numRecords = (int) Math.ceil(((double) file.length()) / recordSize);
		numBlocks = (int) Math.ceil(((double) numRecords) / blockFactor);

		int recordsLeft = numRecords - filePointer / recordSize;
		int recordsToRead = Math.min(blockFactor, recordsLeft);
		int bufferSize = recordsToRead * recordSize;
		buffer = new byte[bufferSize];
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
				System.out.println(attr.getLength() + " " + attr.getName());
				String field = line.substring(linePosition, linePosition + attr.getLength());
				System.out.println("*" + field + "*");
				field = field.trim();
				linePosition += attr.getLength();
				Class<?> cls = attr.getValueClass();
				
				if(cls == CharType.class) {
					CharType str = new CharType(field.length());
					try {
						str.set(field);
						record.addAttribute(attr, str);
					} catch(InvalidLengthException e) {
						file.close();
						throw new InvalidRecordException("CharType", field);
					}
				} else if(cls == VarCharType.class) {
					VarCharType str = new VarCharType(field.length());
					try {
						str.set(field);
						record.addAttribute(attr, str);
					} catch(InvalidLengthException e) {
						file.close();
						throw new InvalidRecordException("VarCharType", field);
					}
				} else if(cls == Date.class) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					try {
						Date date = sdf.parse(field);
						System.out.println(date);
						record.addAttribute(attr, date);
					} catch(ParseException e) {
						file.close();
						throw new InvalidRecordException("Date", field);
					}
				} else if(cls == Boolean.class) {
					if(field.equals("true")) {
						record.addAttribute(attr, true);
					} else if(field.equals("false")) {
						record.addAttribute(attr, false);
					} else {
						file.close();
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
						file.close();
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
		file.close();
		fireUpdateBlockPerformed(); // ozvezavanje tabele
		return true;
	}

	public abstract boolean addRecord(ArrayList<String> record) throws IOException;

	public abstract boolean updateRecord(ArrayList<String> record) throws IOException;

	public abstract List<Record> findRecord(String[] terms, boolean all);

	public abstract boolean deleteRecord(ArrayList<String> record);

	protected int blockFactor = 20; // velicina bloka u slogovima, menja
									// korisnik!
	protected int recordSize = 2; // velicina sloga u bajtovima, setuje se kad
									// se dodaje atribut, \r\n ruzan hak
	// BUFFER SIZE = BLOCK_FACTOR * RECORD_SIZE
	// protected int FILE_SIZE = 0; // velicina datoteke u bajtovima
	protected int numRecords = 0; // broj slogova u datoteci
	protected int numBlocks = 0; // broj blokova u datoteci, prikazati u view!
	protected int filePointer = 0; // pokazivac dokle smo stigli

	protected String path; // full path do entiteta, constructor TODO
	// protected boolean directory;

	protected byte[] buffer;

	protected ArrayList<Record> currentBlock; // trenutno ucitan blok
	
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

	protected void fireUpdateBlockPerformed() {
		for (UpdateBlockListener listener : updateBlockListeners) {
			listener.blockUpdated();
		}
	}

	// ;

	// TODO: listeners IZNAD
	// TODO: ucitavanje iz serial ispod
}

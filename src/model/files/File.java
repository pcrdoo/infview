package model.files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.Attribute;
import model.Entity;
import model.InfResource;
import model.InvalidLengthException;
import model.Record;
import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;

public abstract class File extends Entity {
	private ArrayList<UpdateBlockListener> updateBlockListeners;

	// Velicina bloka u slogovima, menja korisnik.
	protected int blockFactor = 20;

	// Velicina sloga u bajtovima, setuje se kad se dodaje atribut, \r\n ruzan
	// hak
	protected int recordSize = 2;

	// Broj slogova u datoteci
	protected int numRecords = 0;

	// Broj blokova u datoteci, prikazati u view!
	protected int numBlocks = 0;

	// Pokazivac dokle smo stigli
	protected int filePointer = 0;

	// Ukupno blokova fetchovanih ikada
	protected int blocksFetched = 0;

	// Full path do entiteta, constructor TODO
	protected String path;

	protected RandomAccessFile file;

	public File(String name, String path, InfResource parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
		updateBlockListeners = new ArrayList<UpdateBlockListener>();
		this.path = path;
	}

	protected void lazyOpenFile() throws FileNotFoundException {
		if (file == null) {
			file = new RandomAccessFile(path, "r");
		}
	}

	protected void closeFile() {
		if (file != null) {
			try {
				file.close();
			} catch (IOException e) {
				System.err.println("File: Failed closing file, ignoring as there's nothing we can do");
			}
		}

		file = null;
	}
	
	protected Record parseRecordLine(String line) throws InvalidRecordException {
		Record record = new Record(this);

		int linePosition = 0;
		for (Attribute attr : this.attributes) {
			String field = line.substring(linePosition, linePosition + attr.getLength());
			linePosition += attr.getLength();
			try {
				Object obj = parseStringField(field, attr, line);
				record.addAttribute(attr, obj);
			} catch (InvalidRecordException e) {
				closeFile();
				throw e;
			}
		}
		
		return record;
	}

	public ArrayList<Record> fetchNextBlock() throws IOException, InvalidRecordException {
		lazyOpenFile();

		numRecords = (int) Math.ceil(((double) file.length()) / recordSize);
		numBlocks = (int) Math.ceil(((double) numRecords) / blockFactor);

		if (filePointer + 2 == recordSize * numRecords) {
			// opet glup hak za \r\n
			closeFile();
			return null;
		}
		int recordsLeft = numRecords - filePointer / recordSize;
		int recordsToRead = Math.min(blockFactor, recordsLeft);
		int bufferSize = recordsToRead * recordSize;
		byte[] buffer = new byte[bufferSize];
		file.seek(filePointer);
		file.read(buffer);
		String contents = new String(buffer);
		// poravnanje?!

		ArrayList<Record> currentBlock = new ArrayList<Record>();
		for (int i = 0; i < recordsToRead; i++) {
			// svaki slog predstavlja jednu liniju teksta
			String line = contents.substring(i * recordSize, (i + 1) * recordSize);
			currentBlock.add(parseRecordLine(line));
		}

		// pozicioniramo file pointer tamo gde smo stali sa citanjem
		filePointer = (int) file.getFilePointer();
		blocksFetched++;
		return currentBlock;
	}

	public static Object parseStringField(String field, Attribute attr, String lineForReference) throws InvalidRecordException {
		Class<?> cls = attr.getValueClass();
		field = field.trim();
		
		if (cls == CharType.class) {
			CharType str = new CharType(field.length());
			try {
				str.set(field);
				return str;
			} catch (InvalidLengthException e) {
				throw new InvalidRecordException("CharType", field, lineForReference);
			}
		} else if (cls == VarCharType.class) {
			VarCharType str = new VarCharType(field.length());
			try {
				str.set(field);
				return str;
			} catch (InvalidLengthException e) {
				throw new InvalidRecordException("VarCharType", field, lineForReference);
			}
		} else if (cls == DateType.class) {
			try {
				DateType date = new DateType(field);
				return date;
			} catch (ParseException e) {
				throw new InvalidRecordException("Date", field, lineForReference);
			}
		} else if (cls == Boolean.class) {
			if (field.equals("true")) {
				return true;
			} else if (field.equals("false")) {
				return false;
			} else {
				throw new InvalidRecordException("Boolean", field, lineForReference);
			}
		} else if (cls == Integer.class) {
			try {
				if (attr.getName().equals("GodinaStudija") && (field.equals("") || field.equals("a") || field.equals("A"))) {
					field = "1";
					// ISPRAVKA GRESKE U VELIKOM SETU PODATAKA
				}
				Integer num = Integer.parseInt(field);
				return num;
			} catch (NumberFormatException e) {
				throw new InvalidRecordException("Integer", field, lineForReference);
			}
		} else {
			return null;
		}
	}

	public abstract boolean addRecord(Record record) throws IOException;

	public abstract boolean updateRecord(Record record) throws IOException;

	public abstract List<Record> findRecord(String[] terms, boolean all);

	public abstract boolean deleteRecord(Record record) throws IOException;

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
	
	public int getFilePointer() {
		return filePointer;
	}

	public void setFilePointer(int filePointer) {
		this.filePointer = filePointer;
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

	public void fireUpdateBlockPerformed(ArrayList<Record> currentBlock) {
		for (UpdateBlockListener listener : updateBlockListeners) {
			listener.blockUpdated(currentBlock);
		}
	}

	// ;
	public int getBlocksFetched() {
		return blocksFetched;
	}
}

package model.files;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Attribute;
import model.CharType;
import model.InfResource;
import model.InvalidLengthException;
import model.Record;
import model.VarCharType;

public class SerialFile extends File {

	public SerialFile(String name, String path, InfResource parent) {
		super(name, path, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
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

		for (int i = 0; i < recordsToRead; i++) {
			// svaki slog predstavlja jednu liniju teksta
			String line = contents.substring(i * recordSize, (i+1) * recordSize);
			int linePosition = 0;
			Record record = new Record(this);
			for(Attribute attr: this.attributes) {
				String field = line.substring(linePosition, linePosition + attr.getLength());
				linePosition += attr.getLength();
				Class<?> cls = attr.getValueClass();
				
				if(cls == CharType.class) {
					CharType str = new CharType(field.length());
					try {
						str.set(field);
						record.addAttribute(attr, str);
					} catch(InvalidLengthException e) {
						throw new InvalidRecordException("CharType", field);
					}
				} else if(cls == VarCharType.class) {
					VarCharType str = new VarCharType(field.length());
					try {
						str.set(field);
						record.addAttribute(attr, str);
					} catch(InvalidLengthException e) {
						throw new InvalidRecordException("VarCharType", field);
					}
				} else if(cls == Date.class) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					try {
						Date date = sdf.parse(field);
						record.addAttribute(attr, date);
					} catch(ParseException e) {
						throw new InvalidRecordException("Date", field);
					}
				} else if(cls == Boolean.class) {
					if(field.equals("true")) {
						record.addAttribute(attr, true);
					} else if(field.equals("false")) {
						record.addAttribute(attr, false);
					} else {
						throw new InvalidRecordException("Boolean", field);
					}
				} else if(cls == Integer.class) {
					try {
						Integer num = Integer.parseInt(field);
					} catch(NumberFormatException e) {
						throw new InvalidRecordException("Integer", field);
					}
				}
			}
		}

		// pozicioniramo file pointer tamo gde smo stali sa citanjem
		filePointer = (int)file.getFilePointer();
		file.close();
		fireUpdateBlockPerformed(); // ozvezavanje tabele
		return true;
	}

	@Override
	public boolean addRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int findRecord(int TODO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteRecord(ArrayList<String> record) {
		// TODO Auto-generated method stub
		return false;
	}

}

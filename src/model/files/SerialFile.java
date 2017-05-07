package model.files;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Attribute;
import model.InfResource;
import model.InvalidLengthException;
import model.Record;
import model.datatypes.CharType;
import model.datatypes.VarCharType;

public class SerialFile extends File {

	public SerialFile(String name, String path, InfResource parent) {
		super(name, path, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Record> findRecord(String[] terms, boolean all) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addRecord(Record record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(Record record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRecord(Record record) {
		// TODO Auto-generated method stub
		return false;
	}

}

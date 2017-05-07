package model.files;

import java.io.IOException;
import java.util.List;

import model.InfResource;
import model.Record;

public class SerialFile extends File {

	public SerialFile(String name, String path, InfResource parent) {
		super(name, path, parent);
		// TODO Auto-generated constructor stub
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
	public List<Record> findRecord(String[] terms, boolean all) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRecord(Record record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}

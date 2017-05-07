package model.files;

import java.io.IOException;
import java.util.ArrayList;

import model.InfResource;

public class IndexedSequentialFile extends File {


	public IndexedSequentialFile(String name, String path, InfResource parent) {
		super(name, path, parent);
		// TODO Auto-generated constructor stub
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

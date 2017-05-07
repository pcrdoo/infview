package model.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.InfResource;
import model.Record;

public class SequentialFile extends File {

	public SequentialFile(String name, String path, InfResource parent) {
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
	public List<Record> findRecord(String[] terms, boolean all) {

		ArrayList<Record> result = new ArrayList<>();
		
		while(this.filePointer < this.getFileSize()) {
			try {
				fetchNextBlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (Record record : this.currentBlock) {
				if(record.matches(terms)) {
					result.add(record);
				}
				
				if(record.greatherThen(terms)) {
					return result;
				}
			}
		}
		return result;
	}

	@Override
	public boolean deleteRecord(ArrayList<String> record) {
		// TODO Auto-generated method stub
		return false;
	}

}

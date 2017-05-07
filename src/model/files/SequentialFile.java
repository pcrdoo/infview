package model.files;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.InfResource;
import model.Record;
import model.indextree.Tree;

import model.Attribute;

import model.indextree.Tree;
import model.indextree.Node;
import model.indextree.KeyElement;
import model.indextree.NodeElement;
import model.indextree.InvariantViolationException;

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

		ArrayList<Record> currentBlock;
		try {
			currentBlock = fetchNextBlock();

			while (currentBlock != null) {
				for (Record record : currentBlock) {
					if (record.matches(terms)) {
						result.add(record);

						if (!all)
							return result;
					}

					if (record.greaterThan(terms)) {
						return result;
					}
				}
				currentBlock = fetchNextBlock();
				System.out.println(this.filePointer);
			}
		} catch (Exception e) {
			e.printStackTrace(); // lol
		}
		return result;
	}
	
	public ArrayList<Record> findRecord(String[] terms, boolean all, boolean toFile, boolean fromStart) {
		System.out.println("Pocinjem da trazim gari...");
		if (fromStart)
			this.filePointer = 0;

		ArrayList<Record> result = (ArrayList<Record>) this.findRecord(terms, all);

		if (!toFile) {
			ArrayList<Record> currentBlock = (ArrayList<Record>) result;
			System.out.println("Naso sam " + currentBlock.size() + " gari...");
		} else {
			try {
				java.io.File f = new java.io.File("search-results.txt");

				PrintWriter writer = new PrintWriter(f, "UTF-8");
				for (Record record : result) {
					writer.println(record);
				}
				writer.close();

				Desktop.getDesktop().open(f);
			} catch (IOException e) {
				e.printStackTrace();
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

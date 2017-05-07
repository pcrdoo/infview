package model.files;

import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Attribute;
import model.InfResource;
import model.Record;
import model.indextree.InvariantViolationException;
import model.indextree.KeyElement;
import model.indextree.Node;
import model.indextree.NodeElement;
import model.indextree.Tree;


public class IndexedSequentialFile extends SequentialFile {
	private Tree tree;
	
	public IndexedSequentialFile(String name, String path, InfResource parent) {
		super(name, path, parent);
	}
	
	private String getTreePath() {
		return path.replace(".stxt", ".tree");
	}
	
	public void loadOrMakeTree() throws InvalidRecordException, IOException {
		String treePath = getTreePath();
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(treePath));
			Tree tree = (Tree) stream.readObject();
			
			this.tree = tree;
		} catch (Exception e) {
			System.err.println("Tree for " + name + " not available, building a new one.");
			this.tree = makeTree();
			writeTree();
		}
	}
	
	public void writeTree() throws IOException {
		String treePath = getTreePath();
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(treePath));
		stream.writeObject(tree);
	}
	
	public Tree getTree() {
		return tree;
	}
	
	private Tree makeTreeFromElements(ArrayList<NodeElement> elements) {
		ArrayList<Node> level = new ArrayList<>();
		
		// Group the first level
		for (int i = 0; i < elements.size(); i += 2) {
			ArrayList<NodeElement> childElements = new ArrayList<>();
			childElements.add(elements.get(i));
			if (i + 1 < elements.size()) {
				childElements.add(elements.get(i + 1));
			}
			
			try {
				level.add(new Node(childElements));
			} catch (InvariantViolationException e) {
				System.err.println("The unhappenable has happened");
			}
		}
		
		// Group Nodes into new Nodes
		while (level.size() > 1) {
			ArrayList<Node> newLevel = new ArrayList<>();

			for (int i = 0; i < level.size(); i += 2) {
				ArrayList<NodeElement> childElements = new ArrayList<>();
				try {
					Node node = new Node(childElements);
					
					node.addChild(level.get(i));
					childElements.add(new NodeElement(0 /* unused */, level.get(i).getElements().get(0).getKeys()));
					if (i + 1 < level.size()) {
						node.addChild(level.get(i + 1));
						childElements.add(new NodeElement(0 /* unused */, level.get(i + 1).getElements().get(0).getKeys()));
					}
					
					newLevel.add(node);
				} catch (InvariantViolationException e) {
					System.err.println("The unhappenable has happened");
				}
			}
			
			level = newLevel;
		}
		
		return new Tree(level.get(0));
	}

	private Tree makeTree() throws IOException, InvalidRecordException {
		lazyOpenFile();
		
		ArrayList<NodeElement> elements = new ArrayList<>();
		ArrayList<Record> currentBlock = null;
		while ((currentBlock = fetchNextBlock()) != null) {
			if (currentBlock.size() == 0) {
				break;
			}
			
			Record firstRecord = currentBlock.get(0);
			ArrayList<KeyElement> keys = new ArrayList<>();
			
			// Get all PKs of the first record into KeyElement list
			for (HashMap.Entry<Attribute, Object> entry : firstRecord.getAttributes().entrySet()) {
				if (entry.getKey().isPrimaryKey()) {
					keys.add(new KeyElement(entry.getKey().getValueClass(), entry.getValue()));
				}
			}
			
			// Create a NodeElement representing this block, based on the lowest (first) PKs in it
			NodeElement element = new NodeElement(filePointer / recordSize, keys);
			
			elements.add(element);
		}
		
		closeFile();
		return makeTreeFromElements(elements);
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

package model.indextree;
import java.util.ArrayList;

public class NodeElement {
	private int blockAddress;
	private ArrayList<KeyElement> keys;
	
	public NodeElement(int blockAddress, ArrayList<KeyElement> keys) {
		this.blockAddress = blockAddress;
		this.keys = keys;
	}

	public int getBlockAddress() {
		return blockAddress;
	}

	public ArrayList<KeyElement> getKeys() {
		return keys;
	}
}

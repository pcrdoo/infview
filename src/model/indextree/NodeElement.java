package model.indextree;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518268574061266923L;
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

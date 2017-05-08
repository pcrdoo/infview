package model.indextree;

import java.io.Serializable;

public class IndexTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6119574262296845506L;
	private Node root;

	public IndexTree(Node root) {
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}
}

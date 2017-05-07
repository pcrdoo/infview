package model.indextree;
import java.io.Serializable;
public class Tree implements Serializable {
	private Node root;
	
	public Tree(Node root) {
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}
}

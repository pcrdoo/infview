package model.indextree;

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1529325228604172167L;
	private ArrayList<Node> children = new ArrayList<>();
	private ArrayList<NodeElement> elements;

	public Node(ArrayList<NodeElement> elements) throws InvariantViolationException {
		if (elements.size() > 2) {
			throw new InvariantViolationException("Cannot have more than 2 elements");
		}
		this.elements = elements;
	}

	public void addChild(Node child) throws InvariantViolationException {
		if (children.size() > 1) {
			throw new InvariantViolationException("Cannot have more than 2 children");
		}
		children.add(child);
	}

	public void removeChild(Node child) {
		children.remove(child);
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public ArrayList<NodeElement> getElements() {
		return elements;
	}
}

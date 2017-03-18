package model;

import java.util.*;

import javax.swing.tree.TreeNode;

public abstract class InfResource implements TreeNode {
	
	protected String name;
	private InfResource parent;
	
	public InfResource() {
		//TODO izbrisati, mozda
	}
	
	public InfResource(String name, InfResource parent) {
		this.name = name;
		this.parent = parent;
	}
	
	protected List<? extends InfResource> getChildren() {
		System.err.println("OgiException: INFRESOURCE HAS NO CHILDREN!");
		return null;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		return this.getChildren().get(childIndex);
	}

	@Override
	public int getChildCount() {
		return this.getChildren().size();
	}

	@Override
	public TreeNode getParent() {
		return this.parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return this.getChildren().indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return (this instanceof Warehouse || this instanceof Package || this instanceof Entity);
	}

	@Override
	public boolean isLeaf() {
		return this instanceof Attribute || this instanceof Relation || this.getChildren().isEmpty();
	}

	@Override
	public Enumeration children() {
		return null; //TODO
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String indentStringRepresentation(String s, int indentSpaces) {
		String[] lines = s.split("\n");
		String indentString = "";
		for (int i = 0; i < indentSpaces; i++) {
			indentString += " ";
		}
		for (int i = 0; i < lines.length; i++) {
			lines[i] = indentString + lines[i];
		}
		
		return String.join("\n", lines);
	}
	
	public abstract String toIndentedString(int indentSpaces);
	
	@Override
	public String toString() {
		return toIndentedString(0);
	}
}
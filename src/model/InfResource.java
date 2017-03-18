package model;

import java.util.*;

import javax.swing.tree.TreeNode;

public abstract class InfResource implements TreeNode {
	
	private String name;
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
		return true; //TODO
	}

	@Override
	public boolean isLeaf() {
		return this.getChildren().isEmpty();
	}

	@Override
	public Enumeration children() {
		return null; //TODO
	}
}
package view.tree;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import model.Attribute;
import model.Entity;
import model.InfResource;
import model.Package;
import model.Relation;
import model.Warehouse;

public class InfNode implements TreeNode {

	private InfResource resource;
	private InfNode parent;
	private ArrayList<InfNode> children;

	public InfNode(InfResource resource) {
		this.resource = resource;
		this.children = new ArrayList<>();
	}

	public void populate() {
		this.children.clear();
		if (this.resource.getChildren() != null) {
			for (InfResource childRes : this.resource.getChildren()) {
				InfNode child = new InfNode(childRes);
				this.addChild(child);
				if (childRes instanceof Warehouse || childRes instanceof Package) {
					child.populate();
				}
			}
		}
	}

	private void addChild(InfNode child) {
		child.parent = this;
		this.children.add(child);
	}

	public InfResource getResource() {
		return this.resource;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return this.children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return this.children.size();
	}

	@Override
	public TreeNode getParent() {
		return this.parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return this.children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return (this.resource instanceof Warehouse || this.resource instanceof Package);
	}

	@Override
	public boolean isLeaf() {
		return this.resource instanceof Entity || this.children.isEmpty();
	}

	@Override
	public Enumeration children() {
		return null; // TODO
	}

	@Override
	public String toString() {
		return this.resource.getName();
	}
}

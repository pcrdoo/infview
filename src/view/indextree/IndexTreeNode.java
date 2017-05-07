package view.indextree;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import model.indextree.KeyElement;
import model.indextree.Node;
import model.indextree.NodeElement;

public class IndexTreeNode implements TreeNode {

	private Node node;
	private IndexTreeNode parent;
	private ArrayList<IndexTreeNode> children;

	public IndexTreeNode(Node node) {
		this.node = node;
		this.children = new ArrayList<>();
	}

	public void populate() {
		this.children.clear();
		if (this.node.getChildren() != null) {
			for (Node childNode : this.node.getChildren()) {
				IndexTreeNode child = new IndexTreeNode(childNode);
				this.addChild(child);
				child.populate();
			}
		}
	}

	private void addChild(IndexTreeNode child) {
		child.parent = this;
		this.children.add(child);
	}

	public Node getNode() {
		return this.node;
	}

	@Override
	public IndexTreeNode getChildAt(int childIndex) {
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
		return true;
	}

	@Override
	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	@Override
	public Enumeration children() {
		return null; // TODO
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (NodeElement nodeElement : node.getElements()) {
			sb.append("(");
			for (KeyElement keyElement : nodeElement.getKeys()) {
				sb.append(keyElement.getValue().toString());
				if (keyElement != nodeElement.getKeys().get(nodeElement.getKeys().size() - 1)) {
					// If not last
					sb.append(", ");
				}
			}
			sb.append(")");
			if (nodeElement != node.getElements().get(node.getElements().size() - 1)) {
				// If not last
				sb.append(", ");
			}
		}
		sb.append("]");
		
		return sb.toString();
	}
}

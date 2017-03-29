package view.tree;

import controller.TreeController;
import model.Warehouse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import constants.Constants;

public class TreeView extends JPanel {

	private TreeController controller;
	private InfTree tree;

	public TreeView() {
		this.controller = new TreeController(this);
		this.initialize();
	}
	
	private void addMockNodes(Warehouse root) {
		
	}
	
	private void initialize() {
		this.setPreferredSize(Constants.TREE_SIZE);
		
		InfNode root = new InfNode(Warehouse.getInstance());
		root.populate();
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.tree = new InfTree();
		this.tree.setModel(model);

		InfTreeCellRenderer itcl = new InfTreeCellRenderer();
		this.tree.setCellRenderer(itcl);

		this.tree.setShowsRootHandles(true);

		TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.setSelectionModel(selectionModel);

		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(1000, 1000));

		JScrollPane treeScrollPane = new JScrollPane(tree);

		treeScrollPane.setSize(new Dimension(50, (int) Math.round(Constants.TREE_VIEW_HEIGHT)));
		treeScrollPane.setMaximumSize(new Dimension(50, (int) Math.round(Constants.TREE_VIEW_HEIGHT)));
		treeScrollPane.setMinimumSize(new Dimension(50, (int) Math.round(Constants.TREE_VIEW_HEIGHT)));
		treeScrollPane.setPreferredSize(new Dimension(50, (int) Math.round(Constants.TREE_VIEW_HEIGHT)));

		this.add(treeScrollPane, BorderLayout.NORTH);
	}
	public InfTree getTree() {
		return this.tree;
	}
}
package view.indextree;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JPanel;

import controller.TreeController;
import model.Warehouse;
import net.miginfocom.swing.MigLayout;

import controller.indextree.IndexTreeController;
import model.indextree.Node;

public class IndexTreeView extends JPanel {

	private IndexTreeController controller;
	private IndexTreeNode root;
	private IndexTree tree;
	
	public IndexTreeView(Node root) {
		this.controller = new IndexTreeController(this);
		this.initialize(root);
	}
	
	private void initialize(Node modelRoot) {
		IndexTreeNode root = new IndexTreeNode(modelRoot);
		root.populate();
		this.root = root;
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.tree = new IndexTree();
		this.tree.setModel(model);

		IndexTreeCellRenderer itcl = new IndexTreeCellRenderer();
		this.tree.setCellRenderer(itcl);

		this.tree.setShowsRootHandles(true);

		TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.setSelectionModel(selectionModel);

		this.setLayout(new MigLayout("fill", "0[]0", "0[]0"));
		JScrollPane treeScrollPane = new JScrollPane(tree);
		this.add(treeScrollPane, "cell 0 0, grow");
	}
	
	public void refresh() {
		root.populate();
	}
	
	public IndexTree getTree() {
		return this.tree;
	}
}

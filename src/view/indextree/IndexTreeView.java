package view.indextree;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import controller.indextree.IndexTreeController;
import model.indextree.Node;
import net.miginfocom.swing.MigLayout;

public class IndexTreeView extends JPanel {
	private static final long serialVersionUID = 5555927999642550382L;
	private IndexTreeController controller;
	private IndexTreeNode root;
	private IndexTree tree;

	public IndexTreeView(Node root) {
		this.setController(new IndexTreeController());
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

	public IndexTreeController getController() {
		return controller;
	}

	public void setController(IndexTreeController controller) {
		this.controller = controller;
	}
}

package controller;

import view.tree.TreeView;

public class TreeController {

	private TreeView view;

	public TreeController(TreeView view) {
		this.view = view;
	}

	public void refresh() {
		view.refresh();
	}
}
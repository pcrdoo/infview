package view.tree;

import javax.swing.JTree;

import controller.InfTreeController;

public class InfTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114677039149698971L;
	InfTreeController controller;

	public InfTree() {
		this.controller = new InfTreeController(this);
	}
}

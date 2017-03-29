package view.tree;

import javax.swing.JTree;

import controller.InfTreeController;

public class InfTree extends JTree {
	InfTreeController controller;
	
	public InfTree() {
		this.controller = new InfTreeController(this);
	}
}

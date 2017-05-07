/***********************************************************************
 * Module:  DesktopView.java
 * Author:  Random
 * Purpose: Defines the Class DesktopView
 ***********************************************************************/

package view;

import java.awt.Component;

import javax.swing.JPanel;

import controller.DesktopController;
import model.indextree.Node;
import net.miginfocom.swing.MigLayout;
import view.indextree.IndexTreeView;

public class DesktopView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3341974273545880060L;
	private DesktopController desktopController;
	private TabbedTables mainTable;
	private TabbedTables detailsTable;

	public DesktopView() {
		setDesktopController(new DesktopController());
		this.setLayout(new MigLayout("fill", "5[]5", "5[100, grow 10]5[100, grow 10]5"));
		mainTable = new TabbedTables(true);
		detailsTable = new TabbedTables(false);
		this.add(mainTable, "grow, wrap");
	}

	public TabbedTables getMainTable() {
		return mainTable;
	}

	public TabbedTables getDetailsTable() {
		return detailsTable;
	}

	public void attachDetailsTable() {
		this.add(detailsTable, "grow");
		this.repaint();
	}

	public void detachDetailsTable() {
		for (Component c : this.getComponents()) {
			if (c == detailsTable) {
				this.remove(c);
				this.repaint();
			}
		}
	}

	public void attachIndexTree(Node node) {
		detachIndexTree();
		this.add(new IndexTreeView(node), "grow");
		this.repaint();
	}

	public void detachIndexTree() {
		for (Component c : this.getComponents()) {
			if (c instanceof IndexTreeView) {
				this.remove(c);
				this.repaint();
			}
		}
	}

	public DesktopController getDesktopController() {
		return desktopController;
	}

	public void setDesktopController(DesktopController desktopController) {
		this.desktopController = desktopController;
	}

}
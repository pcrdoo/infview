/***********************************************************************
 * Module:  BottomPanel.java
 * Author:  Random
 * Purpose: Defines the Class BottomPanel
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.BottomPanelController;
import model.Entity;
import model.InfTableModel;

public class BottomPanel extends JPanel {

	// private BottomPanelController bottomPanelController;
	private JTable table;
	private InfTableModel tableModel;
	private JLabel info;

	public BottomPanel() {
		this.setLayout(new BorderLayout());
		info = new JLabel("Ako heder danas ja");
		initTable();
		add(info, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.SOUTH);
	}

	private void initTable() {
		tableModel = new InfTableModel(new Entity());
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		table.setFillsViewportHeight(true);
	}
	
	public JTable getTable() {
		return table;
	}
}
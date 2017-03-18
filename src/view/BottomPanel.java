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

import model.Entity;
import model.InfTableModel;
import model.Warehouse;

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
		Entity mock = Warehouse.getInstance().getPackages().get(0).getEntities().get(0);
		tableModel = new InfTableModel(mock);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		table.setFillsViewportHeight(true);
	}
	
	public JTable getTable() {
		return table;
	}
}
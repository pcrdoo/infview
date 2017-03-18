/***********************************************************************
 * Module:  BottomPanel.java
 * Author:  Random
 * Purpose: Defines the Class BottomPanel
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
	private TablePanel tablePanel;

	public BottomPanel() {
		this.setBackground(Color.BLUE);
		tablePanel = new TablePanel();
		this.add(tablePanel);
	}

}
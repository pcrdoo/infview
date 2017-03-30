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
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import model.Entity;
import model.InfTableModel;
import model.Warehouse;
import net.miginfocom.swing.MigLayout;

public class BottomPanel extends JPanel {

	// private BottomPanelController bottomPanelController;
	private TablePanel tablePanel;

	private JTabbedPane tabs;

	public BottomPanel() {
			this.setLayout(new MigLayout("fill", "0[]0", "0[]0"));
			tabs = new JTabbedPane();
			addTab(Warehouse.getInstance().getPackages().get(0).getEntities().get(0));
			addTab(Warehouse.getInstance().getPackages().get(0).getEntities().get(1));
			this.add(tabs, "grow");
		}

	public void addTab(Entity entity) {
		tabs.addTab(entity.getName(), new TablePanel(entity));
	}

}
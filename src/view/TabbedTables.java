package view;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Entity;
import model.Warehouse;
import net.miginfocom.swing.MigLayout;

public abstract class TabbedTables extends JPanel {

	private TablePanel tablePanel;
	private JTabbedPane tabs;

	public TabbedTables() {
		this.setLayout(new MigLayout("fill", "0[]0", "0[]0"));
		tabs = new JTabbedPane();
		this.add(tabs, "grow");
	}

	public boolean addTab(Entity entity) {
		for (Component c : tabs.getComponents()) {
			if (c instanceof TablePanel) {
				TablePanel panel = (TablePanel) c;
				if (panel.getEntity() == entity) {
					tabs.setSelectedComponent(c);
					return false;
				}
			}
		}
		TablePanel panel = new TablePanel(entity);
		TabComponent tabComponent = new TabComponent(tabs, entity);
		tabs.addTab(entity.getName(), panel);
		tabs.setTabComponentAt(tabs.indexOfComponent(panel), tabComponent);
		tabs.setSelectedComponent(panel);
		return true;
	}

}

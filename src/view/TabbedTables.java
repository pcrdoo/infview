package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import model.Entity;
import model.Warehouse;
import net.miginfocom.swing.MigLayout;

public class TabbedTables extends JPanel {

	private JToolBar toolbar;
	private JTabbedPane tabs; // dodajemo tablePanele
	private boolean autoRefresh;
	
	public TabbedTables(boolean autoRefresh) {
		this.setLayout(new MigLayout("fill", "", "0[]0[grow]0"));
		toolbar = new JToolBar();
		this.add(toolbar, "grow, wrap, height 50px");
		tabs = new JTabbedPane();
		this.add(tabs, "grow, height 250px");
		this.autoRefresh = autoRefresh;
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
		TablePanel panel = new TablePanel(entity, autoRefresh); // 
		TabComponent tabComponent = new TabComponent(tabs, entity);
		tabs.addTab(entity.getName(), panel);
		tabs.setTabComponentAt(tabs.indexOfComponent(panel), tabComponent);
		tabs.setSelectedComponent(panel);
		return true;
	}

}

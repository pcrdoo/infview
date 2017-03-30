/***********************************************************************
 * Module:  TopPanel.java
 * Author:  Random
 * Purpose: Defines the Class TopPanel
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Entity;
import model.Warehouse;
import net.miginfocom.swing.MigLayout;

/** @pdOid d741f619-ce90-417f-8eae-517dd3ebf0c8 */
public class TopPanel extends JPanel {

	// privateTopPanelController topPanelController;
	private TablePanel tablePanel;
	
	private JTabbedPane tabs;
	
	public TopPanel() {
		this.setLayout(new MigLayout("fill", "0[]0", "0[]0"));
		tabs = new JTabbedPane();
		addTab(Warehouse.getInstance().getPackages().get(0).getEntities().get(0));
		// TODO: obrisi mock tab i fiksiraj pozicije tabela, napravi TabbedTable kao klasu za sebe
		this.add(tabs, "grow");
	}

	public boolean addTab(Entity entity) {
		for(Component c : tabs.getComponents()) {
			if(c instanceof TablePanel) {
				TablePanel panel = (TablePanel)c;
				if(panel.getEntity() == entity) {
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
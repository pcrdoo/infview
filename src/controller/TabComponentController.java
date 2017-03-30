package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import model.Entity;
import view.TablePanel;

public class TabComponentController {
	
	private JTabbedPane tabs;
	private Entity entity;

	public TabComponentController(JTabbedPane tabs, Entity entity, JButton button) {
		this.tabs = tabs;
		this.entity = entity;
		button.addActionListener(new TabCloseActionListener());
	}
	
	public class TabCloseActionListener implements ActionListener {

	    public void actionPerformed(ActionEvent evt) {
	    	for(Component c : tabs.getComponents()) {
				if(c instanceof TablePanel) {
					TablePanel panel = (TablePanel)c;
					if(panel.getEntity().equals(entity)) {
						System.out.println(entity);
						tabs.remove(c);
						return;
					}
				}
			}
	    }

	} 
}

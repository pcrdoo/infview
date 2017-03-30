package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.TabComponentController;
import model.Entity;
import net.miginfocom.swing.MigLayout;

public class TabComponent extends JPanel {
	
	private JLabel title;
	private JButton closeButton;
	
	public TabComponent(JTabbedPane tabs, Entity entity) {
		this.setLayout(new MigLayout("fill"));
		title = new JLabel(entity.getName());
		this.add(title, "grow 60");
		closeButton = new JButton("x");
		this.add(closeButton, "grow 40");
		
		TabComponentController controller = new TabComponentController(tabs, entity, closeButton);
		closeButton.addActionListener(new ActionListener() {

		    public void actionPerformed(ActionEvent evt) {
		    	

		        Component selected = tabs.getSelectedComponent();
		        if (selected != null) {

		            tabs.remove(selected);
		            // It would probably be worthwhile getting the source
		            // casting it back to a JButton and removing
		            // the action handler reference ;)

		        }

		    }

		});
	}
	
}

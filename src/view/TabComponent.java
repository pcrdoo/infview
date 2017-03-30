package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import controller.TabComponentController;
import javafx.scene.text.Font;
import model.Entity;
import net.miginfocom.swing.MigLayout;

public class TabComponent extends JPanel {
	
	private JLabel title;
	private JButton closeButton;
	
	public TabComponent(JTabbedPane tabs, Entity entity) {
		this.setLayout(new MigLayout("fill", "0[]0[]0", "0[]0"));
		title = new JLabel(entity.getName());
		this.add(title, "grow 80");
		closeButton = new JButton("x");
		closeButton.setVerticalAlignment(SwingConstants.TOP);
		closeButton.setOpaque(false);
		closeButton.setFocusPainted(false);
		closeButton.setBorderPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder(1,5,1,1)); 
		this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); // Especially important
		this.add(closeButton, "grow 20");
		this.setOpaque(false);
		
		TabComponentController controller = new TabComponentController(tabs, entity, closeButton);
	}
	
}

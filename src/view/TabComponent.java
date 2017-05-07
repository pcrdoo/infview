package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import controller.TabComponentController;
import model.Entity;
import net.miginfocom.swing.MigLayout;

public class TabComponent extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8416684836744867614L;
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
		closeButton.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Especially
																		// important
		this.add(closeButton, "grow 20");
		this.setOpaque(false);
		new TabComponentController(tabs, entity, closeButton);
	}

}

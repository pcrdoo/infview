
package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controller.MenuBarController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MenuBarView extends JMenuBar {

	private MenuBarController menuBarController;
	private JMenu about;
	private JMenuItem aboutItem;

	public MenuBarView() {
		about = new JMenu("About");
		aboutItem = new JMenuItem("About Us");
		about.add(aboutItem);

		this.menuBarController = new MenuBarController(this);
		this.add(about);
	}

	public void addAboutListener(ActionListener l) {
		this.aboutItem.addActionListener(l);
	}
}
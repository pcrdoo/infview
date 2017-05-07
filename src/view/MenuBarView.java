
package view;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.MenuBarController;

public class MenuBarView extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373903714858883170L;
	private MenuBarController menuBarController;
	private JMenu about;
	private JMenuItem aboutItem;

	public MenuBarView() {
		about = new JMenu("About");
		aboutItem = new JMenuItem("About Us");
		about.add(aboutItem);

		this.setMenuBarController(new MenuBarController(this));
		this.add(about);
	}

	public void addAboutListener(ActionListener l) {
		this.aboutItem.addActionListener(l);
	}

	public MenuBarController getMenuBarController() {
		return menuBarController;
	}

	public void setMenuBarController(MenuBarController menuBarController) {
		this.menuBarController = menuBarController;
	}
}
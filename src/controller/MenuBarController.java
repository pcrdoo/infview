
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AboutDialog;
import view.MenuBarView;

public class MenuBarController {
	public MenuBarView menuBarView;

	public MenuBarController(MenuBarView menuBarView) {
		this.menuBarView = menuBarView;
		this.menuBarView.addAboutListener(new AboutListener());
		// TODO Auto-generated constructor stub
	}

	private class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AboutDialog(null).setVisible(true); // Ogijev mozak je
													// deprecated
		}
	}
}
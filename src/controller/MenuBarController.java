
package controller;

import view.AboutDialog;
import view.GenericDialog;
import view.MenuBarView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import model.Entity;
import model.Record;
import model.files.SequentialFile;

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
			new AboutDialog(null).show();
		}
	}
}
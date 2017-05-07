package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import model.Entity;
import model.InfResource;
import view.MainView;
import view.GenericDialog;
import view.tree.InfNode;

public class GenericDialogController {
	
	GenericDialog view;
	
	public GenericDialogController(GenericDialog view) {
		this.view = view;
		view.setOKListener(new OKListener());
	}
	
	class OKListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (view.isSearch() || view.validateRecord()) {
                view.setDoShit(true);
				view.setVisible(false);
			}
		}
	}
}

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GenericDialog;

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

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import view.search.DBSearchDialog;

public class DBSearchDialogController {
	
	private DBSearchDialog view;
	
	public DBSearchDialogController(DBSearchDialog view) {
		this.view = view;
		this.view.setOKListener(new OKListener());
	}
	
	class OKListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//ArrayList<Object> objects = view.getObjects();
			view.setVisible(false);
		}
	}

}

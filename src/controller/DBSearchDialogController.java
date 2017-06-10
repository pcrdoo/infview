package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import view.search.DBGenericDialog;

public class DBSearchDialogController {
	
	private DBGenericDialog view;
	
	public DBSearchDialogController(DBGenericDialog view) {
		this.view = view;
		this.view.setOKListener(new OKListener());
	}
	
	class OKListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//ArrayList<Object> objects = view.getObjects();
			view.setClosed(false);
			view.setVisible(false);
		}
	}

}

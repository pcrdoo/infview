package controller;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Record;
import view.MainView;

public class TableController {

	private JTable table;
	
	public TableController(JTable table) {
		table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		this.table = table;
	}
	
	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting() == true) {
				return;
			}
			Record record = null;
			if(table.getSelectedRow() != -1) {
				record = MainView.getInstance().getDesktopView().getMainTable().getSelectedRow();
			}
			MainView.getInstance().getDesktopView().getDetailsTable().populateWithChildren(record);
		}
		
	}
}

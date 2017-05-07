package controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.InfTableModel;

public class InfTableModelController {

	public InfTableModelController(InfTableModel model) {
		model.addTableModelListener(new TableModelChangedListener());
	}

	private class TableModelChangedListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
		}
	}

}

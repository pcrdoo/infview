package controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class InfTableModelController implements TableModelListener {

	@Override
	public void tableChanged(TableModelEvent e) {
		TableModel model = (TableModel)e.getSource();
		System.out.println("TableChanged");
	}

}

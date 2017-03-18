package model;

import javax.swing.table.AbstractTableModel;

import controller.InfTableModelController;

public class InfTableModel extends AbstractTableModel {
	
	private Entity entity;
	
	public InfTableModel(Entity entity) {
		addTableModelListener(new InfTableModelController());
		this.entity = entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public int getRowCount() {
		return entity.getEntries().size();
	}

	@Override
	public int getColumnCount() {
		return entity.getAttributes().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return "KLIKA";
	}

}

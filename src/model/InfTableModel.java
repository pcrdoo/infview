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
		Entry entry = entity.getEntries().get(rowIndex);
		Attribute attribute = entity.getAttributes().get(columnIndex);
		return entry.getAttributes().get(attribute);
	}
	
	@Override
    public String getColumnName(int column) {
		if(column >= entity.getAttributes().size()) {
			return null;
		}
		return entity.getAttributes().get(column).getName();
    }

    // public int findColumn(String columnName)

	@Override
    public Class<?> getColumnClass(int columnIndex) {
    	return entity.getAttributes().get(columnIndex).getValueClass();
    }

}

package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import controller.InfTableModelController;
import model.files.File;

public class InfTableModel extends AbstractTableModel {

	private Entity entity; // OVO JE FILE

	public InfTableModel(Entity entity) {
		addTableModelListener(new InfTableModelController());
		this.entity = entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public int getRowCount() {
		if (entity instanceof File) {
			return ((File) entity).getCurrentBlock().size();
		}
		return 0; // TODO Baza
	}

	@Override
	public int getColumnCount() {
		return entity.getAttributes().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (entity instanceof File) {
			Record record = ((File) entity).getCurrentBlock().get(rowIndex);
			Attribute attribute = entity.getAttributes().get(columnIndex);
			return record.getAttributes().get(attribute);
		}
		return null; // TODO Baza
	}

	@Override
	public String getColumnName(int column) {
		if (column >= entity.getAttributes().size()) {
			return null;
		}
		return entity.getAttributes().get(column).getName();
	}

	// public int findColumn(String columnName)

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return entity.getAttributes().get(columnIndex).getValueClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (entity instanceof File) {
			Attribute attr = entity.getAttributes().get(col);
			Object obj = ((File) entity).getCurrentBlock().get(row).getAttributes().put(attr, value);
			fireTableCellUpdated(row, col);
		}
		return; // TODO Baza
	}

}

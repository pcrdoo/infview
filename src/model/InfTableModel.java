package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import controller.InfTableModelController;
import model.files.File;

public class InfTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6824400698671124398L;
	private Entity entity; // OVO JE FILE
	private ArrayList<Record> currentBlock;

	public InfTableModel(Entity entity) {
		new InfTableModelController(this);
		this.entity = entity;
		currentBlock = new ArrayList<Record>();
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Record getRecordAt(int rowIndex) {
		if (rowIndex < 0 || rowIndex >= currentBlock.size()) {
			return null;
		} else {
			return currentBlock.get(rowIndex);
		}
	}

	@Override
	public int getRowCount() {
		if (entity instanceof File) {
			return currentBlock.size();
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
			Record record = currentBlock.get(rowIndex);
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
			currentBlock.get(row).getAttributes().put(attr, value);
			fireTableCellUpdated(row, col);
		}
		return; // TODO Baza
	}

	public void setCurrentBlock(ArrayList<Record> currentBlock) {
		this.currentBlock = currentBlock;
	}

}

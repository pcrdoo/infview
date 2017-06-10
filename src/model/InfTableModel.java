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
		return currentBlock.size();
	}

	@Override
	public int getColumnCount() {
		return entity.getAttributes().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Record record = currentBlock.get(rowIndex);
		Attribute attribute = entity.getAttributes().get(columnIndex);
		return record.getAttributes().get(attribute);
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
		Attribute attr = entity.getAttributes().get(col);
		currentBlock.get(row).getAttributes().put(attr, value);
		fireTableCellUpdated(row, col);
	}

	public void setCurrentBlock(ArrayList<Record> currentBlock) {
		System.out.println(currentBlock.size());
		this.currentBlock = currentBlock;
	}

	public int getRecordIndex(Record record) {
		System.out.println("Trazim index");
		System.out.println(record);
		for (int i = 0; i < currentBlock.size(); i++) {
			System.out.println(currentBlock.get(i));
			if (record.equals(currentBlock.get(i))) {
				return i;
			}
		}
		return -1;
	}

}

package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;

public class Table extends Entity {
	
	String lastSelect;
	
	public Table(String name, InfResource parent) {
		super(name, parent);
	}

	private void setByType(PreparedStatement stmt, int idx, Object obj) throws SQLException {
		if (obj instanceof CharType) {
			stmt.setString(idx, ((CharType) obj).get());
		} else if (obj instanceof VarCharType) {
			stmt.setString(idx, ((VarCharType) obj).get());
		} else if (obj instanceof DateType) {
			//stmt.setDate(idx, ((DateType) obj).getDate());
		} else if (obj instanceof Boolean) {
			stmt.setBoolean(idx, (Boolean) obj);
		} else if (obj instanceof Integer) {
			stmt.setInt(idx, (Integer) obj);
		}
		
	}

	public ArrayList<Record> findRecord(String[] terms) {
		// TODO Auto-generated method stub
		return null;
	}

	public void fetchRecords() throws SQLException {
		ArrayList<Record> records = new ArrayList<>();
		StringBuilder stmtBuilder = new StringBuilder();
		stmtBuilder.append("SELECT * FROM ");
		stmtBuilder.append(name);
		System.out.println(stmtBuilder.toString());
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(stmtBuilder.toString());
		ResultSet results = stmt.executeQuery();
		Record record = new Record(this);
		if (results.getMetaData().getColumnCount() != attributes.size()) {
			System.err.println("DB and MS out of sync.");
			return;
		}
		while (results.next()) {
			System.out.println("ROW FOUND");
			for (Attribute attr : attributes) {
				Object value = results.getObject(attr.getName());
				System.out.println(attr.getName() + " " + value);
				record.addAttribute(attr, value);
			}
			records.add(record);
		}
		// obavezno je zatvaranje Statement i ResultSet objekta
		for(Record r:records) System.out.println(r);
		fireUpdateBlockPerformed(records); // ozvezavanje
		results.close();
		stmt.close();

	}

	public void addRecord(Record record) throws SQLException {
		StringBuilder stmtBuilder = new StringBuilder();
		stmtBuilder.append("INSERT INTO ");
		stmtBuilder.append(name);
		stmtBuilder.append(" (");
		for (Attribute attr : attributes) {
			stmtBuilder.append(attr.getName());
			stmtBuilder.append(",");
		}
		stmtBuilder.deleteCharAt(stmtBuilder.length() - 1);
		stmtBuilder.append(") VALUES(");
		for (int i = 0; i < attributes.size(); i++) {
			stmtBuilder.append("?,");
		}
		stmtBuilder.append(")");
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(stmtBuilder.toString());
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			setByType(stmt, i, record.getAttributes().get(attr));
		}
		int updatedRows = stmt.executeUpdate();
		stmt.close();
	}

	public void updateRecord(Record oldRecord, Record newRecord) throws SQLException {
		StringBuilder stmtBuilder = new StringBuilder();
		stmtBuilder.append("UPDATE ");
		stmtBuilder.append(name);
		stmtBuilder.append(" SET ");
		for (Attribute attr : attributes) {
			stmtBuilder.append(attr.getName());
			stmtBuilder.append("=?, ");
		}
		stmtBuilder.delete(stmtBuilder.length() - 2, stmtBuilder.length());
		stmtBuilder.append("WHERE ");
		for (Attribute attr : attributes) {
			if (attr.isPrimaryKey()) {
				stmtBuilder.append(attr.getName());
				stmtBuilder.append("=? AND ");
			}
		}
		stmtBuilder.delete(stmtBuilder.length() - 5, stmtBuilder.length());
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(stmtBuilder.toString());
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			setByType(stmt, i, newRecord.getAttributes().get(attr));
		}
		int idx = attributes.size();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			if (attr.isPrimaryKey()) {
				setByType(stmt, idx++, oldRecord.getAttributes().get(attr));
			}
		}
		int updatedRows = stmt.executeUpdate();
		stmt.close();
	}

	public ArrayList<Record> filterRecords(String sql, ArrayList<Object> items) throws SQLException {
		ArrayList<Record> records = new ArrayList<>();
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(sql);
		for(int i = 0; i < items.size(); i++) {
			setByType(stmt, i, items.get(i));
		}
		ResultSet results = stmt.executeQuery();
		Record record = new Record(this);
		if (results.getMetaData().getColumnCount() != attributes.size()) {
			System.err.println("DB and MS out of sync...");
			return records;
		}
		while (results.next()) {
			for (Attribute attr : attributes) {
				Object value = results.getObject(attr.getName());
				record.addAttribute(attr, value);
			}
			records.add(record);
		}
		// obavezno je zatvaranje Statement i ResultSet objekta
		fireUpdateBlockPerformed(records); // ozvezavanje
		results.close();
		stmt.close();
		return records;
	}

	public ArrayList<Record> findRecordsByFk(HashMap<Attribute, Object> fkMap) throws SQLException {
		StringBuilder stmtBuilder = new StringBuilder();
		stmtBuilder.append("SELECT * from ");
		stmtBuilder.append(name);
		stmtBuilder.append(" WHERE ");
		ArrayList<Object> values = new ArrayList<>();
		int idx = 0;
		for (Entry<Attribute, Object> attr : fkMap.entrySet()) {
			stmtBuilder.append(attr.getKey().getName());
			stmtBuilder.append("=?, ");
			values.add(attr.getValue());
		}
		stmtBuilder.delete(stmtBuilder.length() - 2, stmtBuilder.length());
		stmtBuilder.append("WHERE ");
		for (Attribute attr : attributes) {
			if (attr.isPrimaryKey()) {
				stmtBuilder.append(attr.getName());
				stmtBuilder.append("=? AND ");
			}
		}
		stmtBuilder.delete(stmtBuilder.length() - 5, stmtBuilder.length());
		return filterRecords(stmtBuilder.toString(), values);
	}

}

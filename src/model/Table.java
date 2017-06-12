package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;

public class Table extends Entity {
	
	private FilterParams lastParams;
	
	public Table(String name, InfResource parent) {
		super(name, parent);
	}

	public void setLastParams(FilterParams lastParams) {
		this.lastParams = lastParams;
	}

	private void setByType(PreparedStatement stmt, int idx, Object obj) throws SQLException {
		if (obj instanceof CharType) {
			stmt.setString(idx, ((CharType) obj).get());
		} else if (obj instanceof VarCharType) {
			stmt.setString(idx, ((VarCharType) obj).get());
		} else if (obj instanceof DateType) {
			Date date = ((DateType) obj).getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateAsStringBecauseJavaStandardLibraryIsRetarded = sdf.format(date);
			stmt.setTimestamp(idx, java.sql.Timestamp.valueOf(dateAsStringBecauseJavaStandardLibraryIsRetarded));
		} else if (obj instanceof Boolean) {
			stmt.setBoolean(idx, (Boolean) obj);
		} else if (obj instanceof Integer) {
			stmt.setInt(idx, (Integer) obj);
		} else {
			System.out.println("Pa druskane nemoj tako");
		}
		
	}

	public ArrayList<Record> findRecord(String[] terms) {
		// TODO Auto-generated method stub
		return null;
	}

	public void fetchRecords() throws SQLException, InvalidLengthException {
		ArrayList<Record> records = new ArrayList<>();
		StringBuilder stmtBuilder = new StringBuilder();
		stmtBuilder.append("SELECT * FROM ");
		stmtBuilder.append(name);
		System.out.println(stmtBuilder.toString());
		lastParams = new FilterParams(stmtBuilder.toString(), new ArrayList<Object>());
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(stmtBuilder.toString());
		ResultSet results = stmt.executeQuery();
		
		if (results.getMetaData().getColumnCount() != attributes.size()) {
			System.err.println("DB and MS out of sync.");
			return;
		}
		while (results.next()) {
			Record record = new Record(this);
			
			for (Attribute attr : attributes) {
				Object value = results.getObject(attr.getName());
				System.out.println(attr + " " + value);
				record.addAttribute(attr, Attribute.fromValue(attr, value));
			}
			records.add(record);
		}
		// obavezno je zatvaranje Statement i ResultSet objekta
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
		stmtBuilder.deleteCharAt(stmtBuilder.length() - 1);
		stmtBuilder.append(")");
		System.out.println(stmtBuilder.toString());
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(stmtBuilder.toString());
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			setByType(stmt, i + 1, record.getAttributes().get(attr));
			System.out.println(record.getAttributes().get(attr));
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
		stmtBuilder.append(" WHERE ");
		for (Attribute attr : attributes) {
			if (attr.isPrimaryKey()) {
				stmtBuilder.append(attr.getName());
				stmtBuilder.append("=? AND ");
			}
		}
		stmtBuilder.delete(stmtBuilder.length() - 5, stmtBuilder.length());
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(stmtBuilder.toString());
		System.out.println("Query");
		System.out.println(stmtBuilder.toString());
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			setByType(stmt, i + 1, newRecord.getAttributes().get(attr));
		}
		int idx = attributes.size() + 1;
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			if (attr.isPrimaryKey()) {
				setByType(stmt, idx++, oldRecord.getAttributes().get(attr));
			}
		}
		int updatedRows = stmt.executeUpdate();
		stmt.close();
	}

	public ArrayList<Record> filterRecords(FilterParams filterParams, String orderBy) throws SQLException, InvalidLengthException {
		ArrayList<Record> records = new ArrayList<>();
		//System.out.println(sql);
		String fullQuery = filterParams.getQuery() + " " + orderBy;
		System.out.println(fullQuery);
		PreparedStatement stmt = Warehouse.getInstance().getDbConnection().prepareStatement(fullQuery);
		//System.out.println(stmt.getParameterMetaData().getParameterCount());
		for(int i = 0; i < filterParams.getObjects().size(); i++) {
			setByType(stmt, i+1, filterParams.getObjects().get(i));
		}
		ResultSet results = stmt.executeQuery();
		if (results.getMetaData().getColumnCount() != attributes.size()) {
			System.err.println("DB and MS out of sync...");
			return records;
		}
		while (results.next()) {
			Record record = new Record(this);
			for (Attribute attr : attributes) {
				Object value = results.getObject(attr.getName());
				record.addAttribute(attr, Attribute.fromValue(attr, value));
			}
			records.add(record);
		}
		// obavezno je zatvaranje Statement i ResultSet objekta
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
			stmtBuilder.append("= ? , ");
			values.add(attr.getValue());
		}
		stmtBuilder.delete(stmtBuilder.length() - 2, stmtBuilder.length());
		try {
			return filterRecords(new FilterParams(stmtBuilder.toString(), values), "");
		} catch (InvalidLengthException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid length error: " + e.getMessage());
			return new ArrayList<Record>();
		}
	}

	public void sortRecords(String orderBy) throws SQLException {
		System.out.println(orderBy + " " + lastParams);
		if(lastParams == null) {
			return;
		}
		try {
			ArrayList<Record> records = filterRecords(lastParams, orderBy);
			System.out.println(records.size() + "vrlo bitno");
			fireUpdateBlockPerformed(records); // ozvezavanje
		} catch (InvalidLengthException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid length error: " + e.getMessage());
		}
	}

	public FilterParams getLastParams() {
		return lastParams;
	}
	
	

}

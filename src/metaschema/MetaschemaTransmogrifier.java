package metaschema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetaschemaTransmogrifier {
	StringBuilder sb;
	void sobj() {
		sb.append("{");
	}
	void eobj() {
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
	}
	void sarr() {
		sb.append("[");
	}
	void earr() {
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
	}
	void str(String s) {
		String s2 = s.replace("\"", "\\\"");
		s2 = s2.replace("'", "\\'");
		sb.append('"');
		sb.append(s2);
		sb.append('"');
	}
	void num(int n) {
		sb.append(Integer.toString(n));
	}
	void key(String k) {
		sb.append(k);
		sb.append(':');
	}
	void kvstr(String k, String v) {
		sb.append(k);
		sb.append(':');
		str(v);
		sb.append(',');
	}
	void kvnum(String k, int v) {
		sb.append(k);
		sb.append(':');
		num(v);
		sb.append(',');
	}
	void aestr(String s) {
		str(s);
		sb.append(',');
	}
	void aenum(int n) {
		num(n);
		sb.append(',');
	}
	void delim() {
		sb.append(',');
	}
	
	public String transmogrify(Connection db) throws SQLException {
		DatabaseMetaData mtdt = db.getMetaData();
		
		sb = new StringBuilder();
	
		ResultSet tbls = mtdt.getTables(null, null, null, new String[]{"TABLE"});
		sobj(); {
			kvstr("location", "nzm");
			kvstr("name", "nzm");
			kvstr("description", "nzm tbr!");
			kvstr("type", "mssql");
			key("packages"); {
				sarr(); { delim(); } earr();
			} delim();
			key("entities"); {
				sarr(); {
					while (tbls.next()) {
						String tableName = tbls.getString(tbls.findColumn("TABLE_NAME"));
						System.out.println("glory. i found ur table " + tableName + ". i will now to make entity from glory table " + tableName + " thanks raheed");
						
						delim();
					}
				} earr();
			} delim();
		} eobj();
		return sb.toString();
	}
}

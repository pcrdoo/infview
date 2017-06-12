package model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;

public class Attribute extends InfResource {
	Class<?> valueClass;
	int length;
	boolean primaryKey;
	boolean mandatory;
	
	public Attribute(String name, InfResource parent, Class<?> valueClass, int length, boolean primaryKey, boolean mandatory) {
		super(name, parent);
		this.name = name;
		this.valueClass = valueClass;
		this.length = length;
		this.primaryKey = primaryKey;
		this.mandatory = mandatory;
	}

	public String getFullyQualifiedName() {
		return parent.getName() + "/" + name;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	@Override
	public String toIndentedString(int indentSpaces) {
		return indentStringRepresentation(
				String.format("Attribute \"%s\" {\n" + "    valueClass = \"%s\"\n" + "    length = %d\n" + "    primaryKey = %s\n" + "    mandatory = %s\n" +  "}", name, valueClass.toString(), length, primaryKey ? "yes" : "no", mandatory ? "yes" : "no"),
				indentSpaces);
	}

	public int getLength() {
		return length;
	}

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}
		
	public boolean isMandatory() {
		return mandatory;
	}
	
	public static Object fromValue(Attribute a, Object value) throws InvalidLengthException {
		if(value == null) {
			return null;
		}
		if (a.valueClass == VarCharType.class || a.valueClass == CharType.class) {
			String s = (String)value;
			if (a.valueClass == VarCharType.class) {
				VarCharType varchar = new VarCharType(s.length());
				varchar.set(s);
				
				return varchar;
			} else {
				CharType ch = new CharType(s.length());
				ch.set(s);
				
				return ch;
			}
		} else if (a.valueClass == Integer.class) {
			System.out.println(value);
			Integer i;
			if (value instanceof java.math.BigDecimal) {
				i = ((BigDecimal)value).intValue();
			} else {
				i = (Integer)value;
			}
			return i;
		} else if (a.valueClass == DateType.class) {
			java.util.Date d;
			if (value instanceof java.sql.Timestamp) {
				java.sql.Timestamp ts = (java.sql.Timestamp)value;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String dateAsStringBecauseJavaStandardLibraryIsRetarded = sdf.format(ts);
				try {
					d = sdf.parse(dateAsStringBecauseJavaStandardLibraryIsRetarded);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			} else {
				d = (java.sql.Date)value;
			}
			
			return new DateType(d);
		} else if (a.valueClass == Boolean.class) {
			Boolean b = (Boolean)value;		
			return b;
		} else {
			throw new IllegalArgumentException("Invalid data type");
		}
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}
package model;

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
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}
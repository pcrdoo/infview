package model;

public class Attribute extends InfResource {
	Class<?> valueClass;
	int length;
	boolean primaryKey;

	public Attribute(String name, InfResource parent, Class<?> valueClass, int length, boolean primaryKey) {
		super(name, parent);
		this.name = name;
		this.valueClass = valueClass;
		this.length = length;
		this.primaryKey = primaryKey;
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
				String.format("Attribute \"%s\" {\n" + "    valueClass = \"%s\"\n" + "}", name, valueClass.toString()),
				indentSpaces);
	}

	public int getLength() {
		return length;
	}

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}
}
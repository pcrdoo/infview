package model;

import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;

public class Attribute extends InfResource {
	Class<?> valueClass;
	int length;
	
	public Attribute(String name, InfResource parent, Class<?> valueClass, int length) {
		super(name, parent);
		this.name = name;
		this.valueClass = valueClass;
		this.length = length;
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
		return indentStringRepresentation(String.format(
			"Attribute \"%s\" {\n" +
			"    valueClass = \"%s\"\n" +
			"}", name, valueClass.toString()), indentSpaces);
	}
}
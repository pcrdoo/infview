package model;

import java.util.List;

public abstract class InfResource {
	
	protected String name;
	protected InfResource parent;
	
	public InfResource(String name, InfResource parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public List<? extends InfResource> getChildren() {
		System.err.println("OgiException: INFRESOURCE HAS NO CHILDREN!");
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public InfResource getParent() {
		return parent;
	}

	public String indentStringRepresentation(String s, int indentSpaces) {
		String[] lines = s.split("\n");
		String indentString = "";
		for (int i = 0; i < indentSpaces; i++) {
			indentString += " ";
		}
		for (int i = 0; i < lines.length; i++) {
			lines[i] = indentString + lines[i];
		}
		
		return String.join("\n", lines);
	}
	
	public abstract String toIndentedString(int indentSpaces);
	
	@Override
	public String toString() {
		return toIndentedString(0);
	}
}
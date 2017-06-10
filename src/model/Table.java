package model;

public class Table extends Entity {
	boolean mandatory;
	
	public Table(String name, InfResource parent) {
		super(name, parent);
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}

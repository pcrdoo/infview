package model;

public class VarCharType {
	private String string;
	private int length;
	
	public VarCharType(int length) {
		this.length = length;
	}
	
	public String get() {
		return string;
	}
	
	public void set(String s) throws InvalidLengthException {
		if (s.length() > length) {
			throw new InvalidLengthException("String of length " + s.length() + " cannot be fit in a varchar(" + length + ")");
		}
		
		this.string = s;
	}
}

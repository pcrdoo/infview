package model;

public class CharType {
	private String string;
	private int length;
	
	public CharType(int length) {
		this.length = length;
	}
	
	public String get() {
		return string;
	}
	
	public void set(String s) throws InvalidLengthException {
		if (s.length() != length) {
			throw new InvalidLengthException("String of length " + s.length() + " cannot be assigned to char(" + length + ")");
		}
		
		this.string = s;
	}
	
	@Override
	public String toString() {
		return string;
	}
}

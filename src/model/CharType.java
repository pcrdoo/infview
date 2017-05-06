package model;

public class CharType {
	String string;
	int length;
	
	CharType(int length) {
		this.length = length;
	}
	
	String get() {
		return string;
	}
	
	void set(String s) throws InvalidLengthException {
		if (s.length() != length) {
			throw new InvalidLengthException("String of length " + s.length() + " cannot be assigned to char(" + length + ")");
		}
		
		this.string = s;
	}
}

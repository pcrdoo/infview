package model;

public class VarCharType {
	String string;
	int length;
	
	VarCharType(int length) {
		this.length = length;
	}
	
	String get() {
		return string;
	}
	
	void set(String s) throws InvalidLengthException {
		if (s.length() > length) {
			throw new InvalidLengthException("String of length " + s.length() + " cannot be fit in a varchar(" + length + ")");
		}
		
		this.string = s;
	}
}

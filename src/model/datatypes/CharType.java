package model.datatypes;

import model.InvalidLengthException;
import java.io.Serializable;

public class CharType implements Comparable<CharType>, Serializable {
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
	
	@Override
	public int compareTo(CharType o) {
		return string.compareTo(o.string);
	}
}

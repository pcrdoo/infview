package model.datatypes;

import java.io.Serializable;

import model.InvalidLengthException;

public class CharType implements Comparable<CharType>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1493345093377440628L;
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
			throw new InvalidLengthException(
					"String of length " + s.length() + " cannot be assigned to char(" + length + ")");
		}

		this.string = s;
	}

	public String getString() {
		return string;
	}
	@Override
	public String toString() {
		return string;
	}

	@Override
	public int compareTo(CharType o) {
		return string.compareTo(o.string);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof CharType)) {
			return false;
		}
		CharType ch = (CharType)obj;
		return this.string.equals(ch.getString());
	}
}

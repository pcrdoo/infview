package model.datatypes;

import java.io.Serializable;

import model.InvalidLengthException;

public class VarCharType implements Comparable<VarCharType>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4381437562578711391L;
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
			throw new InvalidLengthException(
					"String of length " + s.length() + " cannot be fit in a varchar(" + length + ")");
		}

		this.string = s.trim();
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public int compareTo(VarCharType o) {
		return string.compareTo(o.string);
	}

}

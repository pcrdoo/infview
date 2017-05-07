package model.indextree;

import java.io.Serializable;

public class KeyElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7043357217161726484L;
	public Class<?> type;
	public Object value;

	public KeyElement(Class<?> type, Object value) {
		this.type = type;
		this.value = value;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}
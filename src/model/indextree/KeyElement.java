package model.indextree;

public class KeyElement {
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
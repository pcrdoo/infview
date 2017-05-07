package model.indextree;
import java.io.Serializable;

public class KeyElement implements Serializable {
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
package model.files;

public class InvalidRecordException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2256997517646362329L;

	public InvalidRecordException(String type, String field) {
		super("Invalid field. \"" + field + "\" is not an " + type);
	}

	public InvalidRecordException(String type, String field, String line) {
		super("Invalid field. \"" + field + "\" is not an " + type + " on line \"" + line + "\"");
	}
}

package model.files;

public class InvalidRecordException extends Exception {
	public InvalidRecordException(String type, String field) {
		super("Invalid field. \"" + field + "\" is not an " + type);
	}
	
	public InvalidRecordException(String type, String field, String line) {
		super("Invalid field. \"" + field + "\" is not an " + type + " on line \"" + line + "\"");
	}
}

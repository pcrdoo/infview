package model.files;

public class InvalidRecordException extends Exception {
	public InvalidRecordException(String type, String field) {
		super("Invalid field. [type=\"" + type + "\"] [field=\"" + field + "\"");
	}
}

package model.datatypes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateType implements Comparable<DateType>, Serializable {
	private Date date;
	private SimpleDateFormat sdf;

	public DateType() {
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	public DateType(Date date) {
		this();
		this.date = date;
	}
	
	public DateType(String dateString) throws ParseException {
		this();
		this.date = sdf.parse(dateString);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return sdf.format(date);
	}

	@Override
	public int compareTo(DateType o) {
		return date.compareTo(o.date);
	}
}

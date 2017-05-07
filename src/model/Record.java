package model;

import java.util.LinkedHashMap;
import java.util.Map;

import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
import model.files.File;
import model.files.InvalidRecordException;

public class Record implements Comparable<Record> {
	Entity entity;
	Map<Attribute, Object> attributes;

	public Record(Entity entity) {
		this.entity = entity;
		attributes = new LinkedHashMap<>();
	}

	public Entity getEntity() {
		return entity;
	}

	public void addAttribute(Attribute attribute, Object value) {
		if (!entity.getAttributes().contains(attribute))
			return;
		if (attribute.getValueClass() != value.getClass()) {
			return;
		}
		attributes.put(attribute, value);
	}

	public Map<Attribute, Object> getAttributes() {
		return attributes;
	}

	public boolean matches(String[] terms) {
		boolean result = true;
		for (int i = 0; i < this.entity.getAttributes().size(); i++) {
			result &= terms[i].equals("")
					|| terms[i].equals((this.attributes.get(this.entity.getAttributes().get(i))).toString().trim());
		}
		return result;
	}

	public boolean greaterThan(String[] terms) {
		for (int i = 0; i < this.entity.getAttributes().size(); i++) {
			if (!this.entity.getAttributes().get(i).isPrimaryKey() && !terms[i].equals(""))
				return false;
		}

		for (int i = 0; i < this.attributes.size(); i++) {
			if (terms[i].equals("")) {
				return false;
			}
			try {
				Object o = File.parseStringField(terms[i], this.entity.getAttributes().get(i), String.join("", terms));
				int result;
				if (o instanceof Boolean) {
					result = ((Boolean) o).compareTo((Boolean) this.attributes.get(this.entity.getAttributes().get(i)));
				} else if (o instanceof CharType) {
					result = ((CharType) o)
							.compareTo((CharType) this.attributes.get(this.entity.getAttributes().get(i)));
				} else if (o instanceof VarCharType) {
					result = ((VarCharType) o)
							.compareTo((VarCharType) this.attributes.get(this.entity.getAttributes().get(i)));
				} else if (o instanceof Integer) {
					result = ((Integer) o).compareTo((Integer) this.attributes.get(this.entity.getAttributes().get(i)));
				} else if (o instanceof DateType) {
					result = ((DateType) o)
							.compareTo((DateType) this.attributes.get(this.entity.getAttributes().get(i)));
				} else {
					throw new Exception("Alo druskane pa taj tip nije podrzan.");
				}
				if (result == 1) {
					return false;
				} else if (result == -1) {
					return true;
				}
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int resultLength = 0;
		for (Attribute a : this.entity.getAttributes()) {
			resultLength += a.getLength();
			result.append(this.attributes.get(a));
			while (result.length() < resultLength) {
				result.append(" ");
			}
		}
		return result.toString();
	}

	public static Record fromTerms(String[] terms, Entity entity) throws InvalidRecordException {
		Record r = new Record(entity);
		for (int i = 0; i < entity.getAttributes().size(); i++) {
			Object o = File.parseStringField(terms[i], entity.getAttributes().get(i), String.join("", terms));
			r.attributes.put(entity.getAttributes().get(i), o);
		}
		return r;
	}

	public int compareTo(Record other) {
		if (entity != other.entity) {
			return -1;
		}

		for (Attribute a : entity.getAttributes()) {
			if (a.isPrimaryKey()) {
				int result = 0;
				if (a.getValueClass() == CharType.class) {
					result = ((CharType) attributes.get(a)).compareTo((CharType) other.getAttributes().get(a));
				} else if (a.getValueClass() == VarCharType.class) {
					result = ((VarCharType) attributes.get(a)).compareTo((VarCharType) other.getAttributes().get(a));
				} else if (a.getValueClass() == DateType.class) {
					result = ((DateType) attributes.get(a)).compareTo((DateType) other.getAttributes().get(a));
				} else if (a.getValueClass() == Integer.class) {
					result = ((Integer) attributes.get(a)).compareTo((Integer) other.getAttributes().get(a));
				}

				// Lexicographical comparison
				if (result != 0) {
					return result;
				}
			}
		}

		return 0;
	}
}

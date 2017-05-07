package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
import model.files.File;
import model.files.InvalidRecordException;

public class Record {
	Entity entity;
	Map<Attribute, Object> attributes;
	Map<Relation, Record> relations;

	public Record(Entity entity) {
		this.entity = entity;
		attributes = new LinkedHashMap<>();
		relations = new LinkedHashMap<>();
	}

	public void addAttribute(Attribute attribute, Object value) {
		if (!entity.getAttributes().contains(attribute))
			return;
		if (attribute.getValueClass() != value.getClass()) {
			return;
		}
		attributes.put(attribute, value);
	}

	public void addRelation(Relation relation, Record record) {
		if (!entity.getRelations().contains(relation))
			return;

		// A relation to a given entry cannot be added unless the referenced
		// attribute
		// belongs to the same entity as the entry
		if (relation.getReferencedAttribute().getParent() != record.getEntity()) {
			return; // TODO: Exception
		}
		relations.put(relation, record);
	}

	public Map<Attribute, Object> getAttributes() {
		return attributes;
	}

	public Map<Relation, Record> getRelations() {
		return relations;
	}

	private Entity getEntity() {
		return entity;
	}

	public boolean matches(String[] terms) {
		boolean result = true;
		for(int i = 0; i < this.entity.getAttributes().size(); i++) {
			result &= terms[i].equals("") || terms[i].equals((this.attributes.get(this.entity.getAttributes().get(i))).toString().trim());
		}
		return result;
	}

	public boolean greaterThan(String[] terms) {
		for(int i = 0; i < this.entity.getAttributes().size(); i++) {
			if(!this.entity.getAttributes().get(i).isPrimaryKey() && !terms[i].equals(""))
				return false;
		}
		
		for(int i = 0; i < this.attributes.size(); i++) {
			//System.out.println((this.attributes.get(this.entity.getAttributes().get(i))).toString().trim());
			if(terms[i].equals("")) {
				return false;
			}
			try {
				//System.out.println("SALJEM " + terms[i]);
				Object o = File.parseStringField(terms[i], this.entity.getAttributes().get(i));
				int result;
				if(o instanceof Boolean) {
					result = ((Boolean)o).compareTo((Boolean)this.attributes.get(this.entity.getAttributes().get(i)));
				} else if(o instanceof CharType) {
					result = ((CharType)o).compareTo((CharType)this.attributes.get(this.entity.getAttributes().get(i)));
				} else if(o instanceof VarCharType) {
					result = ((VarCharType)o).compareTo((VarCharType)this.attributes.get(this.entity.getAttributes().get(i)));
				} else if(o instanceof Integer) {
					result = ((Integer)o).compareTo((Integer)this.attributes.get(this.entity.getAttributes().get(i)));
				} else if(o instanceof DateType) {
					result = ((DateType)o).compareTo((DateType)this.attributes.get(this.entity.getAttributes().get(i)));
				} else {
					throw new Exception("Alo druskane pa taj tip nije podrzan.");
				}
//				System.out.println(i);
//				System.out.println(this.attributes.get(this.entity.getAttributes().get(i)).getClass());
//				System.out.println(this.attributes.get(this.entity.getAttributes().get(i)));
//				System.out.println(terms[i]);
				if (result == 1) {
					return false;
				} else if (result == -1) {
					return true;
				}
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
//			else if(terms[i].compareToIgnoreCase((this.attributes.get(this.entity.getAttributes().get(i))).toString().trim()) == 1) {
//				return false;
//			} else if(terms[i].compareToIgnoreCase((this.attributes.get(this.entity.getAttributes().get(i))).toString().trim()) == -1) {
//				System.out.println("STAO");
//				return true;
//			}
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

	public static Record fromTerms(String[] terms, Entity entity) {
		Record r = new Record(entity);
		for (int i = 0; i < entity.getAttributes().size(); i++) {
			try {
				Object o = File.parseStringField(terms[i], entity.getAttributes().get(i));
				r.attributes.put(entity.getAttributes().get(i), o);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return r;
	}
}

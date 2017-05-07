package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

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
			result &= !terms[i].equals("") && terms[i].equals(((String)this.attributes.get(this.entity.getAttributes().get(i))));
		}
		return result;
	}

	public boolean greaterThan(String[] terms) {
		for(int i = 0; i < this.entity.getAttributes().size(); i++) {
			if(this.entity.getAttributes().get(i).isPrimaryKey() && !terms[i].equals(""))
				return false;
		}
		
		for(int i = 0; i < this.attributes.size(); i++) {
			if(terms[i].equals("")) {
				return false;
			} else if(terms[i].compareToIgnoreCase(((String)this.attributes.get(this.entity.getAttributes().get(i)))) == 1) {
				return false;
			} else if(terms[i].compareToIgnoreCase(((String)this.attributes.get(this.entity.getAttributes().get(i)))) == -1) {
				return true;
			}
		}
		return false;
	}
}

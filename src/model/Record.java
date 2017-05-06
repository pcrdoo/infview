package model;

import java.util.HashMap;

public class Record {
	Entity entity;
	HashMap<Attribute, Object> attributes;
	HashMap<Relation, Record> relations;

	public Record(Entity entity) {
		this.entity = entity;
		attributes = new HashMap<>();
		relations = new HashMap<>();
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

	public HashMap<Attribute, Object> getAttributes() {
		return attributes;
	}

	public HashMap<Relation, Record> getRelations() {
		return relations;
	}

	private Entity getEntity() {
		return entity;
	}

}

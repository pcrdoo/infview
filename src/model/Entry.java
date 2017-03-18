package model;

import java.util.HashMap;

public class Entry {
	Entity entity;
	HashMap<Attribute, Object> attributes;
	HashMap<Relation, Entry> relations;
	public Entry(Entity entity) {
		this.entity = entity;
		attributes = new HashMap<>();
		relations = new HashMap<>();
	}
	

	public void addAttribute(Attribute attribute, Object value) {
		if(!entity.getAttributes().contains(attribute))
			return;
		if(attribute.getValueClass() != value.getClass()) {
			return;
		}
		attributes.put(attribute, value);
	}
	
	public void addRelation(Relation relation, Entry entry) {
		if(!entity.getRelations().contains(relation))
			return;
		if(relation.getObjectEntity() != entry.getEntity()) {
			return;
		}
		relations.put(relation, entry);
	}


	private Entity getEntity() {
		return entity;
	}
	
}

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
		
		// A relation to a given entry cannot be added unless the referenced attribute
		// belongs to the same entity as the entry
		if(relation.getReferencedAttribute().getParent() != entry.getEntity()) {
			return; // TODO: Exception
		}
		relations.put(relation, entry);
	}


	public HashMap<Attribute, Object> getAttributes() {
		return attributes;
	}


	public HashMap<Relation, Entry> getRelations() {
		return relations;
	}


	private Entity getEntity() {
		return entity;
	}
	
}

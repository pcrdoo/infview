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
}

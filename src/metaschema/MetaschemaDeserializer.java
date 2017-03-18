package metaschema;

import com.google.gson.JsonParser;

import model.Entity;
import model.InfResource;
import model.Warehouse;
import model.Package;
import model.Attribute;
import model.Relation;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MetaschemaDeserializer {
	public void deserializeToInfResource(JsonObject infResourceJson, InfResource destination) {
		destination.setName(infResourceJson.get("name").getAsString());
	}
	
	public void deserializeRelationsToEntity(JsonArray relationsJson, Entity source, HashMap<String, Entity> entities) throws MetaschemaDeserializationException {
		HashSet<String> relationNames = new HashSet<>();
		for (JsonElement relationElement : relationsJson) {
			JsonObject relation = relationElement.getAsJsonObject();
			String relatedEntityName = relation.get("objectEntity").getAsString();
			String sourceEntityName = source.getName();
			if (!entities.containsKey(relatedEntityName)) {
				throw new MetaschemaDeserializationException("Entity '" + sourceEntityName + "' has a relation to unknown entity '" + relatedEntityName + "'");
			}
			
			Entity related = entities.get(relatedEntityName);
			Relation r = new Relation("", related);
			deserializeToInfResource(relation, r);
			
			if (relationNames.contains(r.getName())) {
				throw new MetaschemaDeserializationException("Duplicate relation with name '" + r.getName() + "'");
			}
			
			relationNames.add(r.getName());
			source.addRelation(r);
		}
	}
	
	public Attribute deserializeAttribute(JsonObject attributeJson, HashMap<String, Attribute> attributes) throws MetaschemaDeserializationException {
		String type = attributeJson.get("type").getAsString();
		Class<?> clazz = null;
		switch (type) {
		case "String": clazz = String.class; break;
		case "Int": clazz = Integer.class; break;
		case "DateTime": clazz = Date.class; break;
		case "Boolean": clazz = Boolean.class; break;
		default: throw new MetaschemaDeserializationException("Unknown type '" + type + "'");
		}
		
		Attribute a = new Attribute("", clazz);
		deserializeToInfResource(attributeJson, a);
		
		if (attributes.containsKey(a.getName())) {
			throw new MetaschemaDeserializationException("Duplicate attribute with name '" + a.getName() + "'");
		}
		
		attributes.put(a.getName(), a);
		return a;
	}
	
	public Entity deserializeEntity(JsonObject entityJson, HashMap<String, Entity> entities, HashMap<String, JsonArray> relationsJsons) throws MetaschemaDeserializationException {
		Entity e = new Entity("");
		deserializeToInfResource(entityJson, e);
		String name = e.getName();
		
		if (entities.containsKey(name)) {
			throw new MetaschemaDeserializationException("Duplicate entity with name '" + name + "'");
		}
		
		JsonArray attributesJson = entityJson.getAsJsonArray("attributes");
		HashMap<String, Attribute> attributes = new HashMap<>();
		for (JsonElement attributeJson : attributesJson) {
			e.addAttribute(deserializeAttribute(attributeJson.getAsJsonObject(), attributes));
		}
		
		entities.put(name, e);
		relationsJsons.put(name, entityJson.getAsJsonArray("relations"));
		
		return e;
	}

	public Package deserializePackage(JsonObject packageJson, HashMap<String, Package> packages) throws MetaschemaDeserializationException {
		Package p = new Package("");
		deserializeToInfResource(packageJson, p);
		String name = p.getName();
		
		if (packages.containsKey(name)) {
			throw new MetaschemaDeserializationException("Duplicate package with name '" + name + "'");
		}
		
		HashMap<String, Entity> entities = new HashMap<>();
		HashMap<String, JsonArray> relationsJsons = new HashMap<>();
		
		JsonArray entitiesJson = packageJson.getAsJsonArray("entities");
		for (JsonElement entityJson : entitiesJson) {
			p.addEntity(deserializeEntity(entityJson.getAsJsonObject(), entities, relationsJsons));
		}
		
		for (HashMap.Entry<String, JsonArray> relationsJsonEntry : relationsJsons.entrySet()) {
			deserializeRelationsToEntity(relationsJsonEntry.getValue(), entities.get(relationsJsonEntry.getKey()), entities);
		}
		
		packages.put(name, p);
		return p;
	}

	public void deserialize(String json, Warehouse destination) throws MetaschemaDeserializationException {
		JsonParser parser = new JsonParser();
		JsonObject o = parser.parse(json).getAsJsonObject();

		deserializeToInfResource(o, destination);
		destination.setDescription(o.get("description").getAsString());
		JsonArray packagesJson = o.getAsJsonArray("packages");
		
		HashMap<String, Package> packages = new HashMap<>();
		for (final JsonElement packageJson : packagesJson) {
			destination.addPackage(deserializePackage(packageJson.getAsJsonObject(), packages));
		}
	}
}

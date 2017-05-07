package metaschema;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Attribute;
import model.CharType;
import model.Entity;
import model.InfResource;
import model.Package;
import model.Relation;
import model.VarCharType;
import model.Warehouse;
import model.files.IndexedSequentialFile;
import model.files.SequentialFile;
import model.files.SerialFile;

public class MetaschemaDeserializer {
	public void deserializeToInfResource(JsonObject infResourceJson, InfResource destination) {
		destination.setName(infResourceJson.get("name").getAsString());
	}

	public void deserializeRelationsToEntity(JsonArray relationsJson, Entity source, HashMap<String, Entity> entities)
			throws MetaschemaDeserializationException {
		HashSet<String> relationNames = new HashSet<>();
		for (JsonElement relationElement : relationsJson) {
			JsonObject relation = relationElement.getAsJsonObject();
			String[] referencedAttributeNamePieces = relation.get("referencedAttribute").getAsString().split("/");
			if (referencedAttributeNamePieces.length != 2) {
				throw new MetaschemaDeserializationException(
						"Referenced attribute '" + String.join("/", referencedAttributeNamePieces)
								+ "' is not valid (expected format is 'EntityName/AttributeName')");
			}

			String referringAttributeName = relation.get("referringAttribute").getAsString();
			Attribute referringAttribute = source.findAttributeByName(referringAttributeName);

			String fqn = source.getName() + "/" + referringAttributeName;
			if (referringAttribute == null) {
				throw new MetaschemaDeserializationException("Referring attribute '" + fqn + "' does not exist");
			}

			if (!entities.containsKey(referencedAttributeNamePieces[0])) {
				throw new MetaschemaDeserializationException("Attribute '" + fqn
						+ "' has a relation to unknown entity '" + referencedAttributeNamePieces[0] + "'");
			}

			Entity referenced = entities.get(referencedAttributeNamePieces[0]);
			Attribute referencedAttribute = referenced.findAttributeByName(referencedAttributeNamePieces[1]);
			if (referencedAttribute == null) {
				throw new MetaschemaDeserializationException("Referenced attribute '"
						+ String.join("/", referencedAttributeNamePieces) + "' does not exist");
			}

			Relation r = new Relation(referringAttribute, referencedAttribute, source);

			if (relationNames.contains(r.getName())) {
				throw new MetaschemaDeserializationException("Duplicate relation with name '" + r.getName() + "'");
			}

			relationNames.add(r.getName());
			source.addRelation(r);
		}
	}

	public Attribute deserializeAttribute(JsonObject attributeJson, HashMap<String, Attribute> attributes,
			Entity parent) throws MetaschemaDeserializationException {
		String type = attributeJson.get("type").getAsString();
		int length = attributeJson.get("length").getAsInt();
		boolean primaryKey = attributeJson.get("primaryKey").getAsBoolean();
		Class<?> clazz = null;
		switch (type) {
		case "char":
			clazz = CharType.class;
			break;
		case "varchar":
			clazz = VarCharType.class;
			break;
		case "datetime":
			clazz = Date.class;
			break;
		case "boolean":
			clazz = Boolean.class;
			break;
		case "numeric":
			clazz = Integer.class;
			break;
		default:
			throw new MetaschemaDeserializationException("Unknown type '" + type + "'");
		}
		Attribute a = new Attribute("", parent, clazz, length, primaryKey);
		deserializeToInfResource(attributeJson, a);

		if (attributes.containsKey(a.getName())) {
			throw new MetaschemaDeserializationException("Duplicate attribute with name '" + a.getName() + "'");
		}

		attributes.put(a.getName(), a);
		return a;
	}

	public Entity deserializeEntity(JsonObject entityJson, HashMap<String, Entity> entities, HashMap<String, JsonArray> relationsJsons, Package parent) throws MetaschemaDeserializationException {
		String type = entityJson.get("type").getAsString();
		Entity e;
		switch(type) {
		case "serial":
			e = new SerialFile("", entityJson.get("url").getAsString(), parent);
			break;
		case "sequential":
			e = new SequentialFile("", entityJson.get("url").getAsString(), parent);
			break;
		case "indexedSequential":
			e = new IndexedSequentialFile("", entityJson.get("url").getAsString(), parent);
			break;
		default:
			throw new MetaschemaDeserializationException("Unkown entity type: " + type);
		}
		
		deserializeToInfResource(entityJson, e);
		
		String name = e.getName();
		if (entities.containsKey(name)) {
			throw new MetaschemaDeserializationException("Duplicate entity with name '" + name + "'");
		}
		
		JsonArray attributesJson = entityJson.getAsJsonArray("attributes");
		HashMap<String, Attribute> attributes = new HashMap<>();
		for (JsonElement attributeJson : attributesJson) {
			e.addAttribute(deserializeAttribute(attributeJson.getAsJsonObject(), attributes, e));
		}
		
		entities.put(name, e);
		relationsJsons.put(name, entityJson.getAsJsonArray("relations"));
		
		return e;
	}

	public Package deserializePackage(JsonObject packageJson, HashMap<String, Package> packages, InfResource parent)
			throws MetaschemaDeserializationException {
		Package p = new Package("", parent);
		deserializeToInfResource(packageJson, p);
		String name = p.getName();

		if (packages.containsKey(name)) {
			throw new MetaschemaDeserializationException("Duplicate package with name '" + name + "'");
		}

		HashMap<String, Entity> entities = new HashMap<>();
		HashMap<String, JsonArray> relationsJsons = new HashMap<>();

		if (packageJson.has("entities")) {
			JsonArray entitiesJson = packageJson.getAsJsonArray("entities");

			for (JsonElement entityJson : entitiesJson) {
				p.addEntity(deserializeEntity(entityJson.getAsJsonObject(), entities, relationsJsons, p));
			}
		}

		if (packageJson.has("packages")) {
			ArrayList<Package> subPackages = new ArrayList<>();

			for (JsonElement subPackageJson : packageJson.getAsJsonArray("packages")) {
				subPackages.add(deserializePackage(subPackageJson.getAsJsonObject(), packages, p));
			}
			p.setSubPackages(subPackages);
		}

		for (HashMap.Entry<String, JsonArray> relationsJsonEntry : relationsJsons.entrySet()) {
			deserializeRelationsToEntity(relationsJsonEntry.getValue(), entities.get(relationsJsonEntry.getKey()),
					entities);
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
		Package root = deserializePackage(o, packages, destination);
		for (Package p : root.getSubPackages()) {
			destination.addPackage(p);
		}

		for (Entity e : root.getEntities()) {
			destination.addEntity(e);
		}
	}
}

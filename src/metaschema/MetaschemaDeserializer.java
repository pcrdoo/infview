package metaschema;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Attribute;
import model.Entity;
import model.InfResource;
import model.Package;
import model.Relation;
import model.Warehouse;
import model.Table;
import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
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
			
			JsonArray referencedAttributesJson = relation.get("referencedAttributes").getAsJsonArray();
			JsonArray referringAttributesJson = relation.get("referringAttributes").getAsJsonArray();
			
			if (referencedAttributesJson.size() == 0) {
				throw new MetaschemaDeserializationException("Entity '" + source.getName() + "' has a relation with 0 referenced attributes");
			}
			
			if (referringAttributesJson.size() == 0) {
				throw new MetaschemaDeserializationException("Entity '" + source.getName() + "' has a relation with 0 referring attributes");
			}
			
			if (referencedAttributesJson.size() != referringAttributesJson.size()) {
				throw new MetaschemaDeserializationException("Entity '" + source.getName() + "' has a relation with mismatching attribute counts");
			}
			
			ArrayList<Attribute> referencedAttributes = new ArrayList<>();
			InfResource parent = null;
			for (JsonElement referenced : referencedAttributesJson) {
				String[] referencedAttributeNamePieces = referenced.getAsString().split("/");
				if (referencedAttributeNamePieces.length != 2) {
					throw new MetaschemaDeserializationException(
							"Referenced attribute '" + String.join("/", referencedAttributeNamePieces)
									+ "' is not valid (expected format is 'EntityName/AttributeName')");
				}
				
				if (!entities.containsKey(referencedAttributeNamePieces[0])) {
					throw new MetaschemaDeserializationException("Entity '" + source.getName()
							+ "' has a relation to unknown entity '" + referencedAttributeNamePieces[0] + "'");
				}

				Entity referencedEntity = entities.get(referencedAttributeNamePieces[0]);
				Attribute referencedAttribute = referencedEntity.findAttributeByName(referencedAttributeNamePieces[1]);
				if (referencedAttribute == null) {
					throw new MetaschemaDeserializationException("Referenced attribute '"
							+ String.join("/", referencedAttributeNamePieces) + "' does not exist");
				}

				if (parent == null) {
					parent = referencedAttribute.getParent();
				} else if (parent != referencedAttribute.getParent()) {
					throw new MetaschemaDeserializationException("Cannot have a composite key that refers to attributes in multiple tables on entity '" + source.getName() + "'");
				}
				
				referencedAttributes.add(referencedAttribute);
			}
			
			ArrayList<Attribute> referringAttributes = new ArrayList<>();
			for (JsonElement referring : referringAttributesJson) {
				String referringAttributeName = referring.getAsString();
				Attribute referringAttribute = source.findAttributeByName(referringAttributeName);

				String fqn = source.getName() + "/" + referringAttributeName;
				if (referringAttribute == null) {
					throw new MetaschemaDeserializationException("Referring attribute '" + fqn + "' does not exist");
				}
				
				referringAttributes.add(referringAttribute);
			}
			
			Relation r = new Relation(referringAttributes, referencedAttributes, source);

			if (relationNames.contains(r.getName())) {
				throw new MetaschemaDeserializationException("Duplicate relation with name '" + r.getName() + "'");
			}

			relationNames.add(r.getName());
			source.addRelation(r);
			
			Entity referencedEntity = (Entity) referencedAttributes.get(0).getParent();
			if (referencedEntity instanceof SequentialFile
					&& !(referencedEntity instanceof IndexedSequentialFile) ||
					referencedEntity instanceof Table) {
				referencedEntity.addInverseRelation(r);
			}
		}
	}

	public Attribute deserializeAttribute(JsonObject attributeJson, HashMap<String, Attribute> attributes,
			Entity parent) throws MetaschemaDeserializationException {
		String type = attributeJson.get("type").getAsString();
		int length = attributeJson.get("length").getAsInt();
		boolean primaryKey = attributeJson.get("primaryKey").getAsBoolean();
		
		boolean mandatory = false;
		JsonElement mandatoryElem = attributeJson.get("mandatory");
		if (mandatoryElem != null) {
			mandatory = mandatoryElem.getAsBoolean();
		}
		
		Class<?> clazz = null;
		switch (type) {
		case "char":
			clazz = CharType.class;
			break;
		case "varchar":
			clazz = VarCharType.class;
			break;
		case "datetime":
			clazz = DateType.class;
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
		Attribute a = new Attribute("", parent, clazz, length, primaryKey, mandatory);

		deserializeToInfResource(attributeJson, a);

		if (attributes.containsKey(a.getName())) {
			throw new MetaschemaDeserializationException("Duplicate attribute with name '" + a.getName() + "'");
		}

		attributes.put(a.getName(), a);
		return a;
	}

	public Entity deserializeEntity(JsonObject entityJson, HashMap<String, Entity> entities,
			HashMap<String, JsonArray> relationsJsons, Package parent, String warehouseLocation)
			throws MetaschemaDeserializationException {
		String type = entityJson.get("type").getAsString();
		String fullPath = warehouseLocation + File.separatorChar + entityJson.get("url").getAsString();
		Entity e;
		switch (type) {
		case "serial":
			e = new SerialFile("", fullPath, parent);
			break;
		case "sequential":
			e = new SequentialFile("", fullPath, parent);
			break;
		case "indexedSequential":
			e = new IndexedSequentialFile("", fullPath, parent);
			break;
		case "sqlTable":
			e = new Table("", parent);
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

		if (e instanceof IndexedSequentialFile) {
			try {
				((IndexedSequentialFile) e).loadOrMakeTree();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		relationsJsons.put(name, entityJson.getAsJsonArray("relations"));

		return e;
	}

	public Package deserializePackage(JsonObject packageJson, HashMap<String, Package> packages, InfResource parent,
			String warehouseLocation) throws MetaschemaDeserializationException {
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
				p.addEntity(deserializeEntity(entityJson.getAsJsonObject(), entities, relationsJsons, p,
						warehouseLocation));
			}
		}

		if (packageJson.has("packages")) {
			ArrayList<Package> subPackages = new ArrayList<>();

			for (JsonElement subPackageJson : packageJson.getAsJsonArray("packages")) {
				subPackages.add(deserializePackage(subPackageJson.getAsJsonObject(), packages, p, warehouseLocation));
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
		destination.setLocation(o.get("location").getAsString());
		
		if(o.get("type").getAsString().equals("mssql")) {
			destination.buildConnection();
		}

		HashMap<String, Package> packages = new HashMap<>();
		Package root = deserializePackage(o, packages, destination, destination.getLocation());
		for (Package p : root.getSubPackages()) {
			destination.addPackage(p);
		}

		for (Entity e : root.getEntities()) {
			destination.addEntity(e);
		}
	}
}

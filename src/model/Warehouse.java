package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import metaschema.MetaschemaDeserializationException;
import metaschema.MetaschemaDeserializer;
import metaschema.MetaschemaValidationException;
import metaschema.MetaschemaValidator;

public class Warehouse extends InfResource {

	private String description;
	private String metaschemaString;
	private String location;

	private static Warehouse instance;
	private ArrayList<Package> packages;
	private ArrayList<Entity> entities;

	private Warehouse(String name) {
		super(name, null);
		this.packages = new ArrayList<Package>();
		this.entities = new ArrayList<Entity>();
	}

	public static Warehouse getInstance() {
		if (instance == null) {
			instance = new Warehouse("TEST_WAREHOUSE");
		}
		return instance;
	}

	@Override
	public List<? extends InfResource> getChildren() {
		return Stream.concat(this.packages.stream(), this.entities.stream()).collect(Collectors.toList());
	}

	/**
	 * @param metaschemaPath
	 * @throws MetaschemaDeserializationException
	 * @throws MetaschemaValidationException
	 * @throws IOException
	 * @pdOid fb51985c-d298-469f-8ca9-5c97fb0b9d07
	 */
	public void loadWarehouse(String metaschemaString)
			throws MetaschemaDeserializationException, IOException, MetaschemaValidationException {
		this.metaschemaString = metaschemaString;

		MetaschemaDeserializer d = new MetaschemaDeserializer();
		MetaschemaValidator v = new MetaschemaValidator();
		v.validate(metaschemaString);
		d.deserialize(metaschemaString, this);
	}

	public String getMetaschemaString() {
		return this.metaschemaString;
	}

	public ArrayList<Package> getPackages() {
		return packages;
	}

	public String getDescription() {
		return description;
	}

	public void addPackage(Package pakage) {
		this.packages.add(pakage);
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

	public String toIndentedString(int indentSpaces) {
		ArrayList<String> packagesString = new ArrayList<>();
		for (Package p : packages) {
			packagesString.add(p.toIndentedString(8));
		}
		String packageStr = String.join("\n", packagesString);

		return indentStringRepresentation(String.format(
				"Warehouse \"%s\" {\n" + "    description = \"%s\"\n" + "    packages = [\n" + "%s\n" + "    ]\n" + "}",
				name, description, packageStr), indentSpaces);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
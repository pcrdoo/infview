package model;

import java.nio.file.Path;
import java.util.*;

import model.Package;

public class Warehouse extends InfResource {

	private String description;
	private Path metaschemaPath;

	private static Warehouse instance;
	private ArrayList<Package> packages;

	private Warehouse(String name) {
		super(name, null);
		this.packages = new ArrayList<Package>();
	}

	public static Warehouse getInstance() {
		if (instance == null) {
			instance = new Warehouse("TEST_WAREHOUSE");
		}
		return instance;
	}

	@Override
	public List<? extends InfResource> getChildren() {
		return this.packages;
	}

	/**
	 * @param metaschemaPath
	 * @pdOid fb51985c-d298-469f-8ca9-5c97fb0b9d07
	 */
	public void loadWarehouse(Path metaschemaPath) {
		// TODO: implement
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

	public String toIndentedString(int indentSpaces) {
		ArrayList<String> packagesString = new ArrayList<>();
		for (Package p : packages) {
			packagesString.add(p.toIndentedString(8));
		}
		String packageStr = String.join("\n", packagesString);

		return indentStringRepresentation(String.format(
				"Warehouse \"%s\" {\n" +
				"    description = \"%s\"\n" +
				"    packages = [\n" +
				"%s\n" +
				"    ]\n" +
				"}", name, description, packageStr), indentSpaces);
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
package model;

import java.nio.file.Path;
import java.util.*;

public class Warehouse extends InfResource {

	private String description;
	private Path metaschemaPath;

	private static Warehouse instance;
	private ArrayList<Package> packages;

	public Warehouse(String name) {
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
	protected List<? extends InfResource> getChildren() {
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

}
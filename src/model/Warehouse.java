package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private Connection dbConnection;

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

	public void buildConnection() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String location = "ui-2016-tim201.5:ui-2015-tim201.5.MuY991@jdbc:jtds:sqlserver://147.91.175.155/ui-2016-tim201.5";
		String[] parts = location.split("@");
		String loc = parts[1];
		String[] unpw = parts[0].split(":");
		String username = unpw[0];
		String password = unpw[1];
		try {
			dbConnection = DriverManager.getConnection(loc, username, password);
			// print tables for fun
			DatabaseMetaData dbMetadata = dbConnection.getMetaData();
			String[] dbTypes = { "TABLE" };
			ResultSet rsTables = dbMetadata.getTables(null, null, null, dbTypes);
			while (rsTables.next()) {
				String tableName = rsTables.getString("TABLE_NAME");
				System.out.println(tableName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Connection getDbConnection() {
		return dbConnection;
	}
}
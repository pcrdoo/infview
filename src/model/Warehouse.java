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
			mock();
		}
		return instance;
	}
	
	public static void mock() {
		Package pkg = new Package("Paket");
		Entity grad = new Entity("Grad");
		pkg.addEntity(grad);
		grad.addAttribute(new Attribute("Ime", String.class));
		grad.addAttribute(new Attribute("Povrsina", float.class));
		grad.addAttribute(new Attribute("Sranje", boolean.class));

		grad.addEntry(spawnEntry(grad, "Beograd", 250.12f, true));
	}
	
	private static Entry spawnEntry(Entity grad, String ime, float povrsina, boolean sranje) {
		ArrayList<Attribute> attrs = grad.getAttributes();
		Entry entri = new Entry(grad);
		entri.addAttribute(attrs.get(0),  ime);
		entri.addAttribute(attrs.get(1), povrsina);
		entri.addAttribute(attrs.get(2), sranje);
		return entri;
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
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Package extends InfResource {

	private ArrayList<Entity> entities;
	private ArrayList<Package> subPackages;

	public Package(String name, InfResource parent) {
		super(name, parent);
		this.name = name;
		entities = new ArrayList<>();
		subPackages = new ArrayList<>();
	}

	@Override
	public List<? extends InfResource> getChildren() {
		return Stream.concat(this.subPackages.stream(), this.entities.stream()).collect(Collectors.toList());
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public void addEntity(Entity newEntity) {
		if (newEntity == null)
			return;
		if (this.entities == null)
			this.entities = new ArrayList<Entity>();
		if (!this.entities.contains(newEntity))
			this.entities.add(newEntity);
	}

	public ArrayList<Package> getSubPackages() {
		return subPackages;
	}

	public void setSubPackages(ArrayList<Package> subPackages) {
		this.subPackages = subPackages;
	}

	public String toIndentedString(int indentSpaces) {
		ArrayList<String> packagesString = new ArrayList<>();
		for (Package p : subPackages) {
			packagesString.add(p.toIndentedString(8));
		}
		String packageStr = String.join("\n", packagesString);

		ArrayList<String> entitiesString = new ArrayList<>();
		for (Entity e : entities) {
			entitiesString.add(e.toIndentedString(8));
		}
		String entityStr = String.join("\n", entitiesString);

		return indentStringRepresentation(String.format(
				"Package \"%s\" {\n" +
				"    packages = [\n" +
				"%s\n" +
				"    ]\n" +
				"    entities = [\n" +
				"%s\n" +
				"    ]\n" +
				"}", name, packageStr, entityStr), indentSpaces);
	}
}
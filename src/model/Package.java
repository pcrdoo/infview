package model;

import java.util.*; 

public class Package extends InfResource {

	private ArrayList<Entity> entities;
	private ArrayList<Package> subPackages;

	public Package(String name) {
		this.name = name;
		entities = new ArrayList<>();
		subPackages = new ArrayList<>();
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
}
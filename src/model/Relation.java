/***********************************************************************
 * Module:  Relation.java
 * Author:  Random
 * Purpose: Defines the Class Relation
 ***********************************************************************/

package model;

import java.util.*;

/** @pdOid c127888f-e3b9-4b94-9b7c-8b1a3e10b793 */
public class Relation extends InfResource {
	private Entity objectEntity;

	public Relation(String name, Entity objectEntity) {
		this.name = name;
		this.objectEntity = objectEntity;
	}

	public Entity getObjectEntity() {
		return objectEntity;
	}

	public void setObjectEntity(Entity objectEntity) {
		this.objectEntity = objectEntity;
	}

	@Override
	public String toIndentedString(int indentSpaces) {
		return indentStringRepresentation(String.format(
			"Relation \"%s\" {\n" +
		    "    objectEntity = Entity(\"%s\")\n" +
			"}", name, objectEntity.getName()), indentSpaces);
	}
}
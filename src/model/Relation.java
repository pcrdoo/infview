/***********************************************************************
 * Module:  Relation.java
 * Author:  Random
 * Purpose: Defines the Class Relation
 ***********************************************************************/

package model;

import java.util.*;

/** @pdOid c127888f-e3b9-4b94-9b7c-8b1a3e10b793 */
public class Relation extends InfResource {
	private Attribute referencedAttribute;
	private Attribute referringAttribute;

	public Relation(Attribute referringAttribute, Attribute referencedAttribute, InfResource parent) {
		super(referringAttribute.getName(), parent);
		this.referringAttribute = referringAttribute;
		this.referencedAttribute = referencedAttribute;
	}
	
	public Attribute getReferencedAttribute() {
		return referencedAttribute;
	}

	public Attribute getReferringAttribute() {
		return referringAttribute;
	}

	@Override
	public String toIndentedString(int indentSpaces) {
		return indentStringRepresentation(String.format(
			"Relation \"%s\" {\n" +
		    "    referringAttribute = Attribute(\"%s\")\n" +
		    "    referencingAttribute = Attribute(\"%s\")\n" +
			"}", name, referringAttribute.getFullyQualifiedName(), referringAttribute.getFullyQualifiedName()), indentSpaces);
	}
}
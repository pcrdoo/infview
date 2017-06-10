/***********************************************************************
 * Module:  Relation.java
 * Author:  Random
 * Purpose: Defines the Class Relation
 ***********************************************************************/

package model;
import java.util.ArrayList;

/** @pdOid c127888f-e3b9-4b94-9b7c-8b1a3e10b793 */
public class Relation extends InfResource {
	private ArrayList<Attribute> referencedAttributes;
	private ArrayList<Attribute> referringAttributes;

	private static String makeCompositeKeyName(ArrayList<Attribute> attribs) {
		if (attribs.size() == 0) {
			throw new IllegalArgumentException("Cannot have 0 attributes in a composite key");
		}
		
		if (attribs.size() == 1) {
			return attribs.get(0).getFullyQualifiedName();
		} else {
			StringBuilder sb = new StringBuilder();
			
			Attribute first = attribs.get(0);
			sb.append(first.getParent().getName());
			sb.append("/{");
			for (Attribute a : attribs) {
				if (a.getParent() != first.getParent()) {
					throw new IllegalArgumentException("All attributes in a composite key must belong to the same entity");
				}
				sb.append(a.getName());
				sb.append(",");
			}
			
			sb.deleteCharAt(sb.length() - 1);
			sb.append("}");
			
			return sb.toString();
		}
	}
	
	public Relation(ArrayList<Attribute> referringAttributes, ArrayList<Attribute> referencedAttributes, InfResource parent) {
		super(makeCompositeKeyName(referringAttributes) + " - " + makeCompositeKeyName(referencedAttributes), parent);
		this.referringAttributes = referringAttributes;
		this.referencedAttributes = referencedAttributes;
	}

	public ArrayList<Attribute> getReferencedAttributes() {
		return referencedAttributes;
	}

	public ArrayList<Attribute> getReferringAttributes() {
		return referringAttributes;
	}

	@Override
	public String toIndentedString(int indentSpaces) {
		return indentStringRepresentation(
				String.format(
						"Relation \"%s\" {\n" + "    referringAttributes = %s)\n"
								+ "    referencingAttribute = %s)\n" + "}",
						name, makeCompositeKeyName(referencedAttributes), makeCompositeKeyName(referringAttributes)),
				indentSpaces);
	}
}
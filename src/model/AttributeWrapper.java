package model;

public class AttributeWrapper {
	
	private Attribute attribute;
	
	public AttributeWrapper(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return this.attribute.name;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
}

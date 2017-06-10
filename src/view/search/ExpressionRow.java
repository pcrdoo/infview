package view.search;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Attribute;
import model.Entity;
import model.files.InvalidRecordException;

public abstract class ExpressionRow extends JPanel {

	protected Entity entity;
	protected DBGenericDialog parent;

	protected ExpressionComponent attribute;
	
	public ExpressionRow(Entity entity, DBGenericDialog parent) {
		super();
		this.entity = entity;
		this.parent = parent;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.init();
	}
	
	private void init() {
		this.attribute = new AttributeComboBox(this.entity, this);
		this.add((Component) attribute);
		this.setAttribute(((AttributeComboBox)this.attribute).getSelectedAttribute());
	}
	
	public abstract void setAttribute(Attribute a);
	public abstract String getQuery();
	public abstract Object getObject() throws InvalidRecordException;

}

package view.search;

import java.awt.Component;

import model.Attribute;
import model.Entity;
import model.files.InvalidRecordException;

public class SortRow extends ExpressionRow {
	
	private ExpressionComponent order;
	private ExpressionComponent then;

	public SortRow(Entity entity, DBGenericDialog parent) {
		super(entity, parent);
	}

	@Override
	public void setAttribute(Attribute a) {
		if (order != null && then != null) {
			this.remove((Component) order);
			this.remove((Component) then);
		}
		
		if (this.order == null)
			this.order = new OrderComboBox();
		if (this.then == null)
			this.then = new ThenByComboBox(parent);

		this.add((Component) order);
		this.add((Component) then);
	}

	@Override
	public String getQuery() {
		return this.attribute.getExpression() + this.order.getExpression() + then.getExpression();
	}

	@Override
	public Object getObject() throws InvalidRecordException {
		// TODO Auto-generated method stub
		return null;
	}

}

package view.search;

import java.awt.Component;

import javax.naming.OperationNotSupportedException;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Attribute;
import model.AttributeWrapper;
import model.Entity;
import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
import model.files.File;
import model.files.InvalidRecordException;
import sun.reflect.annotation.ExceptionProxy;

public class SearchRow extends ExpressionRow {
	
	private ExpressionComponent operation;
	private ExpressionComponent value;
	private ExpressionComponent logic;
	
	public SearchRow(Entity entity, DBGenericDialog parent) {
		super(entity, parent);
	}
	
	public void setAttribute(Attribute a) {
		if (operation != null && value != null && logic != null) {
			this.remove((Component) operation);
			this.remove((Component) value);
			this.remove((Component) logic);
		}
		
		this.operation = makeExpressionComponent(a.getValueClass());
		
		if (this.value == null)
			this.value = new InfText();
		if (this.logic == null)
			this.logic = new LogicalComboBox(parent);

		this.add((Component) operation);
		this.add((Component) value);
		this.add((Component) logic);
	}
	
	private ExpressionComponent makeExpressionComponent(Class c) {
		if (c.equals(CharType.class)) {
			return new CharComboBox();
		} else if (c.equals(VarCharType.class)) {
			return new CharComboBox();
		} else if (c.equals(DateType.class)) {
			return new DateComboBox();
		} else if (c.equals(Integer.class)) {
			return new IntegerComboBox();
		} else if (c.equals(Boolean.class)) {
			return new BooleanComboBox();
		} else {
			System.err.println("OgiException: Unknown datatype in BaseSearchDialog.");
			return null;
		}
	}

	public String getQuery() {
		return attribute.getExpression() + operation.getExpression() + logic.getExpression();
	}

	public Object getObject() throws InvalidRecordException {
		return File.parseStringField(value.getExpression(), ((AttributeComboBox)this.attribute).getSelectedAttribute(), "OGILINE");
	}
}

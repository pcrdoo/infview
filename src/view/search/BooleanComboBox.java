package view.search;

import javax.swing.JComboBox;

class BooleanComboBox extends JComboBox<String> implements ExpressionComponent{
	public BooleanComboBox() {
		super();
		this.addItem("equals");
	}

	@Override
	public String getExpression() {
		return "= ? ";
	}
}
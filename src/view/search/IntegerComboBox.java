package view.search;

import javax.swing.JComboBox;

class IntegerComboBox extends JComboBox<String> implements ExpressionComponent{
	public IntegerComboBox() {
		super();
		this.addItem("= ");
		this.addItem("< ");
		this.addItem("> ");
	}

	@Override
	public String getExpression() {
		return this.getSelectedItem().toString() + "? ";
	}
}
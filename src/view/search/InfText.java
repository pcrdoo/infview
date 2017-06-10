package view.search;

import javax.swing.JTextField;

class InfText extends JTextField implements ExpressionComponent{
	
	public InfText() {
		super();
	}

	@Override
	public String getExpression() {
		return this.getText();
	}
}
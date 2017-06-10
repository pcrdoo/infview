package view.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

class LogicalComboBox extends JComboBox<String> implements ExpressionComponent {
	
	public DBSearchDialog grandParent;
	boolean used = false;
	
	public LogicalComboBox(DBSearchDialog grandParent) {
		super();
		this.grandParent = grandParent;
		this.addItem(" ");
		this.addItem("AND");
		this.addItem("OR");
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (used)
					return;
				
				if (getSelectedIndex() != 0) {
					used = true;
					grandParent.addRow();
					removeItemAt(0);
				}
			}
		});
	}

	@Override
	public String getExpression() {
		return this.getSelectedItem().toString() + " ";
	}
}
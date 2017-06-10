package view.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

class ThenByComboBox extends JComboBox<String> implements ExpressionComponent {

	public DBGenericDialog grandParent;
	boolean used = false;

	public ThenByComboBox(DBGenericDialog grandParent) {
		super();
		this.grandParent = grandParent;
		this.addItem(" ");
		this.addItem("ONDA PO");

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
		return ", ";
	}
}
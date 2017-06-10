package view;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SQLErrorDialog {

	private JDialog dialog;

	public SQLErrorDialog(SQLException ex) {
		StringBuilder errorBuilder = new StringBuilder();
		while (ex != null) {
			errorBuilder.append(ex.getSQLState());
			errorBuilder.append("[");
			errorBuilder.append(ex.getErrorCode());
			errorBuilder.append("]: ");
			errorBuilder.append(ex.getMessage());
			errorBuilder.append("\n ");
			ex = ex.getNextException();
		}
		JOptionPane pane = new JOptionPane(errorBuilder.toString(), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_OPTION);
		dialog = pane.createDialog("SQL Error");
	}

	public void launch() {
		dialog.setVisible(true);
	}
}

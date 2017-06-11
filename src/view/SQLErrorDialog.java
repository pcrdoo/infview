package view;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SQLErrorDialog {

	private StringBuilder errorBuilder;

	public SQLErrorDialog(SQLException ex) {
		errorBuilder = new StringBuilder();
		while (ex != null) {
			errorBuilder.append("[Err. ");
			errorBuilder.append(ex.getErrorCode());
			errorBuilder.append("]: ");
			errorBuilder.append(ex.getMessage());
			errorBuilder.append("\n ");
			ex = ex.getNextException();
		}
	}

	public void launch() {
		JOptionPane.showMessageDialog(null, errorBuilder.toString(), "Greška", JOptionPane.ERROR_MESSAGE);
	}
}

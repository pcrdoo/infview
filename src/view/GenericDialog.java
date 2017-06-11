package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GenericDialogController;
import model.Attribute;
import model.Entity;
import model.Record;
import model.files.InvalidRecordException;

public class GenericDialog extends JDialog implements CloseableDialog {

	private GenericDialogController controller;

	private ArrayList<JTextField> attributes;
	private Entity entity;
	private Record record;
	private int attributeCount;

	private JCheckBox findAll;
	private JCheckBox toFile;
	private JCheckBox fromStart;
	private JButton ok;

	boolean closed = true;
	boolean isSearch;

	public GenericDialog(Entity entity, Record record, boolean allowCheckBoxes, boolean allowPrimaryKey,
			boolean isSearch) {
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.entity = entity;
		this.record = record;
		this.isSearch = isSearch;

		this.ok = new JButton(isSearch ? "Search" : "Save");
		this.controller = new GenericDialogController(this);
		
		this.init(allowCheckBoxes, allowPrimaryKey);
	}

	private void init(boolean allowCheckBoxes, boolean allowPrimaryKey) {
		findAll = new JCheckBox("Find all occurrences");
		toFile = new JCheckBox("Write to file");
		fromStart = new JCheckBox("Search from start");

		this.attributeCount = entity.getAttributes().size();
		int heightCount = (attributeCount + 5);
		int rowHeight = 40;

		if (!allowCheckBoxes)
			heightCount -= 3;

		this.setSize(400, heightCount * rowHeight);
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(heightCount, 2, 2, 2));

		if (allowCheckBoxes) {
			panel.add(findAll);
			panel.add(new JLabel());
			panel.add(toFile);
			panel.add(new JLabel());
			panel.add(fromStart);
			panel.add(new JLabel());
		}

		this.attributes = new ArrayList<>();

		for (Attribute a : entity.getAttributes()) {
			panel.add(new JLabel(a.getName()));

			JTextField newAttribute = new JTextField();
			if (record != null) {
				newAttribute.setText(record.getAttributes().get(a).toString());
			}

			if (a.isPrimaryKey()) {
				newAttribute.setBackground(Color.GRAY);
				if (!allowPrimaryKey) {
					newAttribute.setEditable(false);
				}
			}
			this.attributes.add(newAttribute);
			panel.add(newAttribute);
		}

		this.add(panel);
		this.add(ok);
	}

	public String[] getTerms() {
		String[] terms = new String[attributeCount];

		for (int i = 0; i < attributeCount; i++) {
			terms[i] = attributes.get(i).getText();
		}

		return terms;
	}

	public boolean validateRecord() {
		try {
			Record.fromTerms(getTerms(), entity);
			return true;
		} catch (InvalidRecordException e) {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n\n" + "Fix the errors and try again.");
			return false;
		}
	}

	public Record getRecord() {
		try {
			return Record.fromTerms(getTerms(), entity);
		} catch (InvalidRecordException e) {
			return null;
		}
	}

	public boolean getFindAll() {
		return findAll.isSelected();
	}

	public boolean getToFile() {
		return toFile.isSelected();
	}

	public boolean getFromStart() {
		return fromStart.isSelected();
	}

	public void setOKListener(ActionListener l) {
		this.ok.addActionListener(l);
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public void showModal() {
		setClosed(true);
		setModal(true);
		setVisible(true);
	}
	
	public boolean isSearch() {
		return this.isSearch;
	}
}

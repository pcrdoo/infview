package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GenericDialogController;
import model.Attribute;
import model.Entity;
import model.Record;
import model.files.SequentialFile;

public class SearchDialog extends JDialog {
	// Ogi todo
	int atrCount;
	JCheckBox findAll;
	JCheckBox toFile;
	JCheckBox fromStart;
	ArrayList<JTextField> attributes;
	GenericDialogController controller;
	JButton ok;
	Entity entity;
	Record record;
	
	public SearchDialog(Entity entity, Record record) {
		this.entity = entity;
		this.record = record;
		this.ok = new JButton("Search");
		this.controller = new GenericDialogController(this);
		findAll = new JCheckBox("Find all occurrences");
		toFile = new JCheckBox("Write to file");
		fromStart = new JCheckBox("Search from start");
		
		atrCount = entity.getAttributes().size();
		int s = (atrCount + 4) * 30;
		this.setSize(s, s);
		
		JPanel panel = new JPanel();
		//panel.setSize(1000, 1000);
		panel.setLayout(new GridLayout(atrCount + 4, 2));
		//panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(findAll);
		panel.add(new JLabel());
		panel.add(toFile);
		panel.add(new JLabel());
		panel.add(fromStart);
		panel.add(new JLabel());
		
		this.attributes = new ArrayList<>();
		
		for (Attribute a : entity.getAttributes()) {
			panel.add(new JLabel(a.getName()));
			
			JTextField newAttribure = new JTextField();
			if(a.isPrimaryKey()) {
				newAttribure.setBackground(Color.GRAY);
			}
			this.attributes.add(newAttribure);
			panel.add(newAttribure);
		}
		
		panel.add(ok);
		
		this.add(panel);
	}
	
	public String[] getTerms() {
		String[] terms = new String[atrCount];
		
		for(int i = 0; i < atrCount; i++) {
			terms[i] = attributes.get(i).getText();
		}
		
		return terms;
	}
	
	public Record getRecord() {
		return Record.fromTerms(getTerms(), entity);
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
}

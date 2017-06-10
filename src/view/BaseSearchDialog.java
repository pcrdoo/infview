package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.BaseSearchDialogController;
import controller.GenericDialogController;
import model.Attribute;
import model.Entity;
import model.datatypes.CharType;

public class BaseSearchDialog extends JDialog{
	private Entity entity;
	private BaseSearchDialogController controller;
	ArrayList<JTextField> attributes;
	
	public BaseSearchDialog(Entity entity) {
		this.setEntity(entity);
		this.controller = new BaseSearchDialogController(this);
		
		int atrCount = entity.getAttributes().size();
		int num = (atrCount + 1);


		this.setSize(num * 30, num * 30);
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		int maxOptions = 5; // TODO
		panel.setLayout(new GridLayout(num, maxOptions));

		this.attributes = new ArrayList<>();

		for (Attribute a : entity.getAttributes()) {
			panel.add(new JLabel(a.getName()));

//			JTextField newAttribure = new JTextField();
//
//			if (a.isPrimaryKey()) {
//				newAttribure.setBackground(Color.GRAY);
//			}
			
			if (a.getValueClass().equals(Integer.class)) {
				
			} else if (a.getValueClass().equals(CharType.class)) {
				
			}
			
//			this.attributes.add(newAttribure);
//			panel.add(newAttribure);
		}

//		panel.add(ok);

		this.add(panel);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	interface ExpretionComponent {
		abstract public String getText();
	}
	
	class LogicalComboBox extends JComboBox implements ExpretionComponent{
		public LogicalComboBox() {
			super();
			this.addItem("AND");
			this.addItem("OR");
		}

		@Override
		public String getText() {
			return this.getSelectedItem().toString() + " ";
		}
	}
	
	class IntegerComboBox extends JComboBox implements ExpretionComponent{
		public IntegerComboBox() {
			super();
			this.addItem("=");
			this.addItem("<");
			this.addItem(">");
		}

		@Override
		public String getText() {
			return this.getSelectedItem().toString() + "? ";
		}
	}
	
	class CharComboBox extends JComboBox implements ExpretionComponent{
		
		HashMap<String, String> mapa = new HashMap<>();
		
		public CharComboBox() {
			super();
			mapa.put("jednako je", "= ? ");
			mapa.put("nije jednako", "<> ? ");
			mapa.put("sadrzi", "CONCAT('%', ?, '%') ");
			mapa.put("pocinje sa", "CONCAT(?, '%') ");
			mapa.put("zavrsava sa", "CONCAT('%', ?) ");
			for(String s : mapa.keySet())
				this.addItem(s);
		}

		@Override
		public String getText() {
			return mapa.get(this.getSelectedItem().toString());
		}
	}
	
	class InfText extends JTextField implements ExpretionComponent{
		
		public InfText() {
			super();
		}

		@Override
		public String getText() {
			return this.getText();
		}
	}
}

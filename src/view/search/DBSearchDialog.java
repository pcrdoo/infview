package view.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.DBSearchDialogController;
import controller.GenericDialogController;
import model.Attribute;
import model.AttributeWrapper;
import model.Entity;
import model.FilterParams;
import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
import model.files.InvalidRecordException;

public class DBSearchDialog extends JDialog{
	private Entity entity;
	private DBSearchDialogController controller;
	private int heigt;
	private int fieldHeight = 50;
	private JButton ok;
	private JPanel panel;

	private ArrayList<ExpressionPanel> expression;
	
	public DBSearchDialog(Entity entity) {
		this.setEntity(entity);
		this.ok = new JButton("Search");
		this.controller = new DBSearchDialogController(this);
		
		this.expression = new ArrayList<>();
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.setSize(400, 5 * fieldHeight);
		this.setLocationRelativeTo(null);

		panel = new JPanel();
		int maxOptions = 5; // TODO
		//panel.setLayout(new GridLayout(this.entity.getAttributes().size() + 2, maxOptions));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		this.add(panel);
		this.add(ok);
		
		this.addRow();
	}
	
	public void setOKListener(ActionListener l) {
		this.ok.addActionListener(l);
	}
	
	public void addRow() {
		this.heigt ++;
		this.setSize(400, this.heigt * this.fieldHeight);
		ExpressionPanel ep = new ExpressionPanel(this.entity, this);
		this.expression.add(ep);
		this.panel.add(ep);
	}
	
//	public void removeRow() {
//		if (this.expression.isEmpty())
//			return;
//		int idx = this.expression.size() - 1;
//		ExpressionPanel ep = this.expression.get(idx);
//		this.panel.remove(ep);
//		this.expression.remove(ep);
//		this.heigt --;
//		this.setSize(400, this.heigt * this.fieldHeight);
//	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public FilterParams getFilterParams()  throws InvalidRecordException{
		FilterParams params = new FilterParams();
		
		StringBuilder sb = new StringBuilder("SELECT * WHERE ");
		for (ExpressionPanel ep : this.expression) {
			sb.append(ep.getQuery());
		}
		params.setQuery(sb.toString());
		
		for (ExpressionPanel ep : this.expression) {
			params.addObject(ep.getObject());
		}
		
		return params;
	}
}

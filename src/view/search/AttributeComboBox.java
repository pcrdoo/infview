package view.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import com.sun.org.apache.bcel.internal.classfile.AttributeReader;

import model.Attribute;
import model.AttributeWrapper;
import model.Entity;

class AttributeComboBox extends JComboBox<AttributeWrapper> implements ExpressionComponent{
	
	private ExpressionRow parent;
	
	public AttributeComboBox(Entity entity, ExpressionRow parent) {
		super();
		
		this.parent = parent;
		
		for (Attribute a : entity.getAttributes()) {
			this.addItem(new AttributeWrapper(a));
		}
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.setAttribute(((AttributeWrapper)getSelectedItem()).getAttribute());
			}
		});
	}
	
	public Attribute getSelectedAttribute() {
		return ((AttributeWrapper)getSelectedItem()).getAttribute();
	}

	@Override
	public String getExpression() {
		return this.getSelectedItem().toString() + " ";
	}
}
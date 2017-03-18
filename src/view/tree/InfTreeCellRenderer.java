package view.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.InfResource;

public class InfTreeCellRenderer extends DefaultTreeCellRenderer {
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean arg6) {
		
		this.setIcon(new ImageIcon(this.getClass().getResource("/res/node_icon.png")));
		this.setText(((InfResource)value).getName());

		return this;
	}

}

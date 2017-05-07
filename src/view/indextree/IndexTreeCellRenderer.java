package view.indextree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Entity;
import model.Package;
import model.Warehouse;
import view.tree.InfNode;

public class IndexTreeCellRenderer extends DefaultTreeCellRenderer {
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean arg6) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, arg6);
		
		this.setText(((IndexTreeNode)value).toString());

		return this;
	}
}

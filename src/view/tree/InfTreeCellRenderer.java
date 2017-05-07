package view.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Entity;
import model.InfResource;
import model.Package;
import model.Relation;
import model.Warehouse;

public class InfTreeCellRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean arg6) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, arg6);
		String iconPath;

		InfResource resource = ((InfNode) value).getResource();

		System.out.println("RENDER " + resource.getName());

		if (resource instanceof Warehouse) {
			iconPath = "/res/warehouse.png";
		} else if (resource instanceof Package) {
			iconPath = "/res/package.png";
		} else if (resource instanceof Entity) {
			iconPath = "/res/entity.png";
		} else if (resource instanceof Relation) {
			iconPath = "/res/relation.png";
		} else {
			System.err.println("OgiTreeException: Unknown node in Tree");
			iconPath = "/res/node_icon.png";
		}

		this.setIcon(new ImageIcon(this.getClass().getResource(iconPath)));
		this.setText(((InfNode) value).getResource().getName());

		return this;
	}

}

package view.indextree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class IndexTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4697074395210347664L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean arg6) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, arg6);

		this.setText(((IndexTreeNode) value).toString());

		return this;
	}
}

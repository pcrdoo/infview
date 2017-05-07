/***********************************************************************
 * Module:  ToolBarView.java
 * Author:  Random
 * Purpose: Defines the Class ToolBarView
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import controller.ToolBarController;

/** @pdOid c19e4848-2d45-460b-a492-1df0b0353d84 */
public class ToolBarView extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9081108112710396758L;
	/**
	 * @pdRoleInfo migr=no name=ToolBarController assc=association3 mult=1..1
	 *             type=Composition
	 */
	private ToolBarController toolBarController;
	private JButton editMetaschema;

	public ToolBarView(ToolBarController controller) {
		setToolBarController(controller);

		this.setBackground(Color.BLACK);
		editMetaschema = new JButton();
		editMetaschema.setToolTipText("Edit Metaschema");
		editMetaschema.setIcon(new ImageIcon(this.getClass().getResource("/res/edit.png")));
		add(editMetaschema);
	}

	public void addEditMetaschemaListener(ActionListener l) {
		editMetaschema.addActionListener(l);
	}

	public ToolBarController getToolBarController() {
		return toolBarController;
	}

	public void setToolBarController(ToolBarController toolBarController) {
		this.toolBarController = toolBarController;
	}
}
/***********************************************************************
 * Module:  ToolBarController.java
 * Author:  Ognjen
 * Purpose: Defines the Class ToolBarController
 ***********************************************************************/

package controller;

import java.awt.event.ActionListener;

import view.ToolBarView;

/** @pdOid 8a34a4f8-2186-447f-a8eb-bd138dc1f570 */
public class ToolBarController {
	/**
	 * @pdRoleInfo migr=no name=ToolBarView assc=association3 mult=1..1 side=A
	 */
	public ToolBarView toolBarView;

	public ToolBarController() {
		this.toolBarView = new ToolBarView(this);
	}

	public ToolBarView getView() {
		return toolBarView;
	}

	public void addEditMetaschemaListener(ActionListener l) {
		toolBarView.addEditMetaschemaListener(l);
	}
}
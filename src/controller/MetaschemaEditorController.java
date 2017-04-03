/***********************************************************************
 * Module:  MetaschemaEditorController.java
 * Author:  Ognjen
 * Purpose: Defines the Class MetaschemaEditorController
 ***********************************************************************/

package controller;

import view.MetaschemaEditorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import model.Warehouse;

/** @pdOid e62662e0-7709-4c4a-9ba9-6c764ecf7398 */
public class MetaschemaEditorController {
	/**
	 * @pdRoleInfo migr=no name=MetaschemaEditorView assc=association8 mult=1..1
	 *             side=A
	 */
	public MetaschemaEditorView metaschemaEditorView;
	public ArrayList<ActionListener> newMetaschemaListeners = new ArrayList<>();
	
	public MetaschemaEditorController() {
		this.metaschemaEditorView = new MetaschemaEditorView(this, Warehouse.getInstance().getMetaschemaString());

		metaschemaEditorView.addOkListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String newMetaschema = metaschemaEditorView.getText();
				try {
					Warehouse.getInstance().loadWarehouse(newMetaschema);
					for (ActionListener l : newMetaschemaListeners) {
						l.actionPerformed(null);
					}
				} catch (Exception ex) {
					metaschemaEditorView.showFailureMessage(ex.getMessage());
				}
			}
		});
	}
	
	public void addNewMetaschemaListener(ActionListener l) {
		newMetaschemaListeners.add(l);
	}
	
	public void show() {
		metaschemaEditorView.setVisible(true);
	}
	
	public void hide() {
		metaschemaEditorView.setVisible(false);
	}
}
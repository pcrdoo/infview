/***********************************************************************
 * Module:  TreeView.java
 * Author:  Random
 * Purpose: Defines the Class TreeView
 ***********************************************************************/

package view;

import java.awt.Color;

import javax.swing.JPanel;

import controller.TreeController;

/** @pdOid 69256616-539c-46fc-8b0d-dc2a328640c5 */
public class TreeView extends JPanel{
   /** @pdRoleInfo migr=no name=TreeController assc=association1 mult=1..1 type=Composition */
   private TreeController treeController;

   public TreeView() {
	   this.setBackground(Color.GREEN);
   }
}
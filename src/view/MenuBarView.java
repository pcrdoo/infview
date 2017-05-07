/***********************************************************************
 * Module:  MenuBarView.java
 * Author:  Random
 * Purpose: Defines the Class MenuBarView
 ***********************************************************************/

package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import controller.MenuBarController;

/** @pdOid 760c8f7d-95c9-4dfe-81c0-a68e0cd100e3 */
public class MenuBarView extends JMenuBar {
   /** @pdRoleInfo migr=no name=MenuBarController assc=association2 mult=1..1 type=Composition */
   private MenuBarController menuBarController;
   
   public MenuBarView() {
	   this.add(new JMenu("About"));
   }

}
/***********************************************************************
 * Module:  ToolBarView.java
 * Author:  Random
 * Purpose: Defines the Class ToolBarView
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JToolBar;

import controller.ToolBarController;

/** @pdOid c19e4848-2d45-460b-a492-1df0b0353d84 */
public class ToolBarView extends JToolBar{
   /** @pdRoleInfo migr=no name=ToolBarController assc=association3 mult=1..1 type=Composition */
   private ToolBarController toolBarController;
   
   public ToolBarView() {
		this.setBackground(Color.BLACK);
   }

}
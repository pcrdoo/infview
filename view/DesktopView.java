/***********************************************************************
 * Module:  DesktopView.java
 * Author:  Random
 * Purpose: Defines the Class DesktopView
 ***********************************************************************/

package view;

import controller.DesktopController;
import java.util.*;

/** @pdOid 0766f4e2-c230-4fad-8be5-69774bd2dbf4 */
public class DesktopView {
   /** @pdRoleInfo migr=no name=TopPanel assc=association12 mult=1..1 type=Composition */
   private TopPanel topPanel;
   /** @pdRoleInfo migr=no name=DesktopController assc=association4 mult=1..1 type=Composition */
   private DesktopController desktopController;
   
   /** @pdRoleInfo migr=no name=BottomPanel assc=association13 mult=1..1 type=Composition */
   public BottomPanel bottomPanel;

}
/***********************************************************************
 * Module:  MainView.java
 * Author:  Random
 * Purpose: Defines the Class MainView
 ***********************************************************************/

package view;

import controller.MainController;
import java.util.*;

/** @pdOid 100ad1e0-91b2-4511-a8a8-006233aae177 */
public class MainView {
   /** @pdRoleInfo migr=no name=MainView assc=association1 coll=java.util.Collection impl=java.util.HashSet mult=0..* type=Composition */
   private java.util.Collection<MainView> instance;
   /** @pdRoleInfo migr=no name=TreeView assc=association2 mult=1..1 type=Composition */
   private TreeView treeView;
   /** @pdRoleInfo migr=no name=ToolBarView assc=association3 mult=1..1 type=Composition */
   private ToolBarView toolBarView;
   /** @pdRoleInfo migr=no name=MenuBarView assc=association4 mult=1..1 type=Composition */
   private MenuBarView menuBarView;
   /** @pdRoleInfo migr=no name=DesktopView assc=association5 mult=1..1 type=Composition */
   private DesktopView desktopView;
   /** @pdRoleInfo migr=no name=MainController assc=association5 mult=1..1 type=Composition */
   private MainController mainController;
   
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorInstance() {
      if (instance == null)
         instance = new java.util.HashSet<MainView>();
      return instance.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newInstance */
   public void setInstance(java.util.Collection<MainView> newInstance) {
      removeAllInstance();
      for (java.util.Iterator iter = newInstance.iterator(); iter.hasNext();)
         addInstance((MainView)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newMainView */
   public void addInstance(MainView newMainView) {
      if (newMainView == null)
         return;
      if (this.instance == null)
         this.instance = new java.util.HashSet<MainView>();
      if (!this.instance.contains(newMainView))
         this.instance.add(newMainView);
   }
   
   /** @pdGenerated default remove
     * @param oldMainView */
   public void removeInstance(MainView oldMainView) {
      if (oldMainView == null)
         return;
      if (this.instance != null)
         if (this.instance.contains(oldMainView))
            this.instance.remove(oldMainView);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllInstance() {
      if (instance != null)
         instance.clear();
   }
   
   /** @pdOid 500d7754-f78b-41a7-8eed-8fd3462debd1 */
   public MainView getInstance() {
      // TODO: implement
      return null;
   }

}
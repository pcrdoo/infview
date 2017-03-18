/***********************************************************************
 * Module:  Warehouse.java
 * Author:  Ognjen
 * Purpose: Defines the Class Warehouse
 ***********************************************************************/

package model;

import java.util.*;

/** @pdOid 67870f01-f302-48ae-b582-a49404114d56 */
public class Warehouse extends InfResource {
   /** @pdOid ee612498-015b-4168-9d56-13d8cc48728a */
   private String description;
   /** @pdOid 6472bf68-71a9-4006-93df-4c0861e281d0 */
   private Path metaschemaPath;
   
   /** @pdRoleInfo migr=no name=Warehouse assc=1__1 mult=1..1 type=Composition */
   private Warehouse instance;
   /** @pdRoleInfo migr=no name=Package assc=association8 coll=java.util.Collection impl=java.util.ArrayList mult=0..* type=Composition */
   private java.util.Collection<Package> packages;
   
   /** @pdOid b28e6596-f496-46d6-b244-cb5b8132cb24 */
   private Warehouse() {
      // TODO: implement
   }
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Package> getPackages() {
      if (packages == null)
         packages = new java.util.ArrayList<Package>();
      return packages;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPackages() {
      if (packages == null)
         packages = new java.util.ArrayList<Package>();
      return packages.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPackages */
   public void setPackages(java.util.Collection<Package> newPackages) {
      removeAllPackages();
      for (java.util.Iterator iter = newPackages.iterator(); iter.hasNext();)
         addPackages((Package)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPackage */
   public void addPackages(Package newPackage) {
      if (newPackage == null)
         return;
      if (this.packages == null)
         this.packages = new java.util.ArrayList<Package>();
      if (!this.packages.contains(newPackage))
         this.packages.add(newPackage);
   }
   
   /** @pdGenerated default remove
     * @param oldPackage */
   public void removePackages(Package oldPackage) {
      if (oldPackage == null)
         return;
      if (this.packages != null)
         if (this.packages.contains(oldPackage))
            this.packages.remove(oldPackage);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPackages() {
      if (packages != null)
         packages.clear();
   }
   
   /** @pdOid 7db656a8-8fa1-4c9e-b25c-02b0ee38a6e5 */
   public Warehouse getInstance() {
      // TODO: implement
      return null;
   }
   
   /** @param metaschemaPath
    * @pdOid fb51985c-d298-469f-8ca9-5c97fb0b9d07 */
   public void loadWarehouse(Path metaschemaPath) {
      // TODO: implement
   }

}
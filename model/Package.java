/***********************************************************************
 * Module:  Package.java
 * Author:  Ognjen
 * Purpose: Defines the Class Package
 ***********************************************************************/

package model;

import java.util.*;

/** @pdOid d9ae6795-9c52-46dd-a360-69b39460e390 */
public class Package extends InfResource {
   /** @pdRoleInfo migr=no name=Entity assc=association7 coll=java.util.Collection impl=java.util.ArrayList mult=0..* type=Composition */
   private java.util.Collection<Entity> entities;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Entity> getEntities() {
      if (entities == null)
         entities = new java.util.ArrayList<Entity>();
      return entities;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorEntities() {
      if (entities == null)
         entities = new java.util.ArrayList<Entity>();
      return entities.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newEntities */
   public void setEntities(java.util.Collection<Entity> newEntities) {
      removeAllEntities();
      for (java.util.Iterator iter = newEntities.iterator(); iter.hasNext();)
         addEntities((Entity)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newEntity */
   public void addEntities(Entity newEntity) {
      if (newEntity == null)
         return;
      if (this.entities == null)
         this.entities = new java.util.ArrayList<Entity>();
      if (!this.entities.contains(newEntity))
         this.entities.add(newEntity);
   }
   
   /** @pdGenerated default remove
     * @param oldEntity */
   public void removeEntities(Entity oldEntity) {
      if (oldEntity == null)
         return;
      if (this.entities != null)
         if (this.entities.contains(oldEntity))
            this.entities.remove(oldEntity);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllEntities() {
      if (entities != null)
         entities.clear();
   }
   
   /** @pdRoleInfo migr=no name=Package assc=association9 coll=java.util.Collection impl=java.util.HashSet mult=0..* type=Composition */
   public java.util.Collection<Package> subPackages;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Package> getSubPackages() {
      if (subPackages == null)
         subPackages = new java.util.HashSet<Package>();
      return subPackages;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorSubPackages() {
      if (subPackages == null)
         subPackages = new java.util.HashSet<Package>();
      return subPackages.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSubPackages */
   public void setSubPackages(java.util.Collection<Package> newSubPackages) {
      removeAllSubPackages();
      for (java.util.Iterator iter = newSubPackages.iterator(); iter.hasNext();)
         addSubPackages((Package)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPackage */
   public void addSubPackages(Package newPackage) {
      if (newPackage == null)
         return;
      if (this.subPackages == null)
         this.subPackages = new java.util.HashSet<Package>();
      if (!this.subPackages.contains(newPackage))
         this.subPackages.add(newPackage);
   }
   
   /** @pdGenerated default remove
     * @param oldPackage */
   public void removeSubPackages(Package oldPackage) {
      if (oldPackage == null)
         return;
      if (this.subPackages != null)
         if (this.subPackages.contains(oldPackage))
            this.subPackages.remove(oldPackage);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSubPackages() {
      if (subPackages != null)
         subPackages.clear();
   }

}
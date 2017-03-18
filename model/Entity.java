/***********************************************************************
 * Module:  Entity.java
 * Author:  Random
 * Purpose: Defines the Class Entity
 ***********************************************************************/

package model;

import java.util.*;

/** @pdOid 8161b71e-16ef-4895-9c28-ab1cb625c70f */
public class Entity extends InfResource {
   /** @pdRoleInfo migr=no name=Attribute assc=association2 coll=java.util.Collection impl=java.util.ArrayList mult=0..* type=Composition */
   private java.util.Collection<Attribute> attributes;
   /** @pdRoleInfo migr=no name=Relation assc=association3 coll=java.util.Collection impl=java.util.ArrayList mult=0..* type=Composition */
   private java.util.Collection<Relation> relations;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Attribute> getAttributes() {
      if (attributes == null)
         attributes = new java.util.ArrayList<Attribute>();
      return attributes;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAttributes() {
      if (attributes == null)
         attributes = new java.util.ArrayList<Attribute>();
      return attributes.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAttributes */
   public void setAttributes(java.util.Collection<Attribute> newAttributes) {
      removeAllAttributes();
      for (java.util.Iterator iter = newAttributes.iterator(); iter.hasNext();)
         addAttributes((Attribute)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newAttribute */
   public void addAttributes(Attribute newAttribute) {
      if (newAttribute == null)
         return;
      if (this.attributes == null)
         this.attributes = new java.util.ArrayList<Attribute>();
      if (!this.attributes.contains(newAttribute))
         this.attributes.add(newAttribute);
   }
   
   /** @pdGenerated default remove
     * @param oldAttribute */
   public void removeAttributes(Attribute oldAttribute) {
      if (oldAttribute == null)
         return;
      if (this.attributes != null)
         if (this.attributes.contains(oldAttribute))
            this.attributes.remove(oldAttribute);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAttributes() {
      if (attributes != null)
         attributes.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Relation> getRelations() {
      if (relations == null)
         relations = new java.util.ArrayList<Relation>();
      return relations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRelations() {
      if (relations == null)
         relations = new java.util.ArrayList<Relation>();
      return relations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRelations */
   public void setRelations(java.util.Collection<Relation> newRelations) {
      removeAllRelations();
      for (java.util.Iterator iter = newRelations.iterator(); iter.hasNext();)
         addRelations((Relation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRelation */
   public void addRelations(Relation newRelation) {
      if (newRelation == null)
         return;
      if (this.relations == null)
         this.relations = new java.util.ArrayList<Relation>();
      if (!this.relations.contains(newRelation))
         this.relations.add(newRelation);
   }
   
   /** @pdGenerated default remove
     * @param oldRelation */
   public void removeRelations(Relation oldRelation) {
      if (oldRelation == null)
         return;
      if (this.relations != null)
         if (this.relations.contains(oldRelation))
            this.relations.remove(oldRelation);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRelations() {
      if (relations != null)
         relations.clear();
   }

}
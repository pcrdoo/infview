package model;

import java.util.*;

public class Entity extends InfResource {
	
   private ArrayList<Attribute> attributes;
   private ArrayList<Relation> relations;
   
   public ArrayList<Attribute> getAttributes() {
      if (attributes == null)
         attributes = new ArrayList<Attribute>();
      return attributes;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAttributes() {
      if (attributes == null)
         attributes = new ArrayList<Attribute>();
      return attributes.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAttributes */
   public void setAttributes(ArrayList<Attribute> newAttributes) {
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
         this.attributes = new ArrayList<Attribute>();
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
   public ArrayList<Relation> getRelations() {
      if (relations == null)
         relations = new ArrayList<Relation>();
      return relations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRelations() {
      if (relations == null)
         relations = new ArrayList<Relation>();
      return relations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRelations */
   public void setRelations(ArrayList<Relation> newRelations) {
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
         this.relations = new ArrayList<Relation>();
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
package model;

import java.util.*;

public class Entity extends InfResource {
	
   private HashSet<Attribute> attributes;
   private HashSet<Relation> relations;
   private HashSet<Entry> entries;
   
   public Entity() {
       attributes = new HashSet<Attribute>();
       relations = new HashSet<Relation>();
       entries = new HashSet<Entry>();
   }
   
   public HashSet<Attribute> getAttributes() {
      if (attributes == null)
         attributes = new HashSet<Attribute>();
      return attributes;
   }
   
   public void addAttribute(Attribute newAttribute) {
      if (newAttribute == null)
         return;
      if (this.attributes == null)
         this.attributes = new HashSet<Attribute>();
      if (!this.attributes.contains(newAttribute))
         this.attributes.add(newAttribute);
   }
   
   public void removeAttribute(Attribute oldAttribute) {
      if (oldAttribute == null)
         return;
      if (this.attributes != null)
         if (this.attributes.contains(oldAttribute))
            this.attributes.remove(oldAttribute);
   }
   
   public void removeAllAttributes() {
      if (attributes != null)
         attributes.clear();
   }
   
   public HashSet<Relation> getRelations() {
      if (relations == null)
         relations = new HashSet<Relation>();
      return relations;
   }
   
   public void addRelation(Relation newRelation) {
      if (newRelation == null)
         return;
      if (this.relations == null)
         this.relations = new HashSet<Relation>();
      if (!this.relations.contains(newRelation))
         this.relations.add(newRelation);
   }
   
   public void removeRelation(Relation oldRelation) {
      if (oldRelation == null)
         return;
      if (this.relations != null)
         if (this.relations.contains(oldRelation))
            this.relations.remove(oldRelation);
   }
   
   public void removeAllRelations() {
      if (relations != null)
         relations.clear();
   }
   
   public HashSet<Entry> getEntries() {
      if (entries == null)
    	  entries = new HashSet<Entry>();
      return entries;
   }
   
   public void addAttribute(Entry newEntry) {
      if (newEntry == null)
         return;
      if (this.entries == null)
         this.entries = new HashSet<Entry>();
      if (!this.entries.contains(newEntry))
         this.entries.add(newEntry);
   }
   
   public void removeEntry(Entry oldEntry) {
      if (oldEntry == null)
         return;
      if (this.entries != null)
         if (this.entries.contains(oldEntry))
            this.entries.remove(oldEntry);
   }
   
   public void removeAllEntries() {
      if (entries != null)
    	  entries.clear();
   }

}
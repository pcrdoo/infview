package model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Entity extends InfResource {
	
   private ArrayList<Attribute> attributes;
   private ArrayList<Relation> relations;
   private ArrayList<Entry> entries;
   
   public Entity(String name) {
	   this.name = name;
       attributes = new ArrayList<Attribute>();
       relations = new ArrayList<Relation>();
       entries = new ArrayList<Entry>();
   }
   
   @Override
   public boolean equals(Object o) {
	   if(!(o instanceof Entity)) {
		   return false;
	   }
	   Entity e = (Entity)o;
	   if(name.equals(e.name) && attributes.equals(e.attributes) &&
			   relations.equals(e.relations) && entries.equals(e.entries)) {
		   return true;
	   }
	   return false;
   }

	@Override
	public List<? extends InfResource> getChildren() {
		return Stream.concat(this.attributes.stream(), this.relations.stream()).collect(Collectors.toList());
	}
   
   public ArrayList<Attribute> getAttributes() {
      if (attributes == null)
         attributes = new ArrayList<Attribute>();
      return attributes;
   }
   
   public void addAttribute(Attribute newAttribute) {
      if (newAttribute == null)
         return;
      if (this.attributes == null)
         this.attributes = new ArrayList<Attribute>();
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
   
   public ArrayList<Relation> getRelations() {
      if (relations == null)
         relations = new ArrayList<Relation>();
      return relations;
   }
   
   public void addRelation(Relation newRelation) {
      if (newRelation == null)
         return;
      if (this.relations == null)
         this.relations = new ArrayList<Relation>();
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
   
   public ArrayList<Entry> getEntries() {
      if (entries == null)
    	  entries = new ArrayList<Entry>();
      return entries;
   }
   
   public void addEntry(Entry newEntry) {
      if (newEntry == null)
         return;
      if (this.entries == null)
         this.entries = new ArrayList<Entry>();
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

   public String toIndentedString(int indentSpaces) {
	   ArrayList<String> relationStrings = new ArrayList<>();
	   for (Relation r : relations) {
		   relationStrings.add(r.toIndentedString(8));
	   }
	   String relationsStr = String.join("\n", relationStrings);
	   
	   ArrayList<String> attributeStrings = new ArrayList<>();
	   for (Attribute a : attributes) {
		   attributeStrings.add(a.toIndentedString(8));
	   }
	   String attributesStr = String.join("\n", attributeStrings);
	   
	   return indentStringRepresentation(String.format(
			   "Entity \"%s\" {\n" +
	           "    attributes = [\n" +
			   "%s\n" +
	           "    ]\n" +
			   "    relations = [\n" +
	           "%s\n" +
	           "    ]\n" +
			   "}", name, attributesStr, relationsStr), indentSpaces);
   }
}
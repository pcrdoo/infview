package model;

import java.util.*;

public class Package extends InfResource {

   private ArrayList<Entity> entities;
   private ArrayList<Package> subPackages;
   
   @Override
   protected List<? extends InfResource> getChildren() {
	   return this.entities.;
   }
}
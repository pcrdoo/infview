/***********************************************************************
 * Module:  Relation.java
 * Author:  Random
 * Purpose: Defines the Class Relation
 ***********************************************************************/

package model;

import java.util.*;

/** @pdOid c127888f-e3b9-4b94-9b7c-8b1a3e10b793 */
public class Relation extends InfResource {
   private Entity objectEntity;
   public Relation(String name, Entity objectEntity) {
	   this.name = name;
	   this.objectEntity = objectEntity;
   }

}
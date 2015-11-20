/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 80 "../../../../../../../SDK.ump"
public class UpdateDynCreativeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UpdateDynCreativeRequest Attributes
  private List<DynCreativeType> dynCreativeTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setDynCreativeTypes(List<DynCreativeType> adynCreativeTypes){
    dynCreativeTypes = adynCreativeTypes;
  }

  public boolean addDynCreativeType(DynCreativeType aDynCreativeType)
  {
    boolean wasAdded = false;
    wasAdded = dynCreativeTypes.add(aDynCreativeType);
    return wasAdded;
  }

  public boolean removeDynCreativeType(DynCreativeType aDynCreativeType)
  {
    boolean wasRemoved = false;
    wasRemoved = dynCreativeTypes.remove(aDynCreativeType);
    return wasRemoved;
  }

  public DynCreativeType getDynCreativeType(int index)
  {
    DynCreativeType aDynCreativeType = dynCreativeTypes.get(index);
    return aDynCreativeType;
  }

  public List<DynCreativeType> getDynCreativeTypes()
  {
    return dynCreativeTypes;
  }

  public int numberOfDynCreativeTypes()
  {
    int number = dynCreativeTypes.size();
    return number;
  }

  public boolean hasDynCreativeTypes()
  {
    boolean has = dynCreativeTypes.size() > 0;
    return has;
  }

  public int indexOfDynCreativeType(DynCreativeType aDynCreativeType)
  {
    int index = dynCreativeTypes.indexOf(aDynCreativeType);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]"
     + outputString;
  }
}
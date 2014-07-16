/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 34 "../../../../../../../SDKDemo.ump"
public class AddExclusionTypeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddExclusionTypeRequest Attributes
  private List<DynCreativeExclusionType> dynCreativeExclusionTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setDynCreativeExclusionTypes(List<DynCreativeExclusionType> adynCreativeExclusionTypes){
    dynCreativeExclusionTypes = adynCreativeExclusionTypes;
  }

  public boolean addDynCreativeExclusionType(DynCreativeExclusionType aDynCreativeExclusionType)
  {
    boolean wasAdded = false;
    wasAdded = dynCreativeExclusionTypes.add(aDynCreativeExclusionType);
    return wasAdded;
  }

  public boolean removeDynCreativeExclusionType(DynCreativeExclusionType aDynCreativeExclusionType)
  {
    boolean wasRemoved = false;
    wasRemoved = dynCreativeExclusionTypes.remove(aDynCreativeExclusionType);
    return wasRemoved;
  }

  public DynCreativeExclusionType getDynCreativeExclusionType(int index)
  {
    DynCreativeExclusionType aDynCreativeExclusionType = dynCreativeExclusionTypes.get(index);
    return aDynCreativeExclusionType;
  }

  public List<DynCreativeExclusionType> getDynCreativeExclusionTypes()
  {
    return dynCreativeExclusionTypes;
  }

  public int numberOfDynCreativeExclusionTypes()
  {
    int number = dynCreativeExclusionTypes.size();
    return number;
  }

  public boolean hasDynCreativeExclusionTypes()
  {
    boolean has = dynCreativeExclusionTypes.size() > 0;
    return has;
  }

  public int indexOfDynCreativeExclusionType(DynCreativeExclusionType aDynCreativeExclusionType)
  {
    int index = dynCreativeExclusionTypes.indexOf(aDynCreativeExclusionType);
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 78 "../../../../../../../SDKDemo.ump"
public class UpdateSublinkRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UpdateSublinkRequest Attributes
  private List<SublinkType> sublinkTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setSublinkTypes(List<SublinkType> asublinkTypes){
    sublinkTypes = asublinkTypes;
  }

  public boolean addSublinkType(SublinkType aSublinkType)
  {
    boolean wasAdded = false;
    wasAdded = sublinkTypes.add(aSublinkType);
    return wasAdded;
  }

  public boolean removeSublinkType(SublinkType aSublinkType)
  {
    boolean wasRemoved = false;
    wasRemoved = sublinkTypes.remove(aSublinkType);
    return wasRemoved;
  }

  public SublinkType getSublinkType(int index)
  {
    SublinkType aSublinkType = sublinkTypes.get(index);
    return aSublinkType;
  }

  public List<SublinkType> getSublinkTypes()
  {
    return sublinkTypes;
  }

  public int numberOfSublinkTypes()
  {
    int number = sublinkTypes.size();
    return number;
  }

  public boolean hasSublinkTypes()
  {
    boolean has = sublinkTypes.size() > 0;
    return has;
  }

  public int indexOfSublinkType(SublinkType aSublinkType)
  {
    int index = sublinkTypes.indexOf(aSublinkType);
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
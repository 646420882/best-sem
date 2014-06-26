/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 128 "../../../../../../../SDKDemo.ump"
public class UpdateMobileSublinkResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UpdateMobileSublinkResponse Attributes
  private List<MobileSublinkType> mobileSublinkTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setMobileSublinkTypes(List<MobileSublinkType> amobileSublinkTypes){
    mobileSublinkTypes = amobileSublinkTypes;
  }

  public boolean addMobileSublinkType(MobileSublinkType aMobileSublinkType)
  {
    boolean wasAdded = false;
    wasAdded = mobileSublinkTypes.add(aMobileSublinkType);
    return wasAdded;
  }

  public boolean removeMobileSublinkType(MobileSublinkType aMobileSublinkType)
  {
    boolean wasRemoved = false;
    wasRemoved = mobileSublinkTypes.remove(aMobileSublinkType);
    return wasRemoved;
  }

  public MobileSublinkType getMobileSublinkType(int index)
  {
    MobileSublinkType aMobileSublinkType = mobileSublinkTypes.get(index);
    return aMobileSublinkType;
  }

  public List<MobileSublinkType> getMobileSublinkTypes()
  {
    return mobileSublinkTypes;
  }

  public int numberOfMobileSublinkTypes()
  {
    int number = mobileSublinkTypes.size();
    return number;
  }

  public boolean hasMobileSublinkTypes()
  {
    boolean has = mobileSublinkTypes.size() > 0;
    return has;
  }

  public int indexOfMobileSublinkType(MobileSublinkType aMobileSublinkType)
  {
    int index = mobileSublinkTypes.indexOf(aMobileSublinkType);
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
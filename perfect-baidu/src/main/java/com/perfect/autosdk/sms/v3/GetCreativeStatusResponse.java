/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 132 "../../../../../../../SDKDemo.ump"
public class GetCreativeStatusResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCreativeStatusResponse Attributes
  private List<StatusType> creativeStatus;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCreativeStatus(List<StatusType> acreativeStatus){
    creativeStatus = acreativeStatus;
  }

  public boolean addCreativeStatus(StatusType aCreativeStatus)
  {
    boolean wasAdded = false;
    wasAdded = creativeStatus.add(aCreativeStatus);
    return wasAdded;
  }

  public boolean removeCreativeStatus(StatusType aCreativeStatus)
  {
    boolean wasRemoved = false;
    wasRemoved = creativeStatus.remove(aCreativeStatus);
    return wasRemoved;
  }

  public StatusType getCreativeStatus(int index)
  {
    StatusType aCreativeStatus = creativeStatus.get(index);
    return aCreativeStatus;
  }

  public List<StatusType> getCreativeStatus()
  {
    return creativeStatus;
  }

  public int numberOfCreativeStatus()
  {
    int number = creativeStatus.size();
    return number;
  }

  public boolean hasCreativeStatus()
  {
    boolean has = creativeStatus.size() > 0;
    return has;
  }

  public int indexOfCreativeStatus(StatusType aCreativeStatus)
  {
    int index = creativeStatus.indexOf(aCreativeStatus);
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
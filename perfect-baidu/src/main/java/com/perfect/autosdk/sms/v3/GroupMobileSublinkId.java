/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 133 "../../../../../../../SDKDemo.ump"
public class GroupMobileSublinkId
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GroupMobileSublinkId Attributes
  private Long adgroupId;
  private List<Long> mobileSublinkIds;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public void setMobileSublinkIds(List<Long> amobileSublinkIds){
    mobileSublinkIds = amobileSublinkIds;
  }

  public boolean addMobileSublinkId(Long aMobileSublinkId)
  {
    boolean wasAdded = false;
    wasAdded = mobileSublinkIds.add(aMobileSublinkId);
    return wasAdded;
  }

  public boolean removeMobileSublinkId(Long aMobileSublinkId)
  {
    boolean wasRemoved = false;
    wasRemoved = mobileSublinkIds.remove(aMobileSublinkId);
    return wasRemoved;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getMobileSublinkId(int index)
  {
    Long aMobileSublinkId = mobileSublinkIds.get(index);
    return aMobileSublinkId;
  }

  public List<Long> getMobileSublinkIds()
  {
    return mobileSublinkIds;
  }

  public int numberOfMobileSublinkIds()
  {
    int number = mobileSublinkIds.size();
    return number;
  }

  public boolean hasMobileSublinkIds()
  {
    boolean has = mobileSublinkIds.size() > 0;
    return has;
  }

  public int indexOfMobileSublinkId(Long aMobileSublinkId)
  {
    int index = mobileSublinkIds.indexOf(aMobileSublinkId);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupId" + ":" + getAdgroupId()+ "]"
     + outputString;
  }
}
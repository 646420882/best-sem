/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 190 "../../../../../../../SDKDemo.ump"
public class GroupPhoneId
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GroupPhoneId Attributes
  private Long adgroupId;
  private List<Long> phoneIds;

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

  public void setPhoneIds(List<Long> aphoneIds){
    phoneIds = aphoneIds;
  }

  public boolean addPhoneId(Long aPhoneId)
  {
    boolean wasAdded = false;
    wasAdded = phoneIds.add(aPhoneId);
    return wasAdded;
  }

  public boolean removePhoneId(Long aPhoneId)
  {
    boolean wasRemoved = false;
    wasRemoved = phoneIds.remove(aPhoneId);
    return wasRemoved;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getPhoneId(int index)
  {
    Long aPhoneId = phoneIds.get(index);
    return aPhoneId;
  }

  public List<Long> getPhoneIds()
  {
    return phoneIds;
  }

  public int numberOfPhoneIds()
  {
    int number = phoneIds.size();
    return number;
  }

  public boolean hasPhoneIds()
  {
    boolean has = phoneIds.size() > 0;
    return has;
  }

  public int indexOfPhoneId(Long aPhoneId)
  {
    int index = phoneIds.indexOf(aPhoneId);
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
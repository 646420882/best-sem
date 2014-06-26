/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 201 "../../../../../../../SDKDemo.ump"
public class GetPhoneIdByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetPhoneIdByAdgroupIdResponse Attributes
  private List<GroupPhoneId> groupPhoneIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupPhoneIds(List<GroupPhoneId> agroupPhoneIds){
    groupPhoneIds = agroupPhoneIds;
  }

  public boolean addGroupPhoneId(GroupPhoneId aGroupPhoneId)
  {
    boolean wasAdded = false;
    wasAdded = groupPhoneIds.add(aGroupPhoneId);
    return wasAdded;
  }

  public boolean removeGroupPhoneId(GroupPhoneId aGroupPhoneId)
  {
    boolean wasRemoved = false;
    wasRemoved = groupPhoneIds.remove(aGroupPhoneId);
    return wasRemoved;
  }

  public GroupPhoneId getGroupPhoneId(int index)
  {
    GroupPhoneId aGroupPhoneId = groupPhoneIds.get(index);
    return aGroupPhoneId;
  }

  public List<GroupPhoneId> getGroupPhoneIds()
  {
    return groupPhoneIds;
  }

  public int numberOfGroupPhoneIds()
  {
    int number = groupPhoneIds.size();
    return number;
  }

  public boolean hasGroupPhoneIds()
  {
    boolean has = groupPhoneIds.size() > 0;
    return has;
  }

  public int indexOfGroupPhoneId(GroupPhoneId aGroupPhoneId)
  {
    int index = groupPhoneIds.indexOf(aGroupPhoneId);
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
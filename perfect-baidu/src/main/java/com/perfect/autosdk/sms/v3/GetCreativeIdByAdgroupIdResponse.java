/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 99 "../../../../../../../SDKDemo.ump"
public class GetCreativeIdByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCreativeIdByAdgroupIdResponse Attributes
  private List<GroupCreativeId> groupCreativeIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupCreativeIds(List<GroupCreativeId> agroupCreativeIds){
    groupCreativeIds = agroupCreativeIds;
  }

  public boolean addGroupCreativeId(GroupCreativeId aGroupCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = groupCreativeIds.add(aGroupCreativeId);
    return wasAdded;
  }

  public boolean removeGroupCreativeId(GroupCreativeId aGroupCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = groupCreativeIds.remove(aGroupCreativeId);
    return wasRemoved;
  }

  public GroupCreativeId getGroupCreativeId(int index)
  {
    GroupCreativeId aGroupCreativeId = groupCreativeIds.get(index);
    return aGroupCreativeId;
  }

  public List<GroupCreativeId> getGroupCreativeIds()
  {
    return groupCreativeIds;
  }

  public int numberOfGroupCreativeIds()
  {
    int number = groupCreativeIds.size();
    return number;
  }

  public boolean hasGroupCreativeIds()
  {
    boolean has = groupCreativeIds.size() > 0;
    return has;
  }

  public int indexOfGroupCreativeId(GroupCreativeId aGroupCreativeId)
  {
    int index = groupCreativeIds.indexOf(aGroupCreativeId);
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
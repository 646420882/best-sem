/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 145 "../../../../../../../SDKDemo.ump"
public class GetMobileSublinkIdByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetMobileSublinkIdByAdgroupIdResponse Attributes
  private List<GroupMobileSublinkId> groupMobileSublinkIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupMobileSublinkIds(List<GroupMobileSublinkId> agroupMobileSublinkIds){
    groupMobileSublinkIds = agroupMobileSublinkIds;
  }

  public boolean addGroupMobileSublinkId(GroupMobileSublinkId aGroupMobileSublinkId)
  {
    boolean wasAdded = false;
    wasAdded = groupMobileSublinkIds.add(aGroupMobileSublinkId);
    return wasAdded;
  }

  public boolean removeGroupMobileSublinkId(GroupMobileSublinkId aGroupMobileSublinkId)
  {
    boolean wasRemoved = false;
    wasRemoved = groupMobileSublinkIds.remove(aGroupMobileSublinkId);
    return wasRemoved;
  }

  public GroupMobileSublinkId getGroupMobileSublinkId(int index)
  {
    GroupMobileSublinkId aGroupMobileSublinkId = groupMobileSublinkIds.get(index);
    return aGroupMobileSublinkId;
  }

  public List<GroupMobileSublinkId> getGroupMobileSublinkIds()
  {
    return groupMobileSublinkIds;
  }

  public int numberOfGroupMobileSublinkIds()
  {
    int number = groupMobileSublinkIds.size();
    return number;
  }

  public boolean hasGroupMobileSublinkIds()
  {
    boolean has = groupMobileSublinkIds.size() > 0;
    return has;
  }

  public int indexOfGroupMobileSublinkId(GroupMobileSublinkId aGroupMobileSublinkId)
  {
    int index = groupMobileSublinkIds.indexOf(aGroupMobileSublinkId);
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
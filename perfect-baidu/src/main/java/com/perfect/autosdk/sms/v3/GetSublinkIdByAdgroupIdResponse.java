/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 37 "../../../../../../../SDKDemo.ump"
public class GetSublinkIdByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetSublinkIdByAdgroupIdResponse Attributes
  private List<GroupSublinkId> groupSublinkIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupSublinkIds(List<GroupSublinkId> agroupSublinkIds){
    groupSublinkIds = agroupSublinkIds;
  }

  public boolean addGroupSublinkId(GroupSublinkId aGroupSublinkId)
  {
    boolean wasAdded = false;
    wasAdded = groupSublinkIds.add(aGroupSublinkId);
    return wasAdded;
  }

  public boolean removeGroupSublinkId(GroupSublinkId aGroupSublinkId)
  {
    boolean wasRemoved = false;
    wasRemoved = groupSublinkIds.remove(aGroupSublinkId);
    return wasRemoved;
  }

  public GroupSublinkId getGroupSublinkId(int index)
  {
    GroupSublinkId aGroupSublinkId = groupSublinkIds.get(index);
    return aGroupSublinkId;
  }

  public List<GroupSublinkId> getGroupSublinkIds()
  {
    return groupSublinkIds;
  }

  public int numberOfGroupSublinkIds()
  {
    int number = groupSublinkIds.size();
    return number;
  }

  public boolean hasGroupSublinkIds()
  {
    boolean has = groupSublinkIds.size() > 0;
    return has;
  }

  public int indexOfGroupSublinkId(GroupSublinkId aGroupSublinkId)
  {
    int index = groupSublinkIds.indexOf(aGroupSublinkId);
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
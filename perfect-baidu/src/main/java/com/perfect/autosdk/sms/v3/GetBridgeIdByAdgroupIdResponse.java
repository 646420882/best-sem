/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 255 "../../../../../../../SDKDemo.ump"
public class GetBridgeIdByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetBridgeIdByAdgroupIdResponse Attributes
  private List<GroupBridgeId> groupBridgeIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupBridgeIds(List<GroupBridgeId> agroupBridgeIds){
    groupBridgeIds = agroupBridgeIds;
  }

  public boolean addGroupBridgeId(GroupBridgeId aGroupBridgeId)
  {
    boolean wasAdded = false;
    wasAdded = groupBridgeIds.add(aGroupBridgeId);
    return wasAdded;
  }

  public boolean removeGroupBridgeId(GroupBridgeId aGroupBridgeId)
  {
    boolean wasRemoved = false;
    wasRemoved = groupBridgeIds.remove(aGroupBridgeId);
    return wasRemoved;
  }

  public GroupBridgeId getGroupBridgeId(int index)
  {
    GroupBridgeId aGroupBridgeId = groupBridgeIds.get(index);
    return aGroupBridgeId;
  }

  public List<GroupBridgeId> getGroupBridgeIds()
  {
    return groupBridgeIds;
  }

  public int numberOfGroupBridgeIds()
  {
    int number = groupBridgeIds.size();
    return number;
  }

  public boolean hasGroupBridgeIds()
  {
    boolean has = groupBridgeIds.size() > 0;
    return has;
  }

  public int indexOfGroupBridgeId(GroupBridgeId aGroupBridgeId)
  {
    int index = groupBridgeIds.indexOf(aGroupBridgeId);
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
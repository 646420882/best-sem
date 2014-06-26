/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 112 "../../../../../../../SDKDemo.ump"
public class GetKeywordIdByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordIdByAdgroupIdResponse Attributes
  private List<GroupKeywordId> groupKeywordIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupKeywordIds(List<GroupKeywordId> agroupKeywordIds){
    groupKeywordIds = agroupKeywordIds;
  }

  public boolean addGroupKeywordId(GroupKeywordId aGroupKeywordId)
  {
    boolean wasAdded = false;
    wasAdded = groupKeywordIds.add(aGroupKeywordId);
    return wasAdded;
  }

  public boolean removeGroupKeywordId(GroupKeywordId aGroupKeywordId)
  {
    boolean wasRemoved = false;
    wasRemoved = groupKeywordIds.remove(aGroupKeywordId);
    return wasRemoved;
  }

  public GroupKeywordId getGroupKeywordId(int index)
  {
    GroupKeywordId aGroupKeywordId = groupKeywordIds.get(index);
    return aGroupKeywordId;
  }

  public List<GroupKeywordId> getGroupKeywordIds()
  {
    return groupKeywordIds;
  }

  public int numberOfGroupKeywordIds()
  {
    int number = groupKeywordIds.size();
    return number;
  }

  public boolean hasGroupKeywordIds()
  {
    boolean has = groupKeywordIds.size() > 0;
    return has;
  }

  public int indexOfGroupKeywordId(GroupKeywordId aGroupKeywordId)
  {
    int index = groupKeywordIds.indexOf(aGroupKeywordId);
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
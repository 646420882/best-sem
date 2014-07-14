/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 110 "../../../../../../../SDKDemo.ump"
public class GetCreativeByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCreativeByAdgroupIdResponse Attributes
  private List<GroupCreative> groupCreatives;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupCreatives(List<GroupCreative> agroupCreatives){
    groupCreatives = agroupCreatives;
  }

  public boolean addGroupCreative(GroupCreative aGroupCreative)
  {
    boolean wasAdded = false;
    wasAdded = groupCreatives.add(aGroupCreative);
    return wasAdded;
  }

  public boolean removeGroupCreative(GroupCreative aGroupCreative)
  {
    boolean wasRemoved = false;
    wasRemoved = groupCreatives.remove(aGroupCreative);
    return wasRemoved;
  }

  public GroupCreative getGroupCreative(int index)
  {
    GroupCreative aGroupCreative = groupCreatives.get(index);
    return aGroupCreative;
  }

  public List<GroupCreative> getGroupCreatives()
  {
    return groupCreatives;
  }

  public int numberOfGroupCreatives()
  {
    int number = groupCreatives.size();
    return number;
  }

  public boolean hasGroupCreatives()
  {
    boolean has = groupCreatives.size() > 0;
    return has;
  }

  public int indexOfGroupCreative(GroupCreative aGroupCreative)
  {
    int index = groupCreatives.indexOf(aGroupCreative);
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 124 "../../../../../../../SDKDemo.ump"
public class GetKeywordByAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordByAdgroupIdResponse Attributes
  private List<GroupKeyword> groupKeywords;

  //------------------------
  // INTERFACE
  //------------------------

  public void setGroupKeywords(List<GroupKeyword> agroupKeywords){
    groupKeywords = agroupKeywords;
  }

  public boolean addGroupKeyword(GroupKeyword aGroupKeyword)
  {
    boolean wasAdded = false;
    wasAdded = groupKeywords.add(aGroupKeyword);
    return wasAdded;
  }

  public boolean removeGroupKeyword(GroupKeyword aGroupKeyword)
  {
    boolean wasRemoved = false;
    wasRemoved = groupKeywords.remove(aGroupKeyword);
    return wasRemoved;
  }

  public GroupKeyword getGroupKeyword(int index)
  {
    GroupKeyword aGroupKeyword = groupKeywords.get(index);
    return aGroupKeyword;
  }

  public List<GroupKeyword> getGroupKeywords()
  {
    return groupKeywords;
  }

  public int numberOfGroupKeywords()
  {
    int number = groupKeywords.size();
    return number;
  }

  public boolean hasGroupKeywords()
  {
    boolean has = groupKeywords.size() > 0;
    return has;
  }

  public int indexOfGroupKeyword(GroupKeyword aGroupKeyword)
  {
    int index = groupKeywords.indexOf(aGroupKeyword);
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
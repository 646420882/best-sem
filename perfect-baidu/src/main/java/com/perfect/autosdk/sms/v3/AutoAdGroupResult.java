/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 19 "../../../../../../../SDKDemo.ump"
public class AutoAdGroupResult
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AutoAdGroupResult Attributes
  private Long adgroupId;
  private String adgroupName;
  private Boolean isNewAdgroup;
  private List<String> keywords;

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

  public boolean setAdgroupName(String aAdgroupName)
  {
    boolean wasSet = false;
    adgroupName = aAdgroupName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsNewAdgroup(Boolean aIsNewAdgroup)
  {
    boolean wasSet = false;
    isNewAdgroup = aIsNewAdgroup;
    wasSet = true;
    return wasSet;
  }

  public void setKeywords(List<String> akeywords){
    keywords = akeywords;
  }

  public boolean addKeyword(String aKeyword)
  {
    boolean wasAdded = false;
    wasAdded = keywords.add(aKeyword);
    return wasAdded;
  }

  public boolean removeKeyword(String aKeyword)
  {
    boolean wasRemoved = false;
    wasRemoved = keywords.remove(aKeyword);
    return wasRemoved;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public String getAdgroupName()
  {
    return adgroupName;
  }

  public Boolean getIsNewAdgroup()
  {
    return isNewAdgroup;
  }

  public String getKeyword(int index)
  {
    String aKeyword = keywords.get(index);
    return aKeyword;
  }

  public List<String> getKeywords()
  {
    return keywords;
  }

  public int numberOfKeywords()
  {
    int number = keywords.size();
    return number;
  }

  public boolean hasKeywords()
  {
    boolean has = keywords.size() > 0;
    return has;
  }

  public int indexOfKeyword(String aKeyword)
  {
    int index = keywords.indexOf(aKeyword);
    return index;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "adgroupName" + ":" + getAdgroupName()+ "," +
            "isNewAdgroup" + ":" + getIsNewAdgroup()+ "]"
     + outputString;
  }
}
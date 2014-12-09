/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 36 "../../../../../../../SDKDemo.ump"
public class GroupKeywordId
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GroupKeywordId Attributes
  private Long adgroupId;
  private List<Long> keywordIds;

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

  public void setKeywordIds(List<Long> akeywordIds){
    keywordIds = akeywordIds;
  }

  public boolean addKeywordId(Long aKeywordId)
  {
    boolean wasAdded = false;
    wasAdded = keywordIds.add(aKeywordId);
    return wasAdded;
  }

  public boolean removeKeywordId(Long aKeywordId)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordIds.remove(aKeywordId);
    return wasRemoved;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getKeywordId(int index)
  {
    Long aKeywordId = keywordIds.get(index);
    return aKeywordId;
  }

  public List<Long> getKeywordIds()
  {
    return keywordIds;
  }

  public int numberOfKeywordIds()
  {
    int number = keywordIds.size();
    return number;
  }

  public boolean hasKeywordIds()
  {
    boolean has = keywordIds.size() > 0;
    return has;
  }

  public int indexOfKeywordId(Long aKeywordId)
  {
    int index = keywordIds.indexOf(aKeywordId);
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.Date;
import java.util.*;

// line 122 "../../../../../../../SDKDemo.ump"
public class GetChangedItemIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedItemIdResponse Attributes
  private Date endTime;
  private List<ChangedItemType> changedKeywordIds;
  private List<ChangedItemType> changedCreativeIds;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEndTime(Date aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public void setChangedKeywordIds(List<ChangedItemType> achangedKeywordIds){
    changedKeywordIds = achangedKeywordIds;
  }

  public boolean addChangedKeywordId(ChangedItemType aChangedKeywordId)
  {
    boolean wasAdded = false;
    wasAdded = changedKeywordIds.add(aChangedKeywordId);
    return wasAdded;
  }

  public boolean removeChangedKeywordId(ChangedItemType aChangedKeywordId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedKeywordIds.remove(aChangedKeywordId);
    return wasRemoved;
  }

  public void setChangedCreativeIds(List<ChangedItemType> achangedCreativeIds){
    changedCreativeIds = achangedCreativeIds;
  }

  public boolean addChangedCreativeId(ChangedItemType aChangedCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = changedCreativeIds.add(aChangedCreativeId);
    return wasAdded;
  }

  public boolean removeChangedCreativeId(ChangedItemType aChangedCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCreativeIds.remove(aChangedCreativeId);
    return wasRemoved;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ChangedItemType getChangedKeywordId(int index)
  {
    ChangedItemType aChangedKeywordId = changedKeywordIds.get(index);
    return aChangedKeywordId;
  }

  public List<ChangedItemType> getChangedKeywordIds()
  {
    return changedKeywordIds;
  }

  public int numberOfChangedKeywordIds()
  {
    int number = changedKeywordIds.size();
    return number;
  }

  public boolean hasChangedKeywordIds()
  {
    boolean has = changedKeywordIds.size() > 0;
    return has;
  }

  public int indexOfChangedKeywordId(ChangedItemType aChangedKeywordId)
  {
    int index = changedKeywordIds.indexOf(aChangedKeywordId);
    return index;
  }

  public ChangedItemType getChangedCreativeId(int index)
  {
    ChangedItemType aChangedCreativeId = changedCreativeIds.get(index);
    return aChangedCreativeId;
  }

  public List<ChangedItemType> getChangedCreativeIds()
  {
    return changedCreativeIds;
  }

  public int numberOfChangedCreativeIds()
  {
    int number = changedCreativeIds.size();
    return number;
  }

  public boolean hasChangedCreativeIds()
  {
    boolean has = changedCreativeIds.size() > 0;
    return has;
  }

  public int indexOfChangedCreativeId(ChangedItemType aChangedCreativeId)
  {
    int index = changedCreativeIds.indexOf(aChangedCreativeId);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
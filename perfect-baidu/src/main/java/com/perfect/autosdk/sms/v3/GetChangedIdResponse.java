/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.Date;
import java.util.*;

// line 160 "../../../../../../../SDKDemo.ump"
public class GetChangedIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedIdResponse Attributes
  private Date endTime;
  private List<ChangedCampaignIdType> changedCampaignIds;
  private List<ChangedAdgroupIdType> changedAdgroupIds;
  private List<ChangedItemIdType> changedKeywordIds;
  private List<ChangedItemIdType> changedCreativeIds;
  private List<ChangedNewCreativeType> changedNewCreativeIds;

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

  public void setChangedCampaignIds(List<ChangedCampaignIdType> achangedCampaignIds){
    changedCampaignIds = achangedCampaignIds;
  }

  public boolean addChangedCampaignId(ChangedCampaignIdType aChangedCampaignId)
  {
    boolean wasAdded = false;
    wasAdded = changedCampaignIds.add(aChangedCampaignId);
    return wasAdded;
  }

  public boolean removeChangedCampaignId(ChangedCampaignIdType aChangedCampaignId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCampaignIds.remove(aChangedCampaignId);
    return wasRemoved;
  }

  public void setChangedAdgroupIds(List<ChangedAdgroupIdType> achangedAdgroupIds){
    changedAdgroupIds = achangedAdgroupIds;
  }

  public boolean addChangedAdgroupId(ChangedAdgroupIdType aChangedAdgroupId)
  {
    boolean wasAdded = false;
    wasAdded = changedAdgroupIds.add(aChangedAdgroupId);
    return wasAdded;
  }

  public boolean removeChangedAdgroupId(ChangedAdgroupIdType aChangedAdgroupId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedAdgroupIds.remove(aChangedAdgroupId);
    return wasRemoved;
  }

  public void setChangedKeywordIds(List<ChangedItemIdType> achangedKeywordIds){
    changedKeywordIds = achangedKeywordIds;
  }

  public boolean addChangedKeywordId(ChangedItemIdType aChangedKeywordId)
  {
    boolean wasAdded = false;
    wasAdded = changedKeywordIds.add(aChangedKeywordId);
    return wasAdded;
  }

  public boolean removeChangedKeywordId(ChangedItemIdType aChangedKeywordId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedKeywordIds.remove(aChangedKeywordId);
    return wasRemoved;
  }

  public void setChangedCreativeIds(List<ChangedItemIdType> achangedCreativeIds){
    changedCreativeIds = achangedCreativeIds;
  }

  public boolean addChangedCreativeId(ChangedItemIdType aChangedCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = changedCreativeIds.add(aChangedCreativeId);
    return wasAdded;
  }

  public boolean removeChangedCreativeId(ChangedItemIdType aChangedCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCreativeIds.remove(aChangedCreativeId);
    return wasRemoved;
  }

  public void setChangedNewCreativeIds(List<ChangedNewCreativeType> achangedNewCreativeIds){
    changedNewCreativeIds = achangedNewCreativeIds;
  }

  public boolean addChangedNewCreativeId(ChangedNewCreativeType aChangedNewCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = changedNewCreativeIds.add(aChangedNewCreativeId);
    return wasAdded;
  }

  public boolean removeChangedNewCreativeId(ChangedNewCreativeType aChangedNewCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedNewCreativeIds.remove(aChangedNewCreativeId);
    return wasRemoved;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ChangedCampaignIdType getChangedCampaignId(int index)
  {
    ChangedCampaignIdType aChangedCampaignId = changedCampaignIds.get(index);
    return aChangedCampaignId;
  }

  public List<ChangedCampaignIdType> getChangedCampaignIds()
  {
    return changedCampaignIds;
  }

  public int numberOfChangedCampaignIds()
  {
    int number = changedCampaignIds.size();
    return number;
  }

  public boolean hasChangedCampaignIds()
  {
    boolean has = changedCampaignIds.size() > 0;
    return has;
  }

  public int indexOfChangedCampaignId(ChangedCampaignIdType aChangedCampaignId)
  {
    int index = changedCampaignIds.indexOf(aChangedCampaignId);
    return index;
  }

  public ChangedAdgroupIdType getChangedAdgroupId(int index)
  {
    ChangedAdgroupIdType aChangedAdgroupId = changedAdgroupIds.get(index);
    return aChangedAdgroupId;
  }

  public List<ChangedAdgroupIdType> getChangedAdgroupIds()
  {
    return changedAdgroupIds;
  }

  public int numberOfChangedAdgroupIds()
  {
    int number = changedAdgroupIds.size();
    return number;
  }

  public boolean hasChangedAdgroupIds()
  {
    boolean has = changedAdgroupIds.size() > 0;
    return has;
  }

  public int indexOfChangedAdgroupId(ChangedAdgroupIdType aChangedAdgroupId)
  {
    int index = changedAdgroupIds.indexOf(aChangedAdgroupId);
    return index;
  }

  public ChangedItemIdType getChangedKeywordId(int index)
  {
    ChangedItemIdType aChangedKeywordId = changedKeywordIds.get(index);
    return aChangedKeywordId;
  }

  public List<ChangedItemIdType> getChangedKeywordIds()
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

  public int indexOfChangedKeywordId(ChangedItemIdType aChangedKeywordId)
  {
    int index = changedKeywordIds.indexOf(aChangedKeywordId);
    return index;
  }

  public ChangedItemIdType getChangedCreativeId(int index)
  {
    ChangedItemIdType aChangedCreativeId = changedCreativeIds.get(index);
    return aChangedCreativeId;
  }

  public List<ChangedItemIdType> getChangedCreativeIds()
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

  public int indexOfChangedCreativeId(ChangedItemIdType aChangedCreativeId)
  {
    int index = changedCreativeIds.indexOf(aChangedCreativeId);
    return index;
  }

  public ChangedNewCreativeType getChangedNewCreativeId(int index)
  {
    ChangedNewCreativeType aChangedNewCreativeId = changedNewCreativeIds.get(index);
    return aChangedNewCreativeId;
  }

  public List<ChangedNewCreativeType> getChangedNewCreativeIds()
  {
    return changedNewCreativeIds;
  }

  public int numberOfChangedNewCreativeIds()
  {
    int number = changedNewCreativeIds.size();
    return number;
  }

  public boolean hasChangedNewCreativeIds()
  {
    boolean has = changedNewCreativeIds.size() > 0;
    return has;
  }

  public int indexOfChangedNewCreativeId(ChangedNewCreativeType aChangedNewCreativeId)
  {
    int index = changedNewCreativeIds.indexOf(aChangedNewCreativeId);
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
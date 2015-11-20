/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;
import java.util.*;

// line 188 "../../../../../../../SDK.ump"
public class GetChangedIdsResponseData
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedIdsResponseData Attributes
  private Date endTime;
  private List<ChangedItemIdType> changedCampaignIds;
  private List<ChangedItemIdType> changedAdgroupIds;
  private List<ChangedItemIdType> changedKeywordIds;
  private List<ChangedItemIdType> changedCreativeIds;
  private List<ChangedItemIdType> changedSublinkIds;
  private List<ChangedItemIdType> changedMobileSublinkIds;
  private List<ChangedItemIdType> changedPhoneIds;
  private List<ChangedItemIdType> changedBridgeIds;
  private List<ChangedItemIdType> changedDynamicCreativeIds;

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

  public void setChangedCampaignIds(List<ChangedItemIdType> achangedCampaignIds){
    changedCampaignIds = achangedCampaignIds;
  }

  public boolean addChangedCampaignId(ChangedItemIdType aChangedCampaignId)
  {
    boolean wasAdded = false;
    wasAdded = changedCampaignIds.add(aChangedCampaignId);
    return wasAdded;
  }

  public boolean removeChangedCampaignId(ChangedItemIdType aChangedCampaignId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCampaignIds.remove(aChangedCampaignId);
    return wasRemoved;
  }

  public void setChangedAdgroupIds(List<ChangedItemIdType> achangedAdgroupIds){
    changedAdgroupIds = achangedAdgroupIds;
  }

  public boolean addChangedAdgroupId(ChangedItemIdType aChangedAdgroupId)
  {
    boolean wasAdded = false;
    wasAdded = changedAdgroupIds.add(aChangedAdgroupId);
    return wasAdded;
  }

  public boolean removeChangedAdgroupId(ChangedItemIdType aChangedAdgroupId)
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

  public void setChangedSublinkIds(List<ChangedItemIdType> achangedSublinkIds){
    changedSublinkIds = achangedSublinkIds;
  }

  public boolean addChangedSublinkId(ChangedItemIdType aChangedSublinkId)
  {
    boolean wasAdded = false;
    wasAdded = changedSublinkIds.add(aChangedSublinkId);
    return wasAdded;
  }

  public boolean removeChangedSublinkId(ChangedItemIdType aChangedSublinkId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedSublinkIds.remove(aChangedSublinkId);
    return wasRemoved;
  }

  public void setChangedMobileSublinkIds(List<ChangedItemIdType> achangedMobileSublinkIds){
    changedMobileSublinkIds = achangedMobileSublinkIds;
  }

  public boolean addChangedMobileSublinkId(ChangedItemIdType aChangedMobileSublinkId)
  {
    boolean wasAdded = false;
    wasAdded = changedMobileSublinkIds.add(aChangedMobileSublinkId);
    return wasAdded;
  }

  public boolean removeChangedMobileSublinkId(ChangedItemIdType aChangedMobileSublinkId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedMobileSublinkIds.remove(aChangedMobileSublinkId);
    return wasRemoved;
  }

  public void setChangedPhoneIds(List<ChangedItemIdType> achangedPhoneIds){
    changedPhoneIds = achangedPhoneIds;
  }

  public boolean addChangedPhoneId(ChangedItemIdType aChangedPhoneId)
  {
    boolean wasAdded = false;
    wasAdded = changedPhoneIds.add(aChangedPhoneId);
    return wasAdded;
  }

  public boolean removeChangedPhoneId(ChangedItemIdType aChangedPhoneId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedPhoneIds.remove(aChangedPhoneId);
    return wasRemoved;
  }

  public void setChangedBridgeIds(List<ChangedItemIdType> achangedBridgeIds){
    changedBridgeIds = achangedBridgeIds;
  }

  public boolean addChangedBridgeId(ChangedItemIdType aChangedBridgeId)
  {
    boolean wasAdded = false;
    wasAdded = changedBridgeIds.add(aChangedBridgeId);
    return wasAdded;
  }

  public boolean removeChangedBridgeId(ChangedItemIdType aChangedBridgeId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedBridgeIds.remove(aChangedBridgeId);
    return wasRemoved;
  }

  public void setChangedDynamicCreativeIds(List<ChangedItemIdType> achangedDynamicCreativeIds){
    changedDynamicCreativeIds = achangedDynamicCreativeIds;
  }

  public boolean addChangedDynamicCreativeId(ChangedItemIdType aChangedDynamicCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = changedDynamicCreativeIds.add(aChangedDynamicCreativeId);
    return wasAdded;
  }

  public boolean removeChangedDynamicCreativeId(ChangedItemIdType aChangedDynamicCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedDynamicCreativeIds.remove(aChangedDynamicCreativeId);
    return wasRemoved;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ChangedItemIdType getChangedCampaignId(int index)
  {
    ChangedItemIdType aChangedCampaignId = changedCampaignIds.get(index);
    return aChangedCampaignId;
  }

  public List<ChangedItemIdType> getChangedCampaignIds()
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

  public int indexOfChangedCampaignId(ChangedItemIdType aChangedCampaignId)
  {
    int index = changedCampaignIds.indexOf(aChangedCampaignId);
    return index;
  }

  public ChangedItemIdType getChangedAdgroupId(int index)
  {
    ChangedItemIdType aChangedAdgroupId = changedAdgroupIds.get(index);
    return aChangedAdgroupId;
  }

  public List<ChangedItemIdType> getChangedAdgroupIds()
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

  public int indexOfChangedAdgroupId(ChangedItemIdType aChangedAdgroupId)
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

  public ChangedItemIdType getChangedSublinkId(int index)
  {
    ChangedItemIdType aChangedSublinkId = changedSublinkIds.get(index);
    return aChangedSublinkId;
  }

  public List<ChangedItemIdType> getChangedSublinkIds()
  {
    return changedSublinkIds;
  }

  public int numberOfChangedSublinkIds()
  {
    int number = changedSublinkIds.size();
    return number;
  }

  public boolean hasChangedSublinkIds()
  {
    boolean has = changedSublinkIds.size() > 0;
    return has;
  }

  public int indexOfChangedSublinkId(ChangedItemIdType aChangedSublinkId)
  {
    int index = changedSublinkIds.indexOf(aChangedSublinkId);
    return index;
  }

  public ChangedItemIdType getChangedMobileSublinkId(int index)
  {
    ChangedItemIdType aChangedMobileSublinkId = changedMobileSublinkIds.get(index);
    return aChangedMobileSublinkId;
  }

  public List<ChangedItemIdType> getChangedMobileSublinkIds()
  {
    return changedMobileSublinkIds;
  }

  public int numberOfChangedMobileSublinkIds()
  {
    int number = changedMobileSublinkIds.size();
    return number;
  }

  public boolean hasChangedMobileSublinkIds()
  {
    boolean has = changedMobileSublinkIds.size() > 0;
    return has;
  }

  public int indexOfChangedMobileSublinkId(ChangedItemIdType aChangedMobileSublinkId)
  {
    int index = changedMobileSublinkIds.indexOf(aChangedMobileSublinkId);
    return index;
  }

  public ChangedItemIdType getChangedPhoneId(int index)
  {
    ChangedItemIdType aChangedPhoneId = changedPhoneIds.get(index);
    return aChangedPhoneId;
  }

  public List<ChangedItemIdType> getChangedPhoneIds()
  {
    return changedPhoneIds;
  }

  public int numberOfChangedPhoneIds()
  {
    int number = changedPhoneIds.size();
    return number;
  }

  public boolean hasChangedPhoneIds()
  {
    boolean has = changedPhoneIds.size() > 0;
    return has;
  }

  public int indexOfChangedPhoneId(ChangedItemIdType aChangedPhoneId)
  {
    int index = changedPhoneIds.indexOf(aChangedPhoneId);
    return index;
  }

  public ChangedItemIdType getChangedBridgeId(int index)
  {
    ChangedItemIdType aChangedBridgeId = changedBridgeIds.get(index);
    return aChangedBridgeId;
  }

  public List<ChangedItemIdType> getChangedBridgeIds()
  {
    return changedBridgeIds;
  }

  public int numberOfChangedBridgeIds()
  {
    int number = changedBridgeIds.size();
    return number;
  }

  public boolean hasChangedBridgeIds()
  {
    boolean has = changedBridgeIds.size() > 0;
    return has;
  }

  public int indexOfChangedBridgeId(ChangedItemIdType aChangedBridgeId)
  {
    int index = changedBridgeIds.indexOf(aChangedBridgeId);
    return index;
  }

  public ChangedItemIdType getChangedDynamicCreativeId(int index)
  {
    ChangedItemIdType aChangedDynamicCreativeId = changedDynamicCreativeIds.get(index);
    return aChangedDynamicCreativeId;
  }

  public List<ChangedItemIdType> getChangedDynamicCreativeIds()
  {
    return changedDynamicCreativeIds;
  }

  public int numberOfChangedDynamicCreativeIds()
  {
    int number = changedDynamicCreativeIds.size();
    return number;
  }

  public boolean hasChangedDynamicCreativeIds()
  {
    boolean has = changedDynamicCreativeIds.size() > 0;
    return has;
  }

  public int indexOfChangedDynamicCreativeId(ChangedItemIdType aChangedDynamicCreativeId)
  {
    int index = changedDynamicCreativeIds.indexOf(aChangedDynamicCreativeId);
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
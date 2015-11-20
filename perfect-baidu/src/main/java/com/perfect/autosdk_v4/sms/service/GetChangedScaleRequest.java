/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;
import java.util.*;

// line 94 "../../../../../../../SDK.ump"
public class GetChangedScaleRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedScaleRequest Attributes
  private Date startTime;
  private List<Long> campaignIds;
  private Boolean changedCampaignScale;
  private Boolean changedAdgroupScale;
  private Boolean changedKeywordScale;
  private Boolean changedCreativeScale;
  private Boolean changedSublinkScale;
  private Boolean changedMobileSublinkScale;
  private Boolean changedDynamicCreativeScale;
  private Boolean changedPhoneScale;
  private Boolean changedBridgeScale;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Date aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public void setCampaignIds(List<Long> acampaignIds){
    campaignIds = acampaignIds;
  }

  public boolean addCampaignId(Long aCampaignId)
  {
    boolean wasAdded = false;
    wasAdded = campaignIds.add(aCampaignId);
    return wasAdded;
  }

  public boolean removeCampaignId(Long aCampaignId)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignIds.remove(aCampaignId);
    return wasRemoved;
  }

  public boolean setChangedCampaignScale(Boolean aChangedCampaignScale)
  {
    boolean wasSet = false;
    changedCampaignScale = aChangedCampaignScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedAdgroupScale(Boolean aChangedAdgroupScale)
  {
    boolean wasSet = false;
    changedAdgroupScale = aChangedAdgroupScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedKeywordScale(Boolean aChangedKeywordScale)
  {
    boolean wasSet = false;
    changedKeywordScale = aChangedKeywordScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedCreativeScale(Boolean aChangedCreativeScale)
  {
    boolean wasSet = false;
    changedCreativeScale = aChangedCreativeScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedSublinkScale(Boolean aChangedSublinkScale)
  {
    boolean wasSet = false;
    changedSublinkScale = aChangedSublinkScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedMobileSublinkScale(Boolean aChangedMobileSublinkScale)
  {
    boolean wasSet = false;
    changedMobileSublinkScale = aChangedMobileSublinkScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedDynamicCreativeScale(Boolean aChangedDynamicCreativeScale)
  {
    boolean wasSet = false;
    changedDynamicCreativeScale = aChangedDynamicCreativeScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedPhoneScale(Boolean aChangedPhoneScale)
  {
    boolean wasSet = false;
    changedPhoneScale = aChangedPhoneScale;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedBridgeScale(Boolean aChangedBridgeScale)
  {
    boolean wasSet = false;
    changedBridgeScale = aChangedBridgeScale;
    wasSet = true;
    return wasSet;
  }

  public Date getStartTime()
  {
    return startTime;
  }

  public Long getCampaignId(int index)
  {
    Long aCampaignId = campaignIds.get(index);
    return aCampaignId;
  }

  public List<Long> getCampaignIds()
  {
    return campaignIds;
  }

  public int numberOfCampaignIds()
  {
    int number = campaignIds.size();
    return number;
  }

  public boolean hasCampaignIds()
  {
    boolean has = campaignIds.size() > 0;
    return has;
  }

  public int indexOfCampaignId(Long aCampaignId)
  {
    int index = campaignIds.indexOf(aCampaignId);
    return index;
  }

  public Boolean getChangedCampaignScale()
  {
    return changedCampaignScale;
  }

  public Boolean getChangedAdgroupScale()
  {
    return changedAdgroupScale;
  }

  public Boolean getChangedKeywordScale()
  {
    return changedKeywordScale;
  }

  public Boolean getChangedCreativeScale()
  {
    return changedCreativeScale;
  }

  public Boolean getChangedSublinkScale()
  {
    return changedSublinkScale;
  }

  public Boolean getChangedMobileSublinkScale()
  {
    return changedMobileSublinkScale;
  }

  public Boolean getChangedDynamicCreativeScale()
  {
    return changedDynamicCreativeScale;
  }

  public Boolean getChangedPhoneScale()
  {
    return changedPhoneScale;
  }

  public Boolean getChangedBridgeScale()
  {
    return changedBridgeScale;
  }










  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "changedCampaignScale" + ":" + getChangedCampaignScale()+ "," +
            "changedAdgroupScale" + ":" + getChangedAdgroupScale()+ "," +
            "changedKeywordScale" + ":" + getChangedKeywordScale()+ "," +
            "changedCreativeScale" + ":" + getChangedCreativeScale()+ "," +
            "changedSublinkScale" + ":" + getChangedSublinkScale()+ "," +
            "changedMobileSublinkScale" + ":" + getChangedMobileSublinkScale()+ "," +
            "changedDynamicCreativeScale" + ":" + getChangedDynamicCreativeScale()+ "," +
            "changedPhoneScale" + ":" + getChangedPhoneScale()+ "," +
            "changedBridgeScale" + ":" + getChangedBridgeScale()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.Date;
import java.util.*;

// line 184 "../../../../../../../SDKDemo.ump"
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
  private Integer changedNewCreativeScales;

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

  public boolean setChangedNewCreativeScales(Integer aChangedNewCreativeScales)
  {
    boolean wasSet = false;
    changedNewCreativeScales = aChangedNewCreativeScales;
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

  public Integer getChangedNewCreativeScales()
  {
    return changedNewCreativeScales;
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
            "changedNewCreativeScales" + ":" + getChangedNewCreativeScales()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
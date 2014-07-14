/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.Date;
import java.util.*;

// line 222 "../../../../../../../SDKDemo.ump"
public class GetChangedAdgroupIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedAdgroupIdRequest Attributes
  private Date startTime;
  private List<Long> campaignIds;
  private Boolean adgroupLevel;
  private Boolean itemLevel;
  private Integer newCreativeLevel;

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

  public boolean setAdgroupLevel(Boolean aAdgroupLevel)
  {
    boolean wasSet = false;
    adgroupLevel = aAdgroupLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setItemLevel(Boolean aItemLevel)
  {
    boolean wasSet = false;
    itemLevel = aItemLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setNewCreativeLevel(Integer aNewCreativeLevel)
  {
    boolean wasSet = false;
    newCreativeLevel = aNewCreativeLevel;
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

  public Boolean getAdgroupLevel()
  {
    return adgroupLevel;
  }

  public Boolean getItemLevel()
  {
    return itemLevel;
  }

  public Integer getNewCreativeLevel()
  {
    return newCreativeLevel;
  }



  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupLevel" + ":" + getAdgroupLevel()+ "," +
            "itemLevel" + ":" + getItemLevel()+ "," +
            "newCreativeLevel" + ":" + getNewCreativeLevel()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
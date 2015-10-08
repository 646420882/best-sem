/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;
import java.util.*;

// line 57 "../../../../../../../SDK.ump"
public class GetChangedItemIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedItemIdRequest Attributes
  private Date startTime;
  private Integer itemType;
  private Integer type;
  private List<Long> ids;
  private Boolean campaignLevel;
  private Boolean adgroupLevel;
  private Boolean keywordLevel;
  private Boolean creativeLevel;
  private Boolean sublinkLevel;
  private Boolean mobileSublinkLevel;
  private Boolean dynamicCreativeLevel;
  private Boolean phoneLevel;
  private Boolean bridgeLevel;

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

  public boolean setItemType(Integer aItemType)
  {
    boolean wasSet = false;
    itemType = aItemType;
    wasSet = true;
    return wasSet;
  }

  public boolean setType(Integer aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public void setIds(List<Long> aids){
    ids = aids;
  }

  public boolean addId(Long aId)
  {
    boolean wasAdded = false;
    wasAdded = ids.add(aId);
    return wasAdded;
  }

  public boolean removeId(Long aId)
  {
    boolean wasRemoved = false;
    wasRemoved = ids.remove(aId);
    return wasRemoved;
  }

  public boolean setCampaignLevel(Boolean aCampaignLevel)
  {
    boolean wasSet = false;
    campaignLevel = aCampaignLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupLevel(Boolean aAdgroupLevel)
  {
    boolean wasSet = false;
    adgroupLevel = aAdgroupLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeywordLevel(Boolean aKeywordLevel)
  {
    boolean wasSet = false;
    keywordLevel = aKeywordLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreativeLevel(Boolean aCreativeLevel)
  {
    boolean wasSet = false;
    creativeLevel = aCreativeLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setSublinkLevel(Boolean aSublinkLevel)
  {
    boolean wasSet = false;
    sublinkLevel = aSublinkLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileSublinkLevel(Boolean aMobileSublinkLevel)
  {
    boolean wasSet = false;
    mobileSublinkLevel = aMobileSublinkLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setDynamicCreativeLevel(Boolean aDynamicCreativeLevel)
  {
    boolean wasSet = false;
    dynamicCreativeLevel = aDynamicCreativeLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneLevel(Boolean aPhoneLevel)
  {
    boolean wasSet = false;
    phoneLevel = aPhoneLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setBridgeLevel(Boolean aBridgeLevel)
  {
    boolean wasSet = false;
    bridgeLevel = aBridgeLevel;
    wasSet = true;
    return wasSet;
  }

  public Date getStartTime()
  {
    return startTime;
  }

  public Integer getItemType()
  {
    return itemType;
  }

  public Integer getType()
  {
    return type;
  }

  public Long getId(int index)
  {
    Long aId = ids.get(index);
    return aId;
  }

  public List<Long> getIds()
  {
    return ids;
  }

  public int numberOfIds()
  {
    int number = ids.size();
    return number;
  }

  public boolean hasIds()
  {
    boolean has = ids.size() > 0;
    return has;
  }

  public int indexOfId(Long aId)
  {
    int index = ids.indexOf(aId);
    return index;
  }

  public Boolean getCampaignLevel()
  {
    return campaignLevel;
  }

  public Boolean getAdgroupLevel()
  {
    return adgroupLevel;
  }

  public Boolean getKeywordLevel()
  {
    return keywordLevel;
  }

  public Boolean getCreativeLevel()
  {
    return creativeLevel;
  }

  public Boolean getSublinkLevel()
  {
    return sublinkLevel;
  }

  public Boolean getMobileSublinkLevel()
  {
    return mobileSublinkLevel;
  }

  public Boolean getDynamicCreativeLevel()
  {
    return dynamicCreativeLevel;
  }

  public Boolean getPhoneLevel()
  {
    return phoneLevel;
  }

  public Boolean getBridgeLevel()
  {
    return bridgeLevel;
  }










  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "itemType" + ":" + getItemType()+ "," +
            "type" + ":" + getType()+ "," +
            "campaignLevel" + ":" + getCampaignLevel()+ "," +
            "adgroupLevel" + ":" + getAdgroupLevel()+ "," +
            "keywordLevel" + ":" + getKeywordLevel()+ "," +
            "creativeLevel" + ":" + getCreativeLevel()+ "," +
            "sublinkLevel" + ":" + getSublinkLevel()+ "," +
            "mobileSublinkLevel" + ":" + getMobileSublinkLevel()+ "," +
            "dynamicCreativeLevel" + ":" + getDynamicCreativeLevel()+ "," +
            "phoneLevel" + ":" + getPhoneLevel()+ "," +
            "bridgeLevel" + ":" + getBridgeLevel()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
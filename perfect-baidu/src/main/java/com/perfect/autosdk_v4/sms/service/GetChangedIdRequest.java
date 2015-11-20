/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;

// line 209 "../../../../../../../SDK.ump"
public class GetChangedIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedIdRequest Attributes
  private Date startTime;
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
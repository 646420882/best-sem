/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.Date;

// line 139 "../../../../../../../SDKDemo.ump"
public class GetChangedCampaignIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedCampaignIdRequest Attributes
  private Date startTime;
  private Boolean campaignLevel;
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

  public Boolean getCampaignLevel()
  {
    return campaignLevel;
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
            "campaignLevel" + ":" + getCampaignLevel()+ "," +
            "adgroupLevel" + ":" + getAdgroupLevel()+ "," +
            "itemLevel" + ":" + getItemLevel()+ "," +
            "newCreativeLevel" + ":" + getNewCreativeLevel()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
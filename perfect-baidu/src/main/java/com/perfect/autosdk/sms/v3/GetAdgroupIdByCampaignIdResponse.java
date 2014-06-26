/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 46 "../../../../../../../SDKDemo.ump"
public class GetAdgroupIdByCampaignIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdgroupIdByCampaignIdResponse Attributes
  private List<CampaignAdgroupId> campaignAdgroupIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCampaignAdgroupIds(List<CampaignAdgroupId> acampaignAdgroupIds){
    campaignAdgroupIds = acampaignAdgroupIds;
  }

  public boolean addCampaignAdgroupId(CampaignAdgroupId aCampaignAdgroupId)
  {
    boolean wasAdded = false;
    wasAdded = campaignAdgroupIds.add(aCampaignAdgroupId);
    return wasAdded;
  }

  public boolean removeCampaignAdgroupId(CampaignAdgroupId aCampaignAdgroupId)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignAdgroupIds.remove(aCampaignAdgroupId);
    return wasRemoved;
  }

  public CampaignAdgroupId getCampaignAdgroupId(int index)
  {
    CampaignAdgroupId aCampaignAdgroupId = campaignAdgroupIds.get(index);
    return aCampaignAdgroupId;
  }

  public List<CampaignAdgroupId> getCampaignAdgroupIds()
  {
    return campaignAdgroupIds;
  }

  public int numberOfCampaignAdgroupIds()
  {
    int number = campaignAdgroupIds.size();
    return number;
  }

  public boolean hasCampaignAdgroupIds()
  {
    boolean has = campaignAdgroupIds.size() > 0;
    return has;
  }

  public int indexOfCampaignAdgroupId(CampaignAdgroupId aCampaignAdgroupId)
  {
    int index = campaignAdgroupIds.indexOf(aCampaignAdgroupId);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]"
     + outputString;
  }
}
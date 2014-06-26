/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 65 "../../../../../../../SDKDemo.ump"
public class GetAdgroupByCampaignIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdgroupByCampaignIdResponse Attributes
  private List<CampaignAdgroup> campaignAdgroups;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCampaignAdgroups(List<CampaignAdgroup> acampaignAdgroups){
    campaignAdgroups = acampaignAdgroups;
  }

  public boolean addCampaignAdgroup(CampaignAdgroup aCampaignAdgroup)
  {
    boolean wasAdded = false;
    wasAdded = campaignAdgroups.add(aCampaignAdgroup);
    return wasAdded;
  }

  public boolean removeCampaignAdgroup(CampaignAdgroup aCampaignAdgroup)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignAdgroups.remove(aCampaignAdgroup);
    return wasRemoved;
  }

  public CampaignAdgroup getCampaignAdgroup(int index)
  {
    CampaignAdgroup aCampaignAdgroup = campaignAdgroups.get(index);
    return aCampaignAdgroup;
  }

  public List<CampaignAdgroup> getCampaignAdgroups()
  {
    return campaignAdgroups;
  }

  public int numberOfCampaignAdgroups()
  {
    int number = campaignAdgroups.size();
    return number;
  }

  public boolean hasCampaignAdgroups()
  {
    boolean has = campaignAdgroups.size() > 0;
    return has;
  }

  public int indexOfCampaignAdgroup(CampaignAdgroup aCampaignAdgroup)
  {
    int index = campaignAdgroups.indexOf(aCampaignAdgroup);
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 22 "../../../../../../../SDKDemo.ump"
public class AdgroupInfo
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AdgroupInfo Attributes
  private Long adgroupId;
  private String adgroupName;
  private Long campaignId;
  private String campaignName;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupName(String aAdgroupName)
  {
    boolean wasSet = false;
    adgroupName = aAdgroupName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCampaignName(String aCampaignName)
  {
    boolean wasSet = false;
    campaignName = aCampaignName;
    wasSet = true;
    return wasSet;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public String getAdgroupName()
  {
    return adgroupName;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public String getCampaignName()
  {
    return campaignName;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "adgroupName" + ":" + getAdgroupName()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "campaignName" + ":" + getCampaignName()+ "]"
     + outputString;
  }
}
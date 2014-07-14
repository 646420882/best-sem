/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 56 "../../../../../../../SDKDemo.ump"
public class QualityType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //QualityType Attributes
  private Long id;
  private Long adgroupId;
  private Long campaignId;
  private Integer quality;
  private Integer mobileQuality;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Long aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
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

  public boolean setQuality(Integer aQuality)
  {
    boolean wasSet = false;
    quality = aQuality;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileQuality(Integer aMobileQuality)
  {
    boolean wasSet = false;
    mobileQuality = aMobileQuality;
    wasSet = true;
    return wasSet;
  }

  public Long getId()
  {
    return id;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Integer getQuality()
  {
    return quality;
  }

  public Integer getMobileQuality()
  {
    return mobileQuality;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "quality" + ":" + getQuality()+ "," +
            "mobileQuality" + ":" + getMobileQuality()+ "]"
     + outputString;
  }
}
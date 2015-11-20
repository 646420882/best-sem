/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 42 "../../../../../../../SDK.ump"
public class CreativeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CreativeType Attributes
  private Long campaignId;
  private Long creativeId;
  private Long adgroupId;
  private String title;
  private String description1;
  private String description2;
  private String destinationUrl;
  private String displayUrl;
  private Boolean pause;
  private Integer status;
  private String mobileDestinationUrl;
  private String mobileDisplayUrl;
  private String pcDestinationUrl;
  private String pcDisplayUrl;
  private Integer devicePreference;
  private Long temp;
  private String operator;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreativeId(Long aCreativeId)
  {
    boolean wasSet = false;
    creativeId = aCreativeId;
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

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription1(String aDescription1)
  {
    boolean wasSet = false;
    description1 = aDescription1;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription2(String aDescription2)
  {
    boolean wasSet = false;
    description2 = aDescription2;
    wasSet = true;
    return wasSet;
  }

  public boolean setDestinationUrl(String aDestinationUrl)
  {
    boolean wasSet = false;
    destinationUrl = aDestinationUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setDisplayUrl(String aDisplayUrl)
  {
    boolean wasSet = false;
    displayUrl = aDisplayUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setPause(Boolean aPause)
  {
    boolean wasSet = false;
    pause = aPause;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Integer aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileDestinationUrl(String aMobileDestinationUrl)
  {
    boolean wasSet = false;
    mobileDestinationUrl = aMobileDestinationUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileDisplayUrl(String aMobileDisplayUrl)
  {
    boolean wasSet = false;
    mobileDisplayUrl = aMobileDisplayUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcDestinationUrl(String aPcDestinationUrl)
  {
    boolean wasSet = false;
    pcDestinationUrl = aPcDestinationUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcDisplayUrl(String aPcDisplayUrl)
  {
    boolean wasSet = false;
    pcDisplayUrl = aPcDisplayUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setDevicePreference(Integer aDevicePreference)
  {
    boolean wasSet = false;
    devicePreference = aDevicePreference;
    wasSet = true;
    return wasSet;
  }

  public boolean setTemp(Long aTemp)
  {
    boolean wasSet = false;
    temp = aTemp;
    wasSet = true;
    return wasSet;
  }

  public boolean setOperator(String aOperator)
  {
    boolean wasSet = false;
    operator = aOperator;
    wasSet = true;
    return wasSet;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Long getCreativeId()
  {
    return creativeId;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDescription1()
  {
    return description1;
  }

  public String getDescription2()
  {
    return description2;
  }

  public String getDestinationUrl()
  {
    return destinationUrl;
  }

  public String getDisplayUrl()
  {
    return displayUrl;
  }

  public Boolean getPause()
  {
    return pause;
  }

  public Integer getStatus()
  {
    return status;
  }

  public String getMobileDestinationUrl()
  {
    return mobileDestinationUrl;
  }

  public String getMobileDisplayUrl()
  {
    return mobileDisplayUrl;
  }

  public String getPcDestinationUrl()
  {
    return pcDestinationUrl;
  }

  public String getPcDisplayUrl()
  {
    return pcDisplayUrl;
  }

  public Integer getDevicePreference()
  {
    return devicePreference;
  }

  public Long getTemp()
  {
    return temp;
  }

  public String getOperator()
  {
    return operator;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "campaignId" + ":" + getCampaignId()+ "," +
            "creativeId" + ":" + getCreativeId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "title" + ":" + getTitle()+ "," +
            "description1" + ":" + getDescription1()+ "," +
            "description2" + ":" + getDescription2()+ "," +
            "destinationUrl" + ":" + getDestinationUrl()+ "," +
            "displayUrl" + ":" + getDisplayUrl()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "," +
            "mobileDestinationUrl" + ":" + getMobileDestinationUrl()+ "," +
            "mobileDisplayUrl" + ":" + getMobileDisplayUrl()+ "," +
            "pcDestinationUrl" + ":" + getPcDestinationUrl()+ "," +
            "pcDisplayUrl" + ":" + getPcDisplayUrl()+ "," +
            "devicePreference" + ":" + getDevicePreference()+ "," +
            "temp" + ":" + getTemp()+ "," +
            "operator" + ":" + getOperator()+ "]"
     + outputString;
  }
}
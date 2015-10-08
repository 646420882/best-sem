/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 40 "../../../../../../../SDK.ump"
public class DynCreativeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DynCreativeType Attributes
  private Long dynCreativeId;
  private Long campaignId;
  private Long adgroupId;
  private Integer bindingType;
  private String title;
  private String url;
  private String murl;
  private Boolean pause;
  private Integer status;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDynCreativeId(Long aDynCreativeId)
  {
    boolean wasSet = false;
    dynCreativeId = aDynCreativeId;
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

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public boolean setBindingType(Integer aBindingType)
  {
    boolean wasSet = false;
    bindingType = aBindingType;
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

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setMurl(String aMurl)
  {
    boolean wasSet = false;
    murl = aMurl;
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

  public Long getDynCreativeId()
  {
    return dynCreativeId;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Integer getBindingType()
  {
    return bindingType;
  }

  public String getTitle()
  {
    return title;
  }

  public String getUrl()
  {
    return url;
  }

  public String getMurl()
  {
    return murl;
  }

  public Boolean getPause()
  {
    return pause;
  }

  public Integer getStatus()
  {
    return status;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "dynCreativeId" + ":" + getDynCreativeId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "bindingType" + ":" + getBindingType()+ "," +
            "title" + ":" + getTitle()+ "," +
            "url" + ":" + getUrl()+ "," +
            "murl" + ":" + getMurl()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "]"
     + outputString;
  }
}
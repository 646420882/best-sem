/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 42 "../../../../../../../SDK.ump"
public class ChangedItemIdType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChangedItemIdType Attributes
  private Integer operator;
  private Long creativeId;
  private Long dynCreativeId;
  private Long keywordId;
  private Long adgroupId;
  private Long campaignId;
  private Long sublinkId;
  private Long mobileSublinkId;
  private Long phoneId;
  private Long bridgeId;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOperator(Integer aOperator)
  {
    boolean wasSet = false;
    operator = aOperator;
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

  public boolean setDynCreativeId(Long aDynCreativeId)
  {
    boolean wasSet = false;
    dynCreativeId = aDynCreativeId;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeywordId(Long aKeywordId)
  {
    boolean wasSet = false;
    keywordId = aKeywordId;
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

  public boolean setSublinkId(Long aSublinkId)
  {
    boolean wasSet = false;
    sublinkId = aSublinkId;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileSublinkId(Long aMobileSublinkId)
  {
    boolean wasSet = false;
    mobileSublinkId = aMobileSublinkId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneId(Long aPhoneId)
  {
    boolean wasSet = false;
    phoneId = aPhoneId;
    wasSet = true;
    return wasSet;
  }

  public boolean setBridgeId(Long aBridgeId)
  {
    boolean wasSet = false;
    bridgeId = aBridgeId;
    wasSet = true;
    return wasSet;
  }

  public Integer getOperator()
  {
    return operator;
  }

  public Long getCreativeId()
  {
    return creativeId;
  }

  public Long getDynCreativeId()
  {
    return dynCreativeId;
  }

  public Long getKeywordId()
  {
    return keywordId;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Long getSublinkId()
  {
    return sublinkId;
  }

  public Long getMobileSublinkId()
  {
    return mobileSublinkId;
  }

  public Long getPhoneId()
  {
    return phoneId;
  }

  public Long getBridgeId()
  {
    return bridgeId;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "operator" + ":" + getOperator()+ "," +
            "creativeId" + ":" + getCreativeId()+ "," +
            "dynCreativeId" + ":" + getDynCreativeId()+ "," +
            "keywordId" + ":" + getKeywordId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "sublinkId" + ":" + getSublinkId()+ "," +
            "mobileSublinkId" + ":" + getMobileSublinkId()+ "," +
            "phoneId" + ":" + getPhoneId()+ "," +
            "bridgeId" + ":" + getBridgeId()+ "]"
     + outputString;
  }
}
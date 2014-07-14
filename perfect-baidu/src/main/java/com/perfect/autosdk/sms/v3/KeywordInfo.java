/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 12 "../../../../../../../SDKDemo.ump"
public class KeywordInfo
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KeywordInfo Attributes
  private Long keywordId;
  private String keyword;
  private Long adgroupId;
  private String adgroupName;
  private Long campaignId;
  private String campaignName;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKeywordId(Long aKeywordId)
  {
    boolean wasSet = false;
    keywordId = aKeywordId;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeyword(String aKeyword)
  {
    boolean wasSet = false;
    keyword = aKeyword;
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

  public Long getKeywordId()
  {
    return keywordId;
  }

  public String getKeyword()
  {
    return keyword;
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
            "keywordId" + ":" + getKeywordId()+ "," +
            "keyword" + ":" + getKeyword()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "adgroupName" + ":" + getAdgroupName()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "campaignName" + ":" + getCampaignName()+ "]"
     + outputString;
  }
}
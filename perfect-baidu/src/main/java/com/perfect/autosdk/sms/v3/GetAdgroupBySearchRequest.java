/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 52 "../../../../../../../SDKDemo.ump"
public class GetAdgroupBySearchRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdgroupBySearchRequest Attributes
  private String adgroupName;
  private Integer startNum;
  private Integer endNum;
  private Long campaignId;
  private Integer searchType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAdgroupName(String aAdgroupName)
  {
    boolean wasSet = false;
    adgroupName = aAdgroupName;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartNum(Integer aStartNum)
  {
    boolean wasSet = false;
    startNum = aStartNum;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndNum(Integer aEndNum)
  {
    boolean wasSet = false;
    endNum = aEndNum;
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

  public boolean setSearchType(Integer aSearchType)
  {
    boolean wasSet = false;
    searchType = aSearchType;
    wasSet = true;
    return wasSet;
  }

  public String getAdgroupName()
  {
    return adgroupName;
  }

  public Integer getStartNum()
  {
    return startNum;
  }

  public Integer getEndNum()
  {
    return endNum;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Integer getSearchType()
  {
    return searchType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupName" + ":" + getAdgroupName()+ "," +
            "startNum" + ":" + getStartNum()+ "," +
            "endNum" + ":" + getEndNum()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "searchType" + ":" + getSearchType()+ "]"
     + outputString;
  }
}
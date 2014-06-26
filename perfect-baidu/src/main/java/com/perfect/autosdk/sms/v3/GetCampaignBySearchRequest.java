/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 67 "../../../../../../../SDKDemo.ump"
public class GetCampaignBySearchRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCampaignBySearchRequest Attributes
  private String campaignName;
  private Integer startNum;
  private Integer endNum;
  private Integer searchType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCampaignName(String aCampaignName)
  {
    boolean wasSet = false;
    campaignName = aCampaignName;
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

  public boolean setSearchType(Integer aSearchType)
  {
    boolean wasSet = false;
    searchType = aSearchType;
    wasSet = true;
    return wasSet;
  }

  public String getCampaignName()
  {
    return campaignName;
  }

  public Integer getStartNum()
  {
    return startNum;
  }

  public Integer getEndNum()
  {
    return endNum;
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
            "campaignName" + ":" + getCampaignName()+ "," +
            "startNum" + ":" + getStartNum()+ "," +
            "endNum" + ":" + getEndNum()+ "," +
            "searchType" + ":" + getSearchType()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 36 "../../../../../../../SDKDemo.ump"
public class GetKeywordBySearchRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordBySearchRequest Attributes
  private String searchWord;
  private Integer startNum;
  private Integer endNum;
  private Long campaignId;
  private Long adgroupId;
  private Integer searchType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSearchWord(String aSearchWord)
  {
    boolean wasSet = false;
    searchWord = aSearchWord;
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

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
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

  public String getSearchWord()
  {
    return searchWord;
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

  public Long getAdgroupId()
  {
    return adgroupId;
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
            "searchWord" + ":" + getSearchWord()+ "," +
            "startNum" + ":" + getStartNum()+ "," +
            "endNum" + ":" + getEndNum()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "searchType" + ":" + getSearchType()+ "]"
     + outputString;
  }
}
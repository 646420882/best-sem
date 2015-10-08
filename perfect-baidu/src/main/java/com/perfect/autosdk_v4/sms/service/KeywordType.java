/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 57 "../../../../../../../SDK.ump"
public class KeywordType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KeywordType Attributes
  private Long campaignId;
  private Long keywordId;
  private Long adgroupId;
  private String keyword;
  private Double price;
  private String destinationUrl;
  private Integer matchType;
  private Boolean pause;
  private Integer status;
  private String pcDestinationUrl;
  private String mobileDestinationUrl;
  private Integer phraseType;
  private Integer wmatchprefer;
  private Integer deviceprefer;
  private Integer owmatch;
  private Integer pcQuality;
  private Integer pcReliable;
  private Integer pcReason;
  private List<Integer> pcScale;
  private Integer mobileQuality;
  private Integer mobileReliable;
  private Integer mobileReason;
  private List<Integer> mobileScale;
  private Integer temp;
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

  public boolean setKeyword(String aKeyword)
  {
    boolean wasSet = false;
    keyword = aKeyword;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(Double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
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

  public boolean setMatchType(Integer aMatchType)
  {
    boolean wasSet = false;
    matchType = aMatchType;
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

  public boolean setPcDestinationUrl(String aPcDestinationUrl)
  {
    boolean wasSet = false;
    pcDestinationUrl = aPcDestinationUrl;
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

  public boolean setPhraseType(Integer aPhraseType)
  {
    boolean wasSet = false;
    phraseType = aPhraseType;
    wasSet = true;
    return wasSet;
  }

  public boolean setWmatchprefer(Integer aWmatchprefer)
  {
    boolean wasSet = false;
    wmatchprefer = aWmatchprefer;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeviceprefer(Integer aDeviceprefer)
  {
    boolean wasSet = false;
    deviceprefer = aDeviceprefer;
    wasSet = true;
    return wasSet;
  }

  public boolean setOwmatch(Integer aOwmatch)
  {
    boolean wasSet = false;
    owmatch = aOwmatch;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcQuality(Integer aPcQuality)
  {
    boolean wasSet = false;
    pcQuality = aPcQuality;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcReliable(Integer aPcReliable)
  {
    boolean wasSet = false;
    pcReliable = aPcReliable;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcReason(Integer aPcReason)
  {
    boolean wasSet = false;
    pcReason = aPcReason;
    wasSet = true;
    return wasSet;
  }

  public void setPcScale(List<Integer> apcScale){
    pcScale = apcScale;
  }

  public boolean addPcScale(Integer aPcScale)
  {
    boolean wasAdded = false;
    wasAdded = pcScale.add(aPcScale);
    return wasAdded;
  }

  public boolean removePcScale(Integer aPcScale)
  {
    boolean wasRemoved = false;
    wasRemoved = pcScale.remove(aPcScale);
    return wasRemoved;
  }

  public boolean setMobileQuality(Integer aMobileQuality)
  {
    boolean wasSet = false;
    mobileQuality = aMobileQuality;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileReliable(Integer aMobileReliable)
  {
    boolean wasSet = false;
    mobileReliable = aMobileReliable;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileReason(Integer aMobileReason)
  {
    boolean wasSet = false;
    mobileReason = aMobileReason;
    wasSet = true;
    return wasSet;
  }

  public void setMobileScale(List<Integer> amobileScale){
    mobileScale = amobileScale;
  }

  public boolean addMobileScale(Integer aMobileScale)
  {
    boolean wasAdded = false;
    wasAdded = mobileScale.add(aMobileScale);
    return wasAdded;
  }

  public boolean removeMobileScale(Integer aMobileScale)
  {
    boolean wasRemoved = false;
    wasRemoved = mobileScale.remove(aMobileScale);
    return wasRemoved;
  }

  public boolean setTemp(Integer aTemp)
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

  public Long getKeywordId()
  {
    return keywordId;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public String getKeyword()
  {
    return keyword;
  }

  public Double getPrice()
  {
    return price;
  }

  public String getDestinationUrl()
  {
    return destinationUrl;
  }

  public Integer getMatchType()
  {
    return matchType;
  }

  public Boolean getPause()
  {
    return pause;
  }

  public Integer getStatus()
  {
    return status;
  }

  public String getPcDestinationUrl()
  {
    return pcDestinationUrl;
  }

  public String getMobileDestinationUrl()
  {
    return mobileDestinationUrl;
  }

  public Integer getPhraseType()
  {
    return phraseType;
  }

  public Integer getWmatchprefer()
  {
    return wmatchprefer;
  }

  public Integer getDeviceprefer()
  {
    return deviceprefer;
  }

  public Integer getOwmatch()
  {
    return owmatch;
  }

  public Integer getPcQuality()
  {
    return pcQuality;
  }

  public Integer getPcReliable()
  {
    return pcReliable;
  }

  public Integer getPcReason()
  {
    return pcReason;
  }

  public Integer getPcScale(int index)
  {
    Integer aPcScale = pcScale.get(index);
    return aPcScale;
  }

  public List<Integer> getPcScale()
  {
    return pcScale;
  }

  public int numberOfPcScale()
  {
    int number = pcScale.size();
    return number;
  }

  public boolean hasPcScale()
  {
    boolean has = pcScale.size() > 0;
    return has;
  }

  public int indexOfPcScale(Integer aPcScale)
  {
    int index = pcScale.indexOf(aPcScale);
    return index;
  }

  public Integer getMobileQuality()
  {
    return mobileQuality;
  }

  public Integer getMobileReliable()
  {
    return mobileReliable;
  }

  public Integer getMobileReason()
  {
    return mobileReason;
  }

  public Integer getMobileScale(int index)
  {
    Integer aMobileScale = mobileScale.get(index);
    return aMobileScale;
  }

  public List<Integer> getMobileScale()
  {
    return mobileScale;
  }

  public int numberOfMobileScale()
  {
    int number = mobileScale.size();
    return number;
  }

  public boolean hasMobileScale()
  {
    boolean has = mobileScale.size() > 0;
    return has;
  }

  public int indexOfMobileScale(Integer aMobileScale)
  {
    int index = mobileScale.indexOf(aMobileScale);
    return index;
  }

  public Integer getTemp()
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
            "keywordId" + ":" + getKeywordId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "keyword" + ":" + getKeyword()+ "," +
            "price" + ":" + getPrice()+ "," +
            "destinationUrl" + ":" + getDestinationUrl()+ "," +
            "matchType" + ":" + getMatchType()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "," +
            "pcDestinationUrl" + ":" + getPcDestinationUrl()+ "," +
            "mobileDestinationUrl" + ":" + getMobileDestinationUrl()+ "," +
            "phraseType" + ":" + getPhraseType()+ "," +
            "wmatchprefer" + ":" + getWmatchprefer()+ "," +
            "deviceprefer" + ":" + getDeviceprefer()+ "," +
            "owmatch" + ":" + getOwmatch()+ "," +
            "pcQuality" + ":" + getPcQuality()+ "," +
            "pcReliable" + ":" + getPcReliable()+ "," +
            "pcReason" + ":" + getPcReason()+ "," +
            "mobileQuality" + ":" + getMobileQuality()+ "," +
            "mobileReliable" + ":" + getMobileReliable()+ "," +
            "mobileReason" + ":" + getMobileReason()+ "," +
            "temp" + ":" + getTemp()+ "," +
            "operator" + ":" + getOperator()+ "]"
     + outputString;
  }
}
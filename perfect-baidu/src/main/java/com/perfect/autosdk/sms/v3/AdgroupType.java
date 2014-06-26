/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import com.perfect.autosdk.common.OptType;

import java.util.List;

// line 15 "../../../../../../../SDKDemo.ump"
public class AdgroupType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AdgroupType Attributes
  private Long adgroupId;
  private Long campaignId;
  private String adgroupName;
  private Double maxPrice;
  private List<String> negativeWords;
  private List<String> exactNegativeWords;
  private String reserved;
  private Boolean pause;
  private Integer status;
  private OptType opt;

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

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
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

  public boolean setMaxPrice(Double aMaxPrice)
  {
    boolean wasSet = false;
    maxPrice = aMaxPrice;
    wasSet = true;
    return wasSet;
  }

  public void setNegativeWords(List<String> anegativeWords){
    negativeWords = anegativeWords;
  }

  public boolean addNegativeWord(String aNegativeWord)
  {
    boolean wasAdded = false;
    wasAdded = negativeWords.add(aNegativeWord);
    return wasAdded;
  }

  public boolean removeNegativeWord(String aNegativeWord)
  {
    boolean wasRemoved = false;
    wasRemoved = negativeWords.remove(aNegativeWord);
    return wasRemoved;
  }

  public void setExactNegativeWords(List<String> aexactNegativeWords){
    exactNegativeWords = aexactNegativeWords;
  }

  public boolean addExactNegativeWord(String aExactNegativeWord)
  {
    boolean wasAdded = false;
    wasAdded = exactNegativeWords.add(aExactNegativeWord);
    return wasAdded;
  }

  public boolean removeExactNegativeWord(String aExactNegativeWord)
  {
    boolean wasRemoved = false;
    wasRemoved = exactNegativeWords.remove(aExactNegativeWord);
    return wasRemoved;
  }

  public boolean setReserved(String aReserved)
  {
    boolean wasSet = false;
    reserved = aReserved;
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

  public boolean setOpt(OptType aOpt)
  {
    boolean wasSet = false;
    opt = aOpt;
    wasSet = true;
    return wasSet;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public String getAdgroupName()
  {
    return adgroupName;
  }

  public Double getMaxPrice()
  {
    return maxPrice;
  }

  public String getNegativeWord(int index)
  {
    String aNegativeWord = negativeWords.get(index);
    return aNegativeWord;
  }

  public List<String> getNegativeWords()
  {
    return negativeWords;
  }

  public int numberOfNegativeWords()
  {
    int number = negativeWords.size();
    return number;
  }

  public boolean hasNegativeWords()
  {
    boolean has = negativeWords.size() > 0;
    return has;
  }

  public int indexOfNegativeWord(String aNegativeWord)
  {
    int index = negativeWords.indexOf(aNegativeWord);
    return index;
  }

  public String getExactNegativeWord(int index)
  {
    String aExactNegativeWord = exactNegativeWords.get(index);
    return aExactNegativeWord;
  }

  public List<String> getExactNegativeWords()
  {
    return exactNegativeWords;
  }

  public int numberOfExactNegativeWords()
  {
    int number = exactNegativeWords.size();
    return number;
  }

  public boolean hasExactNegativeWords()
  {
    boolean has = exactNegativeWords.size() > 0;
    return has;
  }

  public int indexOfExactNegativeWord(String aExactNegativeWord)
  {
    int index = exactNegativeWords.indexOf(aExactNegativeWord);
    return index;
  }

  public String getReserved()
  {
    return reserved;
  }

  public Boolean getPause()
  {
    return pause;
  }

  public Integer getStatus()
  {
    return status;
  }

  public OptType getOpt()
  {
    return opt;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "adgroupName" + ":" + getAdgroupName()+ "," +
            "maxPrice" + ":" + getMaxPrice()+ "," +
            "reserved" + ":" + getReserved()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "opt" + "=" + (getOpt() != null ? !getOpt().equals(this)  ? getOpt().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
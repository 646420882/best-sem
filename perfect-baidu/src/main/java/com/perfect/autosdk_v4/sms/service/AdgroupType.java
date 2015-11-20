/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 56 "../../../../../../../SDK.ump"
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
  private Boolean pause;
  private List<String> negativeWords;
  private List<String> exactNegativeWords;
  private Integer status;
  private Double accuPriceFactor;
  private Double wordPriceFactor;
  private Double widePriceFactor;
  private Integer matchPriceStatus;
  private Double priceRatio;
  private String operator;

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

  public boolean setPause(Boolean aPause)
  {
    boolean wasSet = false;
    pause = aPause;
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

  public boolean setStatus(Integer aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setAccuPriceFactor(Double aAccuPriceFactor)
  {
    boolean wasSet = false;
    accuPriceFactor = aAccuPriceFactor;
    wasSet = true;
    return wasSet;
  }

  public boolean setWordPriceFactor(Double aWordPriceFactor)
  {
    boolean wasSet = false;
    wordPriceFactor = aWordPriceFactor;
    wasSet = true;
    return wasSet;
  }

  public boolean setWidePriceFactor(Double aWidePriceFactor)
  {
    boolean wasSet = false;
    widePriceFactor = aWidePriceFactor;
    wasSet = true;
    return wasSet;
  }

  public boolean setMatchPriceStatus(Integer aMatchPriceStatus)
  {
    boolean wasSet = false;
    matchPriceStatus = aMatchPriceStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriceRatio(Double aPriceRatio)
  {
    boolean wasSet = false;
    priceRatio = aPriceRatio;
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

  public Boolean getPause()
  {
    return pause;
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

  public Integer getStatus()
  {
    return status;
  }

  public Double getAccuPriceFactor()
  {
    return accuPriceFactor;
  }

  public Double getWordPriceFactor()
  {
    return wordPriceFactor;
  }

  public Double getWidePriceFactor()
  {
    return widePriceFactor;
  }

  public Integer getMatchPriceStatus()
  {
    return matchPriceStatus;
  }

  public Double getPriceRatio()
  {
    return priceRatio;
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
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "adgroupName" + ":" + getAdgroupName()+ "," +
            "maxPrice" + ":" + getMaxPrice()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "," +
            "accuPriceFactor" + ":" + getAccuPriceFactor()+ "," +
            "wordPriceFactor" + ":" + getWordPriceFactor()+ "," +
            "widePriceFactor" + ":" + getWidePriceFactor()+ "," +
            "matchPriceStatus" + ":" + getMatchPriceStatus()+ "," +
            "priceRatio" + ":" + getPriceRatio()+ "," +
            "operator" + ":" + getOperator()+ "]"
     + outputString;
  }
}
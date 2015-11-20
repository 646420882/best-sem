/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 105 "../../../../../../../SDK.ump"
public class SeedFilter
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SeedFilter Attributes
  private Integer device;
  private Long competeLow;
  private Long competeHigh;
  private Long maxNum;
  private List<String> negativeWords;
  private String positiveWord;
  private Long pvLow;
  private Long pvHigh;
  private Boolean regionExtend;
  private Boolean removeDuplicate;
  private List<Long> searchRegions;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public boolean setCompeteLow(Long aCompeteLow)
  {
    boolean wasSet = false;
    competeLow = aCompeteLow;
    wasSet = true;
    return wasSet;
  }

  public boolean setCompeteHigh(Long aCompeteHigh)
  {
    boolean wasSet = false;
    competeHigh = aCompeteHigh;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxNum(Long aMaxNum)
  {
    boolean wasSet = false;
    maxNum = aMaxNum;
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

  public boolean setPositiveWord(String aPositiveWord)
  {
    boolean wasSet = false;
    positiveWord = aPositiveWord;
    wasSet = true;
    return wasSet;
  }

  public boolean setPvLow(Long aPvLow)
  {
    boolean wasSet = false;
    pvLow = aPvLow;
    wasSet = true;
    return wasSet;
  }

  public boolean setPvHigh(Long aPvHigh)
  {
    boolean wasSet = false;
    pvHigh = aPvHigh;
    wasSet = true;
    return wasSet;
  }

  public boolean setRegionExtend(Boolean aRegionExtend)
  {
    boolean wasSet = false;
    regionExtend = aRegionExtend;
    wasSet = true;
    return wasSet;
  }

  public boolean setRemoveDuplicate(Boolean aRemoveDuplicate)
  {
    boolean wasSet = false;
    removeDuplicate = aRemoveDuplicate;
    wasSet = true;
    return wasSet;
  }

  public void setSearchRegions(List<Long> asearchRegions){
    searchRegions = asearchRegions;
  }

  public boolean addSearchRegion(Long aSearchRegion)
  {
    boolean wasAdded = false;
    wasAdded = searchRegions.add(aSearchRegion);
    return wasAdded;
  }

  public boolean removeSearchRegion(Long aSearchRegion)
  {
    boolean wasRemoved = false;
    wasRemoved = searchRegions.remove(aSearchRegion);
    return wasRemoved;
  }

  public Integer getDevice()
  {
    return device;
  }

  public Long getCompeteLow()
  {
    return competeLow;
  }

  public Long getCompeteHigh()
  {
    return competeHigh;
  }

  public Long getMaxNum()
  {
    return maxNum;
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

  public String getPositiveWord()
  {
    return positiveWord;
  }

  public Long getPvLow()
  {
    return pvLow;
  }

  public Long getPvHigh()
  {
    return pvHigh;
  }

  public Boolean getRegionExtend()
  {
    return regionExtend;
  }

  public Boolean getRemoveDuplicate()
  {
    return removeDuplicate;
  }

  public Long getSearchRegion(int index)
  {
    Long aSearchRegion = searchRegions.get(index);
    return aSearchRegion;
  }

  public List<Long> getSearchRegions()
  {
    return searchRegions;
  }

  public int numberOfSearchRegions()
  {
    int number = searchRegions.size();
    return number;
  }

  public boolean hasSearchRegions()
  {
    boolean has = searchRegions.size() > 0;
    return has;
  }

  public int indexOfSearchRegion(Long aSearchRegion)
  {
    int index = searchRegions.indexOf(aSearchRegion);
    return index;
  }



  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "device" + ":" + getDevice()+ "," +
            "competeLow" + ":" + getCompeteLow()+ "," +
            "competeHigh" + ":" + getCompeteHigh()+ "," +
            "maxNum" + ":" + getMaxNum()+ "," +
            "positiveWord" + ":" + getPositiveWord()+ "," +
            "pvLow" + ":" + getPvLow()+ "," +
            "pvHigh" + ":" + getPvHigh()+ "," +
            "regionExtend" + ":" + getRegionExtend()+ "," +
            "removeDuplicate" + ":" + getRemoveDuplicate()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 40 "../../../../../../../SDKDemo.ump"
public class SeedFilter
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SeedFilter Attributes
  private Integer matchType;
  private List<String> negativeWord;
  private Long pvLow;
  private Long pvHigh;
  private Long competeLow;
  private Long competeHigh;
  private List<Integer> searchRegion;
  private Boolean regionExtend;
  private Integer maxNum;
  private Boolean hotMonth;
  private Integer monthFilter;
  private Boolean removeDuplicate;
  private List<Long> duplicateUIds;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMatchType(Integer aMatchType)
  {
    boolean wasSet = false;
    matchType = aMatchType;
    wasSet = true;
    return wasSet;
  }

  public void setNegativeWord(List<String> anegativeWord){
    negativeWord = anegativeWord;
  }

  public boolean addNegativeWord(String aNegativeWord)
  {
    boolean wasAdded = false;
    wasAdded = negativeWord.add(aNegativeWord);
    return wasAdded;
  }

  public boolean removeNegativeWord(String aNegativeWord)
  {
    boolean wasRemoved = false;
    wasRemoved = negativeWord.remove(aNegativeWord);
    return wasRemoved;
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

  public void setSearchRegion(List<Integer> asearchRegion){
    searchRegion = asearchRegion;
  }

  public boolean addSearchRegion(Integer aSearchRegion)
  {
    boolean wasAdded = false;
    wasAdded = searchRegion.add(aSearchRegion);
    return wasAdded;
  }

  public boolean removeSearchRegion(Integer aSearchRegion)
  {
    boolean wasRemoved = false;
    wasRemoved = searchRegion.remove(aSearchRegion);
    return wasRemoved;
  }

  public boolean setRegionExtend(Boolean aRegionExtend)
  {
    boolean wasSet = false;
    regionExtend = aRegionExtend;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxNum(Integer aMaxNum)
  {
    boolean wasSet = false;
    maxNum = aMaxNum;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotMonth(Boolean aHotMonth)
  {
    boolean wasSet = false;
    hotMonth = aHotMonth;
    wasSet = true;
    return wasSet;
  }

  public boolean setMonthFilter(Integer aMonthFilter)
  {
    boolean wasSet = false;
    monthFilter = aMonthFilter;
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

  public void setDuplicateUIds(List<Long> aduplicateUIds){
    duplicateUIds = aduplicateUIds;
  }

  public boolean addDuplicateUId(Long aDuplicateUId)
  {
    boolean wasAdded = false;
    wasAdded = duplicateUIds.add(aDuplicateUId);
    return wasAdded;
  }

  public boolean removeDuplicateUId(Long aDuplicateUId)
  {
    boolean wasRemoved = false;
    wasRemoved = duplicateUIds.remove(aDuplicateUId);
    return wasRemoved;
  }

  public Integer getMatchType()
  {
    return matchType;
  }

  public String getNegativeWord(int index)
  {
    String aNegativeWord = negativeWord.get(index);
    return aNegativeWord;
  }

  public List<String> getNegativeWord()
  {
    return negativeWord;
  }

  public int numberOfNegativeWord()
  {
    int number = negativeWord.size();
    return number;
  }

  public boolean hasNegativeWord()
  {
    boolean has = negativeWord.size() > 0;
    return has;
  }

  public int indexOfNegativeWord(String aNegativeWord)
  {
    int index = negativeWord.indexOf(aNegativeWord);
    return index;
  }

  public Long getPvLow()
  {
    return pvLow;
  }

  public Long getPvHigh()
  {
    return pvHigh;
  }

  public Long getCompeteLow()
  {
    return competeLow;
  }

  public Long getCompeteHigh()
  {
    return competeHigh;
  }

  public Integer getSearchRegion(int index)
  {
    Integer aSearchRegion = searchRegion.get(index);
    return aSearchRegion;
  }

  public List<Integer> getSearchRegion()
  {
    return searchRegion;
  }

  public int numberOfSearchRegion()
  {
    int number = searchRegion.size();
    return number;
  }

  public boolean hasSearchRegion()
  {
    boolean has = searchRegion.size() > 0;
    return has;
  }

  public int indexOfSearchRegion(Integer aSearchRegion)
  {
    int index = searchRegion.indexOf(aSearchRegion);
    return index;
  }

  public Boolean getRegionExtend()
  {
    return regionExtend;
  }

  public Integer getMaxNum()
  {
    return maxNum;
  }

  public Boolean getHotMonth()
  {
    return hotMonth;
  }

  public Integer getMonthFilter()
  {
    return monthFilter;
  }

  public Boolean getRemoveDuplicate()
  {
    return removeDuplicate;
  }

  public Long getDuplicateUId(int index)
  {
    Long aDuplicateUId = duplicateUIds.get(index);
    return aDuplicateUId;
  }

  public List<Long> getDuplicateUIds()
  {
    return duplicateUIds;
  }

  public int numberOfDuplicateUIds()
  {
    int number = duplicateUIds.size();
    return number;
  }

  public boolean hasDuplicateUIds()
  {
    boolean has = duplicateUIds.size() > 0;
    return has;
  }

  public int indexOfDuplicateUId(Long aDuplicateUId)
  {
    int index = duplicateUIds.indexOf(aDuplicateUId);
    return index;
  }




  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "matchType" + ":" + getMatchType()+ "," +
            "pvLow" + ":" + getPvLow()+ "," +
            "pvHigh" + ":" + getPvHigh()+ "," +
            "competeLow" + ":" + getCompeteLow()+ "," +
            "competeHigh" + ":" + getCompeteHigh()+ "," +
            "regionExtend" + ":" + getRegionExtend()+ "," +
            "maxNum" + ":" + getMaxNum()+ "," +
            "hotMonth" + ":" + getHotMonth()+ "," +
            "monthFilter" + ":" + getMonthFilter()+ "," +
            "removeDuplicate" + ":" + getRemoveDuplicate()+ "]"
     + outputString;
  }
}
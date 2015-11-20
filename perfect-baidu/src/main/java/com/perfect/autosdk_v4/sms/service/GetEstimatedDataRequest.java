/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 140 "../../../../../../../SDK.ump"
public class GetEstimatedDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetEstimatedDataRequest Attributes
  private List<KREstimatedType> words;
  private List<Long> searchRegions;

  //------------------------
  // INTERFACE
  //------------------------

  public void setWords(List<KREstimatedType> awords){
    words = awords;
  }

  public boolean addWord(KREstimatedType aWord)
  {
    boolean wasAdded = false;
    wasAdded = words.add(aWord);
    return wasAdded;
  }

  public boolean removeWord(KREstimatedType aWord)
  {
    boolean wasRemoved = false;
    wasRemoved = words.remove(aWord);
    return wasRemoved;
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

  public KREstimatedType getWord(int index)
  {
    KREstimatedType aWord = words.get(index);
    return aWord;
  }

  public List<KREstimatedType> getWords()
  {
    return words;
  }

  public int numberOfWords()
  {
    int number = words.size();
    return number;
  }

  public boolean hasWords()
  {
    boolean has = words.size() > 0;
    return has;
  }

  public int indexOfWord(KREstimatedType aWord)
  {
    int index = words.indexOf(aWord);
    return index;
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
    return super.toString() + "["+ "]"
     + outputString;
  }
}
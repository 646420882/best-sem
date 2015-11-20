/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 15 "../../../../../../../SDK.ump"
public class GetKRFileIdByWordsRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRFileIdByWordsRequest Attributes
  private SeedFilter seedFilter;
  private List<String> seedWords;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSeedFilter(SeedFilter aSeedFilter)
  {
    boolean wasSet = false;
    seedFilter = aSeedFilter;
    wasSet = true;
    return wasSet;
  }

  public void setSeedWords(List<String> aseedWords){
    seedWords = aseedWords;
  }

  public boolean addSeedWord(String aSeedWord)
  {
    boolean wasAdded = false;
    wasAdded = seedWords.add(aSeedWord);
    return wasAdded;
  }

  public boolean removeSeedWord(String aSeedWord)
  {
    boolean wasRemoved = false;
    wasRemoved = seedWords.remove(aSeedWord);
    return wasRemoved;
  }

  public SeedFilter getSeedFilter()
  {
    return seedFilter;
  }

  public String getSeedWord(int index)
  {
    String aSeedWord = seedWords.get(index);
    return aSeedWord;
  }

  public List<String> getSeedWords()
  {
    return seedWords;
  }

  public int numberOfSeedWords()
  {
    int number = seedWords.size();
    return number;
  }

  public boolean hasSeedWords()
  {
    boolean has = seedWords.size() > 0;
    return has;
  }

  public int indexOfSeedWord(String aSeedWord)
  {
    int index = seedWords.indexOf(aSeedWord);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "seedFilter" + "=" + (getSeedFilter() != null ? !getSeedFilter().equals(this)  ? getSeedFilter().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 68 "../../../../../../../SDKDemo.ump"
public class GetKeywordRankResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordRankResponse Attributes
  private List<GetkeywordRankResponseMetaData> wordsRanks;
  private String date;

  //------------------------
  // INTERFACE
  //------------------------

  public void setWordsRanks(List<GetkeywordRankResponseMetaData> awordsRanks){
    wordsRanks = awordsRanks;
  }

  public boolean addWordsRank(GetkeywordRankResponseMetaData aWordsRank)
  {
    boolean wasAdded = false;
    wasAdded = wordsRanks.add(aWordsRank);
    return wasAdded;
  }

  public boolean removeWordsRank(GetkeywordRankResponseMetaData aWordsRank)
  {
    boolean wasRemoved = false;
    wasRemoved = wordsRanks.remove(aWordsRank);
    return wasRemoved;
  }

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public GetkeywordRankResponseMetaData getWordsRank(int index)
  {
    GetkeywordRankResponseMetaData aWordsRank = wordsRanks.get(index);
    return aWordsRank;
  }

  public List<GetkeywordRankResponseMetaData> getWordsRanks()
  {
    return wordsRanks;
  }

  public int numberOfWordsRanks()
  {
    int number = wordsRanks.size();
    return number;
  }

  public boolean hasWordsRanks()
  {
    boolean has = wordsRanks.size() > 0;
    return has;
  }

  public int indexOfWordsRank(GetkeywordRankResponseMetaData aWordsRank)
  {
    int index = wordsRanks.indexOf(aWordsRank);
    return index;
  }

  public String getDate()
  {
    return date;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "date" + ":" + getDate()+ "]"
     + outputString;
  }
}
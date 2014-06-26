/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 46 "../../../../../../../SDKDemo.ump"
public class GetKeywordBySearchResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordBySearchResponse Attributes
  private Integer moreWord;
  private List<KeywordInfo> keywordInfos;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMoreWord(Integer aMoreWord)
  {
    boolean wasSet = false;
    moreWord = aMoreWord;
    wasSet = true;
    return wasSet;
  }

  public void setKeywordInfos(List<KeywordInfo> akeywordInfos){
    keywordInfos = akeywordInfos;
  }

  public boolean addKeywordInfo(KeywordInfo aKeywordInfo)
  {
    boolean wasAdded = false;
    wasAdded = keywordInfos.add(aKeywordInfo);
    return wasAdded;
  }

  public boolean removeKeywordInfo(KeywordInfo aKeywordInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordInfos.remove(aKeywordInfo);
    return wasRemoved;
  }

  public Integer getMoreWord()
  {
    return moreWord;
  }

  public KeywordInfo getKeywordInfo(int index)
  {
    KeywordInfo aKeywordInfo = keywordInfos.get(index);
    return aKeywordInfo;
  }

  public List<KeywordInfo> getKeywordInfos()
  {
    return keywordInfos;
  }

  public int numberOfKeywordInfos()
  {
    int number = keywordInfos.size();
    return number;
  }

  public boolean hasKeywordInfos()
  {
    boolean has = keywordInfos.size() > 0;
    return has;
  }

  public int indexOfKeywordInfo(KeywordInfo aKeywordInfo)
  {
    int index = keywordInfos.indexOf(aKeywordInfo);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "moreWord" + ":" + getMoreWord()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 42 "../../../../../../../SDKDemo.ump"
public class GetAttributeByWordRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAttributeByWordRequest Attributes
  private List<String> searchWords;
  private Integer attributeType;

  //------------------------
  // INTERFACE
  //------------------------

  public void setSearchWords(List<String> asearchWords){
    searchWords = asearchWords;
  }

  public boolean addSearchWord(String aSearchWord)
  {
    boolean wasAdded = false;
    wasAdded = searchWords.add(aSearchWord);
    return wasAdded;
  }

  public boolean removeSearchWord(String aSearchWord)
  {
    boolean wasRemoved = false;
    wasRemoved = searchWords.remove(aSearchWord);
    return wasRemoved;
  }

  public boolean setAttributeType(Integer aAttributeType)
  {
    boolean wasSet = false;
    attributeType = aAttributeType;
    wasSet = true;
    return wasSet;
  }

  public String getSearchWord(int index)
  {
    String aSearchWord = searchWords.get(index);
    return aSearchWord;
  }

  public List<String> getSearchWords()
  {
    return searchWords;
  }

  public int numberOfSearchWords()
  {
    int number = searchWords.size();
    return number;
  }

  public boolean hasSearchWords()
  {
    boolean has = searchWords.size() > 0;
    return has;
  }

  public int indexOfSearchWord(String aSearchWord)
  {
    int index = searchWords.indexOf(aSearchWord);
    return index;
  }

  public Integer getAttributeType()
  {
    return attributeType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "attributeType" + ":" + getAttributeType()+ "]"
     + outputString;
  }
}
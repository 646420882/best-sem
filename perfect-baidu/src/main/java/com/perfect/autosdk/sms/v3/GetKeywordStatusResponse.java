/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 147 "../../../../../../../SDKDemo.ump"
public class GetKeywordStatusResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordStatusResponse Attributes
  private List<StatusType> keywordStatus;

  //------------------------
  // INTERFACE
  //------------------------

  public void setKeywordStatus(List<StatusType> akeywordStatus){
    keywordStatus = akeywordStatus;
  }

  public boolean addKeywordStatus(StatusType aKeywordStatus)
  {
    boolean wasAdded = false;
    wasAdded = keywordStatus.add(aKeywordStatus);
    return wasAdded;
  }

  public boolean removeKeywordStatus(StatusType aKeywordStatus)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordStatus.remove(aKeywordStatus);
    return wasRemoved;
  }

  public StatusType getKeywordStatus(int index)
  {
    StatusType aKeywordStatus = keywordStatus.get(index);
    return aKeywordStatus;
  }

  public List<StatusType> getKeywordStatus()
  {
    return keywordStatus;
  }

  public int numberOfKeywordStatus()
  {
    int number = keywordStatus.size();
    return number;
  }

  public boolean hasKeywordStatus()
  {
    boolean has = keywordStatus.size() > 0;
    return has;
  }

  public int indexOfKeywordStatus(StatusType aKeywordStatus)
  {
    int index = keywordStatus.indexOf(aKeywordStatus);
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
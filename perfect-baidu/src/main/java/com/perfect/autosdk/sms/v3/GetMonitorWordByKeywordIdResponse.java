/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 81 "../../../../../../../SDKDemo.ump"
public class GetMonitorWordByKeywordIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetMonitorWordByKeywordIdResponse Attributes
  private List<KeywordMonitor> keywordMonitors;

  //------------------------
  // INTERFACE
  //------------------------

  public void setKeywordMonitors(List<KeywordMonitor> akeywordMonitors){
    keywordMonitors = akeywordMonitors;
  }

  public boolean addKeywordMonitor(KeywordMonitor aKeywordMonitor)
  {
    boolean wasAdded = false;
    wasAdded = keywordMonitors.add(aKeywordMonitor);
    return wasAdded;
  }

  public boolean removeKeywordMonitor(KeywordMonitor aKeywordMonitor)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordMonitors.remove(aKeywordMonitor);
    return wasRemoved;
  }

  public KeywordMonitor getKeywordMonitor(int index)
  {
    KeywordMonitor aKeywordMonitor = keywordMonitors.get(index);
    return aKeywordMonitor;
  }

  public List<KeywordMonitor> getKeywordMonitors()
  {
    return keywordMonitors;
  }

  public int numberOfKeywordMonitors()
  {
    int number = keywordMonitors.size();
    return number;
  }

  public boolean hasKeywordMonitors()
  {
    boolean has = keywordMonitors.size() > 0;
    return has;
  }

  public int indexOfKeywordMonitor(KeywordMonitor aKeywordMonitor)
  {
    int index = keywordMonitors.indexOf(aKeywordMonitor);
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 40 "../../../../../../../SDKDemo.ump"
public class KeywordMonitor
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KeywordMonitor Attributes
  private Long keywordId;
  private List<Monitor> monitors;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKeywordId(Long aKeywordId)
  {
    boolean wasSet = false;
    keywordId = aKeywordId;
    wasSet = true;
    return wasSet;
  }

  public void setMonitors(List<Monitor> amonitors){
    monitors = amonitors;
  }

  public boolean addMonitor(Monitor aMonitor)
  {
    boolean wasAdded = false;
    wasAdded = monitors.add(aMonitor);
    return wasAdded;
  }

  public boolean removeMonitor(Monitor aMonitor)
  {
    boolean wasRemoved = false;
    wasRemoved = monitors.remove(aMonitor);
    return wasRemoved;
  }

  public Long getKeywordId()
  {
    return keywordId;
  }

  public Monitor getMonitor(int index)
  {
    Monitor aMonitor = monitors.get(index);
    return aMonitor;
  }

  public List<Monitor> getMonitors()
  {
    return monitors;
  }

  public int numberOfMonitors()
  {
    int number = monitors.size();
    return number;
  }

  public boolean hasMonitors()
  {
    boolean has = monitors.size() > 0;
    return has;
  }

  public int indexOfMonitor(Monitor aMonitor)
  {
    int index = monitors.indexOf(aMonitor);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "keywordId" + ":" + getKeywordId()+ "]"
     + outputString;
  }
}
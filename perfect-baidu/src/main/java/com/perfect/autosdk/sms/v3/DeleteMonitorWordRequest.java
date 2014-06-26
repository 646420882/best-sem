/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 126 "../../../../../../../SDKDemo.ump"
public class DeleteMonitorWordRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DeleteMonitorWordRequest Attributes
  private List<Long> monitorIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setMonitorIds(List<Long> amonitorIds){
    monitorIds = amonitorIds;
  }

  public boolean addMonitorId(Long aMonitorId)
  {
    boolean wasAdded = false;
    wasAdded = monitorIds.add(aMonitorId);
    return wasAdded;
  }

  public boolean removeMonitorId(Long aMonitorId)
  {
    boolean wasRemoved = false;
    wasRemoved = monitorIds.remove(aMonitorId);
    return wasRemoved;
  }

  public Long getMonitorId(int index)
  {
    Long aMonitorId = monitorIds.get(index);
    return aMonitorId;
  }

  public List<Long> getMonitorIds()
  {
    return monitorIds;
  }

  public int numberOfMonitorIds()
  {
    int number = monitorIds.size();
    return number;
  }

  public boolean hasMonitorIds()
  {
    boolean has = monitorIds.size() > 0;
    return has;
  }

  public int indexOfMonitorId(Long aMonitorId)
  {
    int index = monitorIds.indexOf(aMonitorId);
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
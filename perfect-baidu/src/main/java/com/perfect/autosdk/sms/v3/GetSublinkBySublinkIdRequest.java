/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 42 "../../../../../../../SDKDemo.ump"
public class GetSublinkBySublinkIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetSublinkBySublinkIdRequest Attributes
  private List<Long> sublinkIds;
  private Integer getTemp;

  //------------------------
  // INTERFACE
  //------------------------

  public void setSublinkIds(List<Long> asublinkIds){
    sublinkIds = asublinkIds;
  }

  public boolean addSublinkId(Long aSublinkId)
  {
    boolean wasAdded = false;
    wasAdded = sublinkIds.add(aSublinkId);
    return wasAdded;
  }

  public boolean removeSublinkId(Long aSublinkId)
  {
    boolean wasRemoved = false;
    wasRemoved = sublinkIds.remove(aSublinkId);
    return wasRemoved;
  }

  public boolean setGetTemp(Integer aGetTemp)
  {
    boolean wasSet = false;
    getTemp = aGetTemp;
    wasSet = true;
    return wasSet;
  }

  public Long getSublinkId(int index)
  {
    Long aSublinkId = sublinkIds.get(index);
    return aSublinkId;
  }

  public List<Long> getSublinkIds()
  {
    return sublinkIds;
  }

  public int numberOfSublinkIds()
  {
    int number = sublinkIds.size();
    return number;
  }

  public boolean hasSublinkIds()
  {
    boolean has = sublinkIds.size() > 0;
    return has;
  }

  public int indexOfSublinkId(Long aSublinkId)
  {
    int index = sublinkIds.indexOf(aSublinkId);
    return index;
  }

  public Integer getGetTemp()
  {
    return getTemp;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "getTemp" + ":" + getGetTemp()+ "]"
     + outputString;
  }
}
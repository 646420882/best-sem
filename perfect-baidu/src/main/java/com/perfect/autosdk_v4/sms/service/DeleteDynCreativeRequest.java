/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 74 "../../../../../../../SDK.ump"
public class DeleteDynCreativeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DeleteDynCreativeRequest Attributes
  private List<Long> dynCreativeIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setDynCreativeIds(List<Long> adynCreativeIds){
    dynCreativeIds = adynCreativeIds;
  }

  public boolean addDynCreativeId(Long aDynCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = dynCreativeIds.add(aDynCreativeId);
    return wasAdded;
  }

  public boolean removeDynCreativeId(Long aDynCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = dynCreativeIds.remove(aDynCreativeId);
    return wasRemoved;
  }

  public Long getDynCreativeId(int index)
  {
    Long aDynCreativeId = dynCreativeIds.get(index);
    return aDynCreativeId;
  }

  public List<Long> getDynCreativeIds()
  {
    return dynCreativeIds;
  }

  public int numberOfDynCreativeIds()
  {
    int number = dynCreativeIds.size();
    return number;
  }

  public boolean hasDynCreativeIds()
  {
    boolean has = dynCreativeIds.size() > 0;
    return has;
  }

  public int indexOfDynCreativeId(Long aDynCreativeId)
  {
    int index = dynCreativeIds.indexOf(aDynCreativeId);
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
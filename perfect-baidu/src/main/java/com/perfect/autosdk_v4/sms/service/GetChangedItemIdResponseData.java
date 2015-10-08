/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;
import java.util.*;

// line 242 "../../../../../../../SDK.ump"
public class GetChangedItemIdResponseData
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedItemIdResponseData Attributes
  private Date endTime;
  private List<ChangedItemIdType> changedItemIds;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEndTime(Date aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public void setChangedItemIds(List<ChangedItemIdType> achangedItemIds){
    changedItemIds = achangedItemIds;
  }

  public boolean addChangedItemId(ChangedItemIdType aChangedItemId)
  {
    boolean wasAdded = false;
    wasAdded = changedItemIds.add(aChangedItemId);
    return wasAdded;
  }

  public boolean removeChangedItemId(ChangedItemIdType aChangedItemId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedItemIds.remove(aChangedItemId);
    return wasRemoved;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ChangedItemIdType getChangedItemId(int index)
  {
    ChangedItemIdType aChangedItemId = changedItemIds.get(index);
    return aChangedItemId;
  }

  public List<ChangedItemIdType> getChangedItemIds()
  {
    return changedItemIds;
  }

  public int numberOfChangedItemIds()
  {
    int number = changedItemIds.size();
    return number;
  }

  public boolean hasChangedItemIds()
  {
    boolean has = changedItemIds.size() > 0;
    return has;
  }

  public int indexOfChangedItemId(ChangedItemIdType aChangedItemId)
  {
    int index = changedItemIds.indexOf(aChangedItemId);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
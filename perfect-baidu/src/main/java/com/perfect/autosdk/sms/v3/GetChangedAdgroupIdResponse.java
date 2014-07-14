/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.Date;
import java.util.*;

// line 231 "../../../../../../../SDKDemo.ump"
public class GetChangedAdgroupIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedAdgroupIdResponse Attributes
  private Date endTime;
  private List<ChangedAdgroupIdType> changedAdgroupIds;

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

  public void setChangedAdgroupIds(List<ChangedAdgroupIdType> achangedAdgroupIds){
    changedAdgroupIds = achangedAdgroupIds;
  }

  public boolean addChangedAdgroupId(ChangedAdgroupIdType aChangedAdgroupId)
  {
    boolean wasAdded = false;
    wasAdded = changedAdgroupIds.add(aChangedAdgroupId);
    return wasAdded;
  }

  public boolean removeChangedAdgroupId(ChangedAdgroupIdType aChangedAdgroupId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedAdgroupIds.remove(aChangedAdgroupId);
    return wasRemoved;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ChangedAdgroupIdType getChangedAdgroupId(int index)
  {
    ChangedAdgroupIdType aChangedAdgroupId = changedAdgroupIds.get(index);
    return aChangedAdgroupId;
  }

  public List<ChangedAdgroupIdType> getChangedAdgroupIds()
  {
    return changedAdgroupIds;
  }

  public int numberOfChangedAdgroupIds()
  {
    int number = changedAdgroupIds.size();
    return number;
  }

  public boolean hasChangedAdgroupIds()
  {
    boolean has = changedAdgroupIds.size() > 0;
    return has;
  }

  public int indexOfChangedAdgroupId(ChangedAdgroupIdType aChangedAdgroupId)
  {
    int index = changedAdgroupIds.indexOf(aChangedAdgroupId);
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
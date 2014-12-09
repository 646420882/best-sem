/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.Date;
import java.util.*;

// line 178 "../../../../../../../SDKDemo.ump"
public class GetChangedNewCreativeIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedNewCreativeIdResponse Attributes
  private Date endTime;
  private List<ChangedNewCreativeType> changedNewCreativeIds;

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

  public void setChangedNewCreativeIds(List<ChangedNewCreativeType> achangedNewCreativeIds){
    changedNewCreativeIds = achangedNewCreativeIds;
  }

  public boolean addChangedNewCreativeId(ChangedNewCreativeType aChangedNewCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = changedNewCreativeIds.add(aChangedNewCreativeId);
    return wasAdded;
  }

  public boolean removeChangedNewCreativeId(ChangedNewCreativeType aChangedNewCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedNewCreativeIds.remove(aChangedNewCreativeId);
    return wasRemoved;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ChangedNewCreativeType getChangedNewCreativeId(int index)
  {
    ChangedNewCreativeType aChangedNewCreativeId = changedNewCreativeIds.get(index);
    return aChangedNewCreativeId;
  }

  public List<ChangedNewCreativeType> getChangedNewCreativeIds()
  {
    return changedNewCreativeIds;
  }

  public int numberOfChangedNewCreativeIds()
  {
    int number = changedNewCreativeIds.size();
    return number;
  }

  public boolean hasChangedNewCreativeIds()
  {
    boolean has = changedNewCreativeIds.size() > 0;
    return has;
  }

  public int indexOfChangedNewCreativeId(ChangedNewCreativeType aChangedNewCreativeId)
  {
    int index = changedNewCreativeIds.indexOf(aChangedNewCreativeId);
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.Date;
import java.util.List;

// line 148 "../../../../../../../SDKDemo.ump"
public class GetChangedCampaignIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedCampaignIdResponse Attributes
  private List<ChangedCampaignIdType> changedCampaignIds;
  private Date endTime;

  //------------------------
  // INTERFACE
  //------------------------

  public void setChangedCampaignIds(List<ChangedCampaignIdType> achangedCampaignIds){
    changedCampaignIds = achangedCampaignIds;
  }

  public boolean addChangedCampaignId(ChangedCampaignIdType aChangedCampaignId)
  {
    boolean wasAdded = false;
    wasAdded = changedCampaignIds.add(aChangedCampaignId);
    return wasAdded;
  }

  public boolean removeChangedCampaignId(ChangedCampaignIdType aChangedCampaignId)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCampaignIds.remove(aChangedCampaignId);
    return wasRemoved;
  }

  public boolean setEndTime(Date aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public ChangedCampaignIdType getChangedCampaignId(int index)
  {
    ChangedCampaignIdType aChangedCampaignId = changedCampaignIds.get(index);
    return aChangedCampaignId;
  }

  public List<ChangedCampaignIdType> getChangedCampaignIds()
  {
    return changedCampaignIds;
  }

  public int numberOfChangedCampaignIds()
  {
    int number = changedCampaignIds.size();
    return number;
  }

  public boolean hasChangedCampaignIds()
  {
    boolean has = changedCampaignIds.size() > 0;
    return has;
  }

  public int indexOfChangedCampaignId(ChangedCampaignIdType aChangedCampaignId)
  {
    int index = changedCampaignIds.indexOf(aChangedCampaignId);
    return index;
  }

  public Date getEndTime()
  {
    return endTime;
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
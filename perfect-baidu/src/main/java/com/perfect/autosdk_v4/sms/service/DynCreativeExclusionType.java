/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 112 "../../../../../../../SDK.ump"
public class DynCreativeExclusionType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DynCreativeExclusionType Attributes
  private Long campaignId;
  private List<ExclusionType> exclusionTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
    wasSet = true;
    return wasSet;
  }

  public void setExclusionTypes(List<ExclusionType> aexclusionTypes){
    exclusionTypes = aexclusionTypes;
  }

  public boolean addExclusionType(ExclusionType aExclusionType)
  {
    boolean wasAdded = false;
    wasAdded = exclusionTypes.add(aExclusionType);
    return wasAdded;
  }

  public boolean removeExclusionType(ExclusionType aExclusionType)
  {
    boolean wasRemoved = false;
    wasRemoved = exclusionTypes.remove(aExclusionType);
    return wasRemoved;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public ExclusionType getExclusionType(int index)
  {
    ExclusionType aExclusionType = exclusionTypes.get(index);
    return aExclusionType;
  }

  public List<ExclusionType> getExclusionTypes()
  {
    return exclusionTypes;
  }

  public int numberOfExclusionTypes()
  {
    int number = exclusionTypes.size();
    return number;
  }

  public boolean hasExclusionTypes()
  {
    boolean has = exclusionTypes.size() > 0;
    return has;
  }

  public int indexOfExclusionType(ExclusionType aExclusionType)
  {
    int index = exclusionTypes.indexOf(aExclusionType);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "campaignId" + ":" + getCampaignId()+ "]"
     + outputString;
  }
}
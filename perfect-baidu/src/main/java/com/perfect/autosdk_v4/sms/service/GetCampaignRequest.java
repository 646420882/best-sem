/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 43 "../../../../../../../SDK.ump"
public class GetCampaignRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCampaignRequest Attributes
  private List<String> campaignFields;
  private List<Long> campaignIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCampaignFields(List<String> acampaignFields){
    campaignFields = acampaignFields;
  }

  public boolean addCampaignField(String aCampaignField)
  {
    boolean wasAdded = false;
    wasAdded = campaignFields.add(aCampaignField);
    return wasAdded;
  }

  public boolean removeCampaignField(String aCampaignField)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignFields.remove(aCampaignField);
    return wasRemoved;
  }

  public void setCampaignIds(List<Long> acampaignIds){
    campaignIds = acampaignIds;
  }

  public boolean addCampaignId(Long aCampaignId)
  {
    boolean wasAdded = false;
    wasAdded = campaignIds.add(aCampaignId);
    return wasAdded;
  }

  public boolean removeCampaignId(Long aCampaignId)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignIds.remove(aCampaignId);
    return wasRemoved;
  }

  public String getCampaignField(int index)
  {
    String aCampaignField = campaignFields.get(index);
    return aCampaignField;
  }

  public List<String> getCampaignFields()
  {
    return campaignFields;
  }

  public int numberOfCampaignFields()
  {
    int number = campaignFields.size();
    return number;
  }

  public boolean hasCampaignFields()
  {
    boolean has = campaignFields.size() > 0;
    return has;
  }

  public int indexOfCampaignField(String aCampaignField)
  {
    int index = campaignFields.indexOf(aCampaignField);
    return index;
  }

  public Long getCampaignId(int index)
  {
    Long aCampaignId = campaignIds.get(index);
    return aCampaignId;
  }

  public List<Long> getCampaignIds()
  {
    return campaignIds;
  }

  public int numberOfCampaignIds()
  {
    int number = campaignIds.size();
    return number;
  }

  public boolean hasCampaignIds()
  {
    boolean has = campaignIds.size() > 0;
    return has;
  }

  public int indexOfCampaignId(Long aCampaignId)
  {
    int index = campaignIds.indexOf(aCampaignId);
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
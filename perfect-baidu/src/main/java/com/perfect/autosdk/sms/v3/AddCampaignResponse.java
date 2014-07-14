/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 82 "../../../../../../../SDKDemo.ump"
public class AddCampaignResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddCampaignResponse Attributes
  private List<CampaignType> campaignTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCampaignTypes(List<CampaignType> acampaignTypes){
    campaignTypes = acampaignTypes;
  }

  public boolean addCampaignType(CampaignType aCampaignType)
  {
    boolean wasAdded = false;
    wasAdded = campaignTypes.add(aCampaignType);
    return wasAdded;
  }

  public boolean removeCampaignType(CampaignType aCampaignType)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignTypes.remove(aCampaignType);
    return wasRemoved;
  }

  public CampaignType getCampaignType(int index)
  {
    CampaignType aCampaignType = campaignTypes.get(index);
    return aCampaignType;
  }

  public List<CampaignType> getCampaignTypes()
  {
    return campaignTypes;
  }

  public int numberOfCampaignTypes()
  {
    int number = campaignTypes.size();
    return number;
  }

  public boolean hasCampaignTypes()
  {
    boolean has = campaignTypes.size() > 0;
    return has;
  }

  public int indexOfCampaignType(CampaignType aCampaignType)
  {
    int index = campaignTypes.indexOf(aCampaignType);
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
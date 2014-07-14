/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 75 "../../../../../../../SDKDemo.ump"
public class GetCampaignBySearchResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCampaignBySearchResponse Attributes
  private Integer moreCampaign;
  private List<CampaignInfo> campaignInfos;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMoreCampaign(Integer aMoreCampaign)
  {
    boolean wasSet = false;
    moreCampaign = aMoreCampaign;
    wasSet = true;
    return wasSet;
  }

  public void setCampaignInfos(List<CampaignInfo> acampaignInfos){
    campaignInfos = acampaignInfos;
  }

  public boolean addCampaignInfo(CampaignInfo aCampaignInfo)
  {
    boolean wasAdded = false;
    wasAdded = campaignInfos.add(aCampaignInfo);
    return wasAdded;
  }

  public boolean removeCampaignInfo(CampaignInfo aCampaignInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignInfos.remove(aCampaignInfo);
    return wasRemoved;
  }

  public Integer getMoreCampaign()
  {
    return moreCampaign;
  }

  public CampaignInfo getCampaignInfo(int index)
  {
    CampaignInfo aCampaignInfo = campaignInfos.get(index);
    return aCampaignInfo;
  }

  public List<CampaignInfo> getCampaignInfos()
  {
    return campaignInfos;
  }

  public int numberOfCampaignInfos()
  {
    int number = campaignInfos.size();
    return number;
  }

  public boolean hasCampaignInfos()
  {
    boolean has = campaignInfos.size() > 0;
    return has;
  }

  public int indexOfCampaignInfo(CampaignInfo aCampaignInfo)
  {
    int index = campaignInfos.indexOf(aCampaignInfo);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "moreCampaign" + ":" + getMoreCampaign()+ "]"
     + outputString;
  }
}
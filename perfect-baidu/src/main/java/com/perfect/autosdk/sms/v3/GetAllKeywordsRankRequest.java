/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 19 "../../../../../../../SDKDemo.ump"
public class GetAllKeywordsRankRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAllKeywordsRankRequest Attributes
  private List<Long> campaignIds;
  private Integer format;

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setFormat(Integer aFormat)
  {
    boolean wasSet = false;
    format = aFormat;
    wasSet = true;
    return wasSet;
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

  public Integer getFormat()
  {
    return format;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "format" + ":" + getFormat()+ "]"
     + outputString;
  }
}
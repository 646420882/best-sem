/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 31 "../../../../../../../SDK.ump"
public class GetCampaignResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCampaignResponse Attributes
  private List<CampaignType> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<CampaignType> adata){
    data = adata;
  }

  public boolean addData(CampaignType aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(CampaignType aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public CampaignType getData(int index)
  {
    CampaignType aData = data.get(index);
    return aData;
  }

  public List<CampaignType> getData()
  {
    return data;
  }

  public int numberOfData()
  {
    int number = data.size();
    return number;
  }

  public boolean hasData()
  {
    boolean has = data.size() > 0;
    return has;
  }

  public int indexOfData(CampaignType aData)
  {
    int index = data.indexOf(aData);
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
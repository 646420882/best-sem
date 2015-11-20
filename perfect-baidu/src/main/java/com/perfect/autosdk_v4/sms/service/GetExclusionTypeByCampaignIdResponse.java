/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 15 "../../../../../../../SDK.ump"
public class GetExclusionTypeByCampaignIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetExclusionTypeByCampaignIdResponse Attributes
  private List<DynCreativeExclusionType> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<DynCreativeExclusionType> adata){
    data = adata;
  }

  public boolean addData(DynCreativeExclusionType aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(DynCreativeExclusionType aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public DynCreativeExclusionType getData(int index)
  {
    DynCreativeExclusionType aData = data.get(index);
    return aData;
  }

  public List<DynCreativeExclusionType> getData()
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

  public int indexOfData(DynCreativeExclusionType aData)
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
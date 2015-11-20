/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 145 "../../../../../../../SDK.ump"
public class GetReportStateResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetReportStateResponse Attributes
  private List<GetReportStateData> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<GetReportStateData> adata){
    data = adata;
  }

  public boolean addData(GetReportStateData aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(GetReportStateData aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public GetReportStateData getData(int index)
  {
    GetReportStateData aData = data.get(index);
    return aData;
  }

  public List<GetReportStateData> getData()
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

  public int indexOfData(GetReportStateData aData)
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
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 90 "../../../../../../../SDK.ump"
public class GetEstimatedDataResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetEstimatedDataResponse Attributes
  private List<GetEstimatedDataResult> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<GetEstimatedDataResult> adata){
    data = adata;
  }

  public boolean addData(GetEstimatedDataResult aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(GetEstimatedDataResult aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public GetEstimatedDataResult getData(int index)
  {
    GetEstimatedDataResult aData = data.get(index);
    return aData;
  }

  public List<GetEstimatedDataResult> getData()
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

  public int indexOfData(GetEstimatedDataResult aData)
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
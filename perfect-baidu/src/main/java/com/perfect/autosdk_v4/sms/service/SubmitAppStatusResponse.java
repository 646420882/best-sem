/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 16 "../../../../../../../SDK.ump"
public class SubmitAppStatusResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SubmitAppStatusResponse Attributes
  private List<AppResponse> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<AppResponse> adata){
    data = adata;
  }

  public boolean addData(AppResponse aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(AppResponse aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public AppResponse getData(int index)
  {
    AppResponse aData = data.get(index);
    return aData;
  }

  public List<AppResponse> getData()
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

  public int indexOfData(AppResponse aData)
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
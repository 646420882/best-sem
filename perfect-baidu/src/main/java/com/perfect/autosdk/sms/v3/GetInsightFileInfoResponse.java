/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 20 "../../../../../../../SDKDemo.ump"
public class GetInsightFileInfoResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetInsightFileInfoResponse Attributes
  private List<BidInsightFileInfo> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<BidInsightFileInfo> adata){
    data = adata;
  }

  public boolean addData(BidInsightFileInfo aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(BidInsightFileInfo aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public BidInsightFileInfo getData(int index)
  {
    BidInsightFileInfo aData = data.get(index);
    return aData;
  }

  public List<BidInsightFileInfo> getData()
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

  public int indexOfData(BidInsightFileInfo aData)
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
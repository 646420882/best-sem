/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 193 "../../../../../../../SDK.ump"
public class GetKRFileIdByWordsResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRFileIdByWordsResponse Attributes
  private List<GetKRFileIdByWordsType> data;

  //------------------------
  // INTERFACE
  //------------------------

  public void setData(List<GetKRFileIdByWordsType> adata){
    data = adata;
  }

  public boolean addData(GetKRFileIdByWordsType aData)
  {
    boolean wasAdded = false;
    wasAdded = data.add(aData);
    return wasAdded;
  }

  public boolean removeData(GetKRFileIdByWordsType aData)
  {
    boolean wasRemoved = false;
    wasRemoved = data.remove(aData);
    return wasRemoved;
  }

  public GetKRFileIdByWordsType getData(int index)
  {
    GetKRFileIdByWordsType aData = data.get(index);
    return aData;
  }

  public List<GetKRFileIdByWordsType> getData()
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

  public int indexOfData(GetKRFileIdByWordsType aData)
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
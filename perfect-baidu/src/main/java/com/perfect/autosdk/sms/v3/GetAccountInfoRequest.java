/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 36 "../../../../../../../SDKDemo.ump"
public class GetAccountInfoRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAccountInfoRequest Attributes
  private List<String> requestData;
  private Integer extended;

  //------------------------
  // INTERFACE
  //------------------------

  public void setRequestData(List<String> arequestData){
    requestData = arequestData;
  }

  public boolean addRequestData(String aRequestData)
  {
    boolean wasAdded = false;
    wasAdded = requestData.add(aRequestData);
    return wasAdded;
  }

  public boolean removeRequestData(String aRequestData)
  {
    boolean wasRemoved = false;
    wasRemoved = requestData.remove(aRequestData);
    return wasRemoved;
  }

  public boolean setExtended(Integer aExtended)
  {
    boolean wasSet = false;
    extended = aExtended;
    wasSet = true;
    return wasSet;
  }

  public String getRequestData(int index)
  {
    String aRequestData = requestData.get(index);
    return aRequestData;
  }

  public List<String> getRequestData()
  {
    return requestData;
  }

  public int numberOfRequestData()
  {
    int number = requestData.size();
    return number;
  }

  public boolean hasRequestData()
  {
    boolean has = requestData.size() > 0;
    return has;
  }

  public int indexOfRequestData(String aRequestData)
  {
    int index = requestData.indexOf(aRequestData);
    return index;
  }

  public Integer getExtended()
  {
    return extended;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "extended" + ":" + getExtended()+ "]"
     + outputString;
  }
}
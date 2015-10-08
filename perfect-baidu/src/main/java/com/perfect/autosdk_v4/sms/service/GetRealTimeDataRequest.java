/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 20 "../../../../../../../SDK.ump"
public class GetRealTimeDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimeDataRequest Attributes
  private ReportRequestType realTimeRequestType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRealTimeRequestType(ReportRequestType aRealTimeRequestType)
  {
    boolean wasSet = false;
    realTimeRequestType = aRealTimeRequestType;
    wasSet = true;
    return wasSet;
  }

  public ReportRequestType getRealTimeRequestType()
  {
    return realTimeRequestType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "realTimeRequestType" + "=" + (getRealTimeRequestType() != null ? !getRealTimeRequestType().equals(this)  ? getRealTimeRequestType().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
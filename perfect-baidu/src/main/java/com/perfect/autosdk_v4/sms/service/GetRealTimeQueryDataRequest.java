/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 151 "../../../../../../../SDK.ump"
public class GetRealTimeQueryDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimeQueryDataRequest Attributes
  private ReportRequestType realTimeQueryRequestType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRealTimeQueryRequestType(ReportRequestType aRealTimeQueryRequestType)
  {
    boolean wasSet = false;
    realTimeQueryRequestType = aRealTimeQueryRequestType;
    wasSet = true;
    return wasSet;
  }

  public ReportRequestType getRealTimeQueryRequestType()
  {
    return realTimeQueryRequestType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "realTimeQueryRequestType" + "=" + (getRealTimeQueryRequestType() != null ? !getRealTimeQueryRequestType().equals(this)  ? getRealTimeQueryRequestType().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 97 "../../../../../../../SDK.ump"
public class GetRealTimePairDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimePairDataRequest Attributes
  private ReportRequestType realTimePairRequestType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRealTimePairRequestType(ReportRequestType aRealTimePairRequestType)
  {
    boolean wasSet = false;
    realTimePairRequestType = aRealTimePairRequestType;
    wasSet = true;
    return wasSet;
  }

  public ReportRequestType getRealTimePairRequestType()
  {
    return realTimePairRequestType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "realTimePairRequestType" + "=" + (getRealTimePairRequestType() != null ? !getRealTimePairRequestType().equals(this)  ? getRealTimePairRequestType().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 85 "../../../../../../../SDK.ump"
public class GetReportFileUrlData
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetReportFileUrlData Attributes
  private String reportFilePath;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReportFilePath(String aReportFilePath)
  {
    boolean wasSet = false;
    reportFilePath = aReportFilePath;
    wasSet = true;
    return wasSet;
  }

  public String getReportFilePath()
  {
    return reportFilePath;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "reportFilePath" + ":" + getReportFilePath()+ "]"
     + outputString;
  }
}
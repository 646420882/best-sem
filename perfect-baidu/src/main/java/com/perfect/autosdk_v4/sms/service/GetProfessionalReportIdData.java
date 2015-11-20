/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 57 "../../../../../../../SDK.ump"
public class GetProfessionalReportIdData
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetProfessionalReportIdData Attributes
  private String reportId;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReportId(String aReportId)
  {
    boolean wasSet = false;
    reportId = aReportId;
    wasSet = true;
    return wasSet;
  }

  public String getReportId()
  {
    return reportId;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "reportId" + ":" + getReportId()+ "]"
     + outputString;
  }
}
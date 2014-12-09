/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 115 "../../../../../../../SDKDemo.ump"
public class GetProfessionalReportIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetProfessionalReportIdRequest Attributes
  private ReportRequestType reportRequestType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReportRequestType(ReportRequestType aReportRequestType)
  {
    boolean wasSet = false;
    reportRequestType = aReportRequestType;
    wasSet = true;
    return wasSet;
  }

  public ReportRequestType getReportRequestType()
  {
    return reportRequestType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reportRequestType" + "=" + (getReportRequestType() != null ? !getReportRequestType().equals(this)  ? getReportRequestType().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
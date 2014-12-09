/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 120 "../../../../../../../SDKDemo.ump"
public class GetProfessionalReportIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetProfessionalReportIdResponse Attributes
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
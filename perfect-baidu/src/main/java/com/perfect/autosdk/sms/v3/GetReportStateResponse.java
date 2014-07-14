/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 130 "../../../../../../../SDKDemo.ump"
public class GetReportStateResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetReportStateResponse Attributes
  private Integer isGenerated;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsGenerated(Integer aIsGenerated)
  {
    boolean wasSet = false;
    isGenerated = aIsGenerated;
    wasSet = true;
    return wasSet;
  }

  public Integer getIsGenerated()
  {
    return isGenerated;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "isGenerated" + ":" + getIsGenerated()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 131 "../../../../../../../SDKDemo.ump"
public class DeleteMonitorWordResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DeleteMonitorWordResponse Attributes
  private String response;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setResponse(String aResponse)
  {
    boolean wasSet = false;
    response = aResponse;
    wasSet = true;
    return wasSet;
  }

  public String getResponse()
  {
    return response;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "response" + ":" + getResponse()+ "]"
     + outputString;
  }
}
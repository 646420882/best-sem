/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 93 "../../../../../../../SDKDemo.ump"
public class DeleteSublinkResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DeleteSublinkResponse Attributes
  private Integer optResult;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOptResult(Integer aOptResult)
  {
    boolean wasSet = false;
    optResult = aOptResult;
    wasSet = true;
    return wasSet;
  }

  public Integer getOptResult()
  {
    return optResult;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "optResult" + ":" + getOptResult()+ "]"
     + outputString;
  }
}
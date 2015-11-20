/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 9 "../../../../../../../SDK.ump"
public class AppResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AppResponse Attributes
  private Integer errorcode;
  private Object data;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setErrorcode(Integer aErrorcode)
  {
    boolean wasSet = false;
    errorcode = aErrorcode;
    wasSet = true;
    return wasSet;
  }

  public boolean setData(Object aData)
  {
    boolean wasSet = false;
    data = aData;
    wasSet = true;
    return wasSet;
  }

  public Integer getErrorcode()
  {
    return errorcode;
  }

  public Object getData()
  {
    return data;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "errorcode" + ":" + getErrorcode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "data" + "=" + (getData() != null ? !getData().equals(this)  ? getData().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
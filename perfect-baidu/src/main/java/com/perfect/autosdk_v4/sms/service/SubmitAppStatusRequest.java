/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 22 "../../../../../../../SDK.ump"
public class SubmitAppStatusRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SubmitAppStatusRequest Attributes
  private Integer event;
  private String app;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEvent(Integer aEvent)
  {
    boolean wasSet = false;
    event = aEvent;
    wasSet = true;
    return wasSet;
  }

  public boolean setApp(String aApp)
  {
    boolean wasSet = false;
    app = aApp;
    wasSet = true;
    return wasSet;
  }

  public Integer getEvent()
  {
    return event;
  }

  public String getApp()
  {
    return app;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "event" + ":" + getEvent()+ "," +
            "app" + ":" + getApp()+ "]"
     + outputString;
  }
}
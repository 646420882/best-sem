/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;

// line 10 "../../../../../../../SDK.ump"
public class OfflineTimeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OfflineTimeType Attributes
  private Date time;
  private Integer flag;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTime(Date aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setFlag(Integer aFlag)
  {
    boolean wasSet = false;
    flag = aFlag;
    wasSet = true;
    return wasSet;
  }

  public Date getTime()
  {
    return time;
  }

  public Integer getFlag()
  {
    return flag;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "flag" + ":" + getFlag()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
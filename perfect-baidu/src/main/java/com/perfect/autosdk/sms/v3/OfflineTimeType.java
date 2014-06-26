/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.Date;

// line 40 "../../../../../../../SDKDemo.ump"
public class OfflineTimeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OfflineTimeType Attributes
  private Integer flag;
  private Date time;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFlag(Integer aFlag)
  {
    boolean wasSet = false;
    flag = aFlag;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Date aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public Integer getFlag()
  {
    return flag;
  }

  public Date getTime()
  {
    return time;
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
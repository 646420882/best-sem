/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 83 "../../../../../../../SDK.ump"
public class ScheduleType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ScheduleType Attributes
  private Long startHour;
  private Long endHour;
  private Long weekDay;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartHour(Long aStartHour)
  {
    boolean wasSet = false;
    startHour = aStartHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndHour(Long aEndHour)
  {
    boolean wasSet = false;
    endHour = aEndHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setWeekDay(Long aWeekDay)
  {
    boolean wasSet = false;
    weekDay = aWeekDay;
    wasSet = true;
    return wasSet;
  }

  public Long getStartHour()
  {
    return startHour;
  }

  public Long getEndHour()
  {
    return endHour;
  }

  public Long getWeekDay()
  {
    return weekDay;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "startHour" + ":" + getStartHour()+ "," +
            "endHour" + ":" + getEndHour()+ "," +
            "weekDay" + ":" + getWeekDay()+ "]"
     + outputString;
  }
}
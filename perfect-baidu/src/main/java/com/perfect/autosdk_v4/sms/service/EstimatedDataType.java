/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 96 "../../../../../../../SDK.ump"
public class EstimatedDataType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EstimatedDataType Attributes
  private Double bid;
  private Long show;
  private Double click;
  private Double charge;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBid(Double aBid)
  {
    boolean wasSet = false;
    bid = aBid;
    wasSet = true;
    return wasSet;
  }

  public boolean setShow(Long aShow)
  {
    boolean wasSet = false;
    show = aShow;
    wasSet = true;
    return wasSet;
  }

  public boolean setClick(Double aClick)
  {
    boolean wasSet = false;
    click = aClick;
    wasSet = true;
    return wasSet;
  }

  public boolean setCharge(Double aCharge)
  {
    boolean wasSet = false;
    charge = aCharge;
    wasSet = true;
    return wasSet;
  }

  public Double getBid()
  {
    return bid;
  }

  public Long getShow()
  {
    return show;
  }

  public Double getClick()
  {
    return click;
  }

  public Double getCharge()
  {
    return charge;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "bid" + ":" + getBid()+ "," +
            "show" + ":" + getShow()+ "," +
            "click" + ":" + getClick()+ "," +
            "charge" + ":" + getCharge()+ "]"
     + outputString;
  }
}
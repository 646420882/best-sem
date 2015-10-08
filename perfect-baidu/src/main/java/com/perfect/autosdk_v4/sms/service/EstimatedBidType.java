/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 33 "../../../../../../../SDK.ump"
public class EstimatedBidType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EstimatedBidType Attributes
  private Long show;
  private Double click;
  private Double cpc;
  private Double charge;
  private Integer rank;
  private Double ctr;

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setCpc(Double aCpc)
  {
    boolean wasSet = false;
    cpc = aCpc;
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

  public boolean setRank(Integer aRank)
  {
    boolean wasSet = false;
    rank = aRank;
    wasSet = true;
    return wasSet;
  }

  public boolean setCtr(Double aCtr)
  {
    boolean wasSet = false;
    ctr = aCtr;
    wasSet = true;
    return wasSet;
  }

  public Long getShow()
  {
    return show;
  }

  public Double getClick()
  {
    return click;
  }

  public Double getCpc()
  {
    return cpc;
  }

  public Double getCharge()
  {
    return charge;
  }

  public Integer getRank()
  {
    return rank;
  }

  public Double getCtr()
  {
    return ctr;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "show" + ":" + getShow()+ "," +
            "click" + ":" + getClick()+ "," +
            "cpc" + ":" + getCpc()+ "," +
            "charge" + ":" + getCharge()+ "," +
            "rank" + ":" + getRank()+ "," +
            "ctr" + ":" + getCtr()+ "]"
     + outputString;
  }
}
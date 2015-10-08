/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 22 "../../../../../../../SDK.ump"
public class GetEstimatedDataByBidResult
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetEstimatedDataByBidResult Attributes
  private String word;
  private Double bid;
  private Integer matchType;
  private EstimatedBidType all;
  private EstimatedBidType pc;
  private EstimatedBidType mobile;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWord(String aWord)
  {
    boolean wasSet = false;
    word = aWord;
    wasSet = true;
    return wasSet;
  }

  public boolean setBid(Double aBid)
  {
    boolean wasSet = false;
    bid = aBid;
    wasSet = true;
    return wasSet;
  }

  public boolean setMatchType(Integer aMatchType)
  {
    boolean wasSet = false;
    matchType = aMatchType;
    wasSet = true;
    return wasSet;
  }

  public boolean setAll(EstimatedBidType aAll)
  {
    boolean wasSet = false;
    all = aAll;
    wasSet = true;
    return wasSet;
  }

  public boolean setPc(EstimatedBidType aPc)
  {
    boolean wasSet = false;
    pc = aPc;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobile(EstimatedBidType aMobile)
  {
    boolean wasSet = false;
    mobile = aMobile;
    wasSet = true;
    return wasSet;
  }

  public String getWord()
  {
    return word;
  }

  public Double getBid()
  {
    return bid;
  }

  public Integer getMatchType()
  {
    return matchType;
  }

  public EstimatedBidType getAll()
  {
    return all;
  }

  public EstimatedBidType getPc()
  {
    return pc;
  }

  public EstimatedBidType getMobile()
  {
    return mobile;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "word" + ":" + getWord()+ "," +
            "bid" + ":" + getBid()+ "," +
            "matchType" + ":" + getMatchType()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "all" + "=" + (getAll() != null ? !getAll().equals(this)  ? getAll().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "pc" + "=" + (getPc() != null ? !getPc().equals(this)  ? getPc().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mobile" + "=" + (getMobile() != null ? !getMobile().equals(this)  ? getMobile().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 147 "../../../../../../../SDK.ump"
public class KREstimatedType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KREstimatedType Attributes
  private String word;
  private Double bid;
  private Integer matchType;

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

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "word" + ":" + getWord()+ "," +
            "bid" + ":" + getBid()+ "," +
            "matchType" + ":" + getMatchType()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 169 "../../../../../../../SDK.ump"
public class KRResult
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KRResult Attributes
  private String word;
  private Long competition;
  private String wordPackage;
  private List<String> businessPoints;
  private Double recBid;
  private Long PV;
  private List<String> showReasons;

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

  public boolean setCompetition(Long aCompetition)
  {
    boolean wasSet = false;
    competition = aCompetition;
    wasSet = true;
    return wasSet;
  }

  public boolean setWordPackage(String aWordPackage)
  {
    boolean wasSet = false;
    wordPackage = aWordPackage;
    wasSet = true;
    return wasSet;
  }

  public boolean setBusinessPoints(List<String> aBusinessPoints)
  {
    boolean wasSet = false;
    businessPoints = aBusinessPoints;
    wasSet = true;
    return wasSet;
  }

  public boolean setRecBid(Double aRecBid)
  {
    boolean wasSet = false;
    recBid = aRecBid;
    wasSet = true;
    return wasSet;
  }

  public boolean setPV(Long aPV)
  {
    boolean wasSet = false;
    PV = aPV;
    wasSet = true;
    return wasSet;
  }

  public boolean setShowReasons(List<String> aShowReasons)
  {
    boolean wasSet = false;
    showReasons = aShowReasons;
    wasSet = true;
    return wasSet;
  }

  public String getWord()
  {
    return word;
  }

  public Long getCompetition()
  {
    return competition;
  }

  public String getWordPackage()
  {
    return wordPackage;
  }

  public List<String> getBusinessPoints()
  {
    return businessPoints;
  }

  public Double getRecBid()
  {
    return recBid;
  }

  public Long getPV()
  {
    return PV;
  }

  public List<String> getShowReasons()
  {
    return showReasons;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "word" + ":" + getWord()+ "," +
            "competition" + ":" + getCompetition()+ "," +
            "wordPackage" + ":" + getWordPackage()+ "," +
            "recBid" + ":" + getRecBid()+ "," +
            "PV" + ":" + getPV()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "businessPoints" + "=" + (getBusinessPoints() != null ? !getBusinessPoints().equals(this)  ? getBusinessPoints().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "showReasons" + "=" + (getShowReasons() != null ? !getShowReasons().equals(this)  ? getShowReasons().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
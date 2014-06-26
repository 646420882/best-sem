/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 57 "../../../../../../../SDKDemo.ump"
public class KRResult
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KRResult Attributes
  private String word;
  private Long exactPV;
  private Long phrasePV;
  private Long broadPV;
  private Integer competition;
  private Integer hotMonth;
  private Long hotMonthPV;
  private String group;
  private Integer flag1;
  private Integer flag2;

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

  public boolean setExactPV(Long aExactPV)
  {
    boolean wasSet = false;
    exactPV = aExactPV;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhrasePV(Long aPhrasePV)
  {
    boolean wasSet = false;
    phrasePV = aPhrasePV;
    wasSet = true;
    return wasSet;
  }

  public boolean setBroadPV(Long aBroadPV)
  {
    boolean wasSet = false;
    broadPV = aBroadPV;
    wasSet = true;
    return wasSet;
  }

  public boolean setCompetition(Integer aCompetition)
  {
    boolean wasSet = false;
    competition = aCompetition;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotMonth(Integer aHotMonth)
  {
    boolean wasSet = false;
    hotMonth = aHotMonth;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotMonthPV(Long aHotMonthPV)
  {
    boolean wasSet = false;
    hotMonthPV = aHotMonthPV;
    wasSet = true;
    return wasSet;
  }

  public boolean setGroup(String aGroup)
  {
    boolean wasSet = false;
    group = aGroup;
    wasSet = true;
    return wasSet;
  }

  public boolean setFlag1(Integer aFlag1)
  {
    boolean wasSet = false;
    flag1 = aFlag1;
    wasSet = true;
    return wasSet;
  }

  public boolean setFlag2(Integer aFlag2)
  {
    boolean wasSet = false;
    flag2 = aFlag2;
    wasSet = true;
    return wasSet;
  }

  public String getWord()
  {
    return word;
  }

  public Long getExactPV()
  {
    return exactPV;
  }

  public Long getPhrasePV()
  {
    return phrasePV;
  }

  public Long getBroadPV()
  {
    return broadPV;
  }

  public Integer getCompetition()
  {
    return competition;
  }

  public Integer getHotMonth()
  {
    return hotMonth;
  }

  public Long getHotMonthPV()
  {
    return hotMonthPV;
  }

  public String getGroup()
  {
    return group;
  }

  public Integer getFlag1()
  {
    return flag1;
  }

  public Integer getFlag2()
  {
    return flag2;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "word" + ":" + getWord()+ "," +
            "exactPV" + ":" + getExactPV()+ "," +
            "phrasePV" + ":" + getPhrasePV()+ "," +
            "broadPV" + ":" + getBroadPV()+ "," +
            "competition" + ":" + getCompetition()+ "," +
            "hotMonth" + ":" + getHotMonth()+ "," +
            "hotMonthPV" + ":" + getHotMonthPV()+ "," +
            "group" + ":" + getGroup()+ "," +
            "flag1" + ":" + getFlag1()+ "," +
            "flag2" + ":" + getFlag2()+ "]"
     + outputString;
  }
}
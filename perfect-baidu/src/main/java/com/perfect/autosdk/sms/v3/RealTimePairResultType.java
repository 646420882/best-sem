/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 70 "../../../../../../../SDKDemo.ump"
public class RealTimePairResultType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RealTimePairResultType Attributes
  private Long keywordId;
  private Long creativeId;
  private List<String> pairInfo;
  private String date;
  private List<String> KPIs;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKeywordId(Long aKeywordId)
  {
    boolean wasSet = false;
    keywordId = aKeywordId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreativeId(Long aCreativeId)
  {
    boolean wasSet = false;
    creativeId = aCreativeId;
    wasSet = true;
    return wasSet;
  }

  public void setPairInfo(List<String> apairInfo){
    pairInfo = apairInfo;
  }

  public boolean addPairInfo(String aPairInfo)
  {
    boolean wasAdded = false;
    wasAdded = pairInfo.add(aPairInfo);
    return wasAdded;
  }

  public boolean removePairInfo(String aPairInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = pairInfo.remove(aPairInfo);
    return wasRemoved;
  }

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public void setKPIs(List<String> aKPIs){
    KPIs = aKPIs;
  }

  public boolean addKPI(String aKPI)
  {
    boolean wasAdded = false;
    wasAdded = KPIs.add(aKPI);
    return wasAdded;
  }

  public boolean removeKPI(String aKPI)
  {
    boolean wasRemoved = false;
    wasRemoved = KPIs.remove(aKPI);
    return wasRemoved;
  }

  public Long getKeywordId()
  {
    return keywordId;
  }

  public Long getCreativeId()
  {
    return creativeId;
  }

  public String getPairInfo(int index)
  {
    String aPairInfo = pairInfo.get(index);
    return aPairInfo;
  }

  public List<String> getPairInfo()
  {
    return pairInfo;
  }

  public int numberOfPairInfo()
  {
    int number = pairInfo.size();
    return number;
  }

  public boolean hasPairInfo()
  {
    boolean has = pairInfo.size() > 0;
    return has;
  }

  public int indexOfPairInfo(String aPairInfo)
  {
    int index = pairInfo.indexOf(aPairInfo);
    return index;
  }

  public String getDate()
  {
    return date;
  }

  public String getKPI(int index)
  {
    String aKPI = KPIs.get(index);
    return aKPI;
  }

  public List<String> getKPIs()
  {
    return KPIs;
  }

  public int numberOfKPIs()
  {
    int number = KPIs.size();
    return number;
  }

  public boolean hasKPIs()
  {
    boolean has = KPIs.size() > 0;
    return has;
  }

  public int indexOfKPI(String aKPI)
  {
    int index = KPIs.indexOf(aKPI);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "keywordId" + ":" + getKeywordId()+ "," +
            "creativeId" + ":" + getCreativeId()+ "," +
            "date" + ":" + getDate()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 75 "../../../../../../../SDK.ump"
public class RealTimeResultType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RealTimeResultType Attributes
  private Long ID;
  private Long relatedId;
  private String date;
  private List<String> KPIs;
  private List<String> name;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setID(Long aID)
  {
    boolean wasSet = false;
    ID = aID;
    wasSet = true;
    return wasSet;
  }

  public boolean setRelatedId(Long aRelatedId)
  {
    boolean wasSet = false;
    relatedId = aRelatedId;
    wasSet = true;
    return wasSet;
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

  public void setName(List<String> aname){
    name = aname;
  }

  public boolean addName(String aName)
  {
    boolean wasAdded = false;
    wasAdded = name.add(aName);
    return wasAdded;
  }

  public boolean removeName(String aName)
  {
    boolean wasRemoved = false;
    wasRemoved = name.remove(aName);
    return wasRemoved;
  }

  public Long getID()
  {
    return ID;
  }

  public Long getRelatedId()
  {
    return relatedId;
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

  public String getName(int index)
  {
    String aName = name.get(index);
    return aName;
  }

  public List<String> getName()
  {
    return name;
  }

  public int numberOfName()
  {
    int number = name.size();
    return number;
  }

  public boolean hasName()
  {
    boolean has = name.size() > 0;
    return has;
  }

  public int indexOfName(String aName)
  {
    int index = name.indexOf(aName);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "ID" + ":" + getID()+ "," +
            "relatedId" + ":" + getRelatedId()+ "," +
            "date" + ":" + getDate()+ "]"
     + outputString;
  }
}
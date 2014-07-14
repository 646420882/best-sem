/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 51 "../../../../../../../SDKDemo.ump"
public class RealTimeResultType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RealTimeResultType Attributes
  private Long ID;
  private List<String> name;
  private String date;
  private List<String> KPIs;
  private Long relatedId;

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

  public boolean setRelatedId(Long aRelatedId)
  {
    boolean wasSet = false;
    relatedId = aRelatedId;
    wasSet = true;
    return wasSet;
  }

  public Long getID()
  {
    return ID;
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

  public Long getRelatedId()
  {
    return relatedId;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "ID" + ":" + getID()+ "," +
            "date" + ":" + getDate()+ "," +
            "relatedId" + ":" + getRelatedId()+ "]"
     + outputString;
  }
}
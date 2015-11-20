/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;
import java.util.*;

// line 30 "../../../../../../../SDK.ump"
public class GetOperationRecordRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetOperationRecordRequest Attributes
  private Date startDate;
  private Date endDate;
  private List<Integer> optTypes;
  private Integer optLevel;
  private List<String> optContents;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public void setOptTypes(List<Integer> aoptTypes){
    optTypes = aoptTypes;
  }

  public boolean addOptType(Integer aOptType)
  {
    boolean wasAdded = false;
    wasAdded = optTypes.add(aOptType);
    return wasAdded;
  }

  public boolean removeOptType(Integer aOptType)
  {
    boolean wasRemoved = false;
    wasRemoved = optTypes.remove(aOptType);
    return wasRemoved;
  }

  public boolean setOptLevel(Integer aOptLevel)
  {
    boolean wasSet = false;
    optLevel = aOptLevel;
    wasSet = true;
    return wasSet;
  }

  public void setOptContents(List<String> aoptContents){
    optContents = aoptContents;
  }

  public boolean addOptContent(String aOptContent)
  {
    boolean wasAdded = false;
    wasAdded = optContents.add(aOptContent);
    return wasAdded;
  }

  public boolean removeOptContent(String aOptContent)
  {
    boolean wasRemoved = false;
    wasRemoved = optContents.remove(aOptContent);
    return wasRemoved;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public Integer getOptType(int index)
  {
    Integer aOptType = optTypes.get(index);
    return aOptType;
  }

  public List<Integer> getOptTypes()
  {
    return optTypes;
  }

  public int numberOfOptTypes()
  {
    int number = optTypes.size();
    return number;
  }

  public boolean hasOptTypes()
  {
    boolean has = optTypes.size() > 0;
    return has;
  }

  public int indexOfOptType(Integer aOptType)
  {
    int index = optTypes.indexOf(aOptType);
    return index;
  }

  public Integer getOptLevel()
  {
    return optLevel;
  }

  public String getOptContent(int index)
  {
    String aOptContent = optContents.get(index);
    return aOptContent;
  }

  public List<String> getOptContents()
  {
    return optContents;
  }

  public int numberOfOptContents()
  {
    int number = optContents.size();
    return number;
  }

  public boolean hasOptContents()
  {
    boolean has = optContents.size() > 0;
    return has;
  }

  public int indexOfOptContent(String aOptContent)
  {
    int index = optContents.indexOf(aOptContent);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "optLevel" + ":" + getOptLevel()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
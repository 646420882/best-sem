/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.Date;
import java.util.List;

// line 36 "../../../../../../../SDKDemo.ump"
public class RealTimeQueryRequestType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RealTimeQueryRequestType Attributes
  private List<String> performanceData;
  private Date startDate;
  private Date endDate;
  private Integer levelOfDetails;
  private List<AttributeType> attributes;
  private Integer reportType;
  private List<Long> statIds;
  private Integer statRange;
  private Integer unitOfTime;
  private Integer number;
  private Integer device;

  //------------------------
  // INTERFACE
  //------------------------

  public void setPerformanceData(List<String> aperformanceData){
    performanceData = aperformanceData;
  }

  public boolean addPerformanceData(String aPerformanceData)
  {
    boolean wasAdded = false;
    wasAdded = performanceData.add(aPerformanceData);
    return wasAdded;
  }

  public boolean removePerformanceData(String aPerformanceData)
  {
    boolean wasRemoved = false;
    wasRemoved = performanceData.remove(aPerformanceData);
    return wasRemoved;
  }

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

  public boolean setLevelOfDetails(Integer aLevelOfDetails)
  {
    boolean wasSet = false;
    levelOfDetails = aLevelOfDetails;
    wasSet = true;
    return wasSet;
  }

  public void setAttributes(List<AttributeType> aattributes){
    attributes = aattributes;
  }

  public boolean addAttribute(AttributeType aAttribute)
  {
    boolean wasAdded = false;
    wasAdded = attributes.add(aAttribute);
    return wasAdded;
  }

  public boolean removeAttribute(AttributeType aAttribute)
  {
    boolean wasRemoved = false;
    wasRemoved = attributes.remove(aAttribute);
    return wasRemoved;
  }

  public boolean setReportType(Integer aReportType)
  {
    boolean wasSet = false;
    reportType = aReportType;
    wasSet = true;
    return wasSet;
  }

  public void setStatIds(List<Long> astatIds){
    statIds = astatIds;
  }

  public boolean addStatId(Long aStatId)
  {
    boolean wasAdded = false;
    wasAdded = statIds.add(aStatId);
    return wasAdded;
  }

  public boolean removeStatId(Long aStatId)
  {
    boolean wasRemoved = false;
    wasRemoved = statIds.remove(aStatId);
    return wasRemoved;
  }

  public boolean setStatRange(Integer aStatRange)
  {
    boolean wasSet = false;
    statRange = aStatRange;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnitOfTime(Integer aUnitOfTime)
  {
    boolean wasSet = false;
    unitOfTime = aUnitOfTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumber(Integer aNumber)
  {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public String getPerformanceData(int index)
  {
    String aPerformanceData = performanceData.get(index);
    return aPerformanceData;
  }

  public List<String> getPerformanceData()
  {
    return performanceData;
  }

  public int numberOfPerformanceData()
  {
    int number = performanceData.size();
    return number;
  }

  public boolean hasPerformanceData()
  {
    boolean has = performanceData.size() > 0;
    return has;
  }

  public int indexOfPerformanceData(String aPerformanceData)
  {
    int index = performanceData.indexOf(aPerformanceData);
    return index;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public Integer getLevelOfDetails()
  {
    return levelOfDetails;
  }

  public AttributeType getAttribute(int index)
  {
    AttributeType aAttribute = attributes.get(index);
    return aAttribute;
  }

  public List<AttributeType> getAttributes()
  {
    return attributes;
  }

  public int numberOfAttributes()
  {
    int number = attributes.size();
    return number;
  }

  public boolean hasAttributes()
  {
    boolean has = attributes.size() > 0;
    return has;
  }

  public int indexOfAttribute(AttributeType aAttribute)
  {
    int index = attributes.indexOf(aAttribute);
    return index;
  }

  public Integer getReportType()
  {
    return reportType;
  }

  public Long getStatId(int index)
  {
    Long aStatId = statIds.get(index);
    return aStatId;
  }

  public List<Long> getStatIds()
  {
    return statIds;
  }

  public int numberOfStatIds()
  {
    int number = statIds.size();
    return number;
  }

  public boolean hasStatIds()
  {
    boolean has = statIds.size() > 0;
    return has;
  }

  public int indexOfStatId(Long aStatId)
  {
    int index = statIds.indexOf(aStatId);
    return index;
  }

  public Integer getStatRange()
  {
    return statRange;
  }

  public Integer getUnitOfTime()
  {
    return unitOfTime;
  }

  public Integer getNumber()
  {
    return number;
  }

  public Integer getDevice()
  {
    return device;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "levelOfDetails" + ":" + getLevelOfDetails()+ "," +
            "reportType" + ":" + getReportType()+ "," +
            "statRange" + ":" + getStatRange()+ "," +
            "unitOfTime" + ":" + getUnitOfTime()+ "," +
            "number" + ":" + getNumber()+ "," +
            "device" + ":" + getDevice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;
import java.util.Date;

// line 125 "../../../../../../../SDK.ump"
public class ReportRequestType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ReportRequestType Attributes
  private List<String> performanceData;
  private Date startDate;
  private Date endDate;
  private Integer levelOfDetails;
  private Boolean idOnly;
  private List<AttributeType> attributes;
  private Integer format;
  private List<Long> statIds;
  private Integer statRange;
  private Integer reportType;
  private Integer unitOfTime;
  private Integer device;
  private Boolean order;
  private Integer number;
  private Integer isrelativetime;

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

  public boolean setIdOnly(Boolean aIdOnly)
  {
    boolean wasSet = false;
    idOnly = aIdOnly;
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

  public boolean setFormat(Integer aFormat)
  {
    boolean wasSet = false;
    format = aFormat;
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

  public boolean setReportType(Integer aReportType)
  {
    boolean wasSet = false;
    reportType = aReportType;
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

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrder(Boolean aOrder)
  {
    boolean wasSet = false;
    order = aOrder;
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

  public boolean setIsrelativetime(Integer aIsrelativetime)
  {
    boolean wasSet = false;
    isrelativetime = aIsrelativetime;
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

  public Boolean getIdOnly()
  {
    return idOnly;
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

  public Integer getFormat()
  {
    return format;
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

  public Integer getReportType()
  {
    return reportType;
  }

  public Integer getUnitOfTime()
  {
    return unitOfTime;
  }

  public Integer getDevice()
  {
    return device;
  }

  public Boolean getOrder()
  {
    return order;
  }

  public Integer getNumber()
  {
    return number;
  }

  public Integer getIsrelativetime()
  {
    return isrelativetime;
  }



  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "levelOfDetails" + ":" + getLevelOfDetails()+ "," +
            "idOnly" + ":" + getIdOnly()+ "," +
            "format" + ":" + getFormat()+ "," +
            "statRange" + ":" + getStatRange()+ "," +
            "reportType" + ":" + getReportType()+ "," +
            "unitOfTime" + ":" + getUnitOfTime()+ "," +
            "device" + ":" + getDevice()+ "," +
            "order" + ":" + getOrder()+ "," +
            "number" + ":" + getNumber()+ "," +
            "isrelativetime" + ":" + getIsrelativetime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
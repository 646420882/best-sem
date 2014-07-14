/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 237 "../../../../../../../SDKDemo.ump"
public class GetSelectedObjectsRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetSelectedObjectsRequest Attributes
  private List<Long> campaignIds;
  private List<String> campaignSelectedColumn;
  private List<String> adgroupSelectedColumn;
  private List<String> keywordSelectedColumn;
  private List<String> creativeSelectedColumn;
  private List<String> newCreativeSelectedColumn;
  private List<VariableColumn> selectedRow;
  private Integer format;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCampaignIds(List<Long> acampaignIds){
    campaignIds = acampaignIds;
  }

  public boolean addCampaignId(Long aCampaignId)
  {
    boolean wasAdded = false;
    wasAdded = campaignIds.add(aCampaignId);
    return wasAdded;
  }

  public boolean removeCampaignId(Long aCampaignId)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignIds.remove(aCampaignId);
    return wasRemoved;
  }

  public void setCampaignSelectedColumn(List<String> acampaignSelectedColumn){
    campaignSelectedColumn = acampaignSelectedColumn;
  }

  public boolean addCampaignSelectedColumn(String aCampaignSelectedColumn)
  {
    boolean wasAdded = false;
    wasAdded = campaignSelectedColumn.add(aCampaignSelectedColumn);
    return wasAdded;
  }

  public boolean removeCampaignSelectedColumn(String aCampaignSelectedColumn)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignSelectedColumn.remove(aCampaignSelectedColumn);
    return wasRemoved;
  }

  public void setAdgroupSelectedColumn(List<String> aadgroupSelectedColumn){
    adgroupSelectedColumn = aadgroupSelectedColumn;
  }

  public boolean addAdgroupSelectedColumn(String aAdgroupSelectedColumn)
  {
    boolean wasAdded = false;
    wasAdded = adgroupSelectedColumn.add(aAdgroupSelectedColumn);
    return wasAdded;
  }

  public boolean removeAdgroupSelectedColumn(String aAdgroupSelectedColumn)
  {
    boolean wasRemoved = false;
    wasRemoved = adgroupSelectedColumn.remove(aAdgroupSelectedColumn);
    return wasRemoved;
  }

  public void setKeywordSelectedColumn(List<String> akeywordSelectedColumn){
    keywordSelectedColumn = akeywordSelectedColumn;
  }

  public boolean addKeywordSelectedColumn(String aKeywordSelectedColumn)
  {
    boolean wasAdded = false;
    wasAdded = keywordSelectedColumn.add(aKeywordSelectedColumn);
    return wasAdded;
  }

  public boolean removeKeywordSelectedColumn(String aKeywordSelectedColumn)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordSelectedColumn.remove(aKeywordSelectedColumn);
    return wasRemoved;
  }

  public void setCreativeSelectedColumn(List<String> acreativeSelectedColumn){
    creativeSelectedColumn = acreativeSelectedColumn;
  }

  public boolean addCreativeSelectedColumn(String aCreativeSelectedColumn)
  {
    boolean wasAdded = false;
    wasAdded = creativeSelectedColumn.add(aCreativeSelectedColumn);
    return wasAdded;
  }

  public boolean removeCreativeSelectedColumn(String aCreativeSelectedColumn)
  {
    boolean wasRemoved = false;
    wasRemoved = creativeSelectedColumn.remove(aCreativeSelectedColumn);
    return wasRemoved;
  }

  public void setNewCreativeSelectedColumn(List<String> anewCreativeSelectedColumn){
    newCreativeSelectedColumn = anewCreativeSelectedColumn;
  }

  public boolean addNewCreativeSelectedColumn(String aNewCreativeSelectedColumn)
  {
    boolean wasAdded = false;
    wasAdded = newCreativeSelectedColumn.add(aNewCreativeSelectedColumn);
    return wasAdded;
  }

  public boolean removeNewCreativeSelectedColumn(String aNewCreativeSelectedColumn)
  {
    boolean wasRemoved = false;
    wasRemoved = newCreativeSelectedColumn.remove(aNewCreativeSelectedColumn);
    return wasRemoved;
  }

  public void setSelectedRow(List<VariableColumn> aselectedRow){
    selectedRow = aselectedRow;
  }

  public boolean addSelectedRow(VariableColumn aSelectedRow)
  {
    boolean wasAdded = false;
    wasAdded = selectedRow.add(aSelectedRow);
    return wasAdded;
  }

  public boolean removeSelectedRow(VariableColumn aSelectedRow)
  {
    boolean wasRemoved = false;
    wasRemoved = selectedRow.remove(aSelectedRow);
    return wasRemoved;
  }

  public boolean setFormat(Integer aFormat)
  {
    boolean wasSet = false;
    format = aFormat;
    wasSet = true;
    return wasSet;
  }

  public Long getCampaignId(int index)
  {
    Long aCampaignId = campaignIds.get(index);
    return aCampaignId;
  }

  public List<Long> getCampaignIds()
  {
    return campaignIds;
  }

  public int numberOfCampaignIds()
  {
    int number = campaignIds.size();
    return number;
  }

  public boolean hasCampaignIds()
  {
    boolean has = campaignIds.size() > 0;
    return has;
  }

  public int indexOfCampaignId(Long aCampaignId)
  {
    int index = campaignIds.indexOf(aCampaignId);
    return index;
  }

  public String getCampaignSelectedColumn(int index)
  {
    String aCampaignSelectedColumn = campaignSelectedColumn.get(index);
    return aCampaignSelectedColumn;
  }

  public List<String> getCampaignSelectedColumn()
  {
    return campaignSelectedColumn;
  }

  public int numberOfCampaignSelectedColumn()
  {
    int number = campaignSelectedColumn.size();
    return number;
  }

  public boolean hasCampaignSelectedColumn()
  {
    boolean has = campaignSelectedColumn.size() > 0;
    return has;
  }

  public int indexOfCampaignSelectedColumn(String aCampaignSelectedColumn)
  {
    int index = campaignSelectedColumn.indexOf(aCampaignSelectedColumn);
    return index;
  }

  public String getAdgroupSelectedColumn(int index)
  {
    String aAdgroupSelectedColumn = adgroupSelectedColumn.get(index);
    return aAdgroupSelectedColumn;
  }

  public List<String> getAdgroupSelectedColumn()
  {
    return adgroupSelectedColumn;
  }

  public int numberOfAdgroupSelectedColumn()
  {
    int number = adgroupSelectedColumn.size();
    return number;
  }

  public boolean hasAdgroupSelectedColumn()
  {
    boolean has = adgroupSelectedColumn.size() > 0;
    return has;
  }

  public int indexOfAdgroupSelectedColumn(String aAdgroupSelectedColumn)
  {
    int index = adgroupSelectedColumn.indexOf(aAdgroupSelectedColumn);
    return index;
  }

  public String getKeywordSelectedColumn(int index)
  {
    String aKeywordSelectedColumn = keywordSelectedColumn.get(index);
    return aKeywordSelectedColumn;
  }

  public List<String> getKeywordSelectedColumn()
  {
    return keywordSelectedColumn;
  }

  public int numberOfKeywordSelectedColumn()
  {
    int number = keywordSelectedColumn.size();
    return number;
  }

  public boolean hasKeywordSelectedColumn()
  {
    boolean has = keywordSelectedColumn.size() > 0;
    return has;
  }

  public int indexOfKeywordSelectedColumn(String aKeywordSelectedColumn)
  {
    int index = keywordSelectedColumn.indexOf(aKeywordSelectedColumn);
    return index;
  }

  public String getCreativeSelectedColumn(int index)
  {
    String aCreativeSelectedColumn = creativeSelectedColumn.get(index);
    return aCreativeSelectedColumn;
  }

  public List<String> getCreativeSelectedColumn()
  {
    return creativeSelectedColumn;
  }

  public int numberOfCreativeSelectedColumn()
  {
    int number = creativeSelectedColumn.size();
    return number;
  }

  public boolean hasCreativeSelectedColumn()
  {
    boolean has = creativeSelectedColumn.size() > 0;
    return has;
  }

  public int indexOfCreativeSelectedColumn(String aCreativeSelectedColumn)
  {
    int index = creativeSelectedColumn.indexOf(aCreativeSelectedColumn);
    return index;
  }

  public String getNewCreativeSelectedColumn(int index)
  {
    String aNewCreativeSelectedColumn = newCreativeSelectedColumn.get(index);
    return aNewCreativeSelectedColumn;
  }

  public List<String> getNewCreativeSelectedColumn()
  {
    return newCreativeSelectedColumn;
  }

  public int numberOfNewCreativeSelectedColumn()
  {
    int number = newCreativeSelectedColumn.size();
    return number;
  }

  public boolean hasNewCreativeSelectedColumn()
  {
    boolean has = newCreativeSelectedColumn.size() > 0;
    return has;
  }

  public int indexOfNewCreativeSelectedColumn(String aNewCreativeSelectedColumn)
  {
    int index = newCreativeSelectedColumn.indexOf(aNewCreativeSelectedColumn);
    return index;
  }

  public VariableColumn getSelectedRow(int index)
  {
    VariableColumn aSelectedRow = selectedRow.get(index);
    return aSelectedRow;
  }

  public List<VariableColumn> getSelectedRow()
  {
    return selectedRow;
  }

  public int numberOfSelectedRow()
  {
    int number = selectedRow.size();
    return number;
  }

  public boolean hasSelectedRow()
  {
    boolean has = selectedRow.size() > 0;
    return has;
  }

  public int indexOfSelectedRow(VariableColumn aSelectedRow)
  {
    int index = selectedRow.indexOf(aSelectedRow);
    return index;
  }

  public Integer getFormat()
  {
    return format;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "format" + ":" + getFormat()+ "]"
     + outputString;
  }
}
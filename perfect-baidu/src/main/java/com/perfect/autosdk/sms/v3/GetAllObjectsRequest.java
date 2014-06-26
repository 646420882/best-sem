/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 204 "../../../../../../../SDKDemo.ump"
public class GetAllObjectsRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAllObjectsRequest Attributes
  private List<Long> campaignIds;
  private Boolean includeQuality;
  private Boolean includeTemp;
  private Integer format;
  private List<VariableColumn> variableColumns;
  private Integer newCreativeFiles;
  private Integer includeTempNewCreatives;
  private Integer includePhraseType;
  private Integer extended;

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

  public boolean setIncludeQuality(Boolean aIncludeQuality)
  {
    boolean wasSet = false;
    includeQuality = aIncludeQuality;
    wasSet = true;
    return wasSet;
  }

  public boolean setIncludeTemp(Boolean aIncludeTemp)
  {
    boolean wasSet = false;
    includeTemp = aIncludeTemp;
    wasSet = true;
    return wasSet;
  }

  public boolean setFormat(Integer aFormat)
  {
    boolean wasSet = false;
    format = aFormat;
    wasSet = true;
    return wasSet;
  }

  public void setVariableColumns(List<VariableColumn> avariableColumns){
    variableColumns = avariableColumns;
  }

  public boolean addVariableColumn(VariableColumn aVariableColumn)
  {
    boolean wasAdded = false;
    wasAdded = variableColumns.add(aVariableColumn);
    return wasAdded;
  }

  public boolean removeVariableColumn(VariableColumn aVariableColumn)
  {
    boolean wasRemoved = false;
    wasRemoved = variableColumns.remove(aVariableColumn);
    return wasRemoved;
  }

  public boolean setNewCreativeFiles(Integer aNewCreativeFiles)
  {
    boolean wasSet = false;
    newCreativeFiles = aNewCreativeFiles;
    wasSet = true;
    return wasSet;
  }

  public boolean setIncludeTempNewCreatives(Integer aIncludeTempNewCreatives)
  {
    boolean wasSet = false;
    includeTempNewCreatives = aIncludeTempNewCreatives;
    wasSet = true;
    return wasSet;
  }

  public boolean setIncludePhraseType(Integer aIncludePhraseType)
  {
    boolean wasSet = false;
    includePhraseType = aIncludePhraseType;
    wasSet = true;
    return wasSet;
  }

  public boolean setExtended(Integer aExtended)
  {
    boolean wasSet = false;
    extended = aExtended;
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

  public Boolean getIncludeQuality()
  {
    return includeQuality;
  }

  public Boolean getIncludeTemp()
  {
    return includeTemp;
  }

  public Integer getFormat()
  {
    return format;
  }

  public VariableColumn getVariableColumn(int index)
  {
    VariableColumn aVariableColumn = variableColumns.get(index);
    return aVariableColumn;
  }

  public List<VariableColumn> getVariableColumns()
  {
    return variableColumns;
  }

  public int numberOfVariableColumns()
  {
    int number = variableColumns.size();
    return number;
  }

  public boolean hasVariableColumns()
  {
    boolean has = variableColumns.size() > 0;
    return has;
  }

  public int indexOfVariableColumn(VariableColumn aVariableColumn)
  {
    int index = variableColumns.indexOf(aVariableColumn);
    return index;
  }

  public Integer getNewCreativeFiles()
  {
    return newCreativeFiles;
  }

  public Integer getIncludeTempNewCreatives()
  {
    return includeTempNewCreatives;
  }

  public Integer getIncludePhraseType()
  {
    return includePhraseType;
  }

  public Integer getExtended()
  {
    return extended;
  }



  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "includeQuality" + ":" + getIncludeQuality()+ "," +
            "includeTemp" + ":" + getIncludeTemp()+ "," +
            "format" + ":" + getFormat()+ "," +
            "newCreativeFiles" + ":" + getNewCreativeFiles()+ "," +
            "includeTempNewCreatives" + ":" + getIncludeTempNewCreatives()+ "," +
            "includePhraseType" + ":" + getIncludePhraseType()+ "," +
            "extended" + ":" + getExtended()+ "]"
     + outputString;
  }
}
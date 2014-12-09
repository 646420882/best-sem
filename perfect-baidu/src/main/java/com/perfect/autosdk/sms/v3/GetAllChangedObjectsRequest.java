/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;
import java.util.Date;

// line 94 "../../../../../../../SDKDemo.ump"
public class GetAllChangedObjectsRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAllChangedObjectsRequest Attributes
  private List<Long> campaignIds;
  private Boolean changedAdgroupFile;
  private Boolean changedCampaignFile;
  private Boolean changedCreativeFile;
  private Boolean changedKeywordFile;
  private Integer changedNewCreativeFiles;
  private Integer format;
  private Boolean includeTemp;
  private Integer includeTempChangedNewCreatives;
  private Date startTime;
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

  public boolean setChangedAdgroupFile(Boolean aChangedAdgroupFile)
  {
    boolean wasSet = false;
    changedAdgroupFile = aChangedAdgroupFile;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedCampaignFile(Boolean aChangedCampaignFile)
  {
    boolean wasSet = false;
    changedCampaignFile = aChangedCampaignFile;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedCreativeFile(Boolean aChangedCreativeFile)
  {
    boolean wasSet = false;
    changedCreativeFile = aChangedCreativeFile;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedKeywordFile(Boolean aChangedKeywordFile)
  {
    boolean wasSet = false;
    changedKeywordFile = aChangedKeywordFile;
    wasSet = true;
    return wasSet;
  }

  public boolean setChangedNewCreativeFiles(Integer aChangedNewCreativeFiles)
  {
    boolean wasSet = false;
    changedNewCreativeFiles = aChangedNewCreativeFiles;
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

  public boolean setIncludeTemp(Boolean aIncludeTemp)
  {
    boolean wasSet = false;
    includeTemp = aIncludeTemp;
    wasSet = true;
    return wasSet;
  }

  public boolean setIncludeTempChangedNewCreatives(Integer aIncludeTempChangedNewCreatives)
  {
    boolean wasSet = false;
    includeTempChangedNewCreatives = aIncludeTempChangedNewCreatives;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Date aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
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

  public Boolean getChangedAdgroupFile()
  {
    return changedAdgroupFile;
  }

  public Boolean getChangedCampaignFile()
  {
    return changedCampaignFile;
  }

  public Boolean getChangedCreativeFile()
  {
    return changedCreativeFile;
  }

  public Boolean getChangedKeywordFile()
  {
    return changedKeywordFile;
  }

  public Integer getChangedNewCreativeFiles()
  {
    return changedNewCreativeFiles;
  }

  public Integer getFormat()
  {
    return format;
  }

  public Boolean getIncludeTemp()
  {
    return includeTemp;
  }

  public Integer getIncludeTempChangedNewCreatives()
  {
    return includeTempChangedNewCreatives;
  }

  public Date getStartTime()
  {
    return startTime;
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
            "changedAdgroupFile" + ":" + getChangedAdgroupFile()+ "," +
            "changedCampaignFile" + ":" + getChangedCampaignFile()+ "," +
            "changedCreativeFile" + ":" + getChangedCreativeFile()+ "," +
            "changedKeywordFile" + ":" + getChangedKeywordFile()+ "," +
            "changedNewCreativeFiles" + ":" + getChangedNewCreativeFiles()+ "," +
            "format" + ":" + getFormat()+ "," +
            "includeTemp" + ":" + getIncludeTemp()+ "," +
            "includeTempChangedNewCreatives" + ":" + getIncludeTempChangedNewCreatives()+ "," +
            "includePhraseType" + ":" + getIncludePhraseType()+ "," +
            "extended" + ":" + getExtended()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
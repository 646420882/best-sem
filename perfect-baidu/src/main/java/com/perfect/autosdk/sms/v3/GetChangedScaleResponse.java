/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 195 "../../../../../../../SDKDemo.ump"
public class GetChangedScaleResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedScaleResponse Attributes
  private List<Long> changedCampaignScale;
  private List<Long> changedAdgroupScale;
  private List<Long> changedKeywordScale;
  private List<Long> changedCreativeScale;
  private List<ChangedNewCreativeScaleType> changedNewCreativeScales;

  //------------------------
  // INTERFACE
  //------------------------

  public void setChangedCampaignScale(List<Long> achangedCampaignScale){
    changedCampaignScale = achangedCampaignScale;
  }

  public boolean addChangedCampaignScale(Long aChangedCampaignScale)
  {
    boolean wasAdded = false;
    wasAdded = changedCampaignScale.add(aChangedCampaignScale);
    return wasAdded;
  }

  public boolean removeChangedCampaignScale(Long aChangedCampaignScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCampaignScale.remove(aChangedCampaignScale);
    return wasRemoved;
  }

  public void setChangedAdgroupScale(List<Long> achangedAdgroupScale){
    changedAdgroupScale = achangedAdgroupScale;
  }

  public boolean addChangedAdgroupScale(Long aChangedAdgroupScale)
  {
    boolean wasAdded = false;
    wasAdded = changedAdgroupScale.add(aChangedAdgroupScale);
    return wasAdded;
  }

  public boolean removeChangedAdgroupScale(Long aChangedAdgroupScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedAdgroupScale.remove(aChangedAdgroupScale);
    return wasRemoved;
  }

  public void setChangedKeywordScale(List<Long> achangedKeywordScale){
    changedKeywordScale = achangedKeywordScale;
  }

  public boolean addChangedKeywordScale(Long aChangedKeywordScale)
  {
    boolean wasAdded = false;
    wasAdded = changedKeywordScale.add(aChangedKeywordScale);
    return wasAdded;
  }

  public boolean removeChangedKeywordScale(Long aChangedKeywordScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedKeywordScale.remove(aChangedKeywordScale);
    return wasRemoved;
  }

  public void setChangedCreativeScale(List<Long> achangedCreativeScale){
    changedCreativeScale = achangedCreativeScale;
  }

  public boolean addChangedCreativeScale(Long aChangedCreativeScale)
  {
    boolean wasAdded = false;
    wasAdded = changedCreativeScale.add(aChangedCreativeScale);
    return wasAdded;
  }

  public boolean removeChangedCreativeScale(Long aChangedCreativeScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedCreativeScale.remove(aChangedCreativeScale);
    return wasRemoved;
  }

  public void setChangedNewCreativeScales(List<ChangedNewCreativeScaleType> achangedNewCreativeScales){
    changedNewCreativeScales = achangedNewCreativeScales;
  }

  public boolean addChangedNewCreativeScale(ChangedNewCreativeScaleType aChangedNewCreativeScale)
  {
    boolean wasAdded = false;
    wasAdded = changedNewCreativeScales.add(aChangedNewCreativeScale);
    return wasAdded;
  }

  public boolean removeChangedNewCreativeScale(ChangedNewCreativeScaleType aChangedNewCreativeScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedNewCreativeScales.remove(aChangedNewCreativeScale);
    return wasRemoved;
  }

  public Long getChangedCampaignScale(int index)
  {
    Long aChangedCampaignScale = changedCampaignScale.get(index);
    return aChangedCampaignScale;
  }

  public List<Long> getChangedCampaignScale()
  {
    return changedCampaignScale;
  }

  public int numberOfChangedCampaignScale()
  {
    int number = changedCampaignScale.size();
    return number;
  }

  public boolean hasChangedCampaignScale()
  {
    boolean has = changedCampaignScale.size() > 0;
    return has;
  }

  public int indexOfChangedCampaignScale(Long aChangedCampaignScale)
  {
    int index = changedCampaignScale.indexOf(aChangedCampaignScale);
    return index;
  }

  public Long getChangedAdgroupScale(int index)
  {
    Long aChangedAdgroupScale = changedAdgroupScale.get(index);
    return aChangedAdgroupScale;
  }

  public List<Long> getChangedAdgroupScale()
  {
    return changedAdgroupScale;
  }

  public int numberOfChangedAdgroupScale()
  {
    int number = changedAdgroupScale.size();
    return number;
  }

  public boolean hasChangedAdgroupScale()
  {
    boolean has = changedAdgroupScale.size() > 0;
    return has;
  }

  public int indexOfChangedAdgroupScale(Long aChangedAdgroupScale)
  {
    int index = changedAdgroupScale.indexOf(aChangedAdgroupScale);
    return index;
  }

  public Long getChangedKeywordScale(int index)
  {
    Long aChangedKeywordScale = changedKeywordScale.get(index);
    return aChangedKeywordScale;
  }

  public List<Long> getChangedKeywordScale()
  {
    return changedKeywordScale;
  }

  public int numberOfChangedKeywordScale()
  {
    int number = changedKeywordScale.size();
    return number;
  }

  public boolean hasChangedKeywordScale()
  {
    boolean has = changedKeywordScale.size() > 0;
    return has;
  }

  public int indexOfChangedKeywordScale(Long aChangedKeywordScale)
  {
    int index = changedKeywordScale.indexOf(aChangedKeywordScale);
    return index;
  }

  public Long getChangedCreativeScale(int index)
  {
    Long aChangedCreativeScale = changedCreativeScale.get(index);
    return aChangedCreativeScale;
  }

  public List<Long> getChangedCreativeScale()
  {
    return changedCreativeScale;
  }

  public int numberOfChangedCreativeScale()
  {
    int number = changedCreativeScale.size();
    return number;
  }

  public boolean hasChangedCreativeScale()
  {
    boolean has = changedCreativeScale.size() > 0;
    return has;
  }

  public int indexOfChangedCreativeScale(Long aChangedCreativeScale)
  {
    int index = changedCreativeScale.indexOf(aChangedCreativeScale);
    return index;
  }

  public ChangedNewCreativeScaleType getChangedNewCreativeScale(int index)
  {
    ChangedNewCreativeScaleType aChangedNewCreativeScale = changedNewCreativeScales.get(index);
    return aChangedNewCreativeScale;
  }

  public List<ChangedNewCreativeScaleType> getChangedNewCreativeScales()
  {
    return changedNewCreativeScales;
  }

  public int numberOfChangedNewCreativeScales()
  {
    int number = changedNewCreativeScales.size();
    return number;
  }

  public boolean hasChangedNewCreativeScales()
  {
    boolean has = changedNewCreativeScales.size() > 0;
    return has;
  }

  public int indexOfChangedNewCreativeScale(ChangedNewCreativeScaleType aChangedNewCreativeScale)
  {
    int index = changedNewCreativeScales.indexOf(aChangedNewCreativeScale);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]"
     + outputString;
  }
}
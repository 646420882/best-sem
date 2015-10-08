/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 276 "../../../../../../../SDK.ump"
public class GetChangedScaleResponseData
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetChangedScaleResponseData Attributes
  private List<Long> changedCampaignScale;
  private List<Long> changedAdgroupScale;
  private List<Long> changedKeywordScale;
  private List<Long> changedCreativeScale;
  private List<Long> changedSublinkScale;
  private List<Long> changedMobileSublinkScale;
  private List<Long> changedPhoneScale;
  private List<Long> changedBridgeScale;
  private List<Long> changedDynamicCreativeScale;

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

  public void setChangedSublinkScale(List<Long> achangedSublinkScale){
    changedSublinkScale = achangedSublinkScale;
  }

  public boolean addChangedSublinkScale(Long aChangedSublinkScale)
  {
    boolean wasAdded = false;
    wasAdded = changedSublinkScale.add(aChangedSublinkScale);
    return wasAdded;
  }

  public boolean removeChangedSublinkScale(Long aChangedSublinkScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedSublinkScale.remove(aChangedSublinkScale);
    return wasRemoved;
  }

  public void setChangedMobileSublinkScale(List<Long> achangedMobileSublinkScale){
    changedMobileSublinkScale = achangedMobileSublinkScale;
  }

  public boolean addChangedMobileSublinkScale(Long aChangedMobileSublinkScale)
  {
    boolean wasAdded = false;
    wasAdded = changedMobileSublinkScale.add(aChangedMobileSublinkScale);
    return wasAdded;
  }

  public boolean removeChangedMobileSublinkScale(Long aChangedMobileSublinkScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedMobileSublinkScale.remove(aChangedMobileSublinkScale);
    return wasRemoved;
  }

  public void setChangedPhoneScale(List<Long> achangedPhoneScale){
    changedPhoneScale = achangedPhoneScale;
  }

  public boolean addChangedPhoneScale(Long aChangedPhoneScale)
  {
    boolean wasAdded = false;
    wasAdded = changedPhoneScale.add(aChangedPhoneScale);
    return wasAdded;
  }

  public boolean removeChangedPhoneScale(Long aChangedPhoneScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedPhoneScale.remove(aChangedPhoneScale);
    return wasRemoved;
  }

  public void setChangedBridgeScale(List<Long> achangedBridgeScale){
    changedBridgeScale = achangedBridgeScale;
  }

  public boolean addChangedBridgeScale(Long aChangedBridgeScale)
  {
    boolean wasAdded = false;
    wasAdded = changedBridgeScale.add(aChangedBridgeScale);
    return wasAdded;
  }

  public boolean removeChangedBridgeScale(Long aChangedBridgeScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedBridgeScale.remove(aChangedBridgeScale);
    return wasRemoved;
  }

  public void setChangedDynamicCreativeScale(List<Long> achangedDynamicCreativeScale){
    changedDynamicCreativeScale = achangedDynamicCreativeScale;
  }

  public boolean addChangedDynamicCreativeScale(Long aChangedDynamicCreativeScale)
  {
    boolean wasAdded = false;
    wasAdded = changedDynamicCreativeScale.add(aChangedDynamicCreativeScale);
    return wasAdded;
  }

  public boolean removeChangedDynamicCreativeScale(Long aChangedDynamicCreativeScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedDynamicCreativeScale.remove(aChangedDynamicCreativeScale);
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

  public Long getChangedSublinkScale(int index)
  {
    Long aChangedSublinkScale = changedSublinkScale.get(index);
    return aChangedSublinkScale;
  }

  public List<Long> getChangedSublinkScale()
  {
    return changedSublinkScale;
  }

  public int numberOfChangedSublinkScale()
  {
    int number = changedSublinkScale.size();
    return number;
  }

  public boolean hasChangedSublinkScale()
  {
    boolean has = changedSublinkScale.size() > 0;
    return has;
  }

  public int indexOfChangedSublinkScale(Long aChangedSublinkScale)
  {
    int index = changedSublinkScale.indexOf(aChangedSublinkScale);
    return index;
  }

  public Long getChangedMobileSublinkScale(int index)
  {
    Long aChangedMobileSublinkScale = changedMobileSublinkScale.get(index);
    return aChangedMobileSublinkScale;
  }

  public List<Long> getChangedMobileSublinkScale()
  {
    return changedMobileSublinkScale;
  }

  public int numberOfChangedMobileSublinkScale()
  {
    int number = changedMobileSublinkScale.size();
    return number;
  }

  public boolean hasChangedMobileSublinkScale()
  {
    boolean has = changedMobileSublinkScale.size() > 0;
    return has;
  }

  public int indexOfChangedMobileSublinkScale(Long aChangedMobileSublinkScale)
  {
    int index = changedMobileSublinkScale.indexOf(aChangedMobileSublinkScale);
    return index;
  }

  public Long getChangedPhoneScale(int index)
  {
    Long aChangedPhoneScale = changedPhoneScale.get(index);
    return aChangedPhoneScale;
  }

  public List<Long> getChangedPhoneScale()
  {
    return changedPhoneScale;
  }

  public int numberOfChangedPhoneScale()
  {
    int number = changedPhoneScale.size();
    return number;
  }

  public boolean hasChangedPhoneScale()
  {
    boolean has = changedPhoneScale.size() > 0;
    return has;
  }

  public int indexOfChangedPhoneScale(Long aChangedPhoneScale)
  {
    int index = changedPhoneScale.indexOf(aChangedPhoneScale);
    return index;
  }

  public Long getChangedBridgeScale(int index)
  {
    Long aChangedBridgeScale = changedBridgeScale.get(index);
    return aChangedBridgeScale;
  }

  public List<Long> getChangedBridgeScale()
  {
    return changedBridgeScale;
  }

  public int numberOfChangedBridgeScale()
  {
    int number = changedBridgeScale.size();
    return number;
  }

  public boolean hasChangedBridgeScale()
  {
    boolean has = changedBridgeScale.size() > 0;
    return has;
  }

  public int indexOfChangedBridgeScale(Long aChangedBridgeScale)
  {
    int index = changedBridgeScale.indexOf(aChangedBridgeScale);
    return index;
  }

  public Long getChangedDynamicCreativeScale(int index)
  {
    Long aChangedDynamicCreativeScale = changedDynamicCreativeScale.get(index);
    return aChangedDynamicCreativeScale;
  }

  public List<Long> getChangedDynamicCreativeScale()
  {
    return changedDynamicCreativeScale;
  }

  public int numberOfChangedDynamicCreativeScale()
  {
    int number = changedDynamicCreativeScale.size();
    return number;
  }

  public boolean hasChangedDynamicCreativeScale()
  {
    boolean has = changedDynamicCreativeScale.size() > 0;
    return has;
  }

  public int indexOfChangedDynamicCreativeScale(Long aChangedDynamicCreativeScale)
  {
    int index = changedDynamicCreativeScale.indexOf(aChangedDynamicCreativeScale);
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
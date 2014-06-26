/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 198 "../../../../../../../SDKDemo.ump"
public class Quality10Type
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Quality10Type Attributes
  private Long id;
  private Long adgroupId;
  private Long campaignId;
  private Integer pcQuality;
  private Integer pcReliable;
  private Integer pcReason;
  private List<Integer> pcScale;
  private Integer mobileQuality;
  private Integer mobileReliable;
  private Integer mobileReason;
  private List<Integer> mobileScale;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Long aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcQuality(Integer aPcQuality)
  {
    boolean wasSet = false;
    pcQuality = aPcQuality;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcReliable(Integer aPcReliable)
  {
    boolean wasSet = false;
    pcReliable = aPcReliable;
    wasSet = true;
    return wasSet;
  }

  public boolean setPcReason(Integer aPcReason)
  {
    boolean wasSet = false;
    pcReason = aPcReason;
    wasSet = true;
    return wasSet;
  }

  public void setPcScale(List<Integer> apcScale){
    pcScale = apcScale;
  }

  public boolean addPcScale(Integer aPcScale)
  {
    boolean wasAdded = false;
    wasAdded = pcScale.add(aPcScale);
    return wasAdded;
  }

  public boolean removePcScale(Integer aPcScale)
  {
    boolean wasRemoved = false;
    wasRemoved = pcScale.remove(aPcScale);
    return wasRemoved;
  }

  public boolean setMobileQuality(Integer aMobileQuality)
  {
    boolean wasSet = false;
    mobileQuality = aMobileQuality;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileReliable(Integer aMobileReliable)
  {
    boolean wasSet = false;
    mobileReliable = aMobileReliable;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobileReason(Integer aMobileReason)
  {
    boolean wasSet = false;
    mobileReason = aMobileReason;
    wasSet = true;
    return wasSet;
  }

  public void setMobileScale(List<Integer> amobileScale){
    mobileScale = amobileScale;
  }

  public boolean addMobileScale(Integer aMobileScale)
  {
    boolean wasAdded = false;
    wasAdded = mobileScale.add(aMobileScale);
    return wasAdded;
  }

  public boolean removeMobileScale(Integer aMobileScale)
  {
    boolean wasRemoved = false;
    wasRemoved = mobileScale.remove(aMobileScale);
    return wasRemoved;
  }

  public Long getId()
  {
    return id;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Integer getPcQuality()
  {
    return pcQuality;
  }

  public Integer getPcReliable()
  {
    return pcReliable;
  }

  public Integer getPcReason()
  {
    return pcReason;
  }

  public Integer getPcScale(int index)
  {
    Integer aPcScale = pcScale.get(index);
    return aPcScale;
  }

  public List<Integer> getPcScale()
  {
    return pcScale;
  }

  public int numberOfPcScale()
  {
    int number = pcScale.size();
    return number;
  }

  public boolean hasPcScale()
  {
    boolean has = pcScale.size() > 0;
    return has;
  }

  public int indexOfPcScale(Integer aPcScale)
  {
    int index = pcScale.indexOf(aPcScale);
    return index;
  }

  public Integer getMobileQuality()
  {
    return mobileQuality;
  }

  public Integer getMobileReliable()
  {
    return mobileReliable;
  }

  public Integer getMobileReason()
  {
    return mobileReason;
  }

  public Integer getMobileScale(int index)
  {
    Integer aMobileScale = mobileScale.get(index);
    return aMobileScale;
  }

  public List<Integer> getMobileScale()
  {
    return mobileScale;
  }

  public int numberOfMobileScale()
  {
    int number = mobileScale.size();
    return number;
  }

  public boolean hasMobileScale()
  {
    boolean has = mobileScale.size() > 0;
    return has;
  }

  public int indexOfMobileScale(Integer aMobileScale)
  {
    int index = mobileScale.indexOf(aMobileScale);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "pcQuality" + ":" + getPcQuality()+ "," +
            "pcReliable" + ":" + getPcReliable()+ "," +
            "pcReason" + ":" + getPcReason()+ "," +
            "mobileQuality" + ":" + getMobileQuality()+ "," +
            "mobileReliable" + ":" + getMobileReliable()+ "," +
            "mobileReason" + ":" + getMobileReason()+ "]"
     + outputString;
  }
}
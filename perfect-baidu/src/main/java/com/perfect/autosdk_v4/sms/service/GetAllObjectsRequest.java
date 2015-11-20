/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 110 "../../../../../../../SDK.ump"
public class GetAllObjectsRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAllObjectsRequest Attributes
  private List<Long> campaignIds;
  private Boolean includeTemp;
  private Integer format;
  private List<String> accountFields;
  private List<String> campaignFields;
  private List<String> adgroupFields;
  private List<String> keywordFields;
  private List<String> creativeFields;
  private List<String> sublinkFields;
  private List<String> mobileSublinkFields;
  private List<String> phoneFields;
  private List<String> bridgeFields;
  private List<String> dynamicCreativeFields;

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

  public void setAccountFields(List<String> aaccountFields){
    accountFields = aaccountFields;
  }

  public boolean addAccountField(String aAccountField)
  {
    boolean wasAdded = false;
    wasAdded = accountFields.add(aAccountField);
    return wasAdded;
  }

  public boolean removeAccountField(String aAccountField)
  {
    boolean wasRemoved = false;
    wasRemoved = accountFields.remove(aAccountField);
    return wasRemoved;
  }

  public void setCampaignFields(List<String> acampaignFields){
    campaignFields = acampaignFields;
  }

  public boolean addCampaignField(String aCampaignField)
  {
    boolean wasAdded = false;
    wasAdded = campaignFields.add(aCampaignField);
    return wasAdded;
  }

  public boolean removeCampaignField(String aCampaignField)
  {
    boolean wasRemoved = false;
    wasRemoved = campaignFields.remove(aCampaignField);
    return wasRemoved;
  }

  public void setAdgroupFields(List<String> aadgroupFields){
    adgroupFields = aadgroupFields;
  }

  public boolean addAdgroupField(String aAdgroupField)
  {
    boolean wasAdded = false;
    wasAdded = adgroupFields.add(aAdgroupField);
    return wasAdded;
  }

  public boolean removeAdgroupField(String aAdgroupField)
  {
    boolean wasRemoved = false;
    wasRemoved = adgroupFields.remove(aAdgroupField);
    return wasRemoved;
  }

  public void setKeywordFields(List<String> akeywordFields){
    keywordFields = akeywordFields;
  }

  public boolean addKeywordField(String aKeywordField)
  {
    boolean wasAdded = false;
    wasAdded = keywordFields.add(aKeywordField);
    return wasAdded;
  }

  public boolean removeKeywordField(String aKeywordField)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordFields.remove(aKeywordField);
    return wasRemoved;
  }

  public void setCreativeFields(List<String> acreativeFields){
    creativeFields = acreativeFields;
  }

  public boolean addCreativeField(String aCreativeField)
  {
    boolean wasAdded = false;
    wasAdded = creativeFields.add(aCreativeField);
    return wasAdded;
  }

  public boolean removeCreativeField(String aCreativeField)
  {
    boolean wasRemoved = false;
    wasRemoved = creativeFields.remove(aCreativeField);
    return wasRemoved;
  }

  public void setSublinkFields(List<String> asublinkFields){
    sublinkFields = asublinkFields;
  }

  public boolean addSublinkField(String aSublinkField)
  {
    boolean wasAdded = false;
    wasAdded = sublinkFields.add(aSublinkField);
    return wasAdded;
  }

  public boolean removeSublinkField(String aSublinkField)
  {
    boolean wasRemoved = false;
    wasRemoved = sublinkFields.remove(aSublinkField);
    return wasRemoved;
  }

  public void setMobileSublinkFields(List<String> amobileSublinkFields){
    mobileSublinkFields = amobileSublinkFields;
  }

  public boolean addMobileSublinkField(String aMobileSublinkField)
  {
    boolean wasAdded = false;
    wasAdded = mobileSublinkFields.add(aMobileSublinkField);
    return wasAdded;
  }

  public boolean removeMobileSublinkField(String aMobileSublinkField)
  {
    boolean wasRemoved = false;
    wasRemoved = mobileSublinkFields.remove(aMobileSublinkField);
    return wasRemoved;
  }

  public void setPhoneFields(List<String> aphoneFields){
    phoneFields = aphoneFields;
  }

  public boolean addPhoneField(String aPhoneField)
  {
    boolean wasAdded = false;
    wasAdded = phoneFields.add(aPhoneField);
    return wasAdded;
  }

  public boolean removePhoneField(String aPhoneField)
  {
    boolean wasRemoved = false;
    wasRemoved = phoneFields.remove(aPhoneField);
    return wasRemoved;
  }

  public void setBridgeFields(List<String> abridgeFields){
    bridgeFields = abridgeFields;
  }

  public boolean addBridgeField(String aBridgeField)
  {
    boolean wasAdded = false;
    wasAdded = bridgeFields.add(aBridgeField);
    return wasAdded;
  }

  public boolean removeBridgeField(String aBridgeField)
  {
    boolean wasRemoved = false;
    wasRemoved = bridgeFields.remove(aBridgeField);
    return wasRemoved;
  }

  public void setDynamicCreativeFields(List<String> adynamicCreativeFields){
    dynamicCreativeFields = adynamicCreativeFields;
  }

  public boolean addDynamicCreativeField(String aDynamicCreativeField)
  {
    boolean wasAdded = false;
    wasAdded = dynamicCreativeFields.add(aDynamicCreativeField);
    return wasAdded;
  }

  public boolean removeDynamicCreativeField(String aDynamicCreativeField)
  {
    boolean wasRemoved = false;
    wasRemoved = dynamicCreativeFields.remove(aDynamicCreativeField);
    return wasRemoved;
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

  public Boolean getIncludeTemp()
  {
    return includeTemp;
  }

  public Integer getFormat()
  {
    return format;
  }

  public String getAccountField(int index)
  {
    String aAccountField = accountFields.get(index);
    return aAccountField;
  }

  public List<String> getAccountFields()
  {
    return accountFields;
  }

  public int numberOfAccountFields()
  {
    int number = accountFields.size();
    return number;
  }

  public boolean hasAccountFields()
  {
    boolean has = accountFields.size() > 0;
    return has;
  }

  public int indexOfAccountField(String aAccountField)
  {
    int index = accountFields.indexOf(aAccountField);
    return index;
  }

  public String getCampaignField(int index)
  {
    String aCampaignField = campaignFields.get(index);
    return aCampaignField;
  }

  public List<String> getCampaignFields()
  {
    return campaignFields;
  }

  public int numberOfCampaignFields()
  {
    int number = campaignFields.size();
    return number;
  }

  public boolean hasCampaignFields()
  {
    boolean has = campaignFields.size() > 0;
    return has;
  }

  public int indexOfCampaignField(String aCampaignField)
  {
    int index = campaignFields.indexOf(aCampaignField);
    return index;
  }

  public String getAdgroupField(int index)
  {
    String aAdgroupField = adgroupFields.get(index);
    return aAdgroupField;
  }

  public List<String> getAdgroupFields()
  {
    return adgroupFields;
  }

  public int numberOfAdgroupFields()
  {
    int number = adgroupFields.size();
    return number;
  }

  public boolean hasAdgroupFields()
  {
    boolean has = adgroupFields.size() > 0;
    return has;
  }

  public int indexOfAdgroupField(String aAdgroupField)
  {
    int index = adgroupFields.indexOf(aAdgroupField);
    return index;
  }

  public String getKeywordField(int index)
  {
    String aKeywordField = keywordFields.get(index);
    return aKeywordField;
  }

  public List<String> getKeywordFields()
  {
    return keywordFields;
  }

  public int numberOfKeywordFields()
  {
    int number = keywordFields.size();
    return number;
  }

  public boolean hasKeywordFields()
  {
    boolean has = keywordFields.size() > 0;
    return has;
  }

  public int indexOfKeywordField(String aKeywordField)
  {
    int index = keywordFields.indexOf(aKeywordField);
    return index;
  }

  public String getCreativeField(int index)
  {
    String aCreativeField = creativeFields.get(index);
    return aCreativeField;
  }

  public List<String> getCreativeFields()
  {
    return creativeFields;
  }

  public int numberOfCreativeFields()
  {
    int number = creativeFields.size();
    return number;
  }

  public boolean hasCreativeFields()
  {
    boolean has = creativeFields.size() > 0;
    return has;
  }

  public int indexOfCreativeField(String aCreativeField)
  {
    int index = creativeFields.indexOf(aCreativeField);
    return index;
  }

  public String getSublinkField(int index)
  {
    String aSublinkField = sublinkFields.get(index);
    return aSublinkField;
  }

  public List<String> getSublinkFields()
  {
    return sublinkFields;
  }

  public int numberOfSublinkFields()
  {
    int number = sublinkFields.size();
    return number;
  }

  public boolean hasSublinkFields()
  {
    boolean has = sublinkFields.size() > 0;
    return has;
  }

  public int indexOfSublinkField(String aSublinkField)
  {
    int index = sublinkFields.indexOf(aSublinkField);
    return index;
  }

  public String getMobileSublinkField(int index)
  {
    String aMobileSublinkField = mobileSublinkFields.get(index);
    return aMobileSublinkField;
  }

  public List<String> getMobileSublinkFields()
  {
    return mobileSublinkFields;
  }

  public int numberOfMobileSublinkFields()
  {
    int number = mobileSublinkFields.size();
    return number;
  }

  public boolean hasMobileSublinkFields()
  {
    boolean has = mobileSublinkFields.size() > 0;
    return has;
  }

  public int indexOfMobileSublinkField(String aMobileSublinkField)
  {
    int index = mobileSublinkFields.indexOf(aMobileSublinkField);
    return index;
  }

  public String getPhoneField(int index)
  {
    String aPhoneField = phoneFields.get(index);
    return aPhoneField;
  }

  public List<String> getPhoneFields()
  {
    return phoneFields;
  }

  public int numberOfPhoneFields()
  {
    int number = phoneFields.size();
    return number;
  }

  public boolean hasPhoneFields()
  {
    boolean has = phoneFields.size() > 0;
    return has;
  }

  public int indexOfPhoneField(String aPhoneField)
  {
    int index = phoneFields.indexOf(aPhoneField);
    return index;
  }

  public String getBridgeField(int index)
  {
    String aBridgeField = bridgeFields.get(index);
    return aBridgeField;
  }

  public List<String> getBridgeFields()
  {
    return bridgeFields;
  }

  public int numberOfBridgeFields()
  {
    int number = bridgeFields.size();
    return number;
  }

  public boolean hasBridgeFields()
  {
    boolean has = bridgeFields.size() > 0;
    return has;
  }

  public int indexOfBridgeField(String aBridgeField)
  {
    int index = bridgeFields.indexOf(aBridgeField);
    return index;
  }

  public String getDynamicCreativeField(int index)
  {
    String aDynamicCreativeField = dynamicCreativeFields.get(index);
    return aDynamicCreativeField;
  }

  public List<String> getDynamicCreativeFields()
  {
    return dynamicCreativeFields;
  }

  public int numberOfDynamicCreativeFields()
  {
    int number = dynamicCreativeFields.size();
    return number;
  }

  public boolean hasDynamicCreativeFields()
  {
    boolean has = dynamicCreativeFields.size() > 0;
    return has;
  }

  public int indexOfDynamicCreativeField(String aDynamicCreativeField)
  {
    int index = dynamicCreativeFields.indexOf(aDynamicCreativeField);
    return index;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "includeTemp" + ":" + getIncludeTemp()+ "," +
            "format" + ":" + getFormat()+ "]"
     + outputString;
  }
}
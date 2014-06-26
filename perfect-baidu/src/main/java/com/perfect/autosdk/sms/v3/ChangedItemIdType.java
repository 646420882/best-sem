/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 57 "../../../../../../../SDKDemo.ump"
public class ChangedItemIdType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChangedItemIdType Attributes
  private Integer operator;
  private Long itemId;
  private Long campaignId;
  private Long adgroupId;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOperator(Integer aOperator)
  {
    boolean wasSet = false;
    operator = aOperator;
    wasSet = true;
    return wasSet;
  }

  public boolean setItemId(Long aItemId)
  {
    boolean wasSet = false;
    itemId = aItemId;
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

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public Integer getOperator()
  {
    return operator;
  }

  public Long getItemId()
  {
    return itemId;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "operator" + ":" + getOperator()+ "," +
            "itemId" + ":" + getItemId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 65 "../../../../../../../SDKDemo.ump"
public class ChangedNewCreativeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChangedNewCreativeType Attributes
  private Integer operator;
  private Long id;
  private Long adgroupId;
  private Long campaignId;

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

  public Integer getOperator()
  {
    return operator;
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

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "operator" + ":" + getOperator()+ "," +
            "id" + ":" + getId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "campaignId" + ":" + getCampaignId()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 44 "../../../../../../../SDKDemo.ump"
public class ChangedCampaignIdType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChangedCampaignIdType Attributes
  private Long campaignId;
  private Integer operator;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
    wasSet = true;
    return wasSet;
  }

  public boolean setOperator(Integer aOperator)
  {
    boolean wasSet = false;
    operator = aOperator;
    wasSet = true;
    return wasSet;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Integer getOperator()
  {
    return operator;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "campaignId" + ":" + getCampaignId()+ "," +
            "operator" + ":" + getOperator()+ "]"
     + outputString;
  }
}
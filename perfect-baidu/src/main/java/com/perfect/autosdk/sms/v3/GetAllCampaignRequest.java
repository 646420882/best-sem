/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 66 "../../../../../../../SDKDemo.ump"
public class GetAllCampaignRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAllCampaignRequest Attributes
  private Integer extended;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setExtended(Integer aExtended)
  {
    boolean wasSet = false;
    extended = aExtended;
    wasSet = true;
    return wasSet;
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
            "extended" + ":" + getExtended()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 131 "../../../../../../../SDKDemo.ump"
public class GetKRbySeedUrlRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRbySeedUrlRequest Attributes
  private String url;
  private SeedFilter seedFilter;
  private Integer device;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setSeedFilter(SeedFilter aSeedFilter)
  {
    boolean wasSet = false;
    seedFilter = aSeedFilter;
    wasSet = true;
    return wasSet;
  }

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public String getUrl()
  {
    return url;
  }

  public SeedFilter getSeedFilter()
  {
    return seedFilter;
  }

  public Integer getDevice()
  {
    return device;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "url" + ":" + getUrl()+ "," +
            "device" + ":" + getDevice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "seedFilter" + "=" + (getSeedFilter() != null ? !getSeedFilter().equals(this)  ? getSeedFilter().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
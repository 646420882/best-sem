/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 182 "../../../../../../../SDKDemo.ump"
public class GetKeywordQualityZipResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordQualityZipResponse Attributes
  private byte[] qualities;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQualities(byte[] aQualities)
  {
    boolean wasSet = false;
    qualities = aQualities;
    wasSet = true;
    return wasSet;
  }

  public byte[] getQualities()
  {
    return qualities;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "qualities" + "=" + (getQualities() != null ? !getQualities().equals(this)  ? getQualities().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
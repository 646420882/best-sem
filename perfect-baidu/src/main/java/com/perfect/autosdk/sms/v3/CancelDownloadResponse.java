/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 259 "../../../../../../../SDKDemo.ump"
public class CancelDownloadResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CancelDownloadResponse Attributes
  private Integer isCanceled;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsCanceled(Integer aIsCanceled)
  {
    boolean wasSet = false;
    isCanceled = aIsCanceled;
    wasSet = true;
    return wasSet;
  }

  public Integer getIsCanceled()
  {
    return isCanceled;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "isCanceled" + ":" + getIsCanceled()+ "]"
     + outputString;
  }
}
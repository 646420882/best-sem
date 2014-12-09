/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 49 "../../../../../../../SDKDemo.ump"
public class DelExclusionTypeResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DelExclusionTypeResponse Attributes
  private Integer result;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setResult(Integer aResult)
  {
    boolean wasSet = false;
    result = aResult;
    wasSet = true;
    return wasSet;
  }

  public Integer getResult()
  {
    return result;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "result" + ":" + getResult()+ "]"
     + outputString;
  }
}
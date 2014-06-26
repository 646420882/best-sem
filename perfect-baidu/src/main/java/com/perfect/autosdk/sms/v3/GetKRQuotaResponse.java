/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 125 "../../../../../../../SDKDemo.ump"
public class GetKRQuotaResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRQuotaResponse Attributes
  private Long total;
  private Long remain;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTotal(Long aTotal)
  {
    boolean wasSet = false;
    total = aTotal;
    wasSet = true;
    return wasSet;
  }

  public boolean setRemain(Long aRemain)
  {
    boolean wasSet = false;
    remain = aRemain;
    wasSet = true;
    return wasSet;
  }

  public Long getTotal()
  {
    return total;
  }

  public Long getRemain()
  {
    return remain;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "total" + ":" + getTotal()+ "," +
            "remain" + ":" + getRemain()+ "]"
     + outputString;
  }
}
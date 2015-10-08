/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 62 "../../../../../../../SDK.ump"
public class GetEstimatedDataResult
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetEstimatedDataResult Attributes
  private List<EstimatedDataType> all;
  private List<EstimatedDataType> pc;
  private List<EstimatedDataType> mobile;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAll(List<EstimatedDataType> aAll)
  {
    boolean wasSet = false;
    all = aAll;
    wasSet = true;
    return wasSet;
  }

  public boolean setPc(List<EstimatedDataType> aPc)
  {
    boolean wasSet = false;
    pc = aPc;
    wasSet = true;
    return wasSet;
  }

  public boolean setMobile(List<EstimatedDataType> aMobile)
  {
    boolean wasSet = false;
    mobile = aMobile;
    wasSet = true;
    return wasSet;
  }

  public List<EstimatedDataType> getAll()
  {
    return all;
  }

  public List<EstimatedDataType> getPc()
  {
    return pc;
  }

  public List<EstimatedDataType> getMobile()
  {
    return mobile;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "all" + "=" + (getAll() != null ? !getAll().equals(this)  ? getAll().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "pc" + "=" + (getPc() != null ? !getPc().equals(this)  ? getPc().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mobile" + "=" + (getMobile() != null ? !getMobile().equals(this)  ? getMobile().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
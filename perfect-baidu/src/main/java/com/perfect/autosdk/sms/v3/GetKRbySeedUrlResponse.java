/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 138 "../../../../../../../SDKDemo.ump"
public class GetKRbySeedUrlResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRbySeedUrlResponse Attributes
  private List<KRResult> krResult;

  //------------------------
  // INTERFACE
  //------------------------

  public void setKrResult(List<KRResult> akrResult){
    krResult = akrResult;
  }

  public boolean addKrResult(KRResult aKrResult)
  {
    boolean wasAdded = false;
    wasAdded = krResult.add(aKrResult);
    return wasAdded;
  }

  public boolean removeKrResult(KRResult aKrResult)
  {
    boolean wasRemoved = false;
    wasRemoved = krResult.remove(aKrResult);
    return wasRemoved;
  }

  public KRResult getKrResult(int index)
  {
    KRResult aKrResult = krResult.get(index);
    return aKrResult;
  }

  public List<KRResult> getKrResult()
  {
    return krResult;
  }

  public int numberOfKrResult()
  {
    int number = krResult.size();
    return number;
  }

  public boolean hasKrResult()
  {
    boolean has = krResult.size() > 0;
    return has;
  }

  public int indexOfKrResult(KRResult aKrResult)
  {
    int index = krResult.indexOf(aKrResult);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]"
     + outputString;
  }
}
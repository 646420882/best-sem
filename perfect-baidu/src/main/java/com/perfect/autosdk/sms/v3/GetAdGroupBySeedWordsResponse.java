/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 34 "../../../../../../../SDKDemo.ump"
public class GetAdGroupBySeedWordsResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdGroupBySeedWordsResponse Attributes
  private List<AutoAdGroupResult> autoAdGroupResults;
  private String sessionid;

  //------------------------
  // INTERFACE
  //------------------------

  public void setAutoAdGroupResults(List<AutoAdGroupResult> aautoAdGroupResults){
    autoAdGroupResults = aautoAdGroupResults;
  }

  public boolean addAutoAdGroupResult(AutoAdGroupResult aAutoAdGroupResult)
  {
    boolean wasAdded = false;
    wasAdded = autoAdGroupResults.add(aAutoAdGroupResult);
    return wasAdded;
  }

  public boolean removeAutoAdGroupResult(AutoAdGroupResult aAutoAdGroupResult)
  {
    boolean wasRemoved = false;
    wasRemoved = autoAdGroupResults.remove(aAutoAdGroupResult);
    return wasRemoved;
  }

  public boolean setSessionid(String aSessionid)
  {
    boolean wasSet = false;
    sessionid = aSessionid;
    wasSet = true;
    return wasSet;
  }

  public AutoAdGroupResult getAutoAdGroupResult(int index)
  {
    AutoAdGroupResult aAutoAdGroupResult = autoAdGroupResults.get(index);
    return aAutoAdGroupResult;
  }

  public List<AutoAdGroupResult> getAutoAdGroupResults()
  {
    return autoAdGroupResults;
  }

  public int numberOfAutoAdGroupResults()
  {
    int number = autoAdGroupResults.size();
    return number;
  }

  public boolean hasAutoAdGroupResults()
  {
    boolean has = autoAdGroupResults.size() > 0;
    return has;
  }

  public int indexOfAutoAdGroupResult(AutoAdGroupResult aAutoAdGroupResult)
  {
    int index = autoAdGroupResults.indexOf(aAutoAdGroupResult);
    return index;
  }

  public String getSessionid()
  {
    return sessionid;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "sessionid" + ":" + getSessionid()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 95 "../../../../../../../SDKDemo.ump"
public class GetCountByIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCountByIdResponse Attributes
  private List<CountInfo> countInfos;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCountInfos(List<CountInfo> acountInfos){
    countInfos = acountInfos;
  }

  public boolean addCountInfo(CountInfo aCountInfo)
  {
    boolean wasAdded = false;
    wasAdded = countInfos.add(aCountInfo);
    return wasAdded;
  }

  public boolean removeCountInfo(CountInfo aCountInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = countInfos.remove(aCountInfo);
    return wasRemoved;
  }

  public CountInfo getCountInfo(int index)
  {
    CountInfo aCountInfo = countInfos.get(index);
    return aCountInfo;
  }

  public List<CountInfo> getCountInfos()
  {
    return countInfos;
  }

  public int numberOfCountInfos()
  {
    int number = countInfos.size();
    return number;
  }

  public boolean hasCountInfos()
  {
    boolean has = countInfos.size() > 0;
    return has;
  }

  public int indexOfCountInfo(CountInfo aCountInfo)
  {
    int index = countInfos.indexOf(aCountInfo);
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
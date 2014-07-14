/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 61 "../../../../../../../SDKDemo.ump"
public class GetAdgroupBySearchResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdgroupBySearchResponse Attributes
  private Integer moreAdgroup;
  private List<AdgroupInfo> adgroupInfos;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMoreAdgroup(Integer aMoreAdgroup)
  {
    boolean wasSet = false;
    moreAdgroup = aMoreAdgroup;
    wasSet = true;
    return wasSet;
  }

  public void setAdgroupInfos(List<AdgroupInfo> aadgroupInfos){
    adgroupInfos = aadgroupInfos;
  }

  public boolean addAdgroupInfo(AdgroupInfo aAdgroupInfo)
  {
    boolean wasAdded = false;
    wasAdded = adgroupInfos.add(aAdgroupInfo);
    return wasAdded;
  }

  public boolean removeAdgroupInfo(AdgroupInfo aAdgroupInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = adgroupInfos.remove(aAdgroupInfo);
    return wasRemoved;
  }

  public Integer getMoreAdgroup()
  {
    return moreAdgroup;
  }

  public AdgroupInfo getAdgroupInfo(int index)
  {
    AdgroupInfo aAdgroupInfo = adgroupInfos.get(index);
    return aAdgroupInfo;
  }

  public List<AdgroupInfo> getAdgroupInfos()
  {
    return adgroupInfos;
  }

  public int numberOfAdgroupInfos()
  {
    int number = adgroupInfos.size();
    return number;
  }

  public boolean hasAdgroupInfos()
  {
    boolean has = adgroupInfos.size() > 0;
    return has;
  }

  public int indexOfAdgroupInfo(AdgroupInfo aAdgroupInfo)
  {
    int index = adgroupInfos.indexOf(aAdgroupInfo);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "moreAdgroup" + ":" + getMoreAdgroup()+ "]"
     + outputString;
  }
}
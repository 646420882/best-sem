/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 63 "../../../../../../../SDKDemo.ump"
public class GetKeywordRankRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordRankRequest Attributes
  private List<Long> winfoids;

  //------------------------
  // INTERFACE
  //------------------------

  public void setWinfoids(List<Long> awinfoids){
    winfoids = awinfoids;
  }

  public boolean addWinfoid(Long aWinfoid)
  {
    boolean wasAdded = false;
    wasAdded = winfoids.add(aWinfoid);
    return wasAdded;
  }

  public boolean removeWinfoid(Long aWinfoid)
  {
    boolean wasRemoved = false;
    wasRemoved = winfoids.remove(aWinfoid);
    return wasRemoved;
  }

  public Long getWinfoid(int index)
  {
    Long aWinfoid = winfoids.get(index);
    return aWinfoid;
  }

  public List<Long> getWinfoids()
  {
    return winfoids;
  }

  public int numberOfWinfoids()
  {
    int number = winfoids.size();
    return number;
  }

  public boolean hasWinfoids()
  {
    boolean has = winfoids.size() > 0;
    return has;
  }

  public int indexOfWinfoid(Long aWinfoid)
  {
    int index = winfoids.indexOf(aWinfoid);
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
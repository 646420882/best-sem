/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 73 "../../../../../../../SDKDemo.ump"
public class ActivateCreativeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ActivateCreativeRequest Attributes
  private List<Long> creativeIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCreativeIds(List<Long> acreativeIds){
    creativeIds = acreativeIds;
  }

  public boolean addCreativeId(Long aCreativeId)
  {
    boolean wasAdded = false;
    wasAdded = creativeIds.add(aCreativeId);
    return wasAdded;
  }

  public boolean removeCreativeId(Long aCreativeId)
  {
    boolean wasRemoved = false;
    wasRemoved = creativeIds.remove(aCreativeId);
    return wasRemoved;
  }

  public Long getCreativeId(int index)
  {
    Long aCreativeId = creativeIds.get(index);
    return aCreativeId;
  }

  public List<Long> getCreativeIds()
  {
    return creativeIds;
  }

  public int numberOfCreativeIds()
  {
    int number = creativeIds.size();
    return number;
  }

  public boolean hasCreativeIds()
  {
    boolean has = creativeIds.size() > 0;
    return has;
  }

  public int indexOfCreativeId(Long aCreativeId)
  {
    int index = creativeIds.indexOf(aCreativeId);
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
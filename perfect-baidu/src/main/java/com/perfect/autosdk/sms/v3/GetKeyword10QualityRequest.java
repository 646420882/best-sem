/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 187 "../../../../../../../SDKDemo.ump"
public class GetKeyword10QualityRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeyword10QualityRequest Attributes
  private List<Long> ids;
  private Integer type;
  private Integer device;
  private Boolean hasScale;

  //------------------------
  // INTERFACE
  //------------------------

  public void setIds(List<Long> aids){
    ids = aids;
  }

  public boolean addId(Long aId)
  {
    boolean wasAdded = false;
    wasAdded = ids.add(aId);
    return wasAdded;
  }

  public boolean removeId(Long aId)
  {
    boolean wasRemoved = false;
    wasRemoved = ids.remove(aId);
    return wasRemoved;
  }

  public boolean setType(Integer aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public boolean setHasScale(Boolean aHasScale)
  {
    boolean wasSet = false;
    hasScale = aHasScale;
    wasSet = true;
    return wasSet;
  }

  public Long getId(int index)
  {
    Long aId = ids.get(index);
    return aId;
  }

  public List<Long> getIds()
  {
    return ids;
  }

  public int numberOfIds()
  {
    int number = ids.size();
    return number;
  }

  public boolean hasIds()
  {
    boolean has = ids.size() > 0;
    return has;
  }

  public int indexOfId(Long aId)
  {
    int index = ids.indexOf(aId);
    return index;
  }

  public Integer getType()
  {
    return type;
  }

  public Integer getDevice()
  {
    return device;
  }

  public Boolean getHasScale()
  {
    return hasScale;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "type" + ":" + getType()+ "," +
            "device" + ":" + getDevice()+ "," +
            "hasScale" + ":" + getHasScale()+ "]"
     + outputString;
  }
}
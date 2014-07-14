/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 73 "../../../../../../../SDKDemo.ump"
public class ChangedNewCreativeScaleType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChangedNewCreativeScaleType Attributes
  private List<Long> changedNewCreativeScale;

  //------------------------
  // INTERFACE
  //------------------------

  public void setChangedNewCreativeScale(List<Long> achangedNewCreativeScale){
    changedNewCreativeScale = achangedNewCreativeScale;
  }

  public boolean addChangedNewCreativeScale(Long aChangedNewCreativeScale)
  {
    boolean wasAdded = false;
    wasAdded = changedNewCreativeScale.add(aChangedNewCreativeScale);
    return wasAdded;
  }

  public boolean removeChangedNewCreativeScale(Long aChangedNewCreativeScale)
  {
    boolean wasRemoved = false;
    wasRemoved = changedNewCreativeScale.remove(aChangedNewCreativeScale);
    return wasRemoved;
  }

  public Long getChangedNewCreativeScale(int index)
  {
    Long aChangedNewCreativeScale = changedNewCreativeScale.get(index);
    return aChangedNewCreativeScale;
  }

  public List<Long> getChangedNewCreativeScale()
  {
    return changedNewCreativeScale;
  }

  public int numberOfChangedNewCreativeScale()
  {
    int number = changedNewCreativeScale.size();
    return number;
  }

  public boolean hasChangedNewCreativeScale()
  {
    boolean has = changedNewCreativeScale.size() > 0;
    return has;
  }

  public int indexOfChangedNewCreativeScale(Long aChangedNewCreativeScale)
  {
    int index = changedNewCreativeScale.indexOf(aChangedNewCreativeScale);
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
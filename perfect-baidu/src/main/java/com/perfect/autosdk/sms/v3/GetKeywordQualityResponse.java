/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 171 "../../../../../../../SDKDemo.ump"
public class GetKeywordQualityResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordQualityResponse Attributes
  private List<QualityType> qualities;

  //------------------------
  // INTERFACE
  //------------------------

  public void setQualities(List<QualityType> aqualities){
    qualities = aqualities;
  }

  public boolean addQuality(QualityType aQuality)
  {
    boolean wasAdded = false;
    wasAdded = qualities.add(aQuality);
    return wasAdded;
  }

  public boolean removeQuality(QualityType aQuality)
  {
    boolean wasRemoved = false;
    wasRemoved = qualities.remove(aQuality);
    return wasRemoved;
  }

  public QualityType getQuality(int index)
  {
    QualityType aQuality = qualities.get(index);
    return aQuality;
  }

  public List<QualityType> getQualities()
  {
    return qualities;
  }

  public int numberOfQualities()
  {
    int number = qualities.size();
    return number;
  }

  public boolean hasQualities()
  {
    boolean has = qualities.size() > 0;
    return has;
  }

  public int indexOfQuality(QualityType aQuality)
  {
    int index = qualities.indexOf(aQuality);
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
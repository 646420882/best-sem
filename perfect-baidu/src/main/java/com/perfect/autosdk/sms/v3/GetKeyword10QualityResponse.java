/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 193 "../../../../../../../SDKDemo.ump"
public class GetKeyword10QualityResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeyword10QualityResponse Attributes
  private List<Quality10Type> keyword10Quality;

  //------------------------
  // INTERFACE
  //------------------------

  public void setKeyword10Quality(List<Quality10Type> akeyword10Quality){
    keyword10Quality = akeyword10Quality;
  }

  public boolean addKeyword10Quality(Quality10Type aKeyword10Quality)
  {
    boolean wasAdded = false;
    wasAdded = keyword10Quality.add(aKeyword10Quality);
    return wasAdded;
  }

  public boolean removeKeyword10Quality(Quality10Type aKeyword10Quality)
  {
    boolean wasRemoved = false;
    wasRemoved = keyword10Quality.remove(aKeyword10Quality);
    return wasRemoved;
  }

  public Quality10Type getKeyword10Quality(int index)
  {
    Quality10Type aKeyword10Quality = keyword10Quality.get(index);
    return aKeyword10Quality;
  }

  public List<Quality10Type> getKeyword10Quality()
  {
    return keyword10Quality;
  }

  public int numberOfKeyword10Quality()
  {
    int number = keyword10Quality.size();
    return number;
  }

  public boolean hasKeyword10Quality()
  {
    boolean has = keyword10Quality.size() > 0;
    return has;
  }

  public int indexOfKeyword10Quality(Quality10Type aKeyword10Quality)
  {
    int index = keyword10Quality.indexOf(aKeyword10Quality);
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
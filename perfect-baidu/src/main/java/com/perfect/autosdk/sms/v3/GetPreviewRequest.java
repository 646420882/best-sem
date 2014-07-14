/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 9 "../../../../../../../SDKDemo.ump"
public class GetPreviewRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetPreviewRequest Attributes
  private List<String> keyWords;
  private Integer device;
  private Integer region;
  private Integer page;
  private Integer display;
  private Integer enableSeo;

  //------------------------
  // INTERFACE
  //------------------------

  public void setKeyWords(List<String> akeyWords){
    keyWords = akeyWords;
  }

  public boolean addKeyWord(String aKeyWord)
  {
    boolean wasAdded = false;
    wasAdded = keyWords.add(aKeyWord);
    return wasAdded;
  }

  public boolean removeKeyWord(String aKeyWord)
  {
    boolean wasRemoved = false;
    wasRemoved = keyWords.remove(aKeyWord);
    return wasRemoved;
  }

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public boolean setRegion(Integer aRegion)
  {
    boolean wasSet = false;
    region = aRegion;
    wasSet = true;
    return wasSet;
  }

  public boolean setPage(Integer aPage)
  {
    boolean wasSet = false;
    page = aPage;
    wasSet = true;
    return wasSet;
  }

  public boolean setDisplay(Integer aDisplay)
  {
    boolean wasSet = false;
    display = aDisplay;
    wasSet = true;
    return wasSet;
  }

  public boolean setEnableSeo(Integer aEnableSeo)
  {
    boolean wasSet = false;
    enableSeo = aEnableSeo;
    wasSet = true;
    return wasSet;
  }

  public String getKeyWord(int index)
  {
    String aKeyWord = keyWords.get(index);
    return aKeyWord;
  }

  public List<String> getKeyWords()
  {
    return keyWords;
  }

  public int numberOfKeyWords()
  {
    int number = keyWords.size();
    return number;
  }

  public boolean hasKeyWords()
  {
    boolean has = keyWords.size() > 0;
    return has;
  }

  public int indexOfKeyWord(String aKeyWord)
  {
    int index = keyWords.indexOf(aKeyWord);
    return index;
  }

  public Integer getDevice()
  {
    return device;
  }

  public Integer getRegion()
  {
    return region;
  }

  public Integer getPage()
  {
    return page;
  }

  public Integer getDisplay()
  {
    return display;
  }

  public Integer getEnableSeo()
  {
    return enableSeo;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "device" + ":" + getDevice()+ "," +
            "region" + ":" + getRegion()+ "," +
            "page" + ":" + getPage()+ "," +
            "display" + ":" + getDisplay()+ "," +
            "enableSeo" + ":" + getEnableSeo()+ "]"
     + outputString;
  }
}
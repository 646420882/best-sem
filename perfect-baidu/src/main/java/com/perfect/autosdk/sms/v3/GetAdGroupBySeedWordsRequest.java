/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 27 "../../../../../../../SDKDemo.ump"
public class GetAdGroupBySeedWordsRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdGroupBySeedWordsRequest Attributes
  private List<Long> adGroupIds;
  private List<String> seedWords;
  private Integer device;

  //------------------------
  // INTERFACE
  //------------------------

  public void setAdGroupIds(List<Long> aadGroupIds){
    adGroupIds = aadGroupIds;
  }

  public boolean addAdGroupId(Long aAdGroupId)
  {
    boolean wasAdded = false;
    wasAdded = adGroupIds.add(aAdGroupId);
    return wasAdded;
  }

  public boolean removeAdGroupId(Long aAdGroupId)
  {
    boolean wasRemoved = false;
    wasRemoved = adGroupIds.remove(aAdGroupId);
    return wasRemoved;
  }

  public void setSeedWords(List<String> aseedWords){
    seedWords = aseedWords;
  }

  public boolean addSeedWord(String aSeedWord)
  {
    boolean wasAdded = false;
    wasAdded = seedWords.add(aSeedWord);
    return wasAdded;
  }

  public boolean removeSeedWord(String aSeedWord)
  {
    boolean wasRemoved = false;
    wasRemoved = seedWords.remove(aSeedWord);
    return wasRemoved;
  }

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public Long getAdGroupId(int index)
  {
    Long aAdGroupId = adGroupIds.get(index);
    return aAdGroupId;
  }

  public List<Long> getAdGroupIds()
  {
    return adGroupIds;
  }

  public int numberOfAdGroupIds()
  {
    int number = adGroupIds.size();
    return number;
  }

  public boolean hasAdGroupIds()
  {
    boolean has = adGroupIds.size() > 0;
    return has;
  }

  public int indexOfAdGroupId(Long aAdGroupId)
  {
    int index = adGroupIds.indexOf(aAdGroupId);
    return index;
  }

  public String getSeedWord(int index)
  {
    String aSeedWord = seedWords.get(index);
    return aSeedWord;
  }

  public List<String> getSeedWords()
  {
    return seedWords;
  }

  public int numberOfSeedWords()
  {
    int number = seedWords.size();
    return number;
  }

  public boolean hasSeedWords()
  {
    boolean has = seedWords.size() > 0;
    return has;
  }

  public int indexOfSeedWord(String aSeedWord)
  {
    int index = seedWords.indexOf(aSeedWord);
    return index;
  }

  public Integer getDevice()
  {
    return device;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "device" + ":" + getDevice()+ "]"
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 28 "../../../../../../../SDKDemo.ump"
public class FilePathType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FilePathType Attributes
  private String accountFilePath;
  private String campaignFilePath;
  private String adgroupFilePath;
  private String keywordFilePath;
  private String creativeFilePath;
  private List<String> newCreativeFilePaths;
  private String accountFileMd5;
  private String campaignFileMd5;
  private String adgroupFileMd5;
  private String keywordFileMd5;
  private String creativeFileMd5;
  private List<String> newCreativeFileMd5s;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccountFilePath(String aAccountFilePath)
  {
    boolean wasSet = false;
    accountFilePath = aAccountFilePath;
    wasSet = true;
    return wasSet;
  }

  public boolean setCampaignFilePath(String aCampaignFilePath)
  {
    boolean wasSet = false;
    campaignFilePath = aCampaignFilePath;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupFilePath(String aAdgroupFilePath)
  {
    boolean wasSet = false;
    adgroupFilePath = aAdgroupFilePath;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeywordFilePath(String aKeywordFilePath)
  {
    boolean wasSet = false;
    keywordFilePath = aKeywordFilePath;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreativeFilePath(String aCreativeFilePath)
  {
    boolean wasSet = false;
    creativeFilePath = aCreativeFilePath;
    wasSet = true;
    return wasSet;
  }

  public void setNewCreativeFilePaths(List<String> anewCreativeFilePaths){
    newCreativeFilePaths = anewCreativeFilePaths;
  }

  public boolean addNewCreativeFilePath(String aNewCreativeFilePath)
  {
    boolean wasAdded = false;
    wasAdded = newCreativeFilePaths.add(aNewCreativeFilePath);
    return wasAdded;
  }

  public boolean removeNewCreativeFilePath(String aNewCreativeFilePath)
  {
    boolean wasRemoved = false;
    wasRemoved = newCreativeFilePaths.remove(aNewCreativeFilePath);
    return wasRemoved;
  }

  public boolean setAccountFileMd5(String aAccountFileMd5)
  {
    boolean wasSet = false;
    accountFileMd5 = aAccountFileMd5;
    wasSet = true;
    return wasSet;
  }

  public boolean setCampaignFileMd5(String aCampaignFileMd5)
  {
    boolean wasSet = false;
    campaignFileMd5 = aCampaignFileMd5;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupFileMd5(String aAdgroupFileMd5)
  {
    boolean wasSet = false;
    adgroupFileMd5 = aAdgroupFileMd5;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeywordFileMd5(String aKeywordFileMd5)
  {
    boolean wasSet = false;
    keywordFileMd5 = aKeywordFileMd5;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreativeFileMd5(String aCreativeFileMd5)
  {
    boolean wasSet = false;
    creativeFileMd5 = aCreativeFileMd5;
    wasSet = true;
    return wasSet;
  }

  public void setNewCreativeFileMd5s(List<String> anewCreativeFileMd5s){
    newCreativeFileMd5s = anewCreativeFileMd5s;
  }

  public boolean addNewCreativeFileMd5(String aNewCreativeFileMd5)
  {
    boolean wasAdded = false;
    wasAdded = newCreativeFileMd5s.add(aNewCreativeFileMd5);
    return wasAdded;
  }

  public boolean removeNewCreativeFileMd5(String aNewCreativeFileMd5)
  {
    boolean wasRemoved = false;
    wasRemoved = newCreativeFileMd5s.remove(aNewCreativeFileMd5);
    return wasRemoved;
  }

  public String getAccountFilePath()
  {
    return accountFilePath;
  }

  public String getCampaignFilePath()
  {
    return campaignFilePath;
  }

  public String getAdgroupFilePath()
  {
    return adgroupFilePath;
  }

  public String getKeywordFilePath()
  {
    return keywordFilePath;
  }

  public String getCreativeFilePath()
  {
    return creativeFilePath;
  }

  public String getNewCreativeFilePath(int index)
  {
    String aNewCreativeFilePath = newCreativeFilePaths.get(index);
    return aNewCreativeFilePath;
  }

  public List<String> getNewCreativeFilePaths()
  {
    return newCreativeFilePaths;
  }

  public int numberOfNewCreativeFilePaths()
  {
    int number = newCreativeFilePaths.size();
    return number;
  }

  public boolean hasNewCreativeFilePaths()
  {
    boolean has = newCreativeFilePaths.size() > 0;
    return has;
  }

  public int indexOfNewCreativeFilePath(String aNewCreativeFilePath)
  {
    int index = newCreativeFilePaths.indexOf(aNewCreativeFilePath);
    return index;
  }

  public String getAccountFileMd5()
  {
    return accountFileMd5;
  }

  public String getCampaignFileMd5()
  {
    return campaignFileMd5;
  }

  public String getAdgroupFileMd5()
  {
    return adgroupFileMd5;
  }

  public String getKeywordFileMd5()
  {
    return keywordFileMd5;
  }

  public String getCreativeFileMd5()
  {
    return creativeFileMd5;
  }

  public String getNewCreativeFileMd5(int index)
  {
    String aNewCreativeFileMd5 = newCreativeFileMd5s.get(index);
    return aNewCreativeFileMd5;
  }

  public List<String> getNewCreativeFileMd5s()
  {
    return newCreativeFileMd5s;
  }

  public int numberOfNewCreativeFileMd5s()
  {
    int number = newCreativeFileMd5s.size();
    return number;
  }

  public boolean hasNewCreativeFileMd5s()
  {
    boolean has = newCreativeFileMd5s.size() > 0;
    return has;
  }

  public int indexOfNewCreativeFileMd5(String aNewCreativeFileMd5)
  {
    int index = newCreativeFileMd5s.indexOf(aNewCreativeFileMd5);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "accountFilePath" + ":" + getAccountFilePath()+ "," +
            "campaignFilePath" + ":" + getCampaignFilePath()+ "," +
            "adgroupFilePath" + ":" + getAdgroupFilePath()+ "," +
            "keywordFilePath" + ":" + getKeywordFilePath()+ "," +
            "creativeFilePath" + ":" + getCreativeFilePath()+ "," +
            "accountFileMd5" + ":" + getAccountFileMd5()+ "," +
            "campaignFileMd5" + ":" + getCampaignFileMd5()+ "," +
            "adgroupFileMd5" + ":" + getAdgroupFileMd5()+ "," +
            "keywordFileMd5" + ":" + getKeywordFileMd5()+ "," +
            "creativeFileMd5" + ":" + getCreativeFileMd5()+ "]"
     + outputString;
  }
}
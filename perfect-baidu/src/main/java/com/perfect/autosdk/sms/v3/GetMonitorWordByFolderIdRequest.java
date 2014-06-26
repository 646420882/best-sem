/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 66 "../../../../../../../SDKDemo.ump"
public class GetMonitorWordByFolderIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetMonitorWordByFolderIdRequest Attributes
  private List<Long> folderIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setFolderIds(List<Long> afolderIds){
    folderIds = afolderIds;
  }

  public boolean addFolderId(Long aFolderId)
  {
    boolean wasAdded = false;
    wasAdded = folderIds.add(aFolderId);
    return wasAdded;
  }

  public boolean removeFolderId(Long aFolderId)
  {
    boolean wasRemoved = false;
    wasRemoved = folderIds.remove(aFolderId);
    return wasRemoved;
  }

  public Long getFolderId(int index)
  {
    Long aFolderId = folderIds.get(index);
    return aFolderId;
  }

  public List<Long> getFolderIds()
  {
    return folderIds;
  }

  public int numberOfFolderIds()
  {
    int number = folderIds.size();
    return number;
  }

  public boolean hasFolderIds()
  {
    boolean has = folderIds.size() > 0;
    return has;
  }

  public int indexOfFolderId(Long aFolderId)
  {
    int index = folderIds.indexOf(aFolderId);
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
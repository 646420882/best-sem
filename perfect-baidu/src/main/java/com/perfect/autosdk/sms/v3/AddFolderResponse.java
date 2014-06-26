/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 91 "../../../../../../../SDKDemo.ump"
public class AddFolderResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddFolderResponse Attributes
  private List<Folder> folders;

  //------------------------
  // INTERFACE
  //------------------------

  public void setFolders(List<Folder> afolders){
    folders = afolders;
  }

  public boolean addFolder(Folder aFolder)
  {
    boolean wasAdded = false;
    wasAdded = folders.add(aFolder);
    return wasAdded;
  }

  public boolean removeFolder(Folder aFolder)
  {
    boolean wasRemoved = false;
    wasRemoved = folders.remove(aFolder);
    return wasRemoved;
  }

  public Folder getFolder(int index)
  {
    Folder aFolder = folders.get(index);
    return aFolder;
  }

  public List<Folder> getFolders()
  {
    return folders;
  }

  public int numberOfFolders()
  {
    int number = folders.size();
    return number;
  }

  public boolean hasFolders()
  {
    boolean has = folders.size() > 0;
    return has;
  }

  public int indexOfFolder(Folder aFolder)
  {
    int index = folders.indexOf(aFolder);
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
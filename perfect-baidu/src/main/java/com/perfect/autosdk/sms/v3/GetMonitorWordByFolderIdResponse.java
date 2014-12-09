/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 71 "../../../../../../../SDKDemo.ump"
public class GetMonitorWordByFolderIdResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetMonitorWordByFolderIdResponse Attributes
  private List<FolderMonitor> folderMonitors;

  //------------------------
  // INTERFACE
  //------------------------

  public void setFolderMonitors(List<FolderMonitor> afolderMonitors){
    folderMonitors = afolderMonitors;
  }

  public boolean addFolderMonitor(FolderMonitor aFolderMonitor)
  {
    boolean wasAdded = false;
    wasAdded = folderMonitors.add(aFolderMonitor);
    return wasAdded;
  }

  public boolean removeFolderMonitor(FolderMonitor aFolderMonitor)
  {
    boolean wasRemoved = false;
    wasRemoved = folderMonitors.remove(aFolderMonitor);
    return wasRemoved;
  }

  public FolderMonitor getFolderMonitor(int index)
  {
    FolderMonitor aFolderMonitor = folderMonitors.get(index);
    return aFolderMonitor;
  }

  public List<FolderMonitor> getFolderMonitors()
  {
    return folderMonitors;
  }

  public int numberOfFolderMonitors()
  {
    int number = folderMonitors.size();
    return number;
  }

  public boolean hasFolderMonitors()
  {
    boolean has = folderMonitors.size() > 0;
    return has;
  }

  public int indexOfFolderMonitor(FolderMonitor aFolderMonitor)
  {
    int index = folderMonitors.indexOf(aFolderMonitor);
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
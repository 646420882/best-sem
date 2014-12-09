/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 17 "../../../../../../../SDKDemo.ump"
public class Folder
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Folder Attributes
  private Long folderId;
  private String folderName;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFolderId(Long aFolderId)
  {
    boolean wasSet = false;
    folderId = aFolderId;
    wasSet = true;
    return wasSet;
  }

  public boolean setFolderName(String aFolderName)
  {
    boolean wasSet = false;
    folderName = aFolderName;
    wasSet = true;
    return wasSet;
  }

  public Long getFolderId()
  {
    return folderId;
  }

  public String getFolderName()
  {
    return folderName;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "folderId" + ":" + getFolderId()+ "," +
            "folderName" + ":" + getFolderName()+ "]"
     + outputString;
  }
}
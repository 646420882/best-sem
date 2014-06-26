/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 134 "../../../../../../../SDKDemo.ump"
public class GetFilePathResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetFilePathResponse Attributes
  private FilePathType filePaths;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFilePaths(FilePathType aFilePaths)
  {
    boolean wasSet = false;
    filePaths = aFilePaths;
    wasSet = true;
    return wasSet;
  }

  public FilePathType getFilePaths()
  {
    return filePaths;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "filePaths" + "=" + (getFilePaths() != null ? !getFilePaths().equals(this)  ? getFilePaths().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 19 "../../../../../../../SDKDemo.ump"
public class GetPreviewResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetPreviewResponse Attributes
  private List<PreviewInfo> previewInfos;

  //------------------------
  // INTERFACE
  //------------------------

  public void setPreviewInfos(List<PreviewInfo> apreviewInfos){
    previewInfos = apreviewInfos;
  }

  public boolean addPreviewInfo(PreviewInfo aPreviewInfo)
  {
    boolean wasAdded = false;
    wasAdded = previewInfos.add(aPreviewInfo);
    return wasAdded;
  }

  public boolean removePreviewInfo(PreviewInfo aPreviewInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = previewInfos.remove(aPreviewInfo);
    return wasRemoved;
  }

  public PreviewInfo getPreviewInfo(int index)
  {
    PreviewInfo aPreviewInfo = previewInfos.get(index);
    return aPreviewInfo;
  }

  public List<PreviewInfo> getPreviewInfos()
  {
    return previewInfos;
  }

  public int numberOfPreviewInfos()
  {
    int number = previewInfos.size();
    return number;
  }

  public boolean hasPreviewInfos()
  {
    boolean has = previewInfos.size() > 0;
    return has;
  }

  public int indexOfPreviewInfo(PreviewInfo aPreviewInfo)
  {
    int index = previewInfos.indexOf(aPreviewInfo);
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
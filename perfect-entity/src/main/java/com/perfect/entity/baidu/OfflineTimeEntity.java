package com.perfect.entity.baidu;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by vbzer_000 on 2014/6/26.
 */
@Document(collection = "OfflineTimeType")
public class OfflineTimeEntity {
    //OfflineTimeType Attributes
    private Integer flag;
    private Date time;

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setFlag(Integer aFlag) {
        boolean wasSet = false;
        flag = aFlag;
        wasSet = true;
        return wasSet;
    }

    public boolean setTime(Date aTime) {
        boolean wasSet = false;
        time = aTime;
        wasSet = true;
        return wasSet;
    }

    public Integer getFlag() {
        return flag;
    }

    public Date getTime() {
        return time;
    }

    public void delete() {
    }

    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "flag" + ":" + getFlag() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this) ? getTime().toString().replaceAll("  ", "    ") : "this" : "null")
                + outputString;
    }
}

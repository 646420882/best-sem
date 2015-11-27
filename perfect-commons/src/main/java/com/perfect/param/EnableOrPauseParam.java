package com.perfect.param;

/**
 * Created by subdong on 15-11-25.
 */
public class EnableOrPauseParam {
    //物料查找层级
    String enableOrPauseType;

    //物料层级对应的操作ID
    String enableOrPauseData;

    //物料开关标识
    Integer enableOrPauseStatus; // 0暂停  1 启用

    public String getEnableOrPauseType() {
        return enableOrPauseType;
    }

    public void setEnableOrPauseType(String enableOrPauseType) {
        this.enableOrPauseType = enableOrPauseType;
    }

    public String getEnableOrPauseData() {
        return enableOrPauseData;
    }

    public void setEnableOrPauseData(String enableOrPauseDataId) {
        this.enableOrPauseData = enableOrPauseDataId;
    }

    public Integer getEnableOrPauseStatus() {
        return enableOrPauseStatus;
    }

    public void setEnableOrPauseStatus(Integer enableOrPauseStatus) {
        this.enableOrPauseStatus = enableOrPauseStatus;
    }

    @Override
    public String toString() {
        return "EnableOrPauseParam{" +
                "enableOrPauseType='" + enableOrPauseType + '\'' +
                ", enableOrPauseDataId='" + enableOrPauseData + '\'' +
                ", enableOrPauseStatus=" + enableOrPauseStatus +
                '}';
    }
}

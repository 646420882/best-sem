package com.perfect.vo;

import com.perfect.dto.creative.MobileSublinkInfoDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2015/2/28.
 */
public class SublinkVo {
    private String id;
    private Long sublinkId;
    private String sublinkInfoVos;
    private Long adgroupId;
    private Boolean pause;
    private Integer status;
    private long accountId;
    private String adgroupObjId;
    private Integer mType;//投放设备 0计算机,1移动设备

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getAdgroupObjId() {
        return adgroupObjId;
    }

    public void setAdgroupObjId(String adgroupObjId) {
        this.adgroupObjId = adgroupObjId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSublinkId() {
        return sublinkId;
    }

    public void setSublinkId(Long sublinkId) {
        this.sublinkId = sublinkId;
    }

    public String getSublinkInfoVos() {
        return sublinkInfoVos;
    }

    public void setSublinkInfoVos(String sublinkInfoVos) {
        this.sublinkInfoVos = sublinkInfoVos;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "SublinkVo{" +
                "accountId=" + accountId +
                ", id='" + id + '\'' +
                ", sublinkId=" + sublinkId +
                ", sublinkInfoVos='" + sublinkInfoVos + '\'' +
                ", adgroupId=" + adgroupId +
                ", pause=" + pause +
                ", status=" + status +
                ", adgroupObjId='" + adgroupObjId + '\'' +
                ", mType=" + mType +
                '}';
    }
}

package com.perfect.dto.creative;

import com.perfect.dto.account.AccountIdDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2015/2/27.
 */
public class MobileSublinkDTO extends AccountIdDTO {
    private String id;
    private Long sublinkId;
    private List<MobileSublinkInfoDTO> mobileSublinkInfos;
    private Long adgroupId;
    private Boolean pause;
    private Integer status;
    private String adgroupObjId;

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

    public List<MobileSublinkInfoDTO> getMobileSublinkInfos() {
        return mobileSublinkInfos;
    }

    public void setMobileSublinkInfos(List<MobileSublinkInfoDTO> mobileSublinkInfos) {
        this.mobileSublinkInfos = mobileSublinkInfos;
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

    @Override
    public String toString() {
        return "MobileSublinkDTO{" +
                "adgroupId=" + adgroupId +
                ", id='" + id + '\'' +
                ", sublinkId=" + sublinkId +
                ", mobileSublinkInfos=" + mobileSublinkInfos +
                ", pause=" + pause +
                ", status=" + status +
                ", adgroupObjId='" + adgroupObjId + '\'' +
                '}';
    }
}

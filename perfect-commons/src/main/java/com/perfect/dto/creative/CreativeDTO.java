package com.perfect.dto.creative;

import com.perfect.dto.account.AccountIdDTO;

/**
 * Created by SubDong on 2014/11/25.
 */
public class CreativeDTO extends AccountIdDTO {

    private Long creativeId;

    private Long adgroupId;

    private String adgroupObjId;

    private String title;

    private String description1;

    private String description2;

    private String pcDestinationUrl;

    private String pcDisplayUrl;

    private String mobileDestinationUrl;

    private String mobileDisplayUrl;

    private Boolean pause;

    private Integer status;

    private Integer devicePreference;

    private Integer localStatus;//本地状态1为新增,2为修改,3为删除,4为级联删除标识

    private String key;//自定义id


    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getPcDestinationUrl() {
        return pcDestinationUrl;
    }

    public void setPcDestinationUrl(String pcDestinationUrl) {
        this.pcDestinationUrl = pcDestinationUrl;
    }

    public String getPcDisplayUrl() {
        return pcDisplayUrl;
    }

    public void setPcDisplayUrl(String pcDisplayUrl) {
        this.pcDisplayUrl = pcDisplayUrl;
    }

    public String getMobileDestinationUrl() {
        return mobileDestinationUrl;
    }

    public void setMobileDestinationUrl(String mobileDestinationUrl) {
        this.mobileDestinationUrl = mobileDestinationUrl;
    }

    public String getMobileDisplayUrl() {
        return mobileDisplayUrl;
    }

    public void setMobileDisplayUrl(String mobileDisplayUrl) {
        this.mobileDisplayUrl = mobileDisplayUrl;
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

    public Integer getDevicePreference() {
        return devicePreference;
    }

    public void setDevicePreference(Integer devicePreference) {
        this.devicePreference = devicePreference;
    }

    public Integer getLocalStatus() {
        return localStatus;
    }

    public void setLocalStatus(Integer localStatus) {
        this.localStatus = localStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "CreativeDTO{" +
                "creativeId=" + creativeId +
                ", adgroupId=" + adgroupId +
                ", adgroupObjId='" + adgroupObjId + '\'' +
                ", title='" + title + '\'' +
                ", description1='" + description1 + '\'' +
                ", description2='" + description2 + '\'' +
                ", pcDestinationUrl='" + pcDestinationUrl + '\'' +
                ", pcDisplayUrl='" + pcDisplayUrl + '\'' +
                ", mobileDestinationUrl='" + mobileDestinationUrl + '\'' +
                ", mobileDisplayUrl='" + mobileDisplayUrl + '\'' +
                ", pause=" + pause +
                ", status=" + status +
                ", devicePreference=" + devicePreference +
                ", localStatus=" + localStatus +
                ", key='" + key + '\'' +
                '}';
    }
}

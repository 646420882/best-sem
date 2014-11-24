package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SubDong on 2014/9/15.
 */
public class FolderMonitorEntity {
    //监控对象id
    @Field("mtId")
    private Long monitorId;
    //监控对象所属的监控文件夹id
    @Field("fdId")
    private Long folderId;
    //监控对象的实际id
    @Field("aclid")
    private Long aclid;
    //监控对象本身所属单元id （暂为  无效属性）
    @Field("adid")
    private Long adgroupId;
    //监控对象本身所属计划id (暂为  无效属性)
    @Field("caId")
    private Long campaignId;
    //监控对象的类型 (目前仅可监控关键词，所以该字段仅提供默认值11 3-计划； 5-单元； 7-创意； 11-关键词)
    @Field("type")
    private Integer type;

    @Field("acId")
    private Long accountId;

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getAclid() {
        return aclid;
    }

    public void setAclid(Long aclid) {
        this.aclid = aclid;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "FolderMonitorEntity{" +
                "monitorId=" + monitorId +
                ", folderId=" + folderId +
                ", aclid=" + aclid +
                ", adgroupId=" + adgroupId +
                ", campaignId=" + campaignId +
                ", type=" + type +
                ", accountId=" + accountId +
                '}';
    }
}

package com.perfect.dto.keyword;

import com.perfect.dto.BaseDTO;

import java.io.Serializable;

/**
 * Created by john on 2014/9/2.
 */
public class KeywordInfoDTO extends BaseDTO implements Serializable {

    private String campaignName;

    private Long campaignId;

    private String adgroupName;

    private KeywordDTO object;

    private long quality;//计算机质量度

    private long mobileQuality;//移动端质量度\

    private Long monitorId;

    private Long folderId;

    private String folderName;

    private Long folderCount;//该关键词所属监控文件夹数
    private String keyword;
    private String keywordId;

    public Long getFolderCount() {
        return folderCount;
    }

    public void setFolderCount(Long folderCount) {
        this.folderCount = folderCount;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public KeywordDTO getObject() {
        return object;
    }

    public void setObject(KeywordDTO object) {
        this.object = object;
    }

    public long getQuality() {
        return quality;
    }

    public void setQuality(long quality) {
        this.quality = quality;
    }

    public long getMobileQuality() {
        return mobileQuality;
    }

    public void setMobileQuality(long mobileQuality) {
        this.mobileQuality = mobileQuality;
    }

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

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

}

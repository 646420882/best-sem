package com.perfect.dto;

import com.perfect.entity.KeywordEntity;

import java.io.Serializable;

/**
 * Created by john on 2014/9/2.
 */
public class KeywordDTO implements Serializable{

    private String campaignName;

    private Long CampaignId;

    private String adgroupName;

    private KeywordEntity object;

    private long quality;

    private long mobileQuality;

    public KeywordDTO(String campaignName, Long campaignId, String adgroupName, KeywordEntity object, long quality, long mobileQuality) {
        this.campaignName = campaignName;
        CampaignId = campaignId;
        this.adgroupName = adgroupName;
        this.object = object;
        this.quality = quality;
        this.mobileQuality = mobileQuality;
    }

    public KeywordDTO() {
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Long getCampaignId() {
        return CampaignId;
    }

    public void setCampaignId(Long campaignId) {
        CampaignId = campaignId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public KeywordEntity getObject() {
        return object;
    }

    public void setObject(KeywordEntity object) {
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
}

package com.perfect.dto;

import com.perfect.entity.KeywordEntity;

import java.io.Serializable;

/**
 * Created by john on 2014/9/2.
 * 可以被删除的关键词
 */
public class KeywordDTO implements Serializable{

    private String campaignName;

    private Long CampaignId;

    private String adgroupName;

    private KeywordEntity object;

    public KeywordDTO(String campaignName, Long campaignId, String adgroupName, KeywordEntity object) {
        this.campaignName = campaignName;
        CampaignId = campaignId;
        this.adgroupName = adgroupName;
        this.object = object;
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
}

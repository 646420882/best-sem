package com.perfect.dto;

import com.perfect.entity.KeywordEntity;

/**
 * Created by john on 2014/9/2.
 * 可以被删除的关键词
 */
public class KeywordDTO{

    private String campaignName;

    private String adgroupName;

    private KeywordEntity object;

    public KeywordDTO(String campaignName, String adgroupName, KeywordEntity object) {
        this.campaignName = campaignName;
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

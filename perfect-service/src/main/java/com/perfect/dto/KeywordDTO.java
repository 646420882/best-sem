package com.perfect.dto;

/**
 * Created by john on 2014/9/2.
 * 可以被删除的关键词
 */
public class KeywordDTO {

    private String campaignName;

    private String adgroupName;

    private Object object;

    public KeywordDTO(String campaignName, String adgroupName, Object object) {
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

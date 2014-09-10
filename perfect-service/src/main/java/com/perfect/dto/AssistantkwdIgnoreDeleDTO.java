package com.perfect.dto;

/**
 * Created by john on 2014/8/22.
 * 推广助手关键字忽略批量删除信息
 */
public class AssistantkwdIgnoreDeleDTO {
    private String campaignName;

    private String adgroupName;

    private String keywordName;

    private String matchModel = "精确";

    public AssistantkwdIgnoreDeleDTO(String campaignName, String adgroupName, String keywordName, String matchModel) {
        this.campaignName = campaignName;
        this.adgroupName = adgroupName;
        this.keywordName = keywordName;
        this.matchModel = matchModel;
    }

    public AssistantkwdIgnoreDeleDTO() {
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

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public String getMatchModel() {
        return matchModel;
    }

    public void setMatchModel(String matchModel) {
        this.matchModel = matchModel;
    }
}

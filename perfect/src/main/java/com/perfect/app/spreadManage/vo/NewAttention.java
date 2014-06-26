package com.perfect.app.spreadManage.vo;

/**
 * Created by baizz on 2014-6-12.
 */
public class NewAttention {
    private Long keywordId;
    private String keyword;
    private Long adgroupId;
    private String adgroupName;
    private Long campaignId;
    private String campaignName;

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewAttention that = (NewAttention) o;

        if (adgroupId != null ? !adgroupId.equals(that.adgroupId) : that.adgroupId != null) return false;
        if (adgroupName != null ? !adgroupName.equals(that.adgroupName) : that.adgroupName != null) return false;
        if (campaignId != null ? !campaignId.equals(that.campaignId) : that.campaignId != null) return false;
        if (campaignName != null ? !campaignName.equals(that.campaignName) : that.campaignName != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;
        if (keywordId != null ? !keywordId.equals(that.keywordId) : that.keywordId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keywordId != null ? keywordId.hashCode() : 0;
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (adgroupId != null ? adgroupId.hashCode() : 0);
        result = 31 * result + (adgroupName != null ? adgroupName.hashCode() : 0);
        result = 31 * result + (campaignId != null ? campaignId.hashCode() : 0);
        result = 31 * result + (campaignName != null ? campaignName.hashCode() : 0);
        return result;
    }
}

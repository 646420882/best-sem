package com.perfect.dto.keyword;

import com.perfect.dto.BaseDTO;

/**
 * Created by XiaoWei on 2014/11/26.
 */
public class KeywordImportDTO extends BaseDTO {
    private String customGroupId;
    private Long keywordId;
    private String keywordName;
    private Long accountId;
    private String biddingStatus;
    private Boolean rule;
    private Long adgroupId;


    public String getCustomGroupId() {
        return customGroupId;
    }

    public void setCustomGroupId(String customGroupId) {
        this.customGroupId = customGroupId;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Boolean getRule() {
        return rule;
    }

    public void setRule(Boolean rule) {
        this.rule = rule;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    @Override
    public String toString() {
        return "KeywordImportDTO{" +
                ", customGroupId='" + customGroupId + '\'' +
                ", keywordId=" + keywordId +
                ", keywordName='" + keywordName + '\'' +
                ", accountId=" + accountId +
                ", biddingStatus='" + biddingStatus + '\'' +
                ", rule=" + rule +
                ", adgroupId=" + adgroupId +
                '}';
    }
}

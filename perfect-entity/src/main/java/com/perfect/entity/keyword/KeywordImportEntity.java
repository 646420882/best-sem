package com.perfect.entity.keyword;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by XiaoWei on 2014/9/22.
 * 重点关键词监控
 */
@Document(collection = MongoEntityConstants.TBL_IMPORTANT_KEYWORD)
public class KeywordImportEntity {
    @Id
    private String id;
    @Field(value = "cgid")
    private String customGroupId;//自定义分组的id

    @Field(value = "kwid")
    private Long keywordId;     //关键词id

    @Field(value = "name")
    private String keywordName;     //关键词名称

    @Field(value = MongoEntityConstants.ACCOUNT_ID)
    private Long accountId;     //账户ID

    @Field(value = "bs")
    private String biddingStatus;       //竞价状态

    @Field(value = "rule")
    private Boolean rule;       //否监控
    @Field(value = MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;     //单元ID

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}

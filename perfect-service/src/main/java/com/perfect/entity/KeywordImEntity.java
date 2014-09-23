package com.perfect.entity;

import com.perfect.mongodb.utils.EntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by XiaoWei on 2014/9/22.
 */
@Document(collection = EntityConstants.TBL_IMPORTANT_KEYWORD)
public class KeywordImEntity  {
    @Id
    private String id;
    @Field(value = "cgid")
    private String customGroupId;
    @Field(value = "kwid")
    private Long keywordId;
    @Field(value = "name")
    private String keywordName;
    @Field(value =EntityConstants.ACCOUNT_ID)
    private Long accountId;
    @Field(value = "bs")
    private String biddingStatus;
    @Field(value = "rule")
    private Boolean rule;

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

package com.perfect.dto.keyword;

import com.perfect.dto.account.AccountIdDTO;

import java.math.BigDecimal;

/**
 * Created by baizz on 14-11-25.
 */
public class KeywordDTO extends AccountIdDTO implements Comparable<KeywordDTO> {

    //KeywordDTO Attributes
    private Long keywordId;

    private Long adgroupId;

    private String adgroupObjId;

    private String keyword;

    private BigDecimal price;//出价

    private String pcDestinationUrl;//pc访问url

    private String mobileDestinationUrl;//mobile访问URL

    private Integer matchType;//匹配模式

    private Boolean pause;//启用状态

    private Integer status;//关键词状态

    private Integer phraseType;//高级短语细分  匹配模式  注：

    private Integer localStatus;//关键词本地状态，1为新增，2为修改，3为删除（软删除），4为级联删除标识

    private Integer orderBy;//排序而已

    public String getAdgroupObjId() {
        return adgroupObjId;
    }

    public void setAdgroupObjId(String adgroupObjId) {
        this.adgroupObjId = adgroupObjId;
    }

    public Integer getLocalStatus() {
        return localStatus;
    }

    public void setLocalStatus(Integer localStatus) {
        this.localStatus = localStatus;
    }

    public void setKeywordId(Long aKeywordId) {
        keywordId = aKeywordId;
    }

    public void setAdgroupId(Long aAdgroupId) {
        adgroupId = aAdgroupId;
    }

    public void setKeyword(String aKeyword) {
        keyword = aKeyword;
    }

    public void setPrice(BigDecimal aPrice) {
        price = aPrice;
    }



    public Long getKeywordId() {
        return keywordId;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public String getKeyword() {
        return keyword;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPcDestinationUrl() {
        return pcDestinationUrl;
    }

    public String getMobileDestinationUrl() {
        return mobileDestinationUrl;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public Boolean getPause() {
        return pause;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getPhraseType() {
        return phraseType;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public void delete() {
    }

    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "keywordId" + ":" + getKeywordId() + "," +
                "adgroupId" + ":" + getAdgroupId() + "," +
                "keyword" + ":" + getKeyword() + "," +
                "price" + ":" + getPrice() + "," +
                "pcDestinationUrl" + ":" + getPcDestinationUrl() + "," +
                "mobileDestinationUrl" + ":" + getMobileDestinationUrl() + "," +
                "matchType" + ":" + getMatchType() + "," +
                "pause" + ":" + getPause() + "," +
                "status" + ":" + getStatus() + "," +
                "phraseType" + ":" + getPhraseType() + "]"
                + outputString;
    }

    @Override
    public int compareTo(KeywordDTO o) {
        return 0;
    }

    public void setPcDestinationUrl(String pcDestinationUrl) {
        this.pcDestinationUrl = pcDestinationUrl;
    }

    public void setMobileDestinationUrl(String mobileDestinationUrl) {
        this.mobileDestinationUrl = mobileDestinationUrl;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPhraseType(Integer phraseType) {
        this.phraseType = phraseType;
    }
}

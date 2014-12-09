package com.perfect.dto.monitor;

import com.perfect.dto.BaseDTO;
import com.perfect.dto.account.AccountIdDTO;

import java.math.BigDecimal;

/**
 * Created by SubDong on 2014/9/12.
 */
public class MonitorDataDTO extends AccountIdDTO {

    private Long keywordId;

    private Long adgroupId;

    private String adgroupObjId;

    private String keyword;

    private BigDecimal price;

    private String pcDestinationUrl;

    private String mobileDestinationUrl;

    private Integer matchType;

    private Boolean pause;

    private Integer status;

    private Integer phraseType;

    private Integer localStatus;//关键词本地状态，1为新增，2为修改，3为删除（软删除），4为级联删除标识

    private Long monitorId;

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

    public void setPcDestinationUrl(String aPcDestinationUrl) {
        pcDestinationUrl = aPcDestinationUrl;
    }

    public void setMobileDestinationUrl(String aMobileDestinationUrl) {
        mobileDestinationUrl = aMobileDestinationUrl;
    }

    public void setMatchType(Integer aMatchType) {
        matchType = aMatchType;
    }

    public void setPause(Boolean aPause) {
        pause = aPause;
    }

    public void setStatus(Integer aStatus) {
        status = aStatus;
    }

    public void setPhraseType(Integer aPhraseType) {
        phraseType = aPhraseType;
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

}

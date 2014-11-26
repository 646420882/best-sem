package com.perfect.dto.backup;

import com.perfect.dto.account.AccountIdDTO;
import com.perfect.dto.keyword.KeywordDTO;

import java.math.BigDecimal;

/**
 * Created by baizz on 2014-11-25.
 */
public class KeyWordBackUpDTO extends AccountIdDTO implements Comparable<KeywordDTO> {

    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean setKeywordId(Long aKeywordId) {
        boolean wasSet = false;
        keywordId = aKeywordId;
        wasSet = true;
        return wasSet;
    }

    public boolean setAdgroupId(Long aAdgroupId) {
        boolean wasSet = false;
        adgroupId = aAdgroupId;
        wasSet = true;
        return wasSet;
    }

    public boolean setKeyword(String aKeyword) {
        boolean wasSet = false;
        keyword = aKeyword;
        wasSet = true;
        return wasSet;
    }

    public boolean setPrice(BigDecimal aPrice) {
        boolean wasSet = false;
        price = aPrice;
        wasSet = true;
        return wasSet;
    }

    public boolean setPcDestinationUrl(String aPcDestinationUrl) {
        boolean wasSet = false;
        pcDestinationUrl = aPcDestinationUrl;
        wasSet = true;
        return wasSet;
    }

    public boolean setMobileDestinationUrl(String aMobileDestinationUrl) {
        boolean wasSet = false;
        mobileDestinationUrl = aMobileDestinationUrl;
        wasSet = true;
        return wasSet;
    }

    public boolean setMatchType(Integer aMatchType) {
        boolean wasSet = false;
        matchType = aMatchType;
        wasSet = true;
        return wasSet;
    }

    public boolean setPause(Boolean aPause) {
        boolean wasSet = false;
        pause = aPause;
        wasSet = true;
        return wasSet;
    }

    public boolean setStatus(Integer aStatus) {
        boolean wasSet = false;
        status = aStatus;
        wasSet = true;
        return wasSet;
    }

    public boolean setPhraseType(Integer aPhraseType) {
        boolean wasSet = false;
        phraseType = aPhraseType;
        wasSet = true;
        return wasSet;
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

}

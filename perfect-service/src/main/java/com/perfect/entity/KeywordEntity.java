/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "keyword")
public class KeywordEntity extends AccountIdEntity {
    //------------------------
    // MEMBER VARIABLES
    //------------------------

    @Id
    private String id;

    //KeywordType Attributes
    @Indexed(unique = true)
    @Field("kwid")
    private Long keywordId;
    @Field("agid")
    private Long adgroupId;
    @Field("name")
    private String keyword;
    private Double price;
    @Field("pc")
    private String pcDestinationUrl;
    @Field("mobile")
    private String mobileDestinationUrl;

    @Field("mt")
    private Integer matchType;

    @Field("p")
    private Boolean pause;

    @Field("s")
    private Integer status;

    @Field("pt")
    private Integer phraseType;

    //------------------------
    // INTERFACE
    //------------------------

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

    public boolean setPrice(Double aPrice) {
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

    public Double getPrice() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
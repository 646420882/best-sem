package com.perfect.dto;

/**
 * Created by john on 2014/9/19.
 * 搜索词报告
 */
public class SearchwordReportDTO {
    private String keyword;

    private String searchWord;

    private String click;

    private String impression;

    private String searchEngine;

    private String adgroupName;

    private String campaignName;

    private String createTitle;

    private String createDesc1;

    private String createDesc2;

    private String date;

    private String parseExtent;


    public SearchwordReportDTO(String keyword, String searchWord, String click, String impression, String searchEngine, String adgroupName, String campaignName, String createTitle, String createDesc1, String createDesc2, String date, String parseExtent) {
        this.keyword = keyword;
        this.searchWord = searchWord;
        this.click = click;
        this.impression = impression;
        this.searchEngine = searchEngine;
        this.adgroupName = adgroupName;
        this.campaignName = campaignName;
        this.createTitle = createTitle;
        this.createDesc1 = createDesc1;
        this.createDesc2 = createDesc2;
        this.date = date;
        this.parseExtent = parseExtent;
    }

    public SearchwordReportDTO() {
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCreateTitle() {
        return createTitle;
    }

    public void setCreateTitle(String createTitle) {
        this.createTitle = createTitle;
    }

    public String getCreateDesc1() {
        return createDesc1;
    }

    public void setCreateDesc1(String createDesc1) {
        this.createDesc1 = createDesc1;
    }

    public String getCreateDesc2() {
        return createDesc2;
    }

    public void setCreateDesc2(String createDesc2) {
        this.createDesc2 = createDesc2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParseExtent() {
        return parseExtent;
    }

    public void setParseExtent(String parseExtent) {
        this.parseExtent = parseExtent;
    }
}

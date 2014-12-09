package com.perfect.dto.creative;

import com.perfect.dto.BaseDTO;

/**
 * Created by vbzer_000 on 2014/9/23.
 */
public class CreativeSourceDTO extends BaseDTO{
    private String body;

    private String title;

    private String host;

    private String keyword;

    private String keywordAnalyzed;

    private String html;

    private String region;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getKeywordAnalyzed() {
        return keywordAnalyzed;
    }

    public void setKeywordAnalyzed(String keywordAnalyzed) {
        this.keywordAnalyzed = keywordAnalyzed;
    }
}

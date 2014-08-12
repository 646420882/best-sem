package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by XiaoWei on 2014/8/11.
 */
@Document(collection = "csvUrl")
public class CSVUrlEntity {
    private Integer lineNumber;
    private String  keyword;
    private String  keywordURL;
    private String  factURL;

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordURL() {
        return keywordURL;
    }

    public void setKeywordURL(String keywordURL) {
        this.keywordURL = keywordURL;
    }

    public String getFactURL() {
        return factURL;
    }

    public void setFactURL(String factURL) {
        this.factURL = factURL;
    }
}

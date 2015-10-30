package com.perfect.dto.keyword;

/**
 * Created on 2015-10-29.
 *
 * @author dolphineor
 */
public class KeywordAggsDTO {

    private Long kwid;

    private String name;

    private Long agid;


    public Long getKeywordId() {
        return kwid;
    }

    public void setKeywordId(Long keywordId) {
        this.kwid = keywordId;
    }

    public String getKeywordName() {
        return name;
    }

    public void setKeywordName(String keywordName) {
        this.name = keywordName;
    }

    public Long getAdgroupId() {
        return agid;
    }

    public void setAdgroupId(Long adgroupId) {
        this.agid = adgroupId;
    }
}

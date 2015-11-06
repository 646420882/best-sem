package com.perfect.entity.creative;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by vbzer_000 on 2014/9/16.
 * 创意推荐实体
 */
@Document(indexName = "data", type = "creative")
public class CreativeSourceEntity {

    @Id
    private String id;

    private String body;    //内容信息

    private String title;       //标题信息

    private String host;        //host信息

    private String keyword;    //关键词

    private String category;    //类别

    private String keywordAnalyzed;     //关键词分析(分词)

    private String html;        //html信息

    private Integer region;     //地域

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getKeywordAnalyzed() {
        return keywordAnalyzed;
    }

    public void setKeywordAnalyzed(String keywordAnalyzed) {
        this.keywordAnalyzed = keywordAnalyzed;
    }
}

package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by baizz on 2014-11-17.
 */
@Document(collection = "sys_crawl_word")
public class CrawlWordEntity {

    @Id
    private String id;//mongodbid

    private String site;//爬取站点

    @Field("c")
    private String category; //种类

    @Field("w")
    private String keyword;//关键词

    @Field("s")
    private Integer status = 0; //爬取状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

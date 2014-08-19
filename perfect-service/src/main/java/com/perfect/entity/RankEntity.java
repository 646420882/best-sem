package com.perfect.entity;

/**
 * 抓取每个排名的关键词，创意，广告主等信息
 * Created by yousheng on 2014/8/18.
 *
 * @author yousheng
 */
public class RankEntity {
    private String keyword;
    private String host;
    private String desc;
    private String title;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

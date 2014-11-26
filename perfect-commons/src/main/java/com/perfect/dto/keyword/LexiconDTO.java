package com.perfect.dto.keyword;

/**
 * Created by baizz on 2014-11-26.
 */
public class LexiconDTO {

    private String id;

    private String trade;   //行业

    private String category;

    private String group;

    private String keyword;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LexiconEntity{" +
                "id='" + id + '\'' +
                ", trade='" + trade + '\'' +
                ", category='" + category + '\'' +
                ", group='" + group + '\'' +
                ", keyword='" + keyword + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by baizz on 2014-08-18.
 */
@Document(collection = "sys_keyword")
public class LexiconEntity implements Serializable {

    @Id
    private String id;

    @Field("tr")
    private String trade;   //行业

    @Field("cago")
    private String category;    //网类

    @Field("grna")
    private String groupName;   //组标识

    @Field("kw")
    private String keyword;     //关键词

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexiconEntity that = (LexiconEntity) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;
        if (trade != null ? !trade.equals(that.trade) : that.trade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (trade != null ? trade.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LexiconEntity{" +
                "id='" + id + '\'' +
                ", trade='" + trade + '\'' +
                ", category='" + category + '\'' +
                ", groupName='" + groupName + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}

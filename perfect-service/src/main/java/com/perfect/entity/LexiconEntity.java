package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by baizz on 2014-08-18.
 */
@Document(collection = "sys_keyword")
@CompoundIndexes({
        @CompoundIndex(name = "keyword_index", def = "{tr : 1, kw : 1}")
})
public class LexiconEntity implements Serializable {

    @Id
    private String id;

    @Field("tr")
    private String trade;   //行业

    @Field("cg")
    private String category;

    @Field("gr")
    private String group;

    @Field("kw")
    private String keyword;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexiconEntity entity = (LexiconEntity) o;

        if (category != null ? !category.equals(entity.category) : entity.category != null) return false;
        if (group != null ? !group.equals(entity.group) : entity.group != null) return false;
        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (keyword != null ? !keyword.equals(entity.keyword) : entity.keyword != null) return false;
        if (trade != null ? !trade.equals(entity.trade) : entity.trade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (trade != null ? trade.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LexiconEntity{" +
                "id='" + id + '\'' +
                ", trade='" + trade + '\'' +
                ", category='" + category + '\'' +
                ", group='" + group + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}

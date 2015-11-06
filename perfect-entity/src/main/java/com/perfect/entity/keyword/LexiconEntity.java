package com.perfect.entity.keyword;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by baizz on 2014-08-18.
 * 2014-11-24 refactor
 * 系统关键词
 */
@Document(collection = MongoEntityConstants.SYS_KEYWORD)
@CompoundIndexes({
        @CompoundIndex(name = "sys_keyword_index", def = "{tr : 1, kw : 1}", unique = true)
})
public class LexiconEntity implements Serializable {

    @Id
    private String id;

    @Field("tr")
    private String trade;   //行业

    @Field("cg")
    private String category;    //类别（类似于计划级别）

    @Field("gr")
    private String group;       //分组（类似与单元级别）

    @Field("kw")
    private String keyword;     //关键词

    @Field("url")
    private String url;         //url

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

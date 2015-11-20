package com.perfect.entity.bidding;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by vbzer_000 on 2014/9/4.
 *
 * @description 智能竞价日志实体类
 */
@Document(collection = "bid_log")
public class BiddingLogEntity {

    @Id
    private String id;

    @Field(MongoEntityConstants.KEYWORD_ID)
    private long keywordId;                     // 关键词ID

    @Field("b")
    private BigDecimal before;                  // 竞价前的出价

    @Field("a")
    private BigDecimal after;                   // 竞价后的出价

    @Field("t")
    private long date;                          // 操作日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public BigDecimal getAfter() {
        return after;
    }

    public void setAfter(BigDecimal after) {
        this.after = after;
    }

    public BigDecimal getBefore() {
        return before;
    }

    public void setBefore(BigDecimal before) {
        this.before = before;
    }

    public long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(long keywordId) {
        this.keywordId = keywordId;
    }
}

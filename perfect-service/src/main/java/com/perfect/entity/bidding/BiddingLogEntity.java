package com.perfect.entity.bidding;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

import static com.perfect.mongodb.utils.EntityConstants.KEYWORD_ID;

/**
 * Created by vbzer_000 on 2014/9/4.
 */
@Document(collection = "bid_log")
public class BiddingLogEntity {
    @Id
    private String id;

    @Field(KEYWORD_ID)
    private long keywordId;

    @Field("b")
    private BigDecimal before;

    @Field("a")
    private BigDecimal after;

    @Field("t")
    private long date;

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

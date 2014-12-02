package com.perfect.dto.bidding;

import com.perfect.dto.account.AccountIdDTO;

import java.math.BigDecimal;

/**
 * Created by yousheng on 14/12/1.
 */
public class BiddingLogDTO extends AccountIdDTO {

    private long keywordId;

    private BigDecimal before;

    private BigDecimal after;

    private long date;

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

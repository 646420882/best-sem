package com.perfect.bidding.redis;

import java.io.Serializable;

/**
 * Created by baizz on 2014-12-24.
 */
public class BiddingMessage implements Constants, Serializable {

    private String n;   //username

    private Long a;   //baiduAccountId

    private String r;   //biddingRuleId


    /**
     * <p>biddingMessage
     *
     * @param n username
     * @param a baiduAccountId
     * @param r biddingRuleId
     */
    public BiddingMessage(String n, Long a, String r) {
        this.n = n;
        this.a = a;
        this.r = r;
    }

    public String getName() {
        return n;
    }

    public void setName(String n) {
        this.n = n;
    }

    public Long getBaiduAccountId() {
        return a;
    }

    public void setBaiduAccountId(Long a) {
        this.a = a;
    }

    public String getBiddingRuleId() {
        return r;
    }

    public void setBiddingRuleId(String r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return n + JOB_SEP + a + JOB_SEP + r;
    }
}

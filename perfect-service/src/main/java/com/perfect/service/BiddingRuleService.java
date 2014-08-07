package com.perfect.service;

import com.perfect.entity.BiddingRuleEntity;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleService {

    public void createBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId);
}

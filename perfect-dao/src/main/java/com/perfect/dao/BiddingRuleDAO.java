package com.perfect.dao;

import com.perfect.entity.BiddingRuleEntity;

import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleDAO {

    public void createBidding(BiddingRuleEntity biddingRuleEntity);

    public void batchCreate(List<BiddingRuleEntity> biddingRuleEntityList);

    public void updateBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId);
}

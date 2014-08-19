package com.perfect.dao;

import com.perfect.entity.bidding.BiddingRuleEntity;

import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleDAO extends MongoCrudRepository<BiddingRuleEntity, String> {

    public void createBidding(BiddingRuleEntity biddingRuleEntity);

    public void batchCreate(List<BiddingRuleEntity> biddingRuleEntityList);

    public void updateBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId);

    public List<BiddingRuleEntity> getReadyRule();

    public boolean disableRule(String id);

    public List<BiddingRuleEntity> getNextRunByGroupId(String userName, Long id, int gid);

    void updateToNextRunTime(List<BiddingRuleEntity> tasks);
}

package com.perfect.service;

import com.perfect.entity.bidding.BiddingRuleEntity;

import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleService {

    public void createBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId);

    public BiddingRuleEntity updateToNextTime(BiddingRuleEntity biddingRuleEntity);

    public boolean createRule(BiddingRuleEntity entity);

    public boolean disableRule(String id);

    public boolean enableRule(String id);

    public void updateRule(BiddingRuleEntity entity);

    public List<BiddingRuleEntity> getReadyRule();

    public List<BiddingRuleEntity> getNextRunByGroupId(String userName, Long id, int gid);

    void updateRule(List<BiddingRuleEntity> tasks);
}

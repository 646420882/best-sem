package com.perfect.service.impl;

import com.perfect.dao.BiddingRuleDAO;
import com.perfect.entity.BiddingRuleEntity;
import com.perfect.service.BiddingRuleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public class BiddingRuleServiceImpl implements BiddingRuleService {

    @Resource
    BiddingRuleDAO biddingRuleDAO;

    @Override
    public void createBiddingRule(BiddingRuleEntity biddingRuleEntity) {
        biddingRuleDAO.createBidding(biddingRuleEntity);
    }

    @Override
    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId) {
        return biddingRuleDAO.getBiddingRuleByKeywordId(keywordId);
    }

    @Override
    public BiddingRuleEntity updateToNextTime(BiddingRuleEntity biddingRuleEntity) {



        return null;
    }
}

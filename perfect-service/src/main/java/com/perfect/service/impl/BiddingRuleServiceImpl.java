package com.perfect.service.impl;

import com.perfect.dao.BiddingRuleDAO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.service.BiddingRuleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
@Component
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

    @Override
    public boolean createRule(BiddingRuleEntity entity) {
        return false;
    }

    @Override
    public boolean disableRule(String id) {
        biddingRuleDAO.disableRule(id);
        return false;
    }

    @Override
    public boolean enableRule(String id) {
        return false;
    }

    @Override
    public void updateRule(BiddingRuleEntity entity) {
    }

    @Override
    public List<BiddingRuleEntity> getReadyRule() {
        return biddingRuleDAO.getReadyRule();
    }

    @Override
    public List<BiddingRuleEntity> getNextRunByGroupId(String userName, Long id, int gid) {
        return biddingRuleDAO.getNextRunByGroupId(userName,id,gid);
    }

    @Override
    public void updateRule(List<BiddingRuleEntity> tasks) {
        biddingRuleDAO.updateToNextRunTime(tasks);
    }
}

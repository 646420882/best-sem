package com.perfect.service.impl;

import com.perfect.dao.BiddingRuleDAO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.service.BiddingRuleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public BiddingRuleEntity findByKeywordId(Long keywordId) {
        return biddingRuleDAO.getBiddingRuleByKeywordId(keywordId);
    }

    @Override
    public void updateToNextTime(BiddingRuleEntity biddingRuleEntity, int time) {
        biddingRuleDAO.save(biddingRuleEntity);
    }

    @Override
    public void createRule(BiddingRuleEntity entity) {
        biddingRuleDAO.save(entity);
    }

    @Override
    public void disableRule(String id) {
        biddingRuleDAO.disableRule(id);
    }

    @Override
    public void enableRule(String id) {
        biddingRuleDAO.enableRule(id);
    }

    @Override
    public void updateRule(BiddingRuleEntity entity) {
        biddingRuleDAO.save(entity);
    }

    @Override
    public List<BiddingRuleEntity> getReadyRule() {
        return biddingRuleDAO.getReadyRule();
    }

    @Override
    public List<BiddingRuleEntity> getTaskByAccountId(String userName, Long id, long hour) {
        return biddingRuleDAO.getTaskByAccoundId(userName, id, hour);
    }

    @Override
    public void updateRule(List<BiddingRuleEntity> tasks) {
        biddingRuleDAO.updateToNextRunTime(tasks);
    }

    @Override
    public List<BiddingRuleEntity> findRules(Map<String, Object> q, int skip, int limit, String sort, Sort.Direction direction) {

        if (q.containsKey("cp")) {

        }

        return biddingRuleDAO.find(q, skip, limit, sort, direction);
    }

    @Override
    public List<BiddingRuleEntity> findRules(Map<String, Object> q, String kw, String query, int skip, int limit, String sort, Sort.Direction direction) {
        return biddingRuleDAO.find(q, kw, query, skip, limit, sort, direction);
    }

    @Override
    public List<BiddingRuleEntity> findByKeywordIds(List<Long> ids) {
        return biddingRuleDAO.findByKeywordIds(ids);
    }

    @Override
    public void remove(Long id) {
        biddingRuleDAO.delete(id);
    }

    @Override
    public void removeByKeywordId(Long id) {
        biddingRuleDAO.removeByKeywordId(id);
    }

    @Override
    public void removeByKeywordIds(List<Long> ids) {
        biddingRuleDAO.removeByKeywordIds(ids);
    }

    @Override
    public boolean exists(Long keywordId) {
        return biddingRuleDAO.existsByKeywordId(keywordId);
    }

    @Override
    public void updateRank(Collection<BiddingRuleEntity> values) {
        biddingRuleDAO.updateRank(values);
    }

    @Override
    public boolean setEnable(Long[] ids, boolean ebl) {
        return biddingRuleDAO.setEnable(ids,ebl);
    }

    @Override
    public List<BiddingRuleEntity> findByNames(String[] split, boolean fullMatch, PaginationParam param) {
        return biddingRuleDAO.findByNames(split,fullMatch,param);
    }


    public long countRule(String userName) {
        return biddingRuleDAO.count();
    }
}

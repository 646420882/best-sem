package com.perfect.service.impl;

import com.perfect.dao.bidding.BiddingRuleDAO;
import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.param.BiddingRuleParam;
import com.perfect.service.BiddingRuleService;
import com.perfect.utils.paging.PaginationParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
@Component("biddingRuleService")
public class BiddingRuleServiceImpl implements BiddingRuleService {

    @Resource
    BiddingRuleDAO biddingRuleDAO;

    @Override
    public void createBiddingRule(BiddingRuleDTO biddingRuleDTO) {


        biddingRuleDAO.createBidding(biddingRuleDTO);
    }

    @Override
    public BiddingRuleDTO findByKeywordId(Long keywordId) {
        return biddingRuleDAO.getBiddingRuleByKeywordId(keywordId);
    }

    @Override
    public void updateToNextTime(BiddingRuleDTO biddingRuleEntity, int time) {
        biddingRuleDAO.save(biddingRuleEntity);
    }

    @Override
    public void createRule(BiddingRuleDTO entity) {
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
    public void updateRule(BiddingRuleParam entity) {
    }

    @Override
    public List<BiddingRuleDTO> getReadyRule() {
        return biddingRuleDAO.getReadyRule();
    }

    @Override
    public List<BiddingRuleDTO> getTaskByAccountId(String userName, Long id, long hour) {
        return biddingRuleDAO.getTaskByAccoundId(userName, id, hour);
    }

    @Override
    public void updateRule(List<BiddingRuleDTO> tasks) {
        biddingRuleDAO.updateToNextRunTime(tasks);
    }

    @Override
    public List<BiddingRuleDTO> findRules(Map<String, Object> q, int skip, int limit, String sort, boolean asc) {
        return biddingRuleDAO.find(q, skip, limit, sort, asc);
    }

    @Override
    public List<BiddingRuleDTO> findRules(Map<String, Object> q, String kw, String query, int skip, int limit, String sort, boolean asc) {
        return Collections.emptyList();
    }

    @Override
    public List<BiddingRuleDTO> findByKeywordIds(List<Long> ids) {
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
    public void updateRank(Collection<BiddingRuleDTO> values) {
    }

    @Override
    public boolean setEnable(Long[] ids, boolean ebl) {
        return biddingRuleDAO.setEnable(ids, ebl);
    }

    @Override
    public List<BiddingRuleDTO> findByNames(String[] split, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {
        return biddingRuleDAO.findByNames(split, fullMatch, param, queryParams);
    }

    @Override
    public Integer countBiddingRuleDTOfindByNames(String[] split, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {
        return biddingRuleDAO.findByNames(split, fullMatch, param, queryParams).size();
    }

    @Override
    public BiddingRuleDTO takeOne(String userName, Long id, long hour) {
        return biddingRuleDAO.takeOne(userName, id, hour);
    }


    public long countRule(String userName) {
        return biddingRuleDAO.count();
    }
}

package com.perfect.service;

import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.mongodb.utils.PaginationParam;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleService {

    public void createBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity findByKeywordId(Long keywordId);

    void updateToNextTime(BiddingRuleEntity biddingRuleEntity, int time);

    public void createRule(BiddingRuleEntity entity);

    public void disableRule(String id);

    public void enableRule(String id);

    public void updateRule(BiddingRuleEntity entity);

    public List<BiddingRuleEntity> getReadyRule();

    public List<BiddingRuleEntity> getTaskByAccountId(String userName, Long id, long hour);

    void updateRule(List<BiddingRuleEntity> tasks);

    List<BiddingRuleEntity> findRules(Map<String, Object> q, int skip, int limit, String sort, Sort.Direction direction);

    List<BiddingRuleEntity> findRules(Map<String, Object> q, String kw, String query, int skip, int limit, String sort, Sort.Direction direction);

    List<BiddingRuleEntity> findByKeywordIds(List<Long> ids);

    void remove(Long id);

    void removeByKeywordId(Long id);

    void removeByKeywordIds(List<Long> id);

    boolean exists(Long keywordId);

    @Deprecated
    void updateRank(Collection<BiddingRuleEntity> values);

    boolean setEnable(Long[] ids, boolean ebl);

    List<BiddingRuleEntity> findByNames(String[] split, boolean fullMatch,PaginationParam param);
}

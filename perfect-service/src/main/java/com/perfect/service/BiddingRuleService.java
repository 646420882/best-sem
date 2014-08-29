package com.perfect.service;

import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleService {

    public void createBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity getBiddingRuleByKeywordId(Long keywordId);

    void updateToNextTime(BiddingRuleEntity biddingRuleEntity, long time);

    public void createRule(BiddingRuleEntity entity);

    public void disableRule(String id);

    public void enableRule(String id);

    public void updateRule(BiddingRuleEntity entity);

    public List<BiddingRuleEntity> getReadyRule();

    public List<BiddingRuleEntity> getTaskByAccountId(String userName, Long id);

    void updateRule(List<BiddingRuleEntity> tasks);

    List<BiddingRuleEntity> findRules(Map<String, Object> q, int skip, int limit, String sort, Sort.Direction direction);

    List<BiddingRuleEntity> findRules(Map<String, Object> q, String kw, String query, int skip, int limit, String sort, Sort.Direction direction);

    List<BiddingRuleEntity> findRules(List<Long> ids);

    void remove(Long id);

    void removeByKeywordId(Long id);

    void removeByKeywordIds(List<Long> id);

    boolean exists(Long keywordId);
}

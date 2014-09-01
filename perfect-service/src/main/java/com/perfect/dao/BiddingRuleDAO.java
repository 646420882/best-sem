package com.perfect.dao;

import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleDAO extends MongoCrudRepository<BiddingRuleEntity, Long> {

    public void createBidding(BiddingRuleEntity biddingRuleEntity);

    public List<BiddingRuleEntity> findByCampagainId(long cid, int skip, int limit, String field, Sort.Direction direction);

    public void batchCreate(List<BiddingRuleEntity> biddingRuleEntityList);

    public void updateBiddingRule(BiddingRuleEntity biddingRuleEntity);

    public BiddingRuleEntity getBiddingRuleByKeywordId(Long keywordId);

    public List<BiddingRuleEntity> getReadyRule();

    public boolean disableRule(String id);

    public int startRule(List<String> id);

    public List<BiddingRuleEntity> getTaskByAccoundId(String userName, Long id, long hour);

    void updateToNextRunTime(List<BiddingRuleEntity> tasks);

    void enableRule(String id);

    List<BiddingRuleEntity> find(List<Long> ids);

    void removeByKeywordId(Long id);

    void removeByKeywordIds(List<Long> ids);

    boolean existsByKeywordId(Long keywordId);

    @Deprecated
    void updateRank(Collection<BiddingRuleEntity> values);
}

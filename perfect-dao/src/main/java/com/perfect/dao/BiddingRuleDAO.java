package com.perfect.dao;

import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.dao.mongodb.utils.PaginationParam;
import com.perfect.param.BiddingRuleParam;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleDAO extends MongoCrudRepository<BiddingRuleEntity, Long> {

    public void createBidding(BiddingRuleParam biddingRuleEntity);

    public List<BiddingRuleEntity> findByCampagainId(long cid, int skip, int limit, String field, Sort.Direction direction);

    public BiddingRuleEntity getBiddingRuleByKeywordId(Long keywordId);

    public List<BiddingRuleEntity> getReadyRule();

    public boolean disableRule(String id);

    public List<BiddingRuleEntity> getTaskByAccoundId(String userName, Long id, long hour);

    void updateToNextRunTime(List<BiddingRuleEntity> tasks);

    void enableRule(String id);

    List<BiddingRuleEntity> findByKeywordIds(List<Long> ids);

    void removeByKeywordId(Long id);

    void removeByKeywordIds(List<Long> ids);

    boolean existsByKeywordId(Long keywordId);

    boolean setEnable(Long[] ids, boolean ebl);

    List<BiddingRuleEntity> findByNames(String[] split, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    BiddingRuleEntity takeOne(String userName, Long id, long hour);
}

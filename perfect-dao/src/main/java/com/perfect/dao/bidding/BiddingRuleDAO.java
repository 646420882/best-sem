package com.perfect.dao.bidding;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.utils.paging.PaginationParam;

import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleDAO extends HeyCrudRepository<BiddingRuleDTO, Long> {

    void createBidding(BiddingRuleDTO biddingRuleDTO);

    BiddingRuleDTO getBiddingRuleByKeywordId(Long keywordId);

    void disableRule(Long accountId, Long keywordId);

    List<BiddingRuleDTO> getTaskByAccoundId(String userName, Long id, long hour);

    void updateToNextRunTime(List<BiddingRuleDTO> tasks);

    void enableRule(Long accountId, Long keywordId);

    boolean isPause(Long accountId, Long keywordId);

    List<BiddingRuleDTO> findByKeywordIds(List<Long> ids);

    void removeByKeywordId(Long id);

    void removeByKeywordIds(List<Long> ids);

    boolean existsByKeywordId(Long keywordId);

    boolean setEnable(Long[] ids, boolean ebl);

    List<BiddingRuleDTO> findByNames(String[] split, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    BiddingRuleDTO takeOne(String userName, Long id, long time);

    List<BiddingRuleDTO> getAvailableRules(String username, long time);

    BiddingRuleDTO takeOneById(String username, String objectId);
}

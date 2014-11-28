package com.perfect.dao.keyword;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.bidding.KeywordRankEntity;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
public interface KeywordRankDAO extends MongoCrudRepository<KeywordRankEntity, String> {

    KeywordRankEntity findByKeywordId(String id);
}

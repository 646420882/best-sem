package com.perfect.service;

import com.perfect.entity.bidding.KeywordRankEntity;

import java.util.Collection;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
public interface KeywordRankService {
    KeywordRankEntity findRankByKeywordId(String id);

    void updateRanks(Collection<KeywordRankEntity> values);
}

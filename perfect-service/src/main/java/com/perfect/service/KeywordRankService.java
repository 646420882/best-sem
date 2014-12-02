package com.perfect.service;

import com.perfect.dto.keyword.KeywordRankDTO;

import java.util.Collection;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
public interface KeywordRankService {
    KeywordRankDTO findRankByKeywordId(String id);

    void updateRanks(Collection<KeywordRankDTO> values);
}

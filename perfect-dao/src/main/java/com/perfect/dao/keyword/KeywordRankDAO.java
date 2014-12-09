package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.keyword.KeywordRankDTO;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
public interface KeywordRankDAO extends HeyCrudRepository<KeywordRankDTO, String> {

    KeywordRankDTO findByKeywordId(String id);
}

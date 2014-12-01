package com.perfect.dao.keyword;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.keyword.KeywordRankDTO;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
public interface KeywordRankDAO extends MongoCrudRepository<KeywordRankDTO, String> {

    KeywordRankDTO findByKeywordId(String id);
}

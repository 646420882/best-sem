package com.perfect.service;

import com.perfect.dto.keyword.KeywordDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-26.
 */
public interface KeywordService {

    List<KeywordDTO> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    List<Long> getKeywordIdByAdgroupId(Long adgroupId);

    List<KeywordDTO> find(Map<String, Object> params, int skip, int limit);

    KeywordDTO findOne(Long id);

    void insertAll(List<KeywordDTO> keywordDTOList);

    KeywordDTO save(KeywordDTO keywordDTO);

    void updateMulti(String field, String seedWord, Object value);

    void updateMultiKeyword(Long[] ids, BigDecimal price, String pcUrl);

    void delete(Long id);

    void deleteByIds(List<Long> ids);
}

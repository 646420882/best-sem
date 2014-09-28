package com.perfect.service;

import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PaginationParam;

import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface SysKeywordService {

    public List<KeywordEntity> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams);

    public List<KeywordEntity> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams);

    public Long keywordCount(List<Long> adgroupIds);

    KeywordEntity findById(Long kwid);

    KeywordEntity findByName(String keyword, Long accountId);

    List<KeywordEntity> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    Integer countKeywordfindByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    List<KeywordEntity> findByIds(List<Long> ids, PaginationParam... param);
}

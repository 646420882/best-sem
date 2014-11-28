package com.perfect.service;


import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.paging.PaginationParam;

import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface SysKeywordService {

    public List<KeywordDTO> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams);

    public List<KeywordDTO> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams);

    public Long keywordCount(List<Long> adgroupIds);

    KeywordDTO findById(Long kwid);

    KeywordDTO findByName(String keyword, Long accountId);

    List<KeywordDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    Integer countKeywordfindByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    List<KeywordDTO> findByIds(List<Long> ids, PaginationParam... param);
}

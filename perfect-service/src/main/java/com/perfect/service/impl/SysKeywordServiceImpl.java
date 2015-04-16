package com.perfect.service.impl;


import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.SysKeywordService;
import com.perfect.utils.paging.PaginationParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
@Component("sysKeywordService")
public class SysKeywordServiceImpl implements SysKeywordService {

    @Resource
    KeywordDAO keywordDAO;

    @Override
    public List<KeywordDTO> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams) {
        return keywordDAO.findByAdgroupId(adgroupId, param, queryParams);
    }

    @Override
    public List<KeywordDTO> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams) {
        return keywordDAO.findByAdgroupIds(adgroupIds, param, queryParams);
    }

    @Override
    public Long keywordCount(List<Long> adgroupIds) {
        return keywordDAO.keywordCount(adgroupIds);
    }

    @Override
    public KeywordDTO findById(Long kwid) {
        return keywordDAO.findOne(kwid);
    }

    @Override
    public KeywordDTO findByName(String keyword, Long accountId) {
        return keywordDAO.findByName(keyword, accountId);
    }

    @Override
    public List<KeywordDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {
        List<KeywordDTO> keywordDTOs = keywordDAO.findByNames(query, fullMatch, param, queryParams);
        return keywordDTOs;
    }

    @Override
    public Integer countKeywordfindByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {
        return keywordDAO.findByNames(query, fullMatch, param, queryParams).size();
    }

    @Override
    public List<KeywordDTO> findByIds(List<Long> ids, PaginationParam... param) {
        return keywordDAO.findByIds(ids, param);
    }
}

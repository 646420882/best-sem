package com.perfect.service.impl;


import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.service.SysKeywordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
@Component
public class SysKeywordServiceImpl implements SysKeywordService {

    @Resource
    KeywordDAO keywordDAO;

    @Override
    public List<KeywordEntity> findByAdgroupId(Long adgroupId, PaginationParam param) {
        return keywordDAO.findByAdgroupId(adgroupId, param);
    }

    @Override
    public List<KeywordEntity> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param) {
        return keywordDAO.findByAdgroupIds(adgroupIds, param);
    }

    @Override
    public Long keywordCount(List<Long> adgroupIds) {
        return keywordDAO.keywordCount(adgroupIds);
    }

    @Override
    public KeywordEntity findById(Long kwid) {
        return keywordDAO.findOne(kwid);
    }

    @Override
    public KeywordEntity findByName(String keyword, Long accountId) {
        return keywordDAO.findByName(keyword, accountId);
    }

    @Override
    public List<KeywordEntity> findByNames(String[] query, boolean fullMatch, PaginationParam param) {

        return keywordDAO.findByNames(query, fullMatch, param);
    }

    @Override
    public Integer countKeywordfindByNames(String[] query, boolean fullMatch, PaginationParam param) {
        return keywordDAO.findByNames(query, fullMatch, param).size();
    }

    @Override
    public List<KeywordEntity> findByIds(List<Long> ids) {
        return keywordDAO.findByIds(ids);
    }
}

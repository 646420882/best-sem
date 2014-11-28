package com.perfect.service.impl;

import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.KeywordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-26.
 * 2014-11-26 refactor
 */
@Service("keywordService")
public class KeywordServiceImpl implements KeywordService {

    @Resource
    private KeywordDAO keywordDAO;

    @Override
    public List<KeywordDTO> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        return keywordDAO.getKeywordByAdgroupId(adgroupId, params, skip, limit);
    }

    @Override
    public List<Long> getKeywordIdByAdgroupId(Long adgroupId) {
        return keywordDAO.getKeywordIdByAdgroupId(adgroupId);
    }

    @Override
    public List<KeywordDTO> find(Map<String, Object> params, int skip, int limit) {
        return keywordDAO.find(params, skip, limit);
    }

    @Override
    public KeywordDTO findOne(Long id) {
        return keywordDAO.findOne(id);
    }

    @Override
    public void insertAll(List<KeywordDTO> keywordDTOList) {
        keywordDAO.insertAll(keywordDTOList);
    }

    @Override
    public KeywordDTO save(KeywordDTO keywordDTO) {
        return keywordDAO.save(keywordDTO);
    }

    @Override
    public void updateMulti(String field, String seedWord, Object value) {
        keywordDAO.updateMulti(field, seedWord, value);
    }

    @Override
    public void updateMultiKeyword(Long[] ids, BigDecimal price, String pcUrl) {
        keywordDAO.updateMultiKeyword(ids, price, pcUrl);
    }

    @Override
    public void delete(Long id) {
        keywordDAO.delete(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        keywordDAO.deleteByIds(ids);
    }
}

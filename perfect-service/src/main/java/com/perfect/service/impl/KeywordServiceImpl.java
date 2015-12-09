package com.perfect.service.impl;

import com.perfect.commons.constants.LogLevelConstants;
import com.perfect.commons.constants.LogObjConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.filters.field.enums.KeyWordEnum;
import com.perfect.log.filters.field.enums.OptContentEnum;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.log.util.LogOptUtil;
import com.perfect.service.*;
import com.perfect.utils.OperationRecordModelBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Resource
    private KeywordDeduplicateService keywordDeduplicateService;
    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private LogSaveService logSaveService;

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
    public List<String> insertAll(List<KeywordDTO> keywordDTOList) {

        List<KeywordDTO> dtos = keywordDeduplicateService.deduplicate(AppContext.getAccountId(), keywordDTOList.get(0).getAdgroupId(), keywordDTOList);
        dtos.stream().forEach(s -> {
            logSaveService.saveKeywordLog(s);
        });

        Iterable<KeywordDTO> keywordDTOs = keywordDAO.save(dtos);
        List<String> strings = new ArrayList<>();
        keywordDTOs.forEach(e -> strings.add(e.getKeyword()));
        return strings;
    }

    @Override
    public KeywordDTO save(KeywordDTO keywordDTO) {
        logSaveService.saveKeywordLog(keywordDTO);
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

    private Boolean saveLog(OperationRecordModel orm) {
        return LogOptUtil.saveLogs(orm).isSuccess();
    }
}

package com.perfect.service.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.service.AssistantKeywordService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
@Repository("assistantKeywordService")
public class AssistantKeywordServiceImpl implements AssistantKeywordService{

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Override
    public Iterable<KeywordEntity> getAllKeyWord() {
        return keywordDAO.findAll();
    }

    @Override
    public void deleteByKwIds(List<Long> kwids) {
        keywordDAO.deleteByIds(kwids);
    }

    @Override
    public void updateKeyword(KeywordEntity keywordEntity) {
        keywordDAO.update(keywordEntity);
    }
}

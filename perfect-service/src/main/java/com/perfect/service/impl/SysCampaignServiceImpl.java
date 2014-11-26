package com.perfect.service.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.SysCampaignService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
@Component("sysCampaignService")
public class SysCampaignServiceImpl implements SysCampaignService {

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Override
    public CampaignDTO findById(Long id) {
        return campaignDAO.findOne(id);
    }

    @Override
    public CampaignDTO findByKeywordId(Long kwid) {
        KeywordDTO keywordEntity = keywordDAO.findOne(kwid);
        Long adgroupId = keywordEntity.getAdgroupId();

        AdgroupDTO adgroupEntity = adgroupDAO.findOne(adgroupId);

        Long campaignId = adgroupEntity.getCampaignId();

        return campaignDAO.findOne(campaignId);
    }
}

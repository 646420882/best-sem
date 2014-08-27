package com.perfect.service.impl;

import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import com.perfect.service.SysCampaignService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
@Component
public class SysCampaignServiceImpl implements SysCampaignService {

    @Resource
    private CampaignDAO campaignDAO;

    @Override
    public CampaignEntity findById(Long id) {
        return campaignDAO.findOne(id);
    }
}

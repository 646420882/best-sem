package com.perfect.service;

import com.perfect.dto.campaign.CampaignDTO;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface SysCampaignService {

    public CampaignDTO findById(Long id);

    CampaignDTO findByKeywordId(Long kwid);
}

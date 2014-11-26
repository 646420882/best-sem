package com.perfect.service;

import com.perfect.dto.campaign.CampaignDTO;

/**
 * Created by john on 2014/9/16.
 */
public interface CampaignBackUpService {
    CampaignDTO reducUpdate(String id);

    void reducDel(String id);
}

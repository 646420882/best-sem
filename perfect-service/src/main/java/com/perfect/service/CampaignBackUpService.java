package com.perfect.service;

import com.perfect.entity.CampaignEntity;

/**
 * Created by john on 2014/9/16.
 */
public interface CampaignBackUpService {
    CampaignEntity reducUpdate(String id);

    void reducDel(String id);
}

package com.perfect.service;

import com.perfect.entity.AdgroupEntity;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface SysAdgroupService {

    public List<AdgroupEntity> findByCampaignId(Long cid);

    public List<AdgroupEntity> findIdByCampaignId(Long cid);
}

package com.perfect.service;

import com.perfect.dto.adgroup.AdgroupDTO;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface SysAdgroupService {

    public List<AdgroupDTO> findByCampaignId(Long cid);

    public List<AdgroupDTO> findIdByCampaignId(Long cid);

    public AdgroupDTO findByAdgroupId(Long agid);
}

package com.perfect.service.impl;

import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.service.SysAdgroupService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 * 2014-12-2 refactor
 */
@Component("sysAdgroupService")
public class SysAdgroupServiceImpl implements SysAdgroupService {

    @Resource
    private AdgroupDAO adgroupDAO;

    @Override
    public List<AdgroupDTO> findByCampaignId(Long cid) {
        return adgroupDAO.findByCampaignId(cid);
    }

    @Override
    public List<AdgroupDTO> findIdByCampaignId(Long cid) {
        return adgroupDAO.findIdByCampaignId(cid);
    }

    @Override
    public AdgroupDTO findByAdgroupId(Long agid) {
        return adgroupDAO.findOne(agid);
    }
}

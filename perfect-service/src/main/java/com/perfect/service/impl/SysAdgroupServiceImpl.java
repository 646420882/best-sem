package com.perfect.service.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.service.SysAdgroupService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
@Component("sysAdgroupService")
public class SysAdgroupServiceImpl implements SysAdgroupService {

    @Resource
    private AdgroupDAO adgroupDAO;

    @Override
    public List<AdgroupEntity> findByCampaignId(Long cid) {
        return adgroupDAO.findByCampaignId(cid);
    }

    @Override
    public List<AdgroupEntity> findIdByCampaignId(Long cid) {
        return adgroupDAO.findIdByCampaignId(cid);
    }

    @Override
    public AdgroupEntity findByAdgroupId(Long agid) {
        return adgroupDAO.findOne(agid);
    }
}

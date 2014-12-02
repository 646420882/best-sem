package com.perfect.service.impl;

import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.service.AdgroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-26.
 * 2014-12-2 refactor
 */
@Service("adgroupService")
public class AdgroupServiceImpl implements AdgroupService {

    @Resource
    private AdgroupDAO adgroupDAO;

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        return adgroupDAO.getAdgroupByCampaignId(campaignId, params, skip, limit);
    }

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId) {
        return adgroupDAO.getAdgroupByCampaignObjId(campaignObjId);
    }

    @Override
    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        return adgroupDAO.getAdgroupIdByCampaignId(campaignId);
    }

    @Override
    public AdgroupDTO findOne(Long id) {
        return adgroupDAO.findOne(id);
    }

    @Override
    public List<AdgroupDTO> find(Map<String, Object> params, int skip, int limit) {
        return adgroupDAO.find(params, skip, limit);
    }

    @Override
    public void insertAll(List<AdgroupDTO> entities) {
        adgroupDAO.save(entities);
    }

    @Override
    public void update(AdgroupDTO adgroupDTO) {
        AdgroupBackupDTO adgroupBackupDTO = new AdgroupBackupDTO();
        BeanUtils.copyProperties(adgroupDTO, adgroupBackupDTO);
        adgroupDAO.update(adgroupDTO, adgroupBackupDTO);
    }

    @Override
    public void delete(Long id) {
        adgroupDAO.delete(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        adgroupDAO.deleteByIds(ids);
    }
}

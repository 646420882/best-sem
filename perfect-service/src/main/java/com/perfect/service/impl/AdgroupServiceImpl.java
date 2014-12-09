package com.perfect.service.impl;

import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.service.AdgroupService;
import com.perfect.utils.paging.PagerInfo;
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
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignObjId) {
        return adgroupDAO.getAdgroupByCampaignId(campaignObjId);
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

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize) {
        return adgroupDAO.findByPagerInfo(params,nowPage,pageSize);
    }

    @Override
    public Object insertOutId(AdgroupDTO adgroupEntity) {
        return adgroupDAO.insertOutId(adgroupEntity);
    }

    @Override
    public void deleteByObjId(String oid) {
        adgroupDAO.deleteByObjId(oid);
    }

    @Override
    public void deleteByObjId(Long adgroupId) {
        adgroupDAO.deleteByObjId(adgroupId);
    }

    @Override
    public AdgroupDTO findByObjId(String oid) {
        return adgroupDAO.findByObjId(oid);
    }

    @Override
    public void updateByObjId(AdgroupDTO adgroupEntity) {
        adgroupDAO.updateByObjId(adgroupEntity);
    }

    @Override
    public void update(AdgroupDTO adgroupEntity, AdgroupDTO bakAdgroupEntity) {
        adgroupDAO.update(adgroupEntity,bakAdgroupEntity);
    }

    @Override
    public void delBack(Long oid) {
        adgroupDAO.delBack(oid);
    }

    @Override
    public AdgroupDTO fndEntity(Map<String, Object> params) {
        return adgroupDAO.fndEntity(params);
    }

    @Override
    public void save(AdgroupDTO adgroupDTO) {
        adgroupDAO.save(adgroupDTO);
    }
}

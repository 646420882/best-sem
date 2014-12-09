package com.perfect.service.impl;

import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.CampaignService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by SubDong on 2014/11/26.
 */
@Repository("campaignService")
public class CampaignServiceImpl implements CampaignService {

    @Resource
    private CampaignDAO campaignDAO;

    @Override
    public CampaignDTO findOne(Long campaignId) {
        return campaignDAO.findOne(campaignId);
    }

    @Override
    public Iterable<CampaignDTO> findAll() {
        return campaignDAO.findAll();
    }

    @Override
    public Iterable<CampaignDTO> findAllDownloadCampaign() {
        return campaignDAO.findAllDownloadCampaign();
    }

    @Override
    public void insertAll(List<CampaignDTO> list) {
        campaignDAO.insertAll(list);
    }

    @Override
    public void update(CampaignDTO campaignEntity) {
        campaignDAO.update(campaignEntity);
    }

    @Override
    public void delete(Long campaignId) {
        campaignDAO.delete(campaignId);
    }

    @Override
    public void deleteByIds(List<Long> campaignIds) {
        campaignDAO.deleteByIds(campaignIds);
    }

    @Override
    public PagerInfo findByPageInfo(Long accountId, int pageSize, int pageNo) {
        return campaignDAO.findByPageInfo(accountId,pageSize,pageNo);
    }

    @Override
    public CampaignDTO findByObjectId(String oid) {
        return campaignDAO.findByObjectId(oid);
    }

    @Override
    public void softDel(long id) {
        campaignDAO.softDel(id);
    }

    @Override
    public void deleteByMongoId(String id) {
        campaignDAO.deleteByMongoId(id);
    }

    @Override
    public void save(CampaignDTO dto) {
        campaignDAO.save(dto);
    }

    @Override
    public void updateByMongoId(CampaignDTO newCampaign, CampaignDTO campaignEntity) {
        campaignDAO.updateByMongoId(newCampaign,campaignEntity);
    }

    @Override
    public String insertReturnId(CampaignDTO campaignEntity) {
        return campaignDAO.insertReturnId(campaignEntity);
    }
}

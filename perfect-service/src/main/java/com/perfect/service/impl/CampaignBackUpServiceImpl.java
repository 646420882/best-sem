package com.perfect.service.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignBackUpDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.backup.CampaignBackUpEntity;
import com.perfect.service.CampaignBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by john on 2014/9/16.
 */
@Repository("campaignBackUpService")
public class CampaignBackUpServiceImpl implements CampaignBackUpService{

    @Resource
    private CampaignBackUpDAO campaignBackUpDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    public CampaignEntity reducUpdate(String id){
        if(id.matches("^\\d+$")==true){
            CampaignEntity campaignEntity = new CampaignEntity();
            CampaignBackUpEntity campaignBackUpEntity = campaignBackUpDAO.findOne(Long.parseLong(id));
            BeanUtils.copyProperties(campaignBackUpEntity, campaignEntity);
            campaignEntity.setLocalStatus(null);
            campaignDAO.save(campaignEntity);
            campaignBackUpDAO.deleteByCid(Long.parseLong(id));
            return campaignEntity;
        }else{
            CampaignEntity campaignEntity = campaignDAO.findByObjectId(id);
            campaignDAO.deleteByMongoId(id);
            return campaignEntity;
        }
    }

    /**
     * 软删除
     * @param id
     * @return
     */
    public void reducDel(String id){
        if(id.matches("^\\d+$")==true){
            campaignDAO.updateLocalstatu(Long.parseLong(id));
        }
    }
}

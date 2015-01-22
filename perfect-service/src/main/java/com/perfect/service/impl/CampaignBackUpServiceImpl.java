package com.perfect.service.impl;

import com.perfect.dao.campaign.CampaignBackUpDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dto.backup.CampaignBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.CampaignBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 2014/9/16.
 */
@Repository("campaignBackUpService")
public class CampaignBackUpServiceImpl implements CampaignBackUpService{

    @Resource
    private CampaignBackUpDAO campaignBackUpDAO;

    @Resource
    private CampaignDAO campaignDAO;

    public CampaignDTO reducUpdate(String id){
        if(id.matches("^\\d+$")==true){
            CampaignDTO campaignEntity = new CampaignDTO();
            CampaignBackUpDTO campaignBackUpEntity = campaignBackUpDAO.findOne(Long.parseLong(id));
            BeanUtils.copyProperties(campaignBackUpEntity, campaignEntity);
            campaignEntity.setLocalStatus(null);
            campaignDAO.save(campaignEntity);
            campaignBackUpDAO.deleteByCid(Long.parseLong(id));
            return campaignEntity;
        }else{
            CampaignDTO campaignEntity = campaignDAO.findByObjectId(id);
            campaignDAO.deleteByMongoId(id);
            return campaignEntity;
        }
    }

    /**
     * 软删除
     * @param id
     * @return
     */
    public void reduceDel(String id){
        if(id.matches("^\\d+$")==true){
            campaignDAO.updateLocalstatu(Long.parseLong(id));
        }
    }

    @Override
    public void deleteByOId(List<String> obj) {
        campaignBackUpDAO.deleteByOId(obj);
        campaignDAO.updateRemoveLs(obj);
    }
}

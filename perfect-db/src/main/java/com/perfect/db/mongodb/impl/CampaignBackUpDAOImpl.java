package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.campaign.CampaignBackUpDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.backup.CampaignBackUpDTO;
import com.perfect.entity.backup.CampaignBackUpEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/9/16.
 */
@Service
public class CampaignBackUpDAOImpl extends AbstractUserBaseDAOImpl<CampaignBackUpDTO,Long> implements CampaignBackUpDAO{
    @Override
    public Class<CampaignBackUpDTO> getEntityClass() {
        return CampaignBackUpDTO.class;
    }

    public Class<CampaignBackUpEntity> getCampaignBackUpEntityClass() {
        return CampaignBackUpEntity.class;
    }

    @Override
    public CampaignBackUpDTO findByObjectId(String id) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        return mongoTemplate.findOne(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)),getEntityClass(), MongoEntityConstants.BAK_CAMPAIGN);
    }

    @Override
    public void deleteByObjectId(String id) {

    }


    public  void deleteByCid(long cid){
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)),getEntityClass(),MongoEntityConstants.BAK_CAMPAIGN);
    }

    public  CampaignBackUpDTO findOne(long cid){
        MongoTemplate mongoTemplate = getMongoTemplate();
        List<CampaignBackUpEntity> list= mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), getCampaignBackUpEntityClass(), MongoEntityConstants.BAK_CAMPAIGN);

        List<CampaignBackUpDTO> campaignBackUpDTOs = ObjectUtils.convert(list,CampaignBackUpDTO.class);

        return campaignBackUpDTOs.size()==0?null:campaignBackUpDTOs.get(0);
    }

    @Override
    public Class<CampaignBackUpDTO> getDTOClass() {
        return CampaignBackUpDTO.class;
    }
}

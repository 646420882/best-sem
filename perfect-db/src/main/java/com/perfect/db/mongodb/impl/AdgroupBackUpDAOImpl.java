package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.adgroup.AdgroupBackUpDAO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Component
public class AdgroupBackUpDAOImpl extends AbstractUserBaseDAOImpl<AdgroupBackupDTO, Long> implements AdgroupBackUpDAO {

    @Override
    public Class<AdgroupBackUpEntity> getEntityClass() {
        return AdgroupBackUpEntity.class;
    }

    @Override
    public Class<AdgroupBackupDTO> getDTOClass() {
        return AdgroupBackupDTO.class;
    }

    @Override
    public List<AdgroupBackupDTO> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
        return null;
    }


    @Override
    public AdgroupBackupDTO findOne(String oid) {
        AdgroupBackUpEntity adgroupBackUpEntity = BaseMongoTemplate.getUserMongo().findOne(new Query(Criteria.where(getId()).is(oid)), AdgroupBackUpEntity.class, MongoEntityConstants.BAK_ADGROUP);
        AdgroupBackupDTO adgroupBackupDTO = new AdgroupBackupDTO();
        if (adgroupBackUpEntity != null) {
            BeanUtils.copyProperties(adgroupBackUpEntity, adgroupBackupDTO);
        }
        return adgroupBackupDTO;
    }

    @Override
    public AdgroupBackupDTO findByLongId(Long oid) {
        AdgroupBackUpEntity adgroupBackUpEntity = BaseMongoTemplate.getUserMongo().findOne(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(oid)), AdgroupBackUpEntity.class, MongoEntityConstants.BAK_ADGROUP);
        AdgroupBackupDTO adgroupBackupDTO = new AdgroupBackupDTO();
        if (adgroupBackUpEntity != null) {
            BeanUtils.copyProperties(adgroupBackUpEntity, adgroupBackupDTO);
        }
        return adgroupBackupDTO;
    }

    @Override
    public void deleteByLongId(Long id) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(id)), AdgroupEntity.class, MongoEntityConstants.BAK_ADGROUP);
    }
}

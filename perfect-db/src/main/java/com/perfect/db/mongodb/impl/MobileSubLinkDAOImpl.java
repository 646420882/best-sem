package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.creative.MobileSublinkDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.creative.MobileSublinkDTO;
import com.perfect.entity.creative.MobileSublinkEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by XiaoWei on 2015/2/27.
 */
@Repository("mobileSublinkDAO")
class MobileSublinkDAOImpl extends AbstractSysBaseDAOImpl<MobileSublinkDTO,Long> implements MobileSublinkDAO {
    @Override
    public void customSave(MobileSublinkDTO mobileSublinkDTO) {
        MobileSublinkEntity entity= ObjectUtils.convert(mobileSublinkDTO,MobileSublinkEntity.class);
        getMongoTemplate().save(entity);
    }

    @Override
    public MobileSublinkDTO findByAdgroupLongId(Long adgroupId) {
        MobileSublinkEntity entity=getMongoTemplate().findOne(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(ADGROUP_ID).is(adgroupId)),MobileSublinkEntity.class);
        MobileSublinkDTO sublinkDTO=ObjectUtils.convert(entity,MobileSublinkDTO.class);
        return sublinkDTO;
    }

    @Override
    public MobileSublinkDTO findByAdgroupObjId(String objectId) {
        MobileSublinkEntity entity=getMongoTemplate().findOne(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(OBJ_ADGROUP_ID).is(objectId)),MobileSublinkEntity.class);
        MobileSublinkDTO sublinkDTO=ObjectUtils.convert(entity,MobileSublinkDTO.class);
        return sublinkDTO;
    }

    @Override
    public  Class<MobileSublinkEntity> getEntityClass() {
        return MobileSublinkEntity.class;
    }

    @Override
    public Class<MobileSublinkDTO> getDTOClass() {
        return MobileSublinkDTO.class;
    }
}

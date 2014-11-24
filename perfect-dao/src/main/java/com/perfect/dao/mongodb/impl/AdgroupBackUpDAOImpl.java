package com.perfect.dao.mongodb.impl;

import com.perfect.dao.AdgroupBackUpDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.dao.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.mongodb.base.BaseMongoTemplate;
import com.perfect.dao.mongodb.utils.EntityConstants;
import com.perfect.dao.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Component
public class AdgroupBackUpDAOImpl extends AbstractUserBaseDAOImpl<AdgroupBackUpEntity,Long> implements AdgroupBackUpDAO{

    @Override
    public Class<AdgroupBackUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public AdgroupBackUpEntity findOne(String oid) {
        return BaseMongoTemplate.getUserMongo().findOne(new Query(Criteria.where(getId()).is(oid)),AdgroupBackUpEntity.class, EntityConstants.BAK_ADGROUP);
    }

    @Override
    public AdgroupBackUpEntity findByLongId(Long oid) {
        return BaseMongoTemplate.getUserMongo().findOne(new Query(Criteria.where(EntityConstants.ADGROUP_ID).is(oid)),AdgroupBackUpEntity.class, EntityConstants.BAK_ADGROUP);
    }

    @Override
    public void deleteByLongId(Long id) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(EntityConstants.ADGROUP_ID).is(id)), AdgroupEntity.class,EntityConstants.BAK_ADGROUP);
    }
}

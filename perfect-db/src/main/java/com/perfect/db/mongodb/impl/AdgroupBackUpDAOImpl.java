package com.perfect.db.mongodb.impl;

import com.perfect.dao.AdgroupBackUpDAO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dao.mongodb.utils.EntityConstants;
import com.perfect.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Component
public class AdgroupBackUpDAOImpl extends AbstractUserBaseDAOImpl<AdgroupBackupDTO,Long> implements AdgroupBackUpDAO{

    @Override
    public Class<AdgroupBackupDTO> getEntityClass() {
        return null;
    }

    @Override
    public List<AdgroupBackupDTO> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<AdgroupBackupDTO> find(Map<String, Object> params, String fieldName, String q, int skip, int limit, String sort, boolean asc) {
        return null;
    }

    @Override
    public AdgroupBackupDTO findOne(String oid) {
        return BaseMongoTemplate.getUserMongo().findOne(new Query(Criteria.where(getId()).is(oid)),AdgroupBackUpEntity.class, EntityConstants.BAK_ADGROUP);
    }

    @Override
    public AdgroupBackupDTO findByLongId(Long oid) {
        return BaseMongoTemplate.getUserMongo().findOne(new Query(Criteria.where(EntityConstants.ADGROUP_ID).is(oid)),AdgroupBackUpEntity.class, EntityConstants.BAK_ADGROUP);
    }

    @Override
    public void deleteByLongId(Long id) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(EntityConstants.ADGROUP_ID).is(id)), AdgroupEntity.class,EntityConstants.BAK_ADGROUP);
    }
}

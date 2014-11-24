package com.perfect.dao.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.CreativeBackUpDAO;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.dao.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.mongodb.base.BaseMongoTemplate;
import com.perfect.dao.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Component
public class CreativeBackUpDAOImpl extends AbstractUserBaseDAOImpl<CreativeBackUpEntity, Long> implements CreativeBackUpDAO {
    @Override
    public Class<CreativeBackUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public CreativeBackUpEntity findByStringId(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.findOne((new Query(Criteria.where(getId()).is(id))), CreativeBackUpEntity.class, MongoEntityConstants.BAK_CREATIVE);
    }

    @Override
    public CreativeBackUpEntity findByLongId(Long crid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.findOne((new Query(Criteria.where(MongoEntityConstants.CREATIVE_ID).is(crid))), CreativeBackUpEntity.class, MongoEntityConstants.BAK_CREATIVE);
    }

    @Override
    public void deleteByLongId(Long crid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.CREATIVE_ID).is(crid)), CreativeBackUpEntity.class, MongoEntityConstants.BAK_CREATIVE);
    }
}

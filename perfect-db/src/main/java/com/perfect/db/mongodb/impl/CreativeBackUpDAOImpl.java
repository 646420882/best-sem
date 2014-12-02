package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.creative.CreativeBackUpDAO;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 * 2014-11-26 refactor
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
    public CreativeBackUpDTO findByStringId(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        CreativeBackUpEntity creativeBackUpEntity= mongoTemplate.findOne((new Query(Criteria.where(getId()).is(id))), CreativeBackUpEntity.class, MongoEntityConstants.BAK_CREATIVE);
        CreativeBackUpDTO creativeBackUpDTO=new CreativeBackUpDTO();
        BeanUtils.copyProperties(creativeBackUpEntity,creativeBackUpDTO);
        return creativeBackUpDTO;
    }

    @Override
    public CreativeBackUpDTO findByLongId(Long crid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        CreativeBackUpEntity creativeBackUpEntity=  mongoTemplate.findOne((new Query(Criteria.where(MongoEntityConstants.CREATIVE_ID).is(crid))), CreativeBackUpEntity.class, MongoEntityConstants.BAK_CREATIVE);
        CreativeBackUpDTO creativeBackUpDTO=new CreativeBackUpDTO();
        BeanUtils.copyProperties(creativeBackUpEntity,creativeBackUpDTO);
        return creativeBackUpDTO;
    }

    @Override
    public void deleteByLongId(Long crid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.CREATIVE_ID).is(crid)), CreativeBackUpEntity.class, MongoEntityConstants.BAK_CREATIVE);
    }
}

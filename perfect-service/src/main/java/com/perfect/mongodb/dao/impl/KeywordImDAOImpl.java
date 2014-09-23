package com.perfect.mongodb.dao.impl;

import com.perfect.dao.KeywordImDAO;
import com.perfect.entity.KeywordImEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/22.
 */
@Repository("keywordImDAO")
public class KeywordImDAOImpl extends AbstractUserBaseDAOImpl<KeywordImEntity,Long> implements KeywordImDAO {
    @Override
    public Class<KeywordImEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public KeywordImEntity findByKwdId(Long kwdId) {
        MongoTemplate mongoTemplate= BaseMongoTemplate.getUserMongo();
      return  mongoTemplate.findOne(new Query(Criteria.where(EntityConstants.KEYWORD_ID).is(kwdId)), KeywordImEntity.class);
    }

    @Override
    public List<KeywordImEntity> findByCgId(String cgId) {
        MongoTemplate mongoTemplate=BaseMongoTemplate.getUserMongo();
        List<KeywordImEntity> keywordImEntityList=mongoTemplate.find(new Query(Criteria.where("cgid").is(cgId)),KeywordImEntity.class);
        return keywordImEntityList;
    }
}

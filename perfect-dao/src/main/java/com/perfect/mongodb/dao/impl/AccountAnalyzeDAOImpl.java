package com.perfect.mongodb.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.AccountAnalyzeEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 14-7-25.
 */
@Repository("accountAnalyzeDAO")
public class AccountAnalyzeDAOImpl implements AccountAnalyzeDAO {

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("perfect");

    public KeywordRealTimeDataVOEntity findOne(Long aLong) {
        return null;
    }

    public List<KeywordRealTimeDataVOEntity> findAll() {
        return null;
    }

    public List<KeywordRealTimeDataVOEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    public void insert(KeywordRealTimeDataVOEntity keywordRealTimeDataVOEntity) {

    }

    public void insertAll(List<KeywordRealTimeDataVOEntity> entities) {

    }

    public void update(KeywordRealTimeDataVOEntity keywordRealTimeDataVOEntity) {

    }

    public void update(List<KeywordRealTimeDataVOEntity> entities) {

    }

    public void deleteById(Long aLong) {

    }

    public void deleteByIds(List<Long> longs) {

    }

    public void delete(KeywordRealTimeDataVOEntity keywordRealTimeDataVOEntity) {

    }

    public void deleteAll() {

    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
    public List<AccountAnalyzeEntity> performance(String userTable) {
        List<AccountAnalyzeEntity> list = mongoTemplate.findAll(AccountAnalyzeEntity.class,userTable);
        return list;
    }
}

package com.perfect.mongodb.dao.impl;

import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 14-7-25.
 */
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
}

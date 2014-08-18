package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CSVKeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.vo.CSVEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/8/11.
 */
@Repository("cSVKeywordDAO")
public class CSVKeywordDAOImpl implements CSVKeywordDAO {
    MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("user_perfect");

    @Override
    public KeywordEntity findOne(Long aLong) {
        return null;
    }

    @Override
    public List<KeywordEntity> findAll() {
        return null;
    }

    @Override
    public List<KeywordEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    @Override
    public void insert(KeywordEntity keywordEntity) {

    }

    @Override
    public void insertAll(List<KeywordEntity> csvEntityList) {
        for (KeywordEntity key : csvEntityList) {
            Query q = new Query(Criteria.where("kwid").is(key.getKeywordId()));
            if (!mongoTemplate.exists(q, KeywordEntity.class)) {
//                    mongoTemplate.insert(key,"keyword");
                System.out.println("insert");
            }
        }
    }

    @Override
    public void update(KeywordEntity keywordEntity) {

    }

    @Override
    public void update(List<KeywordEntity> entities) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteByIds(List<Long> longs) {

    }

    @Override
    public void delete(KeywordEntity keywordEntity) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }


}

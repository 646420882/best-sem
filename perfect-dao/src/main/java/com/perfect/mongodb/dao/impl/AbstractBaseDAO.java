package com.perfect.mongodb.dao.impl;

import com.perfect.mongodb.dao.BaseDAO;
import com.perfect.mongodb.entity.SystemUser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public abstract class AbstractBaseDAO<T> implements BaseDAO<T> {

    @Resource
    private MongoTemplate mongoTemplate;


    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void insert(T t) {
        mongoTemplate.insert(t);
    }

    @Override
    public void insertAll(List<T> t) {
        mongoTemplate.insertAll(t);
    }

    @Override
    public void delete(T t) {
        mongoTemplate.remove(t);
    }

}

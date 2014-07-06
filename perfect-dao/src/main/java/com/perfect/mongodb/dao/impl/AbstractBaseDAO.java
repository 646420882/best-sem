package com.perfect.mongodb.dao.impl;

import com.perfect.dao.BaseDAO;
import org.springframework.data.mongodb.core.MongoTemplate;

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

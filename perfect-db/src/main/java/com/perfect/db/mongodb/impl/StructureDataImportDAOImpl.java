package com.perfect.db.mongodb.impl;

import com.perfect.dao.StructureDataImportDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collection;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public class StructureDataImportDAOImpl<T> implements StructureDataImportDAO<T> {

    private String userName;

    public StructureDataImportDAOImpl(String userName) {
        this.userName = userName;
    }

    @Override
    public void save(T t) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        mongoTemplate.save(t);
    }

    @Override
    public void saveList(Collection<T> collection) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        mongoTemplate.insert(collection);
    }
}

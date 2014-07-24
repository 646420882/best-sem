package com.perfect.mongodb.utils;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;

/**
 * Created by baizz on 2014-7-23.
 */
public class BaseMongoTemplate {

    private String databaseName;

    private static MongoTemplate baseMongoTemplate;

    private BaseMongoTemplate() {
    }

    public void init() {
        MongoDbFactory mongoDbFactory = null;

        try {
            mongoDbFactory = new SimpleMongoDbFactory(new MongoClient("115.29.103.38", 27017), databaseName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //remove _class
        MongoConverter mongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        baseMongoTemplate = new Log4MongoTemplate(mongoDbFactory, mongoConverter);
    }

    public static MongoTemplate getMongoTemplate(String databaseName) {
        BaseMongoTemplate _baseMongoTemplate = new BaseMongoTemplate();
        _baseMongoTemplate.databaseName = databaseName;
        _baseMongoTemplate.init();
        return baseMongoTemplate;
    }
}
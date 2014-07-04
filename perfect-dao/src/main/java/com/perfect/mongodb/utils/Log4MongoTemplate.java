package com.perfect.mongodb.utils;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * Created by baizz on 2014-7-1.
 */
public class Log4MongoTemplate extends MongoTemplate {

    public Log4MongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
    }
}
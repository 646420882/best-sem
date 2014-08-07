package com.perfect.mongodb.utils;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by baizz on 2014-07-23.
 */
public class BaseMongoTemplate {

    private static Mongo mongo;

    static {
        InputStream is = BaseMongoTemplate.class.getResourceAsStream("/mongodb.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            mongo = new MongoClient(p.getProperty("mongo.host"), Integer.valueOf(p.getProperty("mongo.port")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BaseMongoTemplate() {
    }


    public static MongoTemplate getMongoTemplate(String databaseName) {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName);

        //remove _class
        MappingMongoConverter mongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(mongoDbFactory, mongoConverter);
    }
}
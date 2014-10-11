package com.perfect.mongodb.base;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.core.AppContext;
import com.perfect.utils.DBNameUtils;
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
            mongo = new MongoClient(p.getProperty("mongo.host"));
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

    public static MongoTemplate getSysMongo() {
        Object mongoObj = ApplicationContextHelper.getCurrentApplicationContext().getBean("mongoSysTemplate");
        if (mongoObj != null) {
            return (MongoTemplate) mongoObj;
        }
        return BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
    }

    public static MongoTemplate getUserMongo() {
        return BaseMongoTemplate.getMongoTemplate(DBNameUtils.getUserDBName(AppContext.getUser(), null));
    }

    public static MongoTemplate getUserReportMongo() {
        return BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(AppContext.getUser()));
    }

    public static MongoTemplate getUserMongo(String userName) {
        return BaseMongoTemplate.getMongoTemplate(DBNameUtils.getUserDBName(userName, null));
    }

    public static MongoTemplate getUserMongo(String userName, String type) {
        return BaseMongoTemplate.getMongoTemplate(DBNameUtils.getUserDBName(userName, type));
    }
}
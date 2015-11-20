package com.perfect.db.mongodb.base;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.perfect.core.AppContext;
import com.perfect.db.mongodb.convert.BigDecimalToDoubleConverter;
import com.perfect.db.mongodb.convert.DoubleToBigDecimalConverter;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2014-07-23.
 *
 * @author dolphineor
 * @update 2015-09-21
 */
public class BaseMongoTemplate {

    private static final ConcurrentHashMap<String, MongoTemplate> mongoTemplateMap = new ConcurrentHashMap<>();

    private static MongoClient mongoClient;

    static {
        if (mongoClient == null) {
            synchronized (BaseMongoTemplate.class) {
                if (mongoClient == null) {
                    InputStream is = BaseMongoTemplate.class.getResourceAsStream("/mongodb.properties");
                    Properties props = new Properties();
                    try {
                        props.load(is);
                        String hosts = props.getProperty("mongo.host");
                        String[] hostArray = hosts.split(",");
                        List<ServerAddress> serverAddresses = new ArrayList<>();
                        for (String host : hostArray) {
                            String[] hostPort = host.split(":");
                            ServerAddress address;
                            if (hostPort.length == 1) {
                                address = new ServerAddress(hostPort[0]);
                            } else {
                                address = new ServerAddress(hostPort[0], Integer.parseInt(hostPort[1]));
                            }
                            serverAddresses.add(address);
                        }

                        mongoClient = new MongoClient(serverAddresses);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private BaseMongoTemplate() {
    }

    /**
     * Get {@code MongoTemplate} by specified db name.
     *
     * @param db database name
     * @return {@code MongoTemplate}
     */
    public static MongoTemplate getMongoTemplate(String db) {
        return mongoTemplateMap.computeIfAbsent(db, dbName -> {
            MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, dbName);

            //remove _class field when save data to mongodb
            MappingMongoConverter mongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
            mongoConverter.setCustomConversions(new CustomConversions(Arrays.asList(
                    new BigDecimalToDoubleConverter(),
                    new DoubleToBigDecimalConverter()
            )));
            mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

            return new MongoTemplate(mongoDbFactory, mongoConverter);
        });
    }

    public static MongoTemplate getSysMongo() {
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
package com.perfect.db.mongodb.base;

import com.mongodb.Mongo;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Created by yousheng on 2014/8/14.
 *
 * @author yousheng
 */
public class MongoPooledFactory implements PooledObjectFactory<MongoTemplate> {


    private final Mongo mongo;
    private final String dbName;

    public MongoPooledFactory(Mongo mongo, String dbName) {
        this.mongo = mongo;
        this.dbName = dbName;
    }

    @Override
    public PooledObject<MongoTemplate> makeObject() throws Exception {

        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, dbName);

        //remove _class
        MappingMongoConverter mongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new DefaultPooledObject<>(new MongoTemplate(mongoDbFactory, mongoConverter));
    }

    @Override
    public void destroyObject(PooledObject<MongoTemplate> p) throws Exception {
        MongoTemplate mongoTemplate = p.getObject();
        if(mongoTemplate != null){
        }
    }

    @Override
    public boolean validateObject(PooledObject<MongoTemplate> p) {
        return false;
    }

    @Override
    public void activateObject(PooledObject<MongoTemplate> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<MongoTemplate> p) throws Exception {

    }
}

package com.perfect.mongodb.utils;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;

/**
 * Created by IntelliJ IDEA 13.
 * User: 白宗直
 * Date: 2014-6-7
 */
public class MongoConverterEnhanceFactoryBean implements FactoryBean<MappingMongoConverter> {

    private MappingMongoConverter _mongoConverter;

    public void setMongoConverter(MappingMongoConverter _mongoConverter) {
        this._mongoConverter = _mongoConverter;
    }

    @Override
    public MappingMongoConverter getObject() throws Exception {
        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
        _mongoConverter.setTypeMapper(typeMapper);
        return _mongoConverter;
    }

    @Override
    public Class<?> getObjectType() {
        return MappingMongoConverter.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

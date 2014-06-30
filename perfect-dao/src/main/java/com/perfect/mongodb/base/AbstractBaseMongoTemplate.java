package com.perfect.mongodb.base;

import com.mongodb.DB;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by IntelliJ IDEA 13.
 * User: 白宗直
 * Date: 2014-6-7
 */
public class AbstractBaseMongoTemplate implements ApplicationContextAware {

    protected MongoTemplate _mongoTemplate;

    protected DB db;

    /**
     * 注入mongoTemplate
     *
     * @param _mongoTemplate
     */
    public void setMongoTemplate(MongoTemplate _mongoTemplate) {
        this._mongoTemplate = _mongoTemplate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MongoTemplate mongoTemplateInstance = applicationContext.getBean("mongoTemplate", MongoTemplate.class);
        setMongoTemplate(mongoTemplateInstance);
        db = mongoTemplateInstance.getDb();
    }
}

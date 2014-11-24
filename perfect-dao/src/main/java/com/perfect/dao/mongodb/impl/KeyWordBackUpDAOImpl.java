package com.perfect.dao.mongodb.impl;

import com.perfect.dao.KeyWordBackUpDAO;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.dao.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.mongodb.base.BaseMongoTemplate;
import com.perfect.dao.mongodb.utils.EntityConstants;
import com.perfect.dao.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/9.
 */
@Component
public class KeyWordBackUpDAOImpl extends AbstractUserBaseDAOImpl<KeyWordBackUpEntity,Long> implements KeyWordBackUpDAO {

    @Override
    public Class<KeyWordBackUpEntity> getEntityClass() {
        return KeyWordBackUpEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }


    /**
     * 根据mongoId得到备份的关键词
     * @param id
     * @return
     */
    public KeyWordBackUpEntity findByObjectId(String id){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.findOne(new Query(Criteria.where(EntityConstants.SYSTEM_ID).is(id)),getEntityClass(), EntityConstants.BAK_KEYWORD);
    }

    /**
     * 根据mongodb id判断该记录是否存在
     * @param id
     * @return
     */
    public boolean existsByObjectId(String id){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.exists(new Query(),getEntityClass(),EntityConstants.BAK_KEYWORD);
    }




    /**
     * 根据mongogdbID删除备份的关键词
     * @param id
     */
    public void deleteByObjectId(String id){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(EntityConstants.SYSTEM_ID).is(id)),getEntityClass(),EntityConstants.BAK_KEYWORD);
    }

    public KeyWordBackUpEntity findById(long id){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
       List<KeyWordBackUpEntity> list = mongoTemplate.find(new Query(Criteria.where(EntityConstants.KEYWORD_ID).is(id)),getEntityClass(),EntityConstants.BAK_KEYWORD);
       return list.size()==0?null:list.get(0);
    }

    public  void deleteByKwid(long kwid){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(EntityConstants.KEYWORD_ID).is(kwid)),getEntityClass(),EntityConstants.BAK_KEYWORD);
    }
}

package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.keyword.KeyWordBackUpDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.backup.KeyWordBackUpDTO;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/9.
 * 2014-11-26 refactor
 */
@Component
public class KeyWordBackUpDAOImpl extends AbstractUserBaseDAOImpl<KeyWordBackUpDTO, Long> implements KeyWordBackUpDAO {

    @Override
    public Class<KeyWordBackUpEntity> getEntityClass() {
        return KeyWordBackUpEntity.class;
    }

    /**
     * 根据mongoId得到备份的关键词
     *
     * @param id
     * @return
     */
    public KeyWordBackUpDTO findByObjectId(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeyWordBackUpEntity keywordBackUpEntity = mongoTemplate.findOne(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)), getEntityClass());

        return ObjectUtils.convertToObject(keywordBackUpEntity, getDTOClass());


    }

    /**
     * 根据mongodb id判断该记录是否存在
     *
     * @param id
     * @return
     */
    public boolean existsByObjectId(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.exists(new Query(), getEntityClass(), MongoEntityConstants.BAK_KEYWORD);
    }

    @Override
    public void myInsertAll(List<KeyWordBackUpDTO> list) {
        List<KeyWordBackUpEntity> keywordBackUpEntityList = ObjectUtils.convert(list, KeyWordBackUpEntity.class);
        getMongoTemplate().insertAll(keywordBackUpEntityList);
    }


//    /**
//     * 根据mongogdbID删除备份的关键词
//     * @param id
//     */
//    public void deleteByObjectId(String id){
//        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
//        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)),getEntityClass(),MongoEntityConstants.BAK_KEYWORD);
//    }

    public KeyWordBackUpDTO findById(long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeyWordBackUpEntity> list = mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), KeyWordBackUpEntity.class, MongoEntityConstants.BAK_KEYWORD);
        KeyWordBackUpEntity keywordBackUpEntity = list.size() == 0 ? null : list.get(0);
        KeyWordBackUpDTO keyWordBackUpDTO = new KeyWordBackUpDTO();
        BeanUtils.copyProperties(keywordBackUpEntity, keyWordBackUpDTO);
        return keyWordBackUpDTO;
    }

    public void deleteByKwid(long kwid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(kwid)), getEntityClass(), MongoEntityConstants.BAK_KEYWORD);
    }

    @Override
    public Class<KeyWordBackUpDTO> getDTOClass() {
        return KeyWordBackUpDTO.class;
    }
}

package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.KeywordImportDAO;
import com.perfect.dto.CustomGroupDTO;
import com.perfect.dto.keyword.KeywordImportDTO;
import com.perfect.entity.CustomGroupEntity;
import com.perfect.entity.KeywordImportEntity;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by XiaoWei on 2014/9/22.
 * 2014-11-26 refactor
 */
@Repository("keywordImportDAO")
public class KeywordImportDAOImpl extends AbstractUserBaseDAOImpl<KeywordImportEntity, Long> implements KeywordImportDAO {
    @Override
    public Class<KeywordImportEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public KeywordImportDTO findByKwdId(Long kwdId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeywordImportEntity keywordImportEntity= mongoTemplate.findOne(new Query(Criteria.where(KEYWORD_ID).is(kwdId)), KeywordImportEntity.class);
        KeywordImportDTO dto=new KeywordImportDTO();
        BeanUtils.copyProperties(keywordImportEntity,dto);
        return dto;
    }

    @Override
    public List<KeywordImportDTO> findByCgId(String cgId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordImportEntity> keywordImportEntityList = mongoTemplate.find(new Query(Criteria.where("cgid").is(cgId)), KeywordImportEntity.class);
        return ObjectUtils.convert(keywordImportEntityList,KeywordImportDTO.class);
    }

    @Override
    public List<KeywordImportDTO> getAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordImportEntity> keywordImportEntityList= mongoTemplate.findAll(KeywordImportEntity.class);
        return ObjectUtils.convert(keywordImportEntityList,KeywordImportDTO.class);
    }

    @Override
    public List<Long> findByAdgroupIds(List<Long> adgroupIds) {
        List<KeywordImportEntity> keywordImEntities = BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), KeywordImportEntity.class);
        List<Long> keywords = new ArrayList<>();
        if (keywordImEntities.size() > 0) {
            for (KeywordImportEntity kwd : keywordImEntities) {
                keywords.add(kwd.getKeywordId());
            }
        }
        return keywords;
    }

    @Override
    public List<Long> findByAdgroupId(Long adgroupId) {
        List<KeywordImportEntity> keywordImEntities = BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(ADGROUP_ID).in(adgroupId)), KeywordImportEntity.class);
        List<Long> keywords = new ArrayList<>();
        if (keywordImEntities.size() > 0) {
            for (KeywordImportEntity kwd : keywordImEntities) {
                keywords.add(kwd.getKeywordId());
            }
        }
        return keywords;
    }

    @Override
    public List<Long> findByKeywordName(String str) {
        Pattern pattern = Pattern.compile("^.*" + str + ".*$", Pattern.CASE_INSENSITIVE);
        List<KeywordImportEntity> keywordImEntities = BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where("name").regex(pattern)), KeywordImportEntity.class);
        List<Long> keywordIds = new ArrayList<>();
        if (keywordImEntities.size() > 0) {
            for (KeywordImportEntity kwd : keywordImEntities) {
                keywordIds.add(kwd.getKeywordId());
            }
        }
        return keywordIds;
    }

    @Override
    public void deleteByObjId(String cgid) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(SYSTEM_ID).is(cgid)), CustomGroupEntity.class);
        deleteBySubData(cgid);
    }

    @Override
    public void update(KeywordImportDTO keywordImportDTO) {
        KeywordImportEntity keywordImportEntity=new KeywordImportEntity();
        BeanUtils.copyProperties(keywordImportDTO,keywordImportEntity);
        getMongoTemplate().save(keywordImportEntity);
    }

    @Override
    public void myInsertAll(List<KeywordImportDTO> keywordImportDTOs) {
        List<KeywordImportEntity> insertList=ObjectUtils.convert(keywordImportDTOs,KeywordImportEntity.class);
        getMongoTemplate().insertAll(insertList);
    }

    @Override
    public void myInsert(KeywordImportDTO dto) {
        KeywordImportEntity keywordImportEntity=new KeywordImportEntity();
        BeanUtils.copyProperties(dto,keywordImportEntity);
        getMongoTemplate().insert(keywordImportEntity, MongoEntityConstants.TBL_IMPORTANT_KEYWORD);
    }

    private void deleteBySubData(String cgid) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where("cgid").in(cgid)), KeywordImportEntity.class);
    }
}

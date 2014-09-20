package com.perfect.mongodb.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.core.AppContext;
import com.perfect.dao.CustomGroupDAO;
import com.perfect.entity.CustomGroupEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 */
@Repository("customGroupDAO")
public class CustomGroupDAOImpl extends AbstractUserBaseDAOImpl<CustomGroupEntity,Long> implements CustomGroupDAO {
    @Override
    public Class<CustomGroupEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<CustomGroupEntity> findAll(Long acId) {
        MongoTemplate mongoTemplate= BaseMongoTemplate.getUserMongo();
        List<CustomGroupEntity> findList= mongoTemplate.find(new Query(Criteria.where(EntityConstants.ACCOUNT_ID).is(acId)),CustomGroupEntity.class);
        return findList;
    }

    @Override
    public ArrayNode getCustomGroupTree() {
        MongoTemplate mongoTemplate=BaseMongoTemplate.getUserMongo();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode;
        List<CustomGroupEntity> customGroupEntityList=mongoTemplate.find(new Query(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())),CustomGroupEntity.class);
        for (CustomGroupEntity cg:customGroupEntityList){
            objectNode = mapper.createObjectNode();
            objectNode.put("id",cg.getId());
            objectNode.put("name",cg.getGroupName());
            arrayNode.add(objectNode);
        }
        return arrayNode;
    }
}

package com.perfect.db.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.CustomGroupDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.CustomGroupDTO;
import com.perfect.entity.adgroup.CustomGroupEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 * 2014-11-26 refactor
 */
@Repository("customGroupDAO")
public class CustomGroupDAOImpl extends AbstractUserBaseDAOImpl<CustomGroupDTO, Long> implements CustomGroupDAO {
    @Override
    public Class<CustomGroupEntity> getEntityClass() {
        return null;
    }


    @Override
    public List<CustomGroupDTO> findAll(Long acId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<CustomGroupEntity> findList = mongoTemplate.find(new Query(Criteria.where(ACCOUNT_ID).is(acId)), CustomGroupEntity.class);
        return ObjectUtils.convert(findList,CustomGroupDTO.class);
    }

    @Override
    public ArrayNode getCustomGroupTree() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode;
        List<CustomGroupEntity> customGroupEntityList = mongoTemplate.find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), CustomGroupEntity.class);
        for (CustomGroupEntity cg : customGroupEntityList) {
            objectNode = mapper.createObjectNode();
            objectNode.put("id", cg.getId());
            objectNode.put("name", cg.getGroupName());
            arrayNode.add(objectNode);
        }
        return arrayNode;
    }

    @Override
    public CustomGroupDTO findByCustomName(String customName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        CustomGroupEntity customGroupEntity= mongoTemplate.findOne(new Query(Criteria.where("gname").is(customName)), CustomGroupEntity.class);
        CustomGroupDTO customGroupDTO=new CustomGroupDTO();
        BeanUtils.copyProperties(customGroupEntity,customGroupDTO);
        return customGroupDTO;
    }

    @Override
    public void myInsert(CustomGroupDTO customGroupDTO) {
        CustomGroupEntity customGroupEntity=new CustomGroupEntity();
        BeanUtils.copyProperties(customGroupDTO,customGroupEntity);
        getMongoTemplate().insert(customGroupEntity, MongoEntityConstants.TBL_CUSTOMGROUP);
    }

    @Override
    public Class<CustomGroupDTO> getDTOClass() {
        return CustomGroupDTO.class;
    }
}

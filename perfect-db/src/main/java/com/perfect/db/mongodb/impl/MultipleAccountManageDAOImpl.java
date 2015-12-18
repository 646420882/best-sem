package com.perfect.db.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.core.AppContext;
import com.perfect.dao.account.MultipleAccountManageDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.db.mongodb.impl.AccountManageDAOImpl.CampaignVO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.entity.sys.SystemUserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@SuppressWarnings("unchecked")
@Repository(value = "multipleAccountManageDAO")
public class MultipleAccountManageDAOImpl extends AbstractUserBaseDAOImpl<SystemUserDTO, String> implements MultipleAccountManageDAO {

    @Resource
    private MultipleAccountManageDAO multipleAccountManageDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    /**
     * 获取百度多账户树
     */
    @Override
    public ArrayNode getAccountDownloadstree(String currSystemUserName) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode;
        //第一步。查询百思账户，查询百度账户
        List<ModuleAccountInfoDTO> list = multipleAccountManageDAO.getBaiduAccountItems(currSystemUserName);
        for (ModuleAccountInfoDTO baiduAccountInfo : list) {
            String baiduAccountId = baiduAccountInfo.getId() + "_";
            objectNode = mapper.createObjectNode();
            objectNode.put("id", baiduAccountId + baiduAccountInfo.getBaiduAccountId());
            objectNode.put("pId", 0);
            objectNode.put("name", baiduAccountInfo.getBaiduUserName());
            arrayNode.add(objectNode);

            //获取推广计划
            MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
            Aggregation aggregationCampaign = Aggregation.newAggregation(
                    match(Criteria.where(ACCOUNT_ID).is(baiduAccountInfo.getBaiduAccountId())),
                    project(CAMPAIGN_ID, NAME, SYSTEM_ID),
                    sort(Sort.Direction.ASC, CAMPAIGN_ID)
            );
            AggregationResults<CampaignVO> results1 = mongoTemplate.aggregate(aggregationCampaign, TBL_CAMPAIGN, CampaignVO.class);
            for (CampaignVO vo : results1) {
                objectNode = mapper.createObjectNode();

                if (vo.getCampaignId() == null) {
                    objectNode.put("id", baiduAccountId + vo.getId());
                } else {
                    objectNode.put("id", baiduAccountId + vo.getCampaignId());
                }

                objectNode.put("pId", baiduAccountId + baiduAccountInfo.getId());
                objectNode.put("name", vo.getCampaignName());
                arrayNode.add(objectNode);
            }
        }
        return arrayNode;
    }


    @Override
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Class<SystemUserDTO> getDTOClass() {
        return SystemUserDTO.class;
    }


    /**
     * TODO 获取当前登录用户的所有百度账户信息
     *
     * @param currUserName
     * @return
     * @see {@code com.perfect.service.SystemUserInfoService#findBaiduAccountsByUserName}
     * @deprecated
     */
    @Override
    public List<ModuleAccountInfoDTO> getBaiduAccountItems(String currUserName) {
        return AppContext.getModuleAccounts();

//        SystemUserDTO systemUserDTO = systemUserDAO.findByUserName(currUserName);
//        if (systemUserDTO != null) {
//            return systemUserDTO.getBaiduAccounts();
//        }
//        return Collections.emptyList();
    }

}

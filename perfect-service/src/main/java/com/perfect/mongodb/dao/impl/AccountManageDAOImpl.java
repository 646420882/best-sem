package com.perfect.mongodb.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-6-25.
 */
@Repository(value = "accountManageDAO")
public class AccountManageDAOImpl implements AccountManageDAO<BaiduAccountInfoEntity> {
    private static ObjectMapper mapper;

    static {
        mapper = (mapper == null) ? new ObjectMapper() : mapper;
    }

    @Resource(name = "systemUserDAO")
    private SystemUserDAO systemUserDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    public static void main(String[] args) {
        BaiduAccountInfoEntity entity = new BaiduAccountInfoEntity();
        entity.setId(6243012l);
        entity.setBaiduUserName("baidu-bjtthunbohui2134115");
        entity.setBaiduPassword("Bjhunbohui7");
        new AccountManageDAOImpl().getAccountTree(entity);
    }

    /**
     * 百度账户�
     *
     * @param entity
     * @return
     */
    @Override
    public ArrayNode getAccountTree(BaiduAccountInfoEntity entity) {
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode;

        Long id = entity.getId();
//        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getUserDBName("perfect", null));

        List<Long> campaignIds = new ArrayList<>();
        Aggregation aggregation1 = Aggregation.newAggregation(
                project("aid", "cid", "name"),
                match(Criteria.where("aid").is(id)),
                group("cid", "name"),
                sort(Sort.Direction.ASC, "cid")
        );
        //推广计划�
        AggregationResults<CampaignVO> results1 = mongoTemplate.aggregate(aggregation1, "campaign", CampaignVO.class);
        for (CampaignVO vo : Lists.newArrayList(results1.iterator())) {
            objectNode = mapper.createObjectNode();
            objectNode.put("id", vo.getCampaignId());
            objectNode.put("pId", 0);
            objectNode.put("name", vo.getCampaignName());
            arrayNode.add(objectNode);
            campaignIds.add(vo.getCampaignId());
        }

        Aggregation aggregation2 = Aggregation.newAggregation(
                project("cid", "adid", "name"),
                match(Criteria.where("cid").in(campaignIds)),
                group("cid", "adid", "name"),
                sort(Sort.Direction.ASC, "adid")
        );
        //推广单元�
        AggregationResults<AdgroupVO> results2 = mongoTemplate.aggregate(aggregation2, "adgroup", AdgroupVO.class);
        for (AdgroupVO vo : Lists.newArrayList(results2.iterator())) {
            objectNode = mapper.createObjectNode();
            objectNode.put("id", vo.getAdgroupId());
            objectNode.put("pId", vo.getCampaignId());
            objectNode.put("name", vo.getAdgroupName());
            arrayNode.add(objectNode);
        }

        return arrayNode;
    }

    /**
     * ---
     *
     * @param currUserName
     * @return
     */
    @Override
    public List<BaiduAccountInfoEntity> getBaiduAccountItems(String currUserName) {
        List<BaiduAccountInfoEntity> list = systemUserDAO
                .findByUserName(currUserName)
                .getBaiduAccountInfoEntities();
        return list;
    }

    /**
     * ---
     *
     * @param baiduUserId
     * @return
     */
    @Override
    public BaiduAccountInfoEntity findByBaiduUserId(Long baiduUserId) {
        List<BaiduAccountInfoEntity> list = getBaiduAccountItems(AppContext.getUser().toString());
        BaiduAccountInfoEntity baiduAccount = new BaiduAccountInfoEntity();
        for (BaiduAccountInfoEntity entity : list) {
            if (baiduUserId.equals(entity.getId())) {
                baiduAccount.setId(baiduUserId);
                baiduAccount.setBaiduUserName(entity.getBaiduUserName());
                baiduAccount.setBaiduPassword(entity.getBaiduPassword());
                baiduAccount.setToken(entity.getToken());
                break;
            }
        }
        return baiduAccount;
    }

    @Override
    public void updateAccountData(Long baiduUserId) {
    }

    /**
     * @param username
     * @param password
     * @param token
     * @return
     */
    @Override
    public List<BaiduAccountInfoEntity> getBaiduAccountInfos(String username, String password, String token) {
        List<BaiduAccountInfoEntity> list = new ArrayList<>();
        Long id = getBaiduAccountId(username, password, token);
        BaiduAccountInfoEntity entity = new BaiduAccountInfoEntity();
        entity.setId(id);
        entity.setBaiduUserName(username);
        entity.setBaiduPassword(password);
        entity.setToken(token);
        list.add(entity);
        return list;
    }

    /**
     * 获取百度用户id
     *
     * @param username
     * @param password
     * @param token
     * @return
     */
    private Long getBaiduAccountId(String username, String password, String token) {
        CommonService service = null;
        Long baiduAccountId = null;
        try {
            service = ServiceFactory.getInstance(username, password, token, null);
            AccountService accountService = service.getService(AccountService.class);
            GetAccountInfoRequest request = new GetAccountInfoRequest();
            GetAccountInfoResponse response = accountService.getAccountInfo(request);
            AccountInfoType accountInfoType = response.getAccountInfoType();
            baiduAccountId = accountInfoType.getUserid();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return baiduAccountId;
    }

    class CampaignVO {

        @Field("cid")
        private Long campaignId;

        @Field("name")
        private String campaignName;

        public Long getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(Long campaignId) {
            this.campaignId = campaignId;
        }

        public String getCampaignName() {
            return campaignName;
        }

        public void setCampaignName(String campaignName) {
            this.campaignName = campaignName;
        }

        public String toString() {
            return "CampaignVO{" +
                    "campaignId=" + campaignId +
                    ", campaignName='" + campaignName + '\'' +
                    '}';
        }
    }

    class AdgroupVO {

        @Field("adid")
        private Long adgroupId;

        @Field("name")
        private String adgroupName;

        @Field("cid")
        private Long campaignId;

        public Long getAdgroupId() {
            return adgroupId;
        }

        public void setAdgroupId(Long adgroupId) {
            this.adgroupId = adgroupId;
        }

        public String getAdgroupName() {
            return adgroupName;
        }

        public void setAdgroupName(String adgroupName) {
            this.adgroupName = adgroupName;
        }

        public Long getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(Long campaignId) {
            this.campaignId = campaignId;
        }

        public String toString() {
            return "AdgroupVO{" +
                    "adgroupId=" + adgroupId +
                    ", adgroupName='" + adgroupName + '\'' +
                    ", campaignId=" + campaignId +
                    '}';
        }
    }
}

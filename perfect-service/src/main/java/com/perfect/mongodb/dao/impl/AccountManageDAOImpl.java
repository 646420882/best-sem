package com.perfect.mongodb.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.perfect.mongodb.utils.EntityConstants.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-6-25.
 */
@Repository(value = "accountManageDAO")
public class AccountManageDAOImpl implements AccountManageDAO<BaiduAccountInfoEntity> {
    @Resource
    private SystemUserDAO systemUserDAO;

    /**
     * 百度账户树
     *
     * @return
     */
    @Override
    public ArrayNode getAccountTree() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode;

        Long accountId = AppContext.getAccountId();
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Aggregation aggregation1 = Aggregation.newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId)),
                project(CAMPAIGN_ID, "name", "_id"),
                sort(Sort.Direction.ASC, CAMPAIGN_ID)
        );
        //推广计划树
        AggregationResults<CampaignVO> results1 = mongoTemplate.aggregate(aggregation1, TBL_CAMPAIGN, CampaignVO.class);
        for (CampaignVO vo : results1) {
            objectNode = mapper.createObjectNode();

            if (vo.getCampaignId() == null) {
                objectNode.put("id", vo.getId());
            } else {
                objectNode.put("id", vo.getCampaignId());
            }

            objectNode.put("pId", 0);
            objectNode.put("name", vo.getCampaignName());
            arrayNode.add(objectNode);
        }

        Aggregation aggregation2 = Aggregation.newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId)),
                project(CAMPAIGN_ID, OBJ_CAMPAIGN_ID, ADGROUP_ID, "_id", "name"),
                sort(Sort.Direction.ASC, ADGROUP_ID)
        );
        AggregationResults<AdgroupVO> results2 = mongoTemplate.aggregate(aggregation2, TBL_ADGROUP, AdgroupVO.class);
        for (AdgroupVO vo : results2) {
            objectNode = mapper.createObjectNode();
            objectNode.put("name", vo.getAdgroupName());
            if (vo.getCampaignId() == null) {
                objectNode.put("id", vo.getId());
                objectNode.put("pId", vo.getCampaignObjId());
                arrayNode.add(objectNode);
                continue;
            }

            if (vo.getAdgroupId() == null) {
                objectNode.put("id", vo.getId());
                objectNode.put("pId", vo.getCampaignId());
                arrayNode.add(objectNode);
                continue;
            }

            objectNode.put("id", vo.getAdgroupId());
            objectNode.put("pId", vo.getCampaignId());
            arrayNode.add(objectNode);
        }

        return arrayNode;
    }

    /**
     * 获取当前登录用户的所有百度账户信息
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
     * 根据百度账户id获取账户信息
     *
     * @param baiduUserId
     * @return
     */
    @Override
    public BaiduAccountInfoEntity findByBaiduUserId(Long baiduUserId) {
        String currUser = AppContext.getUser();
        List<BaiduAccountInfoEntity> list = getBaiduAccountItems(currUser);

        BaiduAccountInfoEntity baiduAccount = null;
        for (BaiduAccountInfoEntity entity : list) {
            if (baiduUserId.equals(entity.getId())) {
                baiduAccount = entity;
                break;
            }
        }

        return baiduAccount;
    }

    @Override
    public void updateBaiduAccountInfo(BaiduAccountInfoEntity entity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
        String currUser = AppContext.getUser();
        Update update = new Update();
        if (entity.getBudget() != null) {
            update.set("bdAccounts.$.bgt", entity.getBudget());
        }
        if (entity.getIsDynamicCreative() != null) {
            update.set("bdAccounts.$.dc", entity.getIsDynamicCreative());
        }
        if (entity.getExcludeIp() != null) {
            update.set("bdAccounts.$.exIp", entity.getExcludeIp());
        }
        mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(currUser).and("bdAccounts.id").is(entity.getId())), update, SystemUserEntity.class);
    }

    /**
     * @param dates
     * @return
     */
    @Override
    public List<AccountReportEntity> getAccountReports(List<Date> dates) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        Long baiduAccountId = AppContext.getAccountId();
        List<AccountReportEntity> reportEntities = mongoTemplate.
                find(Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and("date").in(dates)), AccountReportEntity.class);
        return reportEntities;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Double getYesterdayCost(Long accountId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        Date date = DateUtils.getYesterday();
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and("date").is(date)),
                project("pccost")
        );
        AggregationResults<AccountReportEntity> results = mongoTemplate.aggregate(aggregation, TBL_ACCOUNT_REPORT, AccountReportEntity.class);
        if (results == null) {
            return 0d;
        }
        AccountReportEntity reportEntity = results.getUniqueMappedResult();
        if (reportEntity != null)
            return reportEntity.getPcCost().doubleValue();
        else
            return 0d;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Double getCostRate() {
        Long accountId = AppContext.getAccountId();
        Double cost1 = getYesterdayCost(accountId);
        Double cost2 = 0d;
        Double costRate;
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        Long baiduAccountId = AppContext.get().getAccountId();
        Date date = ((List<Date>) DateUtils.getsLatestAnyDays("MM-dd", 2).get(DateUtils.KEY_DATE)).get(1);
        AccountReportEntity reportEntity = mongoTemplate.
                findOne(Query.query(Criteria.where("date").is(date).and(ACCOUNT_ID).is(baiduAccountId)), AccountReportEntity.class);
        if (reportEntity != null)
            cost2 = reportEntity.getPcCost().doubleValue();
        if (cost2 == 0d)
            return 0d;
        costRate = (cost1 - cost2) / cost2;
        costRate = new BigDecimal(costRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return costRate;
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
        CommonService service;
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

        @Id
        private String id;

        @Field(CAMPAIGN_ID)
        private Long campaignId;

        @Field("name")
        private String campaignName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
    }

    class AdgroupVO {

        @Id
        private String id;

        @Field(ADGROUP_ID)
        private Long adgroupId;

        @Field("name")
        private String adgroupName;

        @Field(CAMPAIGN_ID)
        private Long campaignId;

        @Field(OBJ_CAMPAIGN_ID)
        private String campaignObjId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getCampaignObjId() {
            return campaignObjId;
        }

        public void setCampaignObjId(String campaignObjId) {
            this.campaignObjId = campaignObjId;
        }

    }
}
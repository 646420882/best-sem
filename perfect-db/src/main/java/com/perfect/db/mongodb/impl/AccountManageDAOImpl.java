package com.perfect.db.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.WriteResult;
import com.perfect.utils.DateUtils;
import com.perfect.utils.ObjectUtils;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.entity.sys.BaiduAccountInfoEntity;
import com.perfect.entity.sys.SystemUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-6-25.
 * 2014-11-29 refactor
 */
@SuppressWarnings("unchecked")
@Repository(value = "accountManageDAO")
public class AccountManageDAOImpl implements AccountManageDAO<SystemUserDTO> {
    protected static transient Logger log = LoggerFactory.getLogger(AccountManageDAOImpl.class);

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
                project(CAMPAIGN_ID, NAME, SYSTEM_ID),
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
                project(CAMPAIGN_ID, OBJ_CAMPAIGN_ID, ADGROUP_ID, SYSTEM_ID, NAME),
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
    public List<BaiduAccountInfoDTO> getBaiduAccountItems(String currUserName) {
        return systemUserDAO.findByUserName(currUserName).getBaiduAccountInfoDTOs();
    }

    @Override
    public List<SystemUserDTO> getAllSysUserAccount() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return ObjectUtils.convert(mongoTemplate.find(Query.query(Criteria.where("access").is(2)), SystemUserEntity.class), SystemUserDTO.class);
    }

    /**
     * 根据百度账户id获取账户信息
     *
     * @param baiduUserId
     * @return
     */
    @Override
    public BaiduAccountInfoDTO findByBaiduUserId(Long baiduUserId) {
        String currUser = AppContext.getUser();
        List<BaiduAccountInfoDTO> list = getBaiduAccountItems(currUser);

        BaiduAccountInfoDTO baiduAccount = null;
        for (BaiduAccountInfoDTO dto : list) {
            if (baiduUserId.equals(dto.getId())) {
                baiduAccount = dto;
                break;
            }
        }

        return baiduAccount;
    }

    @Override
    public SystemUserDTO getCurrUserInfo() {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        BeanUtils.copyProperties(mongoTemplate.findOne(Query.query(Criteria.where("userName").is(AppContext.getUser())), getSystemUserEntityClass()), systemUserDTO);
        return systemUserDTO;
    }

    @Override
    public WriteResult updatePwd(String account, String pwd) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(account)), Update.update("password", pwd), "sys_user");
        return writeResult;
    }

    @Override
    public List<SystemUserDTO> getAccount() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return ObjectUtils.convert(mongoTemplate.find(Query.query(Criteria.where("state").is(0)), getSystemUserEntityClass()), getSystemUserDTOClass());
    }

    @Override
    public WriteResult updateBaiDuAccount(String userName, Long baiduId, Long state) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Update update = new Update();
        update.set("bdAccounts.$.state", state);
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(userName).and("bdAccounts._id").is(baiduId)), update, "sys_user");
        return writeResult;
    }

    @Override
    public List<SystemUserDTO> getAccountAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return ObjectUtils.convert(mongoTemplate.find(new Query(), getSystemUserEntityClass()), getSystemUserDTOClass());
    }

    @Override
    public int auditAccount(String userNmae, String baiduAccount, String baiduPassword, String token) {
        int i;
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccount, baiduPassword, token);

        BaiduApiService apiService = new BaiduApiService(commonService);

        AccountInfoType accountInfoType = apiService.getAccountInfo();
        if (accountInfoType == null) {
            i = -1;
        } else {
            Long accountId = accountInfoType.getUserid();
            BaiduAccountInfoEntity entity = new BaiduAccountInfoEntity();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            BeanUtils.copyProperties(accountInfoType, entity);
            entity.setId(accountId);
            entity.setBaiduUserName(baiduAccount);
            entity.setBaiduPassword(baiduPassword);
            entity.setToken(token);
            entity.setDfault(true);
            if (accountId == null) {
                log.info(df.format(new Date()) + ":添加帐号时出错！！");
            }

            Update update = new Update();
            update.addToSet("bdAccounts", entity);
            WriteResult writeResult = mongoTemplate.updateFirst(
                    Query.query(
                            Criteria.where("userName").is(userNmae).and("state").is(0)),
                    update, getSystemUserEntityClass());

            if (writeResult.isUpdateOfExisting()) {
                i = 1;
            } else {
                i = 0;
            }
        }

        return i;
    }

    @Override
    public int updateAccountStruts(String userName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(userName)), Update.update("state", 1), "sys_user");
        int i = 0;
        if (writeResult.isUpdateOfExisting())
            i = 1;
        return i;
    }

    @Override
    public void uploadImg(byte[] bytes) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(AppContext.getUser())), Update.update("img", bytes), getSystemUserEntityClass());
    }

    @Override
    public void updateBaiduAccountInfo(BaiduAccountInfoDTO dto) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        String currUser = AppContext.getUser();
        Update update = new Update();
        if (dto.getBudget() != null) {
            update.set("bdAccounts.$.bgt", dto.getBudget());
        }
        if (dto.getIsDynamicCreative() != null) {
            update.set("bdAccounts.$.dc", dto.getIsDynamicCreative());
        }
        if (dto.getExcludeIp() != null) {
            update.set("bdAccounts.$.exIp", dto.getExcludeIp());
        }
        mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(currUser).and("bdAccounts._id").is(dto.getId())), update, getSystemUserEntityClass());
    }

    /**
     * @param dates
     * @return
     */
    @Override
    public List<AccountReportDTO> getAccountReports(List<Date> dates) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        Long baiduAccountId = AppContext.getAccountId();
        return ObjectUtils.convert(
                mongoTemplate.find(
                        Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and("date").in(dates)),
                        AccountReportEntity.class),
                AccountReportDTO.class);
    }

    @Override
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
    public List<BaiduAccountInfoDTO> getBaiduAccountInfos(String username, String password, String token) {
        List<BaiduAccountInfoDTO> list = new ArrayList<>();
        Long id = getBaiduAccountId(username, password, token);
        BaiduAccountInfoDTO dto = new BaiduAccountInfoDTO();
        dto.setId(id);
        dto.setBaiduUserName(username);
        dto.setBaiduPassword(password);
        dto.setToken(token);
        list.add(dto);
        return list;
    }

    private Class<SystemUserEntity> getSystemUserEntityClass() {
        return SystemUserEntity.class;
    }

    private Class<SystemUserDTO> getSystemUserDTOClass() {
        return SystemUserDTO.class;
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
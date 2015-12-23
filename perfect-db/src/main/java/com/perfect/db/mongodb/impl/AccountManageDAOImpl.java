package com.perfect.db.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.WriteResult;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.entity.sys.ModuleAccountInfoEntity;
import com.perfect.entity.sys.SystemUserEntity;
import com.perfect.utils.DateUtils;
import com.perfect.utils.ObjectUtils;
import org.bson.types.ObjectId;
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
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-6-25.
 * 2014-12-2 refactor
 */
@SuppressWarnings("unchecked")
@Repository(value = "accountManageDAO")
public class AccountManageDAOImpl extends AbstractUserBaseDAOImpl<SystemUserDTO, String> implements AccountManageDAO {

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Class<SystemUserDTO> getDTOClass() {
        return SystemUserDTO.class;
    }

    @Override
    public List<SystemUserDTO> find(Map<String, Object> params, int skip, int limit) {
        return Collections.emptyList();
    }

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
    public List<ModuleAccountInfoDTO> getBaiduAccountItems(String currUserName) {
        SystemUserDTO systemUserDTO = systemUserDAO.findByUserName(currUserName);
        if (Objects.nonNull(systemUserDTO)) {
            try {
                List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = Collections.EMPTY_LIST;

                systemUserDTO.getSystemUserModules()
                        .stream()
                        .filter(o -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, o.getModuleName()))
                        .findFirst()
                        .ifPresent((systemUserModuleDTO -> {
                    moduleAccountInfoDTOs.addAll(systemUserModuleDTO.getAccounts());
                }));

                return moduleAccountInfoDTOs;
            } catch (NullPointerException e) {
                return Collections.emptyList();
            }
        }

        return Collections.emptyList();
    }

    @Override
    public List<SystemUserDTO> getAllSysUserAccount() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        List<SystemUserEntity> userEntityList = mongoTemplate.find(
                Query.query(Criteria.where("access").is(2).and("acstate").is(1).and("state").is(1)), getEntityClass());
        return ObjectUtils.convertToList(userEntityList, getDTOClass());
    }

    /**
     * 根据百度账户id获取账户信息
     *
     * @param baiduUserId
     * @return
     */
    @Override
    public ModuleAccountInfoDTO findByBaiduUserId(Long baiduUserId) {
        List<ModuleAccountInfoDTO> list = AppContext.getModuleAccounts();

        ModuleAccountInfoDTO baiduAccount;

        try {
            baiduAccount = list.stream().filter(moduleAccountInfoDTO -> moduleAccountInfoDTO.getBaiduAccountId().compareTo(baiduUserId) == 0).findFirst().get();
        } catch (Exception e) {
            return null;
        }

        return baiduAccount;
    }

    @Override
    public boolean updatePwd(String userName, String pwd) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(userName)), Update.update("password", pwd), "sys_user");
        return writeResult.isUpdateOfExisting();
    }

    @Override
    public List<SystemUserDTO> getAccount() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return ObjectUtils.convert(mongoTemplate.find(Query.query(Criteria.where("state").is(0)), getEntityClass()), getDTOClass());
    }

    @Override
    public boolean updateBaiduAccountStatus(String userName, String moduleAccountId, Long status) {
        SystemModuleDTO systemModuleDTO = systemUserDAO.findSystemModuleByModuleName(userName, SystemNameConstant.SOUKE_SYSTEM_NAME);
        if (Objects.isNull(systemModuleDTO))
            return false;

        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Update update = Update.update("modules.accounts.$.state", status);
        WriteResult writeResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where("userName").is(userName)
                        .and("modules.moduleId").is(systemModuleDTO.getId())
                        .and("modules.accounts._id").is(new ObjectId(moduleAccountId))),
                update,
                "sys_user");

        return writeResult.isUpdateOfExisting();
    }

    @Override
    public boolean updateBaiduRemarkName(String remarkName, String moduleAccountName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Update update = Update.update("modules.accounts.$.baiduRemarkName", remarkName);
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("modules.accounts.bname").is(moduleAccountName)), update, "sys_user");

        return writeResult.isUpdateOfExisting();
    }

    @Override
    public boolean updateSysAccount(String userName, Long state) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Update update = new Update();
        update.set("acstate", state);
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(userName)), update, "sys_user");
        return writeResult.isUpdateOfExisting();
    }

    @Override
    public List<SystemUserDTO> getAccountAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return ObjectUtils.convert(mongoTemplate.find(new Query(), getEntityClass()), getDTOClass());
    }

    /*@Override
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
                    update, getEntityClass());

            if (writeResult.isUpdateOfExisting()) {
                i = 1;
            } else {
                i = 0;
            }
        }

        return i;
    }*/

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
    public void updateBaiduAccountInfo(BaiduAccountInfoDTO dto) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        String currUser = AppContext.getUser();
        Update update = new Update();
        if (dto.getBudget() != null)
            update.set("bdAccounts.$.bgt", dto.getBudget());

        if (dto.getIsDynamicCreative() != null)
            update.set("bdAccounts.$.dc", dto.getIsDynamicCreative());

        if (dto.getExcludeIp() != null)
            update.set("bdAccounts.$.exIp", dto.getExcludeIp());

        mongoTemplate.updateFirst(Query.query(Criteria.where("userName").is(currUser).and("bdAccounts._id").is(dto.getId())), update, getEntityClass());
    }

    @Override
    public void updateBaiduAccountInfo(String userName, ModuleAccountInfoDTO dto) {
        getSysMongoTemplate().updateFirst(
                Query.query(Criteria.where(SYSTEM_ID).is(userName).and("modules.accounts._id").is(new ObjectId(dto.getId()))),
                Update.update("modules.accounts.$", ObjectUtils.convert(dto, ModuleAccountInfoEntity.class)),
                getEntityClass());
    }

    @Override
    public void updateBaiduAccountBasicInfo(ModuleAccountInfoDTO dto) {
        Update update = new Update();
        if (dto.getBaiduUserName() != null) {
            update.set("modules.accounts.$.bname", dto.getBaiduUserName());
        }

        if (dto.getBaiduPassword() != null) {
            update.set("modules.accounts.$.bpasswd", dto.getBaiduPassword());
        }

        if (dto.getBaiduRemarkName() != null) {
            update.set("modules.accounts.$.rname", dto.getBaiduRemarkName());
        }

        if (dto.getBestRegDomain() != null) {
            update.set("modules.accounts.$.brd", dto.getBestRegDomain());
        }

        getSysMongoTemplate().updateFirst(
                Query.query(Criteria.where("modules.accounts._id").is(new ObjectId(dto.getId()))),
                update, getEntityClass());
    }

    @Override
    public boolean deleteBaiduAccount(String username, String moduleAccountId) {
        WriteResult writeResult = getSysMongoTemplate().remove(
                Query.query(Criteria.where("userName").is(username).and("modules.accounts._id").is(new ObjectId(moduleAccountId))),
                getEntityClass());

        return writeResult.isUpdateOfExisting();
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
        if (results == null)
            return 0d;

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
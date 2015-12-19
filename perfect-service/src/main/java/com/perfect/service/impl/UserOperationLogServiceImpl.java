package com.perfect.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.constants.UserOperationLogProperty;
import com.perfect.commons.constants.UserOperationLogLevelEnum;
import com.perfect.commons.constants.UserOperationTypeEnum;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dao.log.UserOperationLogDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.entity.log.UserOperationLogEntity;
import com.perfect.entity.sys.ModuleAccountInfoEntity;
import com.perfect.service.UserOperationLogService;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.SystemLogDTOBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by XiaoWei on 2015/12/14.
 */
@Service
public class UserOperationLogServiceImpl implements UserOperationLogService {
    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private UserOperationLogDAO userOperationLogDAO;


    private static Map<String, Field> fieldCacheMap = Maps.newTreeMap();

    //TODO  .setOid(KeyWordEnum.addWord)
    //TODO   .setOptType(OptContentEnum.Add)
    @Override
    public UserOperationLogDTO saveKeywordLog(KeywordType newWord) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setOid(newWord.getKeywordId())
                .setName(newWord.getKeyword())
                .setType(UserOperationTypeEnum.ADD_KEYWORD.getValue())
                .setAfter(newWord.getKeyword())
                .setName(newWord.getKeyword());
        fillLayout(builder.build());
        getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO updateKeyword(KeywordType newWord, Object newVal, Object oldVal, String property) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(UserOperationTypeEnum.MODIFY_KEYWORD.getValue())
                .setProperty(property)
                .setOid(newWord.getKeywordId() != null ? newWord.getKeywordId() : null)
                .setName(newWord.getKeyword());
        getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
        if (oldVal != null) {
            builder.setBefore(oldVal.toString());
        }
        if (newVal != null) {
            builder.setAfter(newVal.toString());
        }
        return builder.build();
    }

    @Override
    public UserOperationLogDTO deleteKeywordLog(KeywordDTO newWord) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setType(UserOperationTypeEnum.DEL_KEYWORD.getValue())
                    .setOid(newWord.getKeywordId())
                    .setName(newWord.getKeyword());
            fillLayout(builder.build());
            getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public List<UserOperationLogDTO> updateKeywordAll(KeywordType newWord) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        List<UserOperationLogDTO> logs = Lists.newArrayList();
        if (!baiduType.getPrice().equals(newWord.getPrice())) {
            logs.add(update(newWord, UserOperationLogLevelEnum.KEYWORD, UserOperationLogProperty.PRICE, UserOperationTypeEnum.MODIFY_KEYWORD, newWord.getPrice(), baiduType.getPrice()));
        }
        if (baiduType.getPause() != newWord.getPause()) {
            logs.add(update(newWord, UserOperationLogLevelEnum.KEYWORD, UserOperationLogProperty.PAUSE, UserOperationTypeEnum.MODIFY_KEYWORD, newWord.getPause(), baiduType.getPause()));
        }
        if (baiduType.getMatchType() != newWord.getMatchType()) {
            logs.add(update(newWord, UserOperationLogLevelEnum.KEYWORD, UserOperationLogProperty.MATCH_TYPE, UserOperationTypeEnum.MODIFY_KEYWORD, newWord.getMatchType(), baiduType.getMatchType()));
        }
        String pcOld = nullJudge(baiduType.getPcDestinationUrl());
        String pcNew = nullJudge(newWord.getPcDestinationUrl());
        if (!Objects.equals(pcOld, pcNew)) {
            logs.add(update(newWord, UserOperationLogLevelEnum.KEYWORD, UserOperationLogProperty.PC_DES_URL, UserOperationTypeEnum.MODIFY_KEYWORD, nullJudge(newWord.getPcDestinationUrl()), nullJudge(baiduType.getPcDestinationUrl())));
        }
        if (!nullJudge(baiduType.getMobileDestinationUrl()).equals(nullJudge(newWord.getMobileDestinationUrl()))) {
            logs.add(update(newWord, UserOperationLogLevelEnum.KEYWORD, UserOperationLogProperty.MIB_DES_URL, UserOperationTypeEnum.MODIFY_KEYWORD, nullJudge(newWord.getMobileDestinationUrl()), nullJudge(baiduType.getMobileDestinationUrl())));
        }
        if (!baiduType.getAdgroupId().equals(newWord.getAdgroupId())) {
            AdgroupDTO newAdgroup = adgroupDAO.findOne(newWord.getAdgroupId());
            AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
            logs.add(update(newWord, UserOperationLogLevelEnum.KEYWORD, UserOperationLogProperty.MOVE_ADGROUP, UserOperationTypeEnum.MODIFY_KEYWORD, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName()));
        }
        return logs;
    }

    //------------------------------------计划-----------------------------------------
    @Override
    public UserOperationLogDTO addCampaign(CampaignType campaignType) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setUserId(AppContext.getAccountId())
                .setName(campaignType.getCampaignName())
                .setOid(campaignType.getCampaignId())
                .setType(UserOperationTypeEnum.ADD_CAMPAIGN.getValue())
                .setCampaignName(campaignType.getCampaignName())
                .setAfter(campaignType.getCampaignName());
        fillLayout(builder.build());
        return builder.build();
    }

    @Override
    public UserOperationLogDTO removeCampaign(CampaignDTO campaignType) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CampaignType baiduType = baiduApiService.getCampaignTypeById(campaignType.getCampaignId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setUserId(AppContext.getAccountId())
                    .setOid(campaignType.getCampaignId())
                    .setName(campaignType.getCampaignName())
                    .setType(UserOperationTypeEnum.DEL_CAMPAIGN.getValue())
                    .setCampaignId(campaignType.getCampaignId())
                    .setCampaignName(campaignType.getCampaignName())
                    .setBefore(campaignType.getCampaignName());
            fillLayout(builder.build());
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String property) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setUserId(AppContext.getAccountId())
                .setType(UserOperationTypeEnum.MODIFY_CAMPAIGN.getValue())
                .setCampaignId(campaignType.getCampaignId())
                .setCampaignName(campaignType.getCampaignName())
                .setProperty(property)
                .setName(newvalue)
                .setAfter(newvalue)
                .setBefore(oldvalue)
                .setOid(campaignType.getCampaignId());
        return builder.build();
    }

    @Override
    public List<UserOperationLogDTO> updateCampaignAll(CampaignType newCampaign) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CampaignType baiduType = baiduApiService.getCampaignTypeById(newCampaign.getCampaignId());
        List<UserOperationLogDTO> logs = Lists.newArrayList();
        if (baiduType != null && newCampaign != null) {
            StringBuilder newSbSc = new StringBuilder();
            if (baiduType.getSchedule() != null) {
                for (int i = 0; i < baiduType.getSchedule().size(); i++) {
                    if(baiduType.getSchedule(i)!=null){
                        ScheduleType scheduleTypes=baiduType.getSchedule(i);
                        String _tempText="["+scheduleTypes.getWeekDay()+"]["+scheduleTypes.getStartHour()+"]["+scheduleTypes.getEndHour()+"]";
                        newSbSc.append(_tempText);
                    }
                }
            }
            StringBuilder oldSbSc = new StringBuilder();
            if (newCampaign.getSchedule() != null) {
                if (newCampaign.getSchedule() != null) {
                    for (int i = 0; i < newCampaign.getSchedule().size(); i++) {
                        if(newCampaign.getSchedule(i)!=null){
                            ScheduleType scheduleTypes=newCampaign.getSchedule(i);
                            String _tempText="["+scheduleTypes.getWeekDay()+"]["+scheduleTypes.getStartHour()+"]["+scheduleTypes.getEndHour()+"]";
                            oldSbSc.append(_tempText);
                        }
                    }
                }
            }
            if (!Objects.equals(baiduType.getCampaignName(), newCampaign.getCampaignName())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.NAME, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getCampaignName(), baiduType.getCampaignName()));
            }
            if (!Objects.equals(baiduType.getPause(), newCampaign.getPause())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.PAUSE, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getPause().toString(), baiduType.getPause().toString()));
            }
            if (!Objects.equals(baiduType.getBudget(), newCampaign.getBudget())) {

                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.CAMPAIGN_BUDGET, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getBudget().toString(), nullJudge(baiduType.getBudget()).toString()));
            }
            if (!Objects.equals(newSbSc.toString(), oldSbSc.toString())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.CAMPAIGN_SCHEDULE, UserOperationTypeEnum.MODIFY_CAMPAIGN, newSbSc.toString(), oldSbSc.toString()));
            }
            if (!Objects.equals(baiduType.getDevice(), newCampaign.getDevice())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.DEVICE, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getDevice().toString(), baiduType.getDevice().toString()));
            }
            if (!Objects.equals(nullList(baiduType.getExactNegativeWords()).size(), nullList(newCampaign.getExactNegativeWords()).size())) {
                StringBuilder oldSbWord = new StringBuilder();
                StringBuilder newSbWord = new StringBuilder();
                if (baiduType.getExactNegativeWords() != null) {
                    for (int i = 0; i < baiduType.getExactNegativeWords().size(); i++) {
                        oldSbWord.append(baiduType.getExactNegativeWord(i));
                    }
                }
                if (newCampaign.getExactNegativeWords() != null) {
                    for (int i = 0; i < newCampaign.getExactNegativeWords().size(); i++) {
                        newSbWord.append(newCampaign.getExactNegativeWord(i));
                    }
                }
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.EXA_WORD, UserOperationTypeEnum.MODIFY_CAMPAIGN, newSbWord.toString(), oldSbWord.toString()));
            }
            if (!Objects.equals(nullList(baiduType.getNegativeWords()).size(), nullList(newCampaign.getNegativeWords()).size())) {
                StringBuilder oldSbWord = new StringBuilder();
                StringBuilder newSbWord = new StringBuilder();
                if (baiduType.getNegativeWords() != null) {
                    for (int i = 0; i < baiduType.getNegativeWords().size(); i++) {
                        oldSbWord.append(baiduType.getNegativeWord(i));
                    }
                }
                if (newCampaign.getNegativeWords() != null) {
                    for (int i = 0; i < newCampaign.getNegativeWords().size(); i++) {
                        newSbWord.append(newCampaign.getNegativeWord(i));
                    }
                }
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.NEG_WORD, UserOperationTypeEnum.MODIFY_CAMPAIGN, newSbWord.toString(), oldSbWord.toString()));
            }
            if (!Objects.equals(nullList(baiduType.getExcludeIp()).size(), nullList(newCampaign.getExcludeIp()).size())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.CAMPAIGN_EXCLUDEIP, UserOperationTypeEnum.MODIFY_CAMPAIGN, nullList(newCampaign.getExcludeIp()).toString(), nullJudge(baiduType.getExcludeIp()).toString()));
            }
            if (!Objects.equals(baiduType.getPriceRatio(), newCampaign.getPriceRatio())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.MIB_FACTOR, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getPriceRatio().toString(), baiduType.getPriceRatio().toString()));
            }
            if (!Objects.equals(nullList(baiduType.getRegionTarget()).size(), nullList(newCampaign.getRegionTarget()).size())) {
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.CAMPAIGN_REGION, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getRegionTarget().toString(), baiduType.getRegionTarget().toString()));
            }
            if(!Objects.equals(baiduType.getShowProb(),newCampaign.getShowProb())){
                logs.add(update(newCampaign, UserOperationLogLevelEnum.CAMPAIGN, UserOperationLogProperty.CAMPAIGN_SHOWPRO, UserOperationTypeEnum.MODIFY_CAMPAIGN, newCampaign.getShowProb(),baiduType.getShowProb()));
            }
        }
        return logs;
    }

    //----------------------------------单元------------------------------------------------
    @Override
    public UserOperationLogDTO addAdgroup(AdgroupType adgroupType) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder
                .setType(UserOperationTypeEnum.ADD_ADGROUP.getValue())
                .setOid(adgroupType.getAdgroupId())
                .setName(adgroupType.getAdgroupName())
                .setAdgroupName(adgroupType.getAdgroupName())
                .setAfter(adgroupType.getAdgroupName());
        fillLayout(builder.build());
        getCampInfoByLongId(adgroupType.getCampaignId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO removeAdgroup(AdgroupDTO adgroupType) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AdgroupType baiduType = baiduApiService.getAdgroupTypeById(adgroupType.getAdgroupId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setType(UserOperationTypeEnum.DEL_ADGROUP.getValue())
                    .setOid(adgroupType.getAdgroupId())
                    .setName(adgroupType.getAdgroupName())
                    .setBefore(adgroupType.getAdgroupName());
            fillLayout(builder.build());
            getCampInfoByLongId(adgroupType.getCampaignId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String property) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(UserOperationTypeEnum.MODIFY_ADGROUP.getValue())
                .setAdgroupId(adgroupType.getCampaignId())
                .setAdgroupName(adgroupType.getAdgroupName())
                .setName(newvalue)
                .setAfter(newvalue)
                .setBefore(oldvalue)
                .setOid(adgroupType.getAdgroupId())
                .setUserId(AppContext.getAccountId());
        return builder.build();
    }

    @Override
    public List<UserOperationLogDTO> updateAdgroupAll(AdgroupType newAdgroup) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AdgroupType baiduType = baiduApiService.getAdgroupTypeById(newAdgroup.getAdgroupId());
        List<UserOperationLogDTO> logs = Lists.newArrayList();
        if (baiduType != null && newAdgroup != null) {
            if (!Objects.equals(baiduType.getAdgroupName(), newAdgroup.getAdgroupName())) {
                logs.add(update(newAdgroup, UserOperationLogLevelEnum.ADGROUP, UserOperationLogProperty.NAME, UserOperationTypeEnum.MODIFY_ADGROUP, newAdgroup.getAdgroupName(), baiduType.getAdgroupName()));
            }
            if (!Objects.equals(baiduType.getMaxPrice(), newAdgroup.getMaxPrice())) {
                logs.add(update(newAdgroup, UserOperationLogLevelEnum.ADGROUP, UserOperationLogProperty.PRICE, UserOperationTypeEnum.MODIFY_ADGROUP, newAdgroup.getMaxPrice().toString(), baiduType.getMaxPrice().toString()));
            }
            if (!Objects.equals(nullList(baiduType.getExactNegativeWords()).size(), nullList(newAdgroup.getExactNegativeWords()).size())) {
                StringBuilder oldSb = new StringBuilder();
                StringBuilder newSb = new StringBuilder();
                if (baiduType.getExactNegativeWords() != null) {
                    for (int i = 0; i < baiduType.getExactNegativeWords().size(); i++) {
                        oldSb.append(baiduType.getExactNegativeWord(i));
                    }
                }
                if (newAdgroup.getExactNegativeWords() != null) {
                    for (int i = 0; i < newAdgroup.getExactNegativeWords().size(); i++) {
                        newSb.append(newAdgroup.getExactNegativeWord(i));
                    }
                }
                logs.add(update(newAdgroup, UserOperationLogLevelEnum.ADGROUP, UserOperationLogProperty.EXA_WORD, UserOperationTypeEnum.MODIFY_ADGROUP, newSb.toString(), oldSb.toString()));
            }
            if (!Objects.equals(nullList(baiduType.getNegativeWords()).size(), nullList(newAdgroup.getNegativeWords()).size())) {
                StringBuilder oldSb = new StringBuilder();
                StringBuilder newSb = new StringBuilder();
                if (baiduType.getNegativeWords() != null) {
                    for (int i = 0; i < baiduType.getNegativeWords().size(); i++) {
                        oldSb.append(baiduType.getNegativeWord(i));
                    }
                }
                if (newAdgroup.getNegativeWords() != null) {
                    for (int i = 0; i < newAdgroup.getNegativeWords().size(); i++) {
                        newSb.append(newAdgroup.getNegativeWord(i));
                    }
                }
                logs.add(update(newAdgroup, UserOperationLogLevelEnum.ADGROUP, UserOperationLogProperty.NEG_WORD, UserOperationTypeEnum.MODIFY_ADGROUP, newSb.toString(), oldSb.toString()));
            }
            if (!Objects.equals(baiduType.getCampaignId(), newAdgroup.getCampaignId())) {
                CampaignDTO oldCampaignDTO = campaignDAO.findOne(baiduType.getCampaignId());
                CampaignDTO newCampaignDTO = campaignDAO.findOne(newAdgroup.getCampaignId());
                logs.add(update(newAdgroup, UserOperationLogLevelEnum.ADGROUP, UserOperationLogProperty.MOVE_CAMPAIGN, UserOperationTypeEnum.MODIFY_ADGROUP, newCampaignDTO.getCampaignName(), oldCampaignDTO.getCampaignName()));
            }
            if (!Objects.equals(baiduType.getPause(), newAdgroup.getPause())) {
                logs.add(update(newAdgroup, UserOperationLogLevelEnum.ADGROUP, UserOperationLogProperty.PAUSE, UserOperationTypeEnum.MODIFY_ADGROUP, newAdgroup.getPause().toString(), baiduType.getPause().toString()));
            }
            //TODO 单元移动出价比例搜客暂时没有这个操作,如需单元出价比例操作,解除注释即可
//            if(!Objects.equals(baiduType.getAccuPriceFactor(),newAdgroup.getAccuPriceFactor())){
//                return updateAdgroup(newAdgroup,newAdgroup.getAccuPriceFactor().toString(),baiduType.getAccuPriceFactor().toString(),UserOperationLogProperty.MIB_FACTOR,AdGroupEnum.mobilePriceFactor);
//            }
        }
        return logs;
    }

    //-----------------------------------------创意-----------------------------------------------
    @Override
    public UserOperationLogDTO addCreative(CreativeType creativeType) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(UserOperationTypeEnum.ADD_CREATIVE.getValue())
                .setOid(creativeType.getCreativeId())
                .setName(creativeType.getTitle())
                .setAfter(creativeType.getTitle());
        fillLayout(builder.build());
        getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO removeCreative(CreativeDTO creativeType) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CreativeType baiduType = baiduApiService.getCreativeTypeById(creativeType.getCreativeId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setType(UserOperationTypeEnum.DEL_CREATIVE.getValue())
                    .setOid(creativeType.getCreativeId())
                    .setName(creativeType.getTitle())
                    .setBefore(creativeType.getTitle());
            fillLayout(builder.build());
            getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String property) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(UserOperationTypeEnum.MODIFY_CREATIVE.getValue())
                .setName(newvalue)
                .setProperty(property)
                .setOid(creativeType.getCreativeId());
        getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
        if (newvalue != null) {
            builder.setAfter(newvalue.toString());
        }
        if (oldvalue != null) {
            builder.setBefore(oldvalue.toString());
        }
        return builder.build();
    }

    @Override
    public List<UserOperationLogDTO> updateCreativeAll(CreativeType newCreative) {
        ModuleAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CreativeType baiduType = baiduApiService.getCreativeTypeById(newCreative.getCreativeId());
        List<UserOperationLogDTO> logs = Lists.newArrayList();
        if (baiduType != null && newCreative != null) {
            if (!baiduType.getTitle().equals(newCreative.getTitle())) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.CREATIVE_TITLE, UserOperationTypeEnum.MODIFY_CREATIVE, newCreative.getTitle(), baiduType.getTitle()));
            }
            if (!baiduType.getDescription1().equals(newCreative.getDescription1())) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.CREATIVE_DESC1, UserOperationTypeEnum.MODIFY_CREATIVE, newCreative.getDescription1(), baiduType.getDescription1()));
            }
            if (!baiduType.getDescription2().equals(newCreative.getDescription2())) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.CREATIVE_DESC2, UserOperationTypeEnum.MODIFY_CREATIVE, newCreative.getDescription2(), baiduType.getDescription2()));
            }
            if (!nullJudge(baiduType.getPcDestinationUrl()).equals(nullJudge(newCreative.getPcDestinationUrl()))) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.PC_DES_URL, UserOperationTypeEnum.MODIFY_CREATIVE, nullJudge(newCreative.getPcDestinationUrl()), nullJudge(baiduType.getPcDestinationUrl())));
            }
            if (!nullJudge(baiduType.getPcDisplayUrl()).equals(nullJudge(newCreative.getPcDisplayUrl()))) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.PC_DIS_URL, UserOperationTypeEnum.MODIFY_CREATIVE, nullJudge(newCreative.getPcDisplayUrl()), nullJudge(baiduType.getPcDisplayUrl())));
            }
            if (!nullJudge(baiduType.getMobileDestinationUrl()).equals(nullJudge(newCreative.getMobileDestinationUrl()))) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.MIB_DES_URL, UserOperationTypeEnum.MODIFY_CREATIVE, newCreative.getMobileDestinationUrl(), baiduType.getMobileDestinationUrl()));
            }
            if (!nullJudge(baiduType.getMobileDisplayUrl()).equals(nullJudge(newCreative.getMobileDisplayUrl()))) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.MIB_DIS_URL, UserOperationTypeEnum.MODIFY_CREATIVE, nullJudge(newCreative.getMobileDisplayUrl()), nullJudge(baiduType.getMobileDisplayUrl())));
            }
            if (baiduType.getPause() != newCreative.getPause()) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.PAUSE, UserOperationTypeEnum.MODIFY_CREATIVE, newCreative.getPause().toString(), baiduType.getPause().toString()));
            }
            if (baiduType.getDevicePreference() != newCreative.getDevicePreference()) {
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.DEVICE, UserOperationTypeEnum.MODIFY_CREATIVE, newCreative.getDevicePreference().toString(), baiduType.getDevicePreference().toString()));
            }
            if (baiduType.getAdgroupId().equals(newCreative.getAdgroupId())) {
                AdgroupDTO newAdgroup = adgroupDAO.findOne(newCreative.getAdgroupId());
                AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
                logs.add(update(newCreative, UserOperationLogLevelEnum.CREATIVE, UserOperationLogProperty.MOVE_ADGROUP, UserOperationTypeEnum.MODIFY_CREATIVE, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName()));
            }
        }
        return logs;
    }

    @Override
    public void getCamAdgroupInfoByLong(Long adgroupId, SystemLogDTOBuilder builder) {
        AdgroupDTO adgroupDTO = adgroupDAO.findOne(adgroupId);
        CampaignDTO campaignDTO = campaignDAO.findOne(adgroupDTO.getCampaignId());
        fillCommonData(builder, campaignDTO, adgroupDTO);
    }

    @Override
    public void getCampInfoByLongId(Long campaignId, SystemLogDTOBuilder builder) {
        CampaignDTO campaignDTO = campaignDAO.findOne(campaignId);
        fillCommonData(builder, campaignDTO);
    }

    private SystemLogDTOBuilder fillCommonData(SystemLogDTOBuilder builder, CampaignDTO campaignDTO, AdgroupDTO adgroupDTO) {
        return builder.setUserId(AppContext.getAccountId())
                .setAdgroupId(adgroupDTO.getAdgroupId())
                .setAdgroupName(adgroupDTO.getAdgroupName())
                .setCampaignId(campaignDTO.getCampaignId())
                .setCampaignName(campaignDTO.getCampaignName());

    }

    private SystemLogDTOBuilder fillCommonData(SystemLogDTOBuilder builder, CampaignDTO campaignDTO) {
        return builder.setUserId(AppContext.getAccountId())
                .setCampaignId(campaignDTO.getCampaignId())
                .setCampaignName(campaignDTO.getCampaignName());
    }


    public void save(List<UserOperationLogDTO> userOperationLogDTOs) {
        userOperationLogDAO.save(userOperationLogDTOs);
    }

    @Override
    public void saveLog(UserOperationLogDTO userOperationLogDTO) {
        userOperationLogDAO.save(userOperationLogDTO);
    }


    private final String KEYWORD_ID = "keywordId";
    private final String CAMPAIGN_ID = "campaignId";
    private final String ADGROUP_ID = "adgroupId";
    private final String CREATIVE_ID = "creativeId";

    @Override
    public UserOperationLogDTO update(Object object, UserOperationLogLevelEnum level, String property, UserOperationTypeEnum userOperationTypeEnum, Object newVal, Object
            oldVal) {

        UserOperationLogDTO userOperationLogDTO = new UserOperationLogDTO();
        userOperationLogDTO.setTime(System.currentTimeMillis());
        userOperationLogDTO.setType(userOperationTypeEnum.getValue());
        userOperationLogDTO.setBefore(oldVal);
        userOperationLogDTO.setAfter(newVal);
        userOperationLogDTO.setProperty(property);
        userOperationLogDTO.setUserId(AppContext.getAccountId());
        userOperationLogDTO.setUserName(AppContext.getUser());

        fillObjectInfo(object, userOperationLogDTO);
        fillLayout(userOperationTypeEnum, oldVal, newVal, userOperationLogDTO);
        switch (level) {
            case CAMPAIGN:
                CampaignType campaignType = (CampaignType) object;
                userOperationLogDTO.setCampgainId(campaignType.getCampaignId());
                userOperationLogDTO.setCampaignName(campaignType.getCampaignName());
                break;
            default:
                AdgroupDTO adgroupDTO = fillAdgroupInfo(object, userOperationLogDTO);
                fillCampaignInfo(adgroupDTO, userOperationLogDTO);
                break;
        }
        return userOperationLogDTO;
    }

    private final String VALUE_EMPTY = "空";


    private void fillObjectInfo(Object object, UserOperationLogDTO userOperationLogDTO) {
        if (object == null) {
            return;
        }

        if (object instanceof CampaignType) {
            CampaignType campaignType = (CampaignType) object;
            userOperationLogDTO.setOid(campaignType.getCampaignId());
            userOperationLogDTO.setName(campaignType.getCampaignName());
        } else if (object instanceof AdgroupType) {
            AdgroupType adgroupType = (AdgroupType) object;
            userOperationLogDTO.setOid(adgroupType.getAdgroupId());
            userOperationLogDTO.setName(adgroupType.getAdgroupName());
        } else if (object instanceof CreativeType) {
            CreativeType creativeType = (CreativeType) object;
            userOperationLogDTO.setOid(creativeType.getCreativeId());
            userOperationLogDTO.setName(creativeType.getTitle());
        } else if (object instanceof KeywordType) {
            KeywordType keywordType = (KeywordType) object;
            userOperationLogDTO.setOid(keywordType.getKeywordId());
            userOperationLogDTO.setName(keywordType.getKeyword());
        } else if (object instanceof SublinkType) {
            SublinkType sublinkType = (SublinkType) object;
            userOperationLogDTO.setOid(sublinkType.getSublinkId());
            userOperationLogDTO.setName(sublinkType.toString());
        } else if (object instanceof SublinkInfo) {
            SublinkInfo sublinkInfo = (SublinkInfo) object;
            userOperationLogDTO.setOid(-1l);
            userOperationLogDTO.setName(sublinkInfo.getDescription());
        }
    }

    private void fillCampaignInfo(Object object, UserOperationLogDTO userOperationLogDTO) {

        Long campaignId = null;

        if (object instanceof AdgroupDTO) {
            campaignId = ((AdgroupDTO) object).getCampaignId();
        } else {
            campaignId = getCampaignId(object);
        }

        if (campaignId == null)
            return;
        userOperationLogDTO.setCampgainId(campaignId);

        userOperationLogDTO.setCampaignName(getCampaignName(campaignId));
    }

    private AdgroupDTO fillAdgroupInfo(Object object, UserOperationLogDTO userOperationLogDTO) {
        Long adgroupId = getAdgroupId(object);
        if (adgroupId == null)
            return null;

        AdgroupDTO adgroupDTO = adgroupDAO.findOne(adgroupId);
        if (adgroupDTO == null) {
            return null;
        }

        userOperationLogDTO.setAdgroupdId(adgroupId);

        userOperationLogDTO.setAdgroupName(adgroupDTO.getAdgroupName());
        return adgroupDTO;
    }


    private String getCampaignName(Long id) {
        CampaignDTO campaignDTO = campaignDAO.findByLongId(id);

        if (campaignDTO == null) {
            return StringUtils.EMPTY;
        }

        return campaignDTO.getCampaignName();
    }

    private String getAdgroupName(Long id) {
        AdgroupDTO adgroupDTO = adgroupDAO.findOne(id);
        if (adgroupDTO == null) {
            return StringUtils.EMPTY;
        }

        return adgroupDTO.getAdgroupName();
    }

    private Long getCampaignId(Object object) {
        return getId(object, CAMPAIGN_ID);
    }

    private Long getAdgroupId(Object object) {
        return getId(object, ADGROUP_ID);
    }

    private Long getKeywordId(Object object) {
        return getId(object, KEYWORD_ID);
    }

    private Long getCreativeId(Object object) {
        return getId(object, CREATIVE_ID);
    }

    private Long getId(Object object, String property) {
        if (object == null || Strings.isNullOrEmpty(property)) {
            return -1l;
        }

        Class clz = object.getClass();

        String cacheKey = clz.getSimpleName() + "." + property;
        Field field = fieldCacheMap.get(cacheKey);

        if (field == null) {
            try {
                field = clz.getDeclaredField(property);
            } catch (NoSuchFieldException e) {
                try {
                    field = clz.getField(property);
                } catch (NoSuchFieldException e1) {
                    return -1l;
                }
            }

            if (field == null)
                return -1l;

            fieldCacheMap.put(cacheKey, field);
        }

        try {
            field.setAccessible(true);
            Object val = field.get(object);
            if (val instanceof Long) {
                return (Long) val;
            }

            return -1l;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1l;
    }

    @Override
    public UserOperationLogDTO newdel(Object object, UserOperationLogLevelEnum level, UserOperationTypeEnum
            userOperationTypeEnum) {
        UserOperationLogDTO userOperationLogDTO = new UserOperationLogDTO();
        userOperationLogDTO.setTime(System.currentTimeMillis());
        userOperationLogDTO.setType(userOperationTypeEnum.getValue());
        userOperationLogDTO.setUserId(AppContext.getAccountId());
        userOperationLogDTO.setUserName(AppContext.getUser());

        fillObjectInfo(object, userOperationLogDTO);
        fillLayout(userOperationLogDTO);
        switch (level) {
            case CAMPAIGN:
                CampaignDTO campaignDTO = (CampaignDTO) object;
                userOperationLogDTO.setCampgainId(campaignDTO.getCampaignId());
                userOperationLogDTO.setCampaignName(campaignDTO.getCampaignName());
                break;
            default:
                AdgroupDTO adgroupDTO = fillAdgroupInfo(object, userOperationLogDTO);
                fillCampaignInfo(adgroupDTO, userOperationLogDTO);
                break;
        }
        return userOperationLogDTO;
    }

    private void fillLayout(UserOperationTypeEnum userOperationTypeEnum, Object oldVal, Object newVal,
                            UserOperationLogDTO userOperationLogDTO) {
        String layout = UserOperationTypeEnum.layout(userOperationTypeEnum);

        layout = replaceValue(layout, "%type%", getOperationType(userOperationLogDTO));
        layout = replaceValue(layout, "%name%", userOperationLogDTO.getName());
        layout = replaceValue(layout, "%prop%", getProperty(userOperationLogDTO.getProperty()));
        layout = replaceValue(layout, "%before%", oldVal);
        layout = replaceValue(layout, "%after%", newVal);
        userOperationLogDTO.setText(layout);
    }


    private String replaceValue(String layout, String pattern, Object value) {
        String innerLayout = layout;
        if (value instanceof Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            innerLayout = innerLayout.replace(pattern, dateFormat.format(value));
        } else {
            if (value == null) {
                innerLayout = layout.replace(pattern, VALUE_EMPTY);
            } else {
                innerLayout = layout.replace(pattern, value.toString());
            }
        }

        return innerLayout;
    }

    private void fillLayout(UserOperationLogDTO
                                    userOperationLogDTO) {

        String layout = UserOperationTypeEnum.layout(UserOperationTypeEnum.OPERATION);

        layout = replaceValue(layout, "%type%", getOperationType(userOperationLogDTO));
        layout = replaceValue(layout, "%name%", userOperationLogDTO.getName());
        userOperationLogDTO.setText(layout);
    }

    private String nullJudge(Object obj) {
        if (obj == null) {
            return VALUE_EMPTY;
        } else {
            obj = obj.toString().replaceAll(" ", "");
            if (obj.toString().equals("")) {
                return VALUE_EMPTY;
            } else {
                return obj.toString();
            }
        }
    }

    private List<Object> nullList(Object obj) {
        List<Object> o = Lists.newArrayList();
        if (obj == null) {
            return o;
        } else {
            if (obj instanceof List) {
                return (List<Object>) obj;
            }
        }
        return o;
    }

    private String getOperationType(UserOperationLogDTO userOperationLogDTO) {
        int type = userOperationLogDTO.getType();
        switch (type) {
            case 101:
                return "新增关键词";
            case 102:
                return "新增创意";
            case 103:
                return "新增推广单元";
            case 104:
                return "新增推广计划";
            case 201:
                return "删除关键词";
            case 202:
                return "删除创意";
            case 203:
                return "删除推广单元";
            case 204:
                return "删除推广计划";
            case 301:
                return "修改关键词";
            case 302:
                return "修改创意";
            case 303:
                return "修改推广单元";
            default:
                return "修改推广计划";
        }
    }

    private String getProperty(String property) {
        switch (property) {
            case UserOperationLogProperty.NAME:
                return UserOperationLogProperty.NAME_CH;
            case UserOperationLogProperty.PRICE:
                return UserOperationLogProperty.PRICE_CH;
            case UserOperationLogProperty.PC_DES_URL:
                return UserOperationLogProperty.PC_DES_URL_CH;
            case UserOperationLogProperty.PC_DIS_URL:
                return UserOperationLogProperty.PC_DIS_URL_CH;
            case UserOperationLogProperty.MIB_DES_URL:
                return UserOperationLogProperty.MIB_DES_URL_CH;
            case UserOperationLogProperty.MIB_DIS_URL:
                return UserOperationLogProperty.MIB_DIS_URL_CH;
            case UserOperationLogProperty.MATCH_TYPE:
                return UserOperationLogProperty.MATCH_TYPE_CH;
            case UserOperationLogProperty.PRASE_TYPE:
                return UserOperationLogProperty.PRASE_TYPE_CH;
            case UserOperationLogProperty.PAUSE:
                return UserOperationLogProperty.PAUSE_CH;
            case UserOperationLogProperty.MOVE_ADGROUP:
                return UserOperationLogProperty.MOVE_ADGROUP_CH;
            case UserOperationLogProperty.CREATIVE_TITLE:
                return UserOperationLogProperty.CREATIVE_TITLE_CH;
            case UserOperationLogProperty.CREATIVE_DESC1:
                return UserOperationLogProperty.CREATIVE_DESC1_CH;
            case UserOperationLogProperty.CREATIVE_DESC2:
                return UserOperationLogProperty.CREATIVE_DESC2_CH;
            case UserOperationLogProperty.DEVICE:
                return UserOperationLogProperty.DEVICE_CH;
            case UserOperationLogProperty.NEG_WORD:
                return UserOperationLogProperty.NEG_WORD_CH;
            case UserOperationLogProperty.EXA_WORD:
                return UserOperationLogProperty.EXA_WORD_CH;
            case UserOperationLogProperty.MOVE_CAMPAIGN:
                return UserOperationLogProperty.MOVE_CAMPAIGN_CH;
            case UserOperationLogProperty.MIB_FACTOR:
                return UserOperationLogProperty.MIB_FACTOR_CH;
            case UserOperationLogProperty.CAMPAIGN_BUDGET:
                return UserOperationLogProperty.CAMPAIGN_BUDGET_CH;
            case UserOperationLogProperty.CAMPAIGN_SCHEDULE:
                return UserOperationLogProperty.CAMPAIGN_SCHEDULE_CH;
            case UserOperationLogProperty.CAMPAIGN_EXCLUDEIP:
                return UserOperationLogProperty.CAMPAIGN_EXCLUDEIP_CH;
            case UserOperationLogProperty.CAMPAIGN_REGION:
                return UserOperationLogProperty.CAMPAIGN_REGION_CH;
            case UserOperationLogProperty.CAMPAIGN_SHOWPRO:
                return UserOperationLogProperty.CAMPAIGN_SHOWPRO_CH;
        }
        return null;
    }

}
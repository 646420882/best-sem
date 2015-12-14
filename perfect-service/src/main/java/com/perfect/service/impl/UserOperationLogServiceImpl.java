package com.perfect.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.constants.LogObjConstants;
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
import com.perfect.service.UserOperationLogService;
import com.perfect.utils.SystemLogDTOBuilder;
import org.apache.commons.lang.StringUtils;

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
                .setName(LogObjConstants.NAME);
        getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO updateKeywordLog(KeywordType newWord, Object newVal, Object oldVal, String property) {
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
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setType(UserOperationTypeEnum.DEL_KEYWORD.getValue())
                    .setOid(newWord.getKeywordId())
                    .setName(newWord.getKeyword());
            getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO uploadLogWordUpdate(KeywordType newWord) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        if (baiduType.getPrice() != newWord.getPrice()) {
            return updateKeywordLog(newWord, newWord.getPrice(), baiduType.getPrice(), LogObjConstants.PRICE);
        }
        if (baiduType.getPause() != newWord.getPause()) {
            return updateKeywordLog(newWord, newWord.getPause(), baiduType.getPause(), LogObjConstants.PAUSE);
        }
        if (baiduType.getMatchType() != newWord.getMatchType()) {
            return updateKeywordLog(newWord, newWord.getMatchType(), baiduType.getMatchType(), LogObjConstants.MATCH_TYPE);
        }
        if (!baiduType.getPcDestinationUrl().equals(newWord.getPcDestinationUrl())) {
            return updateKeywordLog(newWord, newWord.getPcDestinationUrl(), baiduType.getPcDestinationUrl(), LogObjConstants.PC_DES_URL);
        }
        if (!baiduType.getMobileDestinationUrl().equals(newWord.getMobileDestinationUrl())) {
            return updateKeywordLog(newWord, newWord.getMobileDestinationUrl(), baiduType.getMobileDestinationUrl(), LogObjConstants.MIB_DES_URL);
        }
        if (baiduType.getAdgroupId() != newWord.getAdgroupId()) {
            AdgroupDTO newAdgroup = adgroupDAO.findOne(newWord.getAdgroupId());
            AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
            return updateKeywordLog(newWord, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName(), LogObjConstants.MOVE_ADGROUP);
        }
        return null;
    }

//    @Override
//    public void reduceKeywordLog(KeywordDTO dbFindKeyWord) {
//        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
//        builder.setType(LogLevelConstants.KEYWORD)
//                .setOid(OptContentEnum.reBak)
//                .setName(dbFindKeyWord.getKeyword())
//                .setOptType(OptContentEnum.reBak)
//                .setOptComprehensiveID(dbFindKeyWord.getKeywordId());
//        getCamAdgroupInfoByLong(dbFindKeyWord.getAdgroupId(), builder);
//        save(builder.build());
//    }

    //    @Override
//    public void moveKeywordLog(KeywordDTO dbFindKeyWord, Object oldVal, Object newVal) {
//        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
//        builder.setType(LogLevelConstants.KEYWORD)
//                .setOid(OptContentEnum.KeyMove)
//                .setName(dbFindKeyWord.getKeyword())
//                .setOptType(OptContentEnum.KeyMove)
//                .setOptObj(LogObjConstants.MOVE_ADGROUP)
//                .setOptComprehensiveID(dbFindKeyWord.getKeywordId());
//        getCamAdgroupInfoByLong(dbFindKeyWord.getAdgroupId(), builder);
//        if (oldVal != null) {
//            builder.setBefore(oldVal.toString());
//        }
//        if (newVal != null) {
//            builder.setAfter(newVal.toString());
//        }
//        save(builder.build());
//    }
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
        return builder.build();
    }

    @Override
    public UserOperationLogDTO removeCampaign(CampaignDTO campaignType) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
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
    public UserOperationLogDTO updateCampaignAll(CampaignType newCampaign) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CampaignType baiduType = baiduApiService.getCampaignTypeById(newCampaign.getCampaignId());
        if (baiduType != null && newCampaign != null) {
            if (!Objects.equals(baiduType.getCampaignName(), newCampaign.getCampaignName())) {
                //TODO 计划名称修改的枚举需要重新添加 暂定用创意的修改表示
                return updateCampaign(newCampaign, newCampaign.getCampaignName(), baiduType.getCampaignName(), LogObjConstants.NAME);
            }
            if (!Objects.equals(baiduType.getPause(), newCampaign.getPause())) {
                return updateCampaign(newCampaign, newCampaign.getPause().toString(), baiduType.getPause().toString(), LogObjConstants.PAUSE);
            }
            if (!Objects.equals(baiduType.getBudget(), newCampaign.getBudget())) {
                return updateCampaign(newCampaign, newCampaign.getBudget().toString(), baiduType.getBudget().toString(), LogObjConstants.CAMPAIGN_BUDGET);
            }
            StringBuilder newSbSc = new StringBuilder();
            for (int i = 0; i < baiduType.getSchedule().size(); i++) {
                newSbSc.append(baiduType.getSchedule(i).toString());
            }
            StringBuilder oldSbSc = new StringBuilder();
            for (int i = 0; i < newCampaign.getSchedule().size(); i++) {
                oldSbSc.append(newCampaign.getSchedule(i).toString());
            }
            if (!Objects.equals(newSbSc.toString(), oldSbSc.toString())) {
                return updateCampaign(newCampaign, newSbSc.toString(), oldSbSc.toString(), LogObjConstants.CAMPAIGN_SCHEDULE);
            }
            if (!Objects.equals(baiduType.getDevice(), newCampaign.getDevice())) {
                return updateCampaign(newCampaign, newCampaign.getDevice().toString(), baiduType.getDevice().toString(), LogObjConstants.DEVICE);
            }
            if (!Objects.equals(baiduType.getExactNegativeWords().size(), newCampaign.getExactNegativeWords().size())) {
                StringBuilder oldSbWord = new StringBuilder();
                StringBuilder newSbWord = new StringBuilder();
                for (int i = 0; i < baiduType.getExactNegativeWords().size(); i++) {
                    oldSbWord.append(baiduType.getExactNegativeWord(i));
                }
                for (int i = 0; i < baiduType.getExactNegativeWords().size(); i++) {
                    newSbWord.append(newCampaign.getExactNegativeWord(i));
                }
                return updateCampaign(newCampaign, newSbWord.toString(), oldSbWord.toString(), LogObjConstants.EXA_WORD);
            }
            if (!Objects.equals(baiduType.getNegativeWords().size(), newCampaign.getNegativeWords().size())) {
                StringBuilder oldSbWord = new StringBuilder();
                StringBuilder newSbWord = new StringBuilder();
                for (int i = 0; i < baiduType.getNegativeWords().size(); i++) {
                    oldSbWord.append(baiduType.getNegativeWord(i));
                }
                for (int i = 0; i < baiduType.getNegativeWords().size(); i++) {
                    newSbWord.append(newCampaign.getNegativeWord(i));
                }
                return updateCampaign(newCampaign, newSbWord.toString(), oldSbWord.toString(), LogObjConstants.NEG_WORD);
            }
            if (!Objects.equals(baiduType.getExcludeIp().size(), newCampaign.getExcludeIp())) {
                return updateCampaign(newCampaign, newCampaign.getExcludeIp().toString(), baiduType.getExcludeIp().toString(), LogObjConstants.CAMPAIGN_EXCLUDEIP);
            }
            if (!Objects.equals(baiduType.getPriceRatio(), newCampaign.getPriceRatio())) {
                return updateCampaign(newCampaign, newCampaign.getPriceRatio().toString(), baiduType.getPriceRatio().toString(), LogObjConstants.MIB_FACTOR);
            }
            if (!Objects.equals(baiduType.getRegionTarget().size(), newCampaign.getRegionTarget().size())) {
                return updateCampaign(newCampaign, newCampaign.getRegionTarget().toString(), baiduType.getRegionTarget().toString(), LogObjConstants.CAMPAIGN_REGION);
            }
        }
        return null;
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
        getCampInfoByLongId(adgroupType.getCampaignId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO removeAdgroup(AdgroupDTO adgroupType) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AdgroupType baiduType = baiduApiService.getAdgroupTypeById(adgroupType.getAdgroupId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setType(UserOperationTypeEnum.DEL_ADGROUP.getValue())
                    .setOid(adgroupType.getAdgroupId())
                    .setName(adgroupType.getAdgroupName())
                    .setBefore(adgroupType.getAdgroupName());
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
    public UserOperationLogDTO updateAdgroupAll(AdgroupType newAdgroup) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AdgroupType baiduType = baiduApiService.getAdgroupTypeById(newAdgroup.getAdgroupId());
        if (baiduType != null && newAdgroup != null) {
            if (!Objects.equals(baiduType.getAdgroupName(), newAdgroup.getAdgroupName())) {
                return updateAdgroup(newAdgroup, newAdgroup.getAdgroupName(), baiduType.getAdgroupName(), LogObjConstants.NAME);
            }
            if (!Objects.equals(baiduType.getMaxPrice(), newAdgroup.getMaxPrice())) {
                return updateAdgroup(newAdgroup, newAdgroup.getMaxPrice().toString(), baiduType.getMaxPrice().toString(), LogObjConstants.PRICE);
            }
            if (!Objects.equals(baiduType.getExactNegativeWords().size(), newAdgroup.getExactNegativeWords().size())) {
                StringBuilder oldSb = new StringBuilder();
                StringBuilder newSb = new StringBuilder();
                for (int i = 0; i < baiduType.getExactNegativeWords().size(); i++) {
                    oldSb.append(baiduType.getExactNegativeWord(i));
                }
                for (int i = 0; i < baiduType.getExactNegativeWords().size(); i++) {
                    newSb.append(newAdgroup.getExactNegativeWord(i));
                }
                return updateAdgroup(newAdgroup, newSb.toString(), oldSb.toString(), LogObjConstants.EXA_WORD);
            }
            if (!Objects.equals(baiduType.getNegativeWords().size(), newAdgroup.getNegativeWords().size())) {
                StringBuilder oldSb = new StringBuilder();
                StringBuilder newSb = new StringBuilder();
                for (int i = 0; i < baiduType.getNegativeWords().size(); i++) {
                    oldSb.append(baiduType.getNegativeWord(i));
                }
                for (int i = 0; i < baiduType.getNegativeWords().size(); i++) {
                    newSb.append(newAdgroup.getNegativeWord(i));
                }
                return updateAdgroup(newAdgroup, newSb.toString(), oldSb.toString(), LogObjConstants.NEG_WORD);
            }
            if (!Objects.equals(baiduType.getCampaignId(), newAdgroup.getCampaignId())) {
                CampaignDTO oldCampaignDTO = campaignDAO.findOne(baiduType.getCampaignId());
                CampaignDTO newCampaignDTO = campaignDAO.findOne(newAdgroup.getCampaignId());
                return updateAdgroup(newAdgroup, newCampaignDTO.getCampaignName(), oldCampaignDTO.getCampaignName(), LogObjConstants.MOVE_CAMPAIGN);
            }
            if (!Objects.equals(baiduType.getPause(), newAdgroup.getPause())) {
                return updateAdgroup(newAdgroup, newAdgroup.getPause().toString(), baiduType.getPause().toString(), LogObjConstants.PAUSE);
            }
            //TODO 单元移动出价比例搜客暂时没有这个操作,如需单元出价比例操作,解除注释即可
//            if(!Objects.equals(baiduType.getAccuPriceFactor(),newAdgroup.getAccuPriceFactor())){
//                return updateAdgroup(newAdgroup,newAdgroup.getAccuPriceFactor().toString(),baiduType.getAccuPriceFactor().toString(),LogObjConstants.MIB_FACTOR,AdGroupEnum.mobilePriceFactor);
//            }
        }
        return null;
    }

    //-----------------------------------------创意-----------------------------------------------
    @Override
    public UserOperationLogDTO addCreative(CreativeType creativeType) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(UserOperationTypeEnum.ADD_CREATIVE.getValue())
                .setOid(creativeType.getCreativeId())
                .setName(creativeType.getTitle())
                .setAfter(creativeType.getTitle());
        getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO removeCreative(CreativeDTO creativeType) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CreativeType baiduType = baiduApiService.getCreativeTypeById(creativeType.getCreativeId());
        if (baiduType != null) {
            SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
            builder.setType(UserOperationTypeEnum.DEL_CREATIVE.getValue())
                    .setOid(creativeType.getCreativeId())
                    .setName(creativeType.getTitle())
                    .setBefore(creativeType.getTitle());
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
    public UserOperationLogDTO updateCreativeAll(CreativeType newCreative) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CreativeType baiduType = baiduApiService.getCreativeTypeById(newCreative.getCreativeId());
        if (baiduType != null && newCreative != null) {
            if (!baiduType.getTitle().equals(newCreative.getTitle())) {
                return updateCreative(newCreative, newCreative.getTitle(), baiduType.getTitle(), LogObjConstants.CREATIVE_TITLE);
            }
            if (!baiduType.getDescription1().equals(newCreative.getDescription1())) {
                return updateCreative(newCreative, newCreative.getDescription1(), baiduType.getDescription1(), LogObjConstants.CREATIVE_DESC1);
            }
            if (!baiduType.getDescription2().equals(newCreative.getDescription2())) {
                return updateCreative(newCreative, newCreative.getDescription2(), baiduType.getDescription2(), LogObjConstants.CREATIVE_DESC2);
            }
            if (!baiduType.getPcDestinationUrl().equals(newCreative.getPcDestinationUrl())) {
                return updateCreative(newCreative, newCreative.getPcDestinationUrl(), baiduType.getPcDestinationUrl(), LogObjConstants.PC_DES_URL);
            }
            if (!baiduType.getPcDisplayUrl().equals(newCreative.getPcDisplayUrl())) {
                return updateCreative(newCreative, newCreative.getPcDisplayUrl(), baiduType.getPcDisplayUrl(), LogObjConstants.PC_DIS_URL);
            }
            if (!baiduType.getMobileDestinationUrl().equals(newCreative.getMobileDestinationUrl())) {
                return updateCreative(newCreative, newCreative.getMobileDestinationUrl(), baiduType.getMobileDestinationUrl(), LogObjConstants.MIB_DES_URL);
            }
            if (!baiduType.getMobileDisplayUrl().equals(newCreative.getMobileDisplayUrl())) {
                return updateCreative(newCreative, newCreative.getMobileDisplayUrl(), baiduType.getMobileDisplayUrl(), LogObjConstants.MIB_DIS_URL);
            }
            if (baiduType.getPause() != newCreative.getPause()) {
                return updateCreative(newCreative, newCreative.getPause().toString(), baiduType.getPause().toString(), LogObjConstants.PAUSE);
            }
            if (baiduType.getDevicePreference() != newCreative.getDevicePreference()) {
                return updateCreative(newCreative, newCreative.getDevicePreference().toString(), baiduType.getDevicePreference().toString(), LogObjConstants.DEVICE);
            }
            if (baiduType.getAdgroupId() != newCreative.getAdgroupId()) {
                AdgroupDTO newAdgroup = adgroupDAO.findOne(newCreative.getAdgroupId());
                AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
                return updateCreative(newCreative, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName(), LogObjConstants.MOVE_ADGROUP);
            }
        }
        return null;
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

    }


    private final String KEYWORD_ID = "keywordId";
    private final String CAMPAIGN_ID = "campaignId";
    private final String ADGROUP_ID = "adgroupId";
    private final String CREATIVE_ID = "creativeId";

    @Override
    public void update(Object object, UserOperationLogLevelEnum level, String property, UserOperationTypeEnum userOperationTypeEnum, Object
            oldVal, Object newVal) {

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
                fillCampaignInfo(object, userOperationLogDTO);
                break;

            case ADGROUP:
                fillCampaignInfo(object, userOperationLogDTO);
                fillAdgroupInfo(object, userOperationLogDTO);
                break;
            default:
                break;
        }


        userOperationLogDAO.save(userOperationLogDTO);
    }

    private final String VALUE_EMPTY = "空";

    private void fillLayout(UserOperationTypeEnum userOperationTypeEnum, Object oldVal, Object newVal,
                            UserOperationLogDTO userOperationLogDTO) {
        String layout = UserOperationTypeEnum.layout(userOperationTypeEnum);

        layout = replaceValue(layout, "%old%", oldVal);
        layout = replaceValue(layout, "%new%", newVal);
        userOperationLogDTO.setText(layout);
    }


    private String replaceValue(String layout, String pattern, Object value) {
        String innerLayout = layout;
        if (value instanceof Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            innerLayout = innerLayout.replace(pattern, dateFormat.format(value));
        } else {
            if (value == null) {
                innerLayout = layout.replace("%old%", VALUE_EMPTY);
            } else {
                innerLayout = layout.replace("%old%", value.toString());
            }
        }

        return innerLayout;
    }

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
        Long campaignId = getCampaignId(object);
        if (campaignId == null)
            return;
        userOperationLogDTO.setCampgainId(campaignId);

        userOperationLogDTO.setCampaignName(getCampaignName(campaignId));
    }

    private void fillAdgroupInfo(Object object, UserOperationLogDTO userOperationLogDTO) {
        Long adgroupId = getAdgroupId(object);
        if (adgroupId == null)
            return;
        userOperationLogDTO.setAdgroupdId(adgroupId);

        userOperationLogDTO.setAdgroupName(getAdgroupName(adgroupId));
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

        if (field != null) {
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
            return field.getLong(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1l;
    }

    @Override
    public void newdel(Object object, UserOperationLogLevelEnum level, UserOperationTypeEnum
            userOperationTypeEnum) {
        UserOperationLogDTO userOperationLogDTO = new UserOperationLogDTO();
        userOperationLogDTO.setTime(System.currentTimeMillis());
        userOperationLogDTO.setType(userOperationTypeEnum.getValue());
        userOperationLogDTO.setUserId(AppContext.getAccountId());
        userOperationLogDTO.setUserName(AppContext.getUser());

        fillObjectInfo(object, userOperationLogDTO);
        fillLayout(userOperationTypeEnum, userOperationLogDTO);
        switch (level) {
            case CAMPAIGN:
                fillCampaignInfo(object, userOperationLogDTO);
                break;

            case ADGROUP:
                fillCampaignInfo(object, userOperationLogDTO);
                fillAdgroupInfo(object, userOperationLogDTO);
                break;
            default:
                break;
        }

        userOperationLogDAO.save(userOperationLogDTO);
    }

    private void fillLayout(UserOperationTypeEnum userOperationTypeEnum, UserOperationLogDTO
            userOperationLogDTO) {

        String layout = UserOperationTypeEnum.layout(userOperationTypeEnum);

        layout = replaceValue(layout, "%target%", userOperationLogDTO.getName());
        userOperationLogDTO.setText(layout);
    }

}

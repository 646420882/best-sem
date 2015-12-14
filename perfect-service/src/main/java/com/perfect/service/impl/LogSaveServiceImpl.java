package com.perfect.service.impl;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.commons.constants.LogLevelConstants;
import com.perfect.commons.constants.LogObjConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.filters.field.enums.*;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.log.util.LogOptUtil;
import com.perfect.service.LogSaveService;
import com.perfect.utils.OperationRecordModelBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author xiaowei
 * @title LogSaveServiceImpl
 * @package com.perfect.service.impl
 * @description
 * @update 2015年12月08日. 下午6:46
 */
@Service
public class LogSaveServiceImpl implements LogSaveService {

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private AccountManageDAO accountManageDAO;


    //TODO  .setOptContentId(KeyWordEnum.addWord)
    //TODO   .setOptType(OptContentEnum.Add)
    @Override
    public OperationRecordModel saveKeywordLog(KeywordType newWord) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.KEYWORD)
                .setOptContentId(KeyWordEnum.addWord)
                .setOptContent(newWord.getKeyword())
                .setOptType(OptContentEnum.Add)
                .setNewValue(newWord.getKeyword())
                .setOptObj(LogObjConstants.NAME);
        getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public OperationRecordModel updateKeyword(KeywordType newWord, Object newVal, Object oldVal, String optObj, Integer contentId) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.KEYWORD)
                .setOptContentId(contentId)
                .setOptContent(newWord.getKeyword())
                .setOptType(OptContentEnum.Edit)
                .setOptObj(optObj)
                .setOptComprehensiveID(newWord.getKeywordId() != null ? newWord.getKeywordId() : null);
        getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
        if (oldVal != null) {
            builder.setOldValue(oldVal.toString());
        }
        if (newVal != null) {
            builder.setNewValue(newVal.toString());
        }
        return builder.build();
    }

    @Override
    public OperationRecordModel deleteKeywordLog(KeywordDTO newWord) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        if (baiduType != null) {
            OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
            builder.setOptLevel(LogLevelConstants.KEYWORD)
                    .setOptContentId(OptContentEnum.delete)
                    .setOptType(KeyWordEnum.delWord)
                    .setOptContent(newWord.getKeyword())
                    .setOptComprehensiveID(newWord.getKeywordId())
                    .setOptContent(newWord.getKeyword());
            getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public OperationRecordModel updateKeywordAll(KeywordType newWord) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        if (baiduType != null && newWord != null) {
            if (baiduType.getPrice() != newWord.getPrice()) {
                return updateKeyword(newWord, newWord.getPrice(), baiduType.getPrice(), LogObjConstants.PRICE, KeyWordEnum.updIdea);
            }
            if (baiduType.getPause() != newWord.getPause()) {
                return updateKeyword(newWord, newWord.getPause(), baiduType.getPause(), LogObjConstants.PAUSE, KeyWordEnum.shelve);
            }
            if (baiduType.getMatchType() != newWord.getMatchType()) {
                return updateKeyword(newWord, newWord.getMatchType(), baiduType.getMatchType(), LogObjConstants.MATCH_TYPE, KeyWordEnum.updWordMatch);
            }
            if (!baiduType.getPcDestinationUrl().equals(newWord.getPcDestinationUrl())) {
                return updateKeyword(newWord, newWord.getPcDestinationUrl(), baiduType.getPcDestinationUrl(), LogObjConstants.PC_DES_URL, KeyWordEnum.updWordUrl);
            }
            if (!baiduType.getMobileDestinationUrl().equals(newWord.getMobileDestinationUrl())) {
                return updateKeyword(newWord, newWord.getMobileDestinationUrl(), baiduType.getMobileDestinationUrl(), LogObjConstants.MIB_DES_URL, KeyWordEnum.updWordMobileUrl);
            }
            if (baiduType.getAdgroupId() != newWord.getAdgroupId()) {
                AdgroupDTO newAdgroup = adgroupDAO.findOne(newWord.getAdgroupId());
                AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
                return updateKeyword(newWord, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName(), LogObjConstants.MOVE_ADGROUP, KeyWordEnum.wordTransfer);
            }
        }
        return null;
    }

//    @Override
//    public void reduceKeywordLog(KeywordDTO dbFindKeyWord) {
//        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
//        builder.setOptLevel(LogLevelConstants.KEYWORD)
//                .setOptContentId(OptContentEnum.reBak)
//                .setOptContent(dbFindKeyWord.getKeyword())
//                .setOptType(OptContentEnum.reBak)
//                .setOptComprehensiveID(dbFindKeyWord.getKeywordId());
//        getCamAdgroupInfoByLong(dbFindKeyWord.getAdgroupId(), builder);
//        save(builder.build());
//    }

    //    @Override
//    public void moveKeywordLog(KeywordDTO dbFindKeyWord, Object oldVal, Object newVal) {
//        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
//        builder.setOptLevel(LogLevelConstants.KEYWORD)
//                .setOptContentId(OptContentEnum.KeyMove)
//                .setOptContent(dbFindKeyWord.getKeyword())
//                .setOptType(OptContentEnum.KeyMove)
//                .setOptObj(LogObjConstants.MOVE_ADGROUP)
//                .setOptComprehensiveID(dbFindKeyWord.getKeywordId());
//        getCamAdgroupInfoByLong(dbFindKeyWord.getAdgroupId(), builder);
//        if (oldVal != null) {
//            builder.setOldValue(oldVal.toString());
//        }
//        if (newVal != null) {
//            builder.setNewValue(newVal.toString());
//        }
//        save(builder.build());
//    }
//------------------------------------计划-----------------------------------------
    @Override
    public OperationRecordModel addCampaign(CampaignType campaignType) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CAMPAIGN)
                .setOptContentId(CampaignEnum.addPlan)
                .setOptContent(campaignType.getCampaignName())
                .setOptType(OptContentEnum.Add)
                .setPlanName(campaignType.getCampaignName())
                .setNewValue(campaignType.getCampaignName());
        return builder.build();
    }

    @Override
    public OperationRecordModel removeCampaign(CampaignDTO campaignType) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CampaignType baiduType = baiduApiService.getCampaignTypeById(campaignType.getCampaignId());
        if (baiduType != null) {
            OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
            builder.setOptLevel(LogLevelConstants.CAMPAIGN)
                    .setOptContentId(CampaignEnum.delPlan)
                    .setOptContent(campaignType.getCampaignName())
                    .setOptType(OptContentEnum.delete)
                    .setPlanId(campaignType.getCampaignId())
                    .setPlanName(campaignType.getCampaignName())
                    .setOldValue(campaignType.getCampaignName())
                    .setOptComprehensiveID(campaignType.getCampaignId());
            return builder.build();
        }
        return null;
    }

    @Override
    public OperationRecordModel updateCampaign(CampaignType campaignType, String newValue, String oldValue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CAMPAIGN)
                .setOptType(OptContentEnum.Edit)
                .setPlanId(campaignType.getCampaignId())
                .setPlanName(campaignType.getCampaignName())
                .setOptContent(newValue)
                .setNewValue(newValue)
                .setOldValue(oldValue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(campaignType.getCampaignId());
        getCampInfoByLongId(campaignType.getCampaignId(), builder);
        return builder.build();
    }

    @Override
    public OperationRecordModel updateCampaignAll(CampaignType newCampaign) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CampaignType baiduType = baiduApiService.getCampaignTypeById(newCampaign.getCampaignId());
        if (baiduType != null && newCampaign != null) {
            if (!Objects.equals(baiduType.getCampaignName(), newCampaign.getCampaignName())) {
                //TODO 计划名称修改的枚举需要重新添加 暂定用创意的修改表示
                return updateCampaign(newCampaign, newCampaign.getCampaignName(), baiduType.getCampaignName(), LogObjConstants.NAME, CampaignEnum.editCampaignName);
            }
            if (!Objects.equals(baiduType.getPause(), newCampaign.getPause())) {
                return updateCampaign(newCampaign, newCampaign.getPause().toString(), baiduType.getPause().toString(), LogObjConstants.PAUSE, CampaignEnum.shelve);
            }
            if (!Objects.equals(baiduType.getBudget(), newCampaign.getBudget())) {
                return updateCampaign(newCampaign, newCampaign.getBudget().toString(), baiduType.getBudget().toString(), LogObjConstants.CAMPAIGN_BUDGET, CampaignEnum.dailyBudget);
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
                return updateCampaign(newCampaign, newSbSc.toString(), oldSbSc.toString(), LogObjConstants.CAMPAIGN_SCHEDULE, CampaignEnum.cycShelve);
            }
            if (!Objects.equals(baiduType.getDevice(), newCampaign.getDevice())) {
                return updateCampaign(newCampaign, newCampaign.getDevice().toString(), baiduType.getDevice().toString(), LogObjConstants.DEVICE, CampaignEnum.targetDevice);
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
                return updateCampaign(newCampaign, newSbWord.toString(), oldSbWord.toString(), LogObjConstants.EXA_WORD, CampaignEnum.accurateNegativeWord);
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
                return updateCampaign(newCampaign, newSbWord.toString(), oldSbWord.toString(), LogObjConstants.NEG_WORD, CampaignEnum.negativeWord);
            }
            if (!Objects.equals(baiduType.getExcludeIp().size(), newCampaign.getExcludeIp())) {
                return updateCampaign(newCampaign, newCampaign.getExcludeIp().toString(), baiduType.getExcludeIp().toString(), LogObjConstants.CAMPAIGN_EXCLUDEIP, CampaignEnum.ipExclude);
            }
            if (!Objects.equals(baiduType.getPriceRatio(), newCampaign.getPriceRatio())) {
                return updateCampaign(newCampaign, newCampaign.getPriceRatio().toString(), baiduType.getPriceRatio().toString(), LogObjConstants.MIB_FACTOR, CampaignEnum.mobilePrice);
            }
            if (!Objects.equals(baiduType.getRegionTarget().size(), newCampaign.getRegionTarget().size())) {
                return updateCampaign(newCampaign, newCampaign.getRegionTarget().toString(), baiduType.getRegionTarget().toString(), LogObjConstants.CAMPAIGN_REGION, CampaignEnum.zone);
            }
        }
        return null;
    }

    //----------------------------------单元------------------------------------------------
    @Override
    public OperationRecordModel addAdgroup(AdgroupType adgroupType) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.Add)
                .setOptContentId(AdGroupEnum.addUnit)
                .setOptContent(adgroupType.getAdgroupName())
                .setUnitName(adgroupType.getAdgroupName())
                .setNewValue(adgroupType.getAdgroupName());
        getCampInfoByLongId(adgroupType.getCampaignId(), builder);
        return builder.build();
    }

    @Override
    public OperationRecordModel removeAdgroup(AdgroupDTO adgroupType) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AdgroupType baiduType = baiduApiService.getAdgroupTypeById(adgroupType.getAdgroupId());
        if (baiduType != null) {
            OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
            builder.setOptLevel(LogLevelConstants.ADGROUP)
                    .setOptType(OptContentEnum.delete)
                    .setOptContentId(AdGroupEnum.delUnit)
                    .setOptContent(adgroupType.getAdgroupName())
                    .setOldValue(adgroupType.getAdgroupName())
                    .setOptComprehensiveID(adgroupType.getAdgroupId());
            getCampInfoByLongId(adgroupType.getCampaignId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public OperationRecordModel updateAdgroup(AdgroupType newAdgroup, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.Edit)
                .setOptContent(newvalue)
                .setNewValue(newvalue)
                .setOldValue(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(newAdgroup.getAdgroupId());
        getCamAdgroupInfoByLong(newAdgroup.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public OperationRecordModel updateAdgroupAll(AdgroupType newAdgroup) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AdgroupType baiduType = baiduApiService.getAdgroupTypeById(newAdgroup.getAdgroupId());
        if (baiduType != null && newAdgroup != null) {
            if (!Objects.equals(baiduType.getAdgroupName(), newAdgroup.getAdgroupName())) {
                return updateAdgroup(newAdgroup, newAdgroup.getAdgroupName(), baiduType.getAdgroupName(), LogObjConstants.NAME, AdGroupEnum.updUnitName);
            }
            if (!Objects.equals(baiduType.getMaxPrice(), newAdgroup.getMaxPrice())) {
                return updateAdgroup(newAdgroup, newAdgroup.getMaxPrice().toString(), baiduType.getMaxPrice().toString(), LogObjConstants.PRICE, AdGroupEnum.bidPrice);
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
                return updateAdgroup(newAdgroup, newSb.toString(), oldSb.toString(), LogObjConstants.EXA_WORD, AdGroupEnum.accurateNegativeWord);
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
                return updateAdgroup(newAdgroup, newSb.toString(), oldSb.toString(), LogObjConstants.NEG_WORD, AdGroupEnum.negativeWord);
            }
            if (!Objects.equals(baiduType.getCampaignId(), newAdgroup.getCampaignId())) {
                CampaignDTO oldCampaignDTO = campaignDAO.findOne(baiduType.getCampaignId());
                CampaignDTO newCampaignDTO = campaignDAO.findOne(newAdgroup.getCampaignId());
                return updateAdgroup(newAdgroup, newCampaignDTO.getCampaignName(), oldCampaignDTO.getCampaignName(), LogObjConstants.MOVE_CAMPAIGN, AdGroupEnum.ydCampaignName);
            }
            if (!Objects.equals(baiduType.getPause(), newAdgroup.getPause())) {
                return updateAdgroup(newAdgroup, newAdgroup.getPause().toString(), baiduType.getPause().toString(), LogObjConstants.PAUSE, AdGroupEnum.shelve);
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
    public OperationRecordModel addCreative(CreativeType creativeType) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.Add)
                .setOptContentId(CreativeEnum.addIdea)
                .setOptContent(creativeType.getTitle())
                .setNewValue(creativeType.getTitle());
        getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public OperationRecordModel removeCreative(CreativeDTO creativeType) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CreativeType baiduType = baiduApiService.getCreativeTypeById(creativeType.getCreativeId());
        if (baiduType != null) {
            OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
            builder.setOptLevel(LogLevelConstants.CREATIVE)
                    .setOptType(OptContentEnum.delete)
                    .setOptContentId(CreativeEnum.delIdea)
                    .setOptContent(creativeType.getTitle())
                    .setOldValue(creativeType.getTitle())
                    .setOptComprehensiveID(creativeType.getCreativeId());
            getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public OperationRecordModel updateCreative(CreativeType newCreative, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.Edit)
                .setOptContent(newvalue)
                .setNewValue(newvalue)
                .setOldValue(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(newCreative.getCreativeId());
        getCamAdgroupInfoByLong(newCreative.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public OperationRecordModel updateCreativeLogAll(CreativeType newCreative) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        CreativeType baiduType = baiduApiService.getCreativeTypeById(newCreative.getCreativeId());
        if (baiduType != null && newCreative != null) {
            if (!baiduType.getTitle().equals(newCreative.getTitle())) {
                return updateCreative(newCreative, newCreative.getTitle(), baiduType.getTitle(), LogObjConstants.CREATIVE_TITLE, CreativeEnum.updIdea);
            }
            if (!baiduType.getDescription1().equals(newCreative.getDescription1())) {
                return updateCreative(newCreative, newCreative.getDescription1(), baiduType.getDescription1(), LogObjConstants.CREATIVE_DESC1, CreativeEnum.updIdea);
            }
            if (!baiduType.getDescription2().equals(newCreative.getDescription2())) {
                return updateCreative(newCreative, newCreative.getDescription2(), baiduType.getDescription2(), LogObjConstants.CREATIVE_DESC2, CreativeEnum.updIdea);
            }
            if (!baiduType.getPcDestinationUrl().equals(newCreative.getPcDestinationUrl())) {
                return updateCreative(newCreative, newCreative.getPcDestinationUrl(), baiduType.getPcDestinationUrl(), LogObjConstants.PC_DES_URL, CreativeEnum.updIdea);
            }
            if (!baiduType.getPcDisplayUrl().equals(newCreative.getPcDisplayUrl())) {
                return updateCreative(newCreative, newCreative.getPcDisplayUrl(), baiduType.getPcDisplayUrl(), LogObjConstants.PC_DIS_URL, CreativeEnum.updIdea);
            }
            if (!baiduType.getMobileDestinationUrl().equals(newCreative.getMobileDestinationUrl())) {
                return updateCreative(newCreative, newCreative.getMobileDestinationUrl(), baiduType.getMobileDestinationUrl(), LogObjConstants.MIB_DES_URL, CreativeEnum.updIdea);
            }
            if (!baiduType.getMobileDisplayUrl().equals(newCreative.getMobileDisplayUrl())) {
                return updateCreative(newCreative, newCreative.getMobileDisplayUrl(), baiduType.getMobileDisplayUrl(), LogObjConstants.MIB_DIS_URL, CreativeEnum.updIdea);
            }
            if (baiduType.getPause() != newCreative.getPause()) {
                return updateCreative(newCreative, newCreative.getPause().toString(), baiduType.getPause().toString(), LogObjConstants.PAUSE, CreativeEnum.shelve);
            }
            if (baiduType.getDevicePreference() != newCreative.getDevicePreference()) {
                return updateCreative(newCreative, newCreative.getDevicePreference().toString(), baiduType.getDevicePreference().toString(), LogObjConstants.DEVICE, CreativeEnum.deviceOpt);
            }
            if (baiduType.getAdgroupId() != newCreative.getAdgroupId()) {
                AdgroupDTO newAdgroup = adgroupDAO.findOne(newCreative.getAdgroupId());
                AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
                return updateCreative(newCreative, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName(), LogObjConstants.MOVE_ADGROUP, CreativeEnum.updIdea);
            }
        }
        return null;
    }

    @Override
    public void getCamAdgroupInfoByLong(Long adgroupId, OperationRecordModelBuilder builder) {
        AdgroupDTO adgroupDTO = adgroupDAO.findOne(adgroupId);
        CampaignDTO campaignDTO = campaignDAO.findOne(adgroupDTO.getCampaignId());
        fillCommonData(builder, campaignDTO, adgroupDTO);
    }

    @Override
    public void getCampInfoByLongId(Long campaignId, OperationRecordModelBuilder builder) {
        CampaignDTO campaignDTO = campaignDAO.findOne(campaignId);
        fillCommonData(builder, campaignDTO);
    }


    @Override
    public Boolean saveLog(OperationRecordModel orm) {
        return LogOptUtil.saveLogs(orm).isSuccess();
    }


    private OperationRecordModelBuilder fillCommonData(OperationRecordModelBuilder builder, CampaignDTO campaignDTO, AdgroupDTO adgroupDTO) {
        return builder.setUserId(AppContext.getAccountId())
                .setUnitId(adgroupDTO.getAdgroupId())
                .setUnitName(adgroupDTO.getAdgroupName())
                .setPlanId(campaignDTO.getCampaignId())
                .setPlanName(campaignDTO.getCampaignName());

    }

    private OperationRecordModelBuilder fillCommonData(OperationRecordModelBuilder builder, CampaignDTO campaignDTO) {
        return builder.setUserId(AppContext.getAccountId())
                .setPlanId(campaignDTO.getCampaignId())
                .setPlanName(campaignDTO.getCampaignName());
    }


    private void save(OperationRecordModel model) {
        LogOptUtil.saveLogs(model);
    }
}

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
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.filters.field.enums.*;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.log.util.LogOptUtil;
import com.perfect.service.AdgroupService;
import com.perfect.service.LogSaveService;
import com.perfect.utils.OperationRecordModelBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;

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
    public OperationRecordModel updateKeywordLog(KeywordType newWord, Object newVal, Object oldVal, String optObj, Integer contentId) {
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
    public OperationRecordModel uploadLogWordUpdate(KeywordType newWord) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        KeywordType baiduType = baiduApiService.getKeywordTypeById(newWord.getKeywordId());
        if (baiduType.getPrice() != newWord.getPrice()) {
            return updateKeywordLog(newWord, newWord.getPrice(), baiduType.getPrice(), LogObjConstants.PRICE, KeyWordEnum.updIdea);
        }
        if (baiduType.getPause() != newWord.getPause()) {
            return updateKeywordLog(newWord, newWord.getPause(), baiduType.getPause(), LogObjConstants.PAUSE, KeyWordEnum.shelve);
        }
        if (baiduType.getMatchType() != newWord.getMatchType()) {
            return updateKeywordLog(newWord, newWord.getMatchType(), baiduType.getMatchType(), LogObjConstants.MATCH_TYPE, KeyWordEnum.updWordMatch);
        }
        if (!baiduType.getPcDestinationUrl().equals(newWord.getPcDestinationUrl())) {
            return updateKeywordLog(newWord, newWord.getPcDestinationUrl(), baiduType.getPcDestinationUrl(), LogObjConstants.PC_DES_URL, KeyWordEnum.updWordUrl);
        }
        if (!baiduType.getMobileDestinationUrl().equals(newWord.getMobileDestinationUrl())) {
            return updateKeywordLog(newWord, newWord.getMobileDestinationUrl(), baiduType.getMobileDestinationUrl(), LogObjConstants.MIB_DES_URL, KeyWordEnum.updWordMobileUrl);
        }
        if (baiduType.getAdgroupId() != newWord.getAdgroupId()) {
            AdgroupDTO newAdgroup = adgroupDAO.findOne(newWord.getAdgroupId());
            AdgroupDTO oldAdgroup = adgroupDAO.findOne(baiduType.getAdgroupId());
            return updateKeywordLog(newWord, newAdgroup.getAdgroupName(), oldAdgroup.getAdgroupName(), LogObjConstants.MOVE_ADGROUP, KeyWordEnum.wordTransfer);
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
    public OperationRecordModel updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CAMPAIGN)
                .setOptType(OptContentEnum.Edit)
                .setPlanId(campaignType.getCampaignId())
                .setPlanName(campaignType.getCampaignName())
                .setOptContent(newvalue)
                .setNewValue(newvalue)
                .setOldValue(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(campaignType.getCampaignId());
        return builder.build();
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
    public OperationRecordModel updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.Edit)
                .setUnitId(adgroupType.getCampaignId())
                .setUnitName(adgroupType.getAdgroupName())
                .setOptContent(newvalue)
                .setNewValue(newvalue)
                .setOldValue(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(adgroupType.getAdgroupId());
        return builder.build();
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
    public OperationRecordModel updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.Edit)
                .setOptContent(newvalue)
                .setNewValue(newvalue)
                .setOldValue(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(creativeType.getCreativeId());
        getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
        return builder.build();
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

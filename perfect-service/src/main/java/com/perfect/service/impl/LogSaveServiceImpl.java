package com.perfect.service.impl;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk_v4.sms.service.CampaignType;
import com.perfect.commons.constants.LogLevelConstants;
import com.perfect.commons.constants.LogObjConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
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


    //TODO  .setOptContentId(KeyWordEnum.addWord)
    //TODO   .setOptType(OptContentEnum.Add)
    @Override
    public void saveKeywordLog(KeywordDTO newKeywordDTO) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.KEYWORD)
                .setOptContentId(KeyWordEnum.addWord)
                .setOptContent(newKeywordDTO.getKeyword())
                .setOptType(OptContentEnum.Add)
                .setNewValue(newKeywordDTO.getKeyword())
                .setOptObj(LogObjConstants.NAME);
        getCamAdgroupInfoByLong(newKeywordDTO.getAdgroupId(), builder);
        save(builder.build());
    }

    @Override
    public void updateKeywordLog(KeywordDTO findKeyWord, Object oldVal, Object newVal, String optObj) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.KEYWORD)
                .setOptContentId(OptContentEnum.Edit)
                .setOptContent(findKeyWord.getKeyword())
                .setOptType(OptContentEnum.Edit)
                .setOptObj(optObj)
                .setOptComprehensiveID(findKeyWord.getKeywordId() != null ? findKeyWord.getKeywordId() : null);
        getCamAdgroupInfoByLong(findKeyWord.getAdgroupId(), builder);
        if (oldVal != null) {
            builder.setOldValue(oldVal.toString());
        }
        if (newVal != null) {
            builder.setNewValue(newVal.toString());
        }
        save(builder.build());
    }

    @Override
    public void deleteKeywordLog(KeywordDTO dbFindKeyWord) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.KEYWORD)
                .setOptContentId(OptContentEnum.delete)
                .setOptType(KeyWordEnum.delWord)
                .setOptContent(dbFindKeyWord.getKeyword())
                .setOptComprehensiveID(dbFindKeyWord.getKeywordId())
                .setOptContent(dbFindKeyWord.getKeyword());
        getCamAdgroupInfoByLong(dbFindKeyWord.getAdgroupId(), builder);
        save(builder.build());
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
                .setUserId(AppContext.getAccountId())
                .setPlanName(campaignType.getCampaignName())
                .setNewValue(campaignType.getCampaignName());
        return builder.build();
    }

    @Override
    public OperationRecordModel removeCampaign(CampaignType campaignType) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CAMPAIGN)
                .setOptContentId(CampaignEnum.delPlan)
                .setOptContent(campaignType.getCampaignName())
                .setOptType(OptContentEnum.delete)
                .setUserId(AppContext.getAccountId())
                .setPlanId(campaignType.getCampaignId())
                .setPlanName(campaignType.getCampaignName())
                .setOldValue(campaignType.getCampaignName())
                .setOptComprehensiveID(campaignType.getCampaignId());
        return builder.build();
    }

    @Override
    public OperationRecordModel updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CAMPAIGN)
                .setOptType(OptContentEnum.Edit)
                .setUserId(AppContext.getAccountId())
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
                .setUserId(AppContext.getAccountId())
                .setUnitName(adgroupType.getAdgroupName())
                .setNewValue(adgroupType.getAdgroupName());
        return builder.build();
    }

    @Override
    public OperationRecordModel removeAdgroup(AdgroupType adgroupType) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.delete)
                .setOptContentId(AdGroupEnum.delUnit)
                .setOptContent(adgroupType.getAdgroupName())
                .setUserId(AppContext.getAccountId())
                .setUnitId(adgroupType.getAdgroupId())
                .setUnitName(adgroupType.getAdgroupName())
                .setOldValue(adgroupType.getAdgroupName())
                .setOptComprehensiveID(adgroupType.getAdgroupId());
        return builder.build();
    }

    @Override
    public OperationRecordModel updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.Edit)
                .setUserId(AppContext.getAccountId())
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
                .setUserId(AppContext.getAccountId())
                .setNewValue(creativeType.getTitle());
        return builder.build();
    }

    @Override
    public OperationRecordModel removeCreative(CreativeType creativeType) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.delete)
                .setOptContentId(CreativeEnum.delIdea)
                .setOptContent(creativeType.getTitle())
                .setUserId(AppContext.getAccountId())
                .setOldValue(creativeType.getTitle())
                .setOptComprehensiveID(creativeType.getCreativeId());
        return builder.build();
    }

    @Override
    public OperationRecordModel updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String optObj, int contentid) {
        OperationRecordModelBuilder builder = OperationRecordModelBuilder.builder();
        builder.setOptLevel(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.Edit)
                .setUserId(AppContext.getAccountId())
                .setOptContent(newvalue)
                .setNewValue(newvalue)
                .setOldValue(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(creativeType.getCreativeId());
        return builder.build();
    }

    @Override
    public void getCamAdgroupInfoByLong(Long adgroupId, OperationRecordModelBuilder builder) {
        AdgroupDTO adgroupDTO = adgroupDAO.findOne(adgroupId);
        CampaignDTO campaignDTO = campaignDAO.findOne(adgroupDTO.getCampaignId());
        fillCommonData(builder, campaignDTO, adgroupDTO);
    }


    private OperationRecordModelBuilder fillCommonData(OperationRecordModelBuilder builder, CampaignDTO campaignDTO, AdgroupDTO adgroupDTO) {
        return builder.setUserId(AppContext.getAccountId())
                .setUnitId(adgroupDTO.getAdgroupId())
                .setUnitName(adgroupDTO.getAdgroupName())
                .setPlanId(campaignDTO.getCampaignId())
                .setPlanName(campaignDTO.getCampaignName());

    }


    private void save(OperationRecordModel model) {
        LogOptUtil.saveLogs(model);
    }
}

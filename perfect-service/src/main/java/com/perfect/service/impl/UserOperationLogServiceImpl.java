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
import com.perfect.dao.log.UserOperationLogDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.service.UserOperationLogService;
import com.perfect.utils.SystemLogDTOBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaowei
 * @title LogSaveServiceImpl
 * @package com.perfect.service.impl
 * @description
 * @update 2015年12月08日. 下午6:46
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
    private UserOperationLogDAO systemLogDAO;


    //TODO  .setOid(KeyWordEnum.addWord)
    //TODO   .setOptType(OptContentEnum.Add)
    @Override
    public UserOperationLogDTO saveKeywordLog(KeywordType newWord) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.KEYWORD)
                .setOid(KeyWordEnum.addWord)
                .setOptContent(newWord.getKeyword())
                .setOptType(OptContentEnum.Add)
                .setAfter(newWord.getKeyword())
                .setOptObj(LogObjConstants.NAME);
        getCamAdgroupInfoByLong(newWord.getAdgroupId(), builder);
        return builder.build();
    }

    @Override
    public UserOperationLogDTO updateKeywordLog(KeywordType newWord, Object newVal, Object oldVal, String optObj, Integer contentId) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.KEYWORD)
                .setOid(contentId)
                .setOptContent(newWord.getKeyword())
                .setOptType(OptContentEnum.Edit)
                .setOptObj(optObj)
                .setOptComprehensiveID(newWord.getKeywordId() != null ? newWord.getKeywordId() : null);
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
            builder.setType(LogLevelConstants.KEYWORD)
                    .setOid(OptContentEnum.delete)
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
    public UserOperationLogDTO uploadLogWordUpdate(KeywordType newWord) {
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
//        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
//        builder.setType(LogLevelConstants.KEYWORD)
//                .setOid(OptContentEnum.reBak)
//                .setOptContent(dbFindKeyWord.getKeyword())
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
//                .setOptContent(dbFindKeyWord.getKeyword())
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
        builder.setType(LogLevelConstants.CAMPAIGN)
                .setOid(CampaignEnum.addPlan)
                .setOptContent(campaignType.getCampaignName())
                .setOptType(OptContentEnum.Add)
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
            builder.setType(LogLevelConstants.CAMPAIGN)
                    .setOid(CampaignEnum.delPlan)
                    .setOptContent(campaignType.getCampaignName())
                    .setOptType(OptContentEnum.delete)
                    .setCampaignId(campaignType.getCampaignId())
                    .setCampaignName(campaignType.getCampaignName())
                    .setBefore(campaignType.getCampaignName())
                    .setOptComprehensiveID(campaignType.getCampaignId());
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String optObj, int contentid) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.CAMPAIGN)
                .setOptType(OptContentEnum.Edit)
                .setCampaignId(campaignType.getCampaignId())
                .setCampaignName(campaignType.getCampaignName())
                .setOptContent(newvalue)
                .setAfter(newvalue)
                .setBefore(oldvalue)
                .setOptObj(optObj)
                .setOid(contentid)
                .setOptComprehensiveID(campaignType.getCampaignId());
        return builder.build();
    }

    //----------------------------------单元------------------------------------------------
    @Override
    public UserOperationLogDTO addAdgroup(AdgroupType adgroupType) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.Add)
                .setOid(AdGroupEnum.addUnit)
                .setOptContent(adgroupType.getAdgroupName())
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
            builder.setType(LogLevelConstants.ADGROUP)
                    .setOptType(OptContentEnum.delete)
                    .setOid(AdGroupEnum.delUnit)
                    .setOptContent(adgroupType.getAdgroupName())
                    .setBefore(adgroupType.getAdgroupName())
                    .setOptComprehensiveID(adgroupType.getAdgroupId());
            getCampInfoByLongId(adgroupType.getCampaignId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String optObj, int contentid) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.ADGROUP)
                .setOptType(OptContentEnum.Edit)
                .setAdgroupId(adgroupType.getCampaignId())
                .setAdgroupName(adgroupType.getAdgroupName())
                .setOptContent(newvalue)
                .setAfter(newvalue)
                .setBefore(oldvalue)
                .setOptObj(optObj)
                .setOptContentId(contentid)
                .setOptComprehensiveID(adgroupType.getAdgroupId());
        return builder.build();
    }

    //-----------------------------------------创意-----------------------------------------------
    @Override
    public UserOperationLogDTO addCreative(CreativeType creativeType) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.Add)
                .setOid(CreativeEnum.addIdea)
                .setOptContent(creativeType.getTitle())
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
            builder.setType(LogLevelConstants.CREATIVE)
                    .setOptType(OptContentEnum.delete)
                    .setOid(CreativeEnum.delIdea)
                    .setOptContent(creativeType.getTitle())
                    .setBefore(creativeType.getTitle())
                    .setOptComprehensiveID(creativeType.getCreativeId());
            getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
            return builder.build();
        }
        return null;
    }

    @Override
    public UserOperationLogDTO updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String optObj, int contentid) {
        SystemLogDTOBuilder builder = SystemLogDTOBuilder.builder();
        builder.setType(LogLevelConstants.CREATIVE)
                .setOptType(OptContentEnum.Edit)
                .setOptContent(newvalue)
                .setAfter(newvalue)
                .setBefore(oldvalue)
                .setOptObj(optObj)
                .setOid(contentid)
                .setOptComprehensiveID(creativeType.getCreativeId());
        getCamAdgroupInfoByLong(creativeType.getAdgroupId(), builder);
        return builder.build();
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
        systemLogDAO.save(userOperationLogDTOs);
    }

}

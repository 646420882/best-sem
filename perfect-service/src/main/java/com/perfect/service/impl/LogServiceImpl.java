package com.perfect.service.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeader;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.constants.LogStatusConstant;
import com.perfect.dao.*;
import com.perfect.entity.*;
import com.perfect.service.LogService;
import com.perfect.service.SystemUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 日志服务
 * Created by vbzer_000 on 2014/9/2.
 */
@Component
public class LogServiceImpl implements LogService {

    @Resource
    private LogDAO logDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private CreativeDAO creativeDAO;

    @Resource
    private SystemUserService systemUserService;

    @Override
    public Map<String, Long> getStatiscs(String userName, Long accountId) {

        Iterable<LogEntity> resultList = logDAO.findAll(accountId);

        Map<String, Long> resultMap = new HashMap<>();

        for (LogEntity logEntity : resultList) {
            if (resultMap.containsKey(logEntity.getType())) {
                Long count = resultMap.get(logEntity.getType());
                resultMap.put(logEntity.getType(), ++count);
            } else {
                resultMap.put(logEntity.getType(), 1l);
            }
        }
        return resultMap;
    }

    @Override
    public boolean upload(String userName, Long accountId) {

        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(accountId);

        CommonService commonService = null;
        for (BaiduAccountInfoEntity accountInfoEntity : systemUserEntity.getBaiduAccountInfoEntities()) {
            if (accountInfoEntity.getId() == accountId) {
                try {
                    commonService = ServiceFactory.getInstance(accountInfoEntity.getBaiduUserName(), accountInfoEntity.getBaiduPassword(), accountInfoEntity.getToken(), null);

                } catch (ApiException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        if (commonService == null) {
            return false;
        }
        Iterable<LogEntity> resultList = logDAO.findAll(accountId);

        Map<String, List<LogEntity>> logMap = new HashMap<>();

        logMap.put(LogStatusConstant.ENTITY_CAMPAIGN, new ArrayList<LogEntity>());
        logMap.put(LogStatusConstant.ENTITY_ADGROUP, new ArrayList<LogEntity>());
        logMap.put(LogStatusConstant.ENTITY_KEYWORD, new ArrayList<LogEntity>());
        logMap.put(LogStatusConstant.ENTITY_CREATIVE, new ArrayList<LogEntity>());

        for (LogEntity logEntity : resultList) {
            logMap.get(logEntity.getType()).add(logEntity);
        }


        uploadCampaign(logMap.get(LogStatusConstant.ENTITY_CAMPAIGN), commonService);
        uploadAdgroup(logMap.get(LogStatusConstant.ENTITY_ADGROUP), commonService);
        uploadKeyword(logMap.get(LogStatusConstant.ENTITY_KEYWORD), commonService);
        uploadCreative(logMap.get(LogStatusConstant.ENTITY_CREATIVE), commonService);
        return false;
    }

    private void uploadCreative(List<LogEntity> logEntityList, CommonService commonService) {

    }

    private void uploadKeyword(List<LogEntity> logEntityList, CommonService commonService) {
        try {
            List<KeywordEntity> oidList = new ArrayList<>();
            List<String> toberemoved = new ArrayList<>();

            KeywordService keywordService = commonService.getService(KeywordService.class);

            AddKeywordRequest addKeywordRequest = new AddKeywordRequest();
            addKeywordRequest.setKeywordTypes(new ArrayList<KeywordType>());

            DeleteKeywordRequest deleteKeywordRequest = new DeleteKeywordRequest();
            deleteKeywordRequest.setKeywordIds(new ArrayList<Long>());

            UpdateKeywordRequest updateKeywordRequest = new UpdateKeywordRequest();
            updateKeywordRequest.setKeywordTypes(new ArrayList<KeywordType>());


            // 遍历所有计划日志,筛选出新增,删除,更新对象
            for (LogEntity logEntity : logEntityList) {
                if (logEntity.getOid() != null) {

                    KeywordEntity keywordEntity = keywordDAO.findByObjectId(logEntity.getOid());

                    if (keywordEntity == null) {
                        toberemoved.add(logEntity.getId());
                        continue;
                    }

                    KeywordType keywordType = new KeywordType();
                    BeanUtils.copyProperties(keywordEntity, keywordType);
                    addKeywordRequest.addKeywordType(keywordType);
                    oidList.add(keywordEntity);

                } else if (logEntity.getOpt() == LogStatusConstant.OPT_DELETE) {
                    deleteKeywordRequest.addKeywordId(logEntity.getBid());

                } else if (logEntity.getOpt() == LogStatusConstant.OPT_UPDATE) {
                    KeywordEntity keywordEntity = keywordDAO.findOne(logEntity.getBid());

                    if (keywordEntity == null) {
                        toberemoved.add(logEntity.getId());
                    }
                    KeywordType keywordType = new KeywordType();
                    BeanUtils.copyProperties(keywordEntity, keywordType);
                    updateKeywordRequest.addKeywordType(keywordType);

                }
            }

            doInsert(addKeywordRequest, keywordService, oidList);
            doDelete(deleteKeywordRequest, keywordService);
            doUpdate(updateKeywordRequest, keywordService);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void doUpdate(UpdateKeywordRequest updateKeywordRequest, KeywordService keywordService) {
        UpdateKeywordResponse response = keywordService.updateKeyword(updateKeywordRequest);
        if (response == null) {
            return;
        }
        List<Long> toberemoved = new ArrayList<>();

        List<KeywordType> keywordTypes = response.getKeywordTypes();
        for (KeywordType adgroupType : keywordTypes) {
            toberemoved.add(adgroupType.getKeywordId());
        }
        logDAO.deleteByBids(toberemoved);
    }

    private void doDelete(DeleteKeywordRequest deleteKeywordRequest, KeywordService keywordService) {
        DeleteKeywordResponse response = keywordService.deleteKeyword(deleteKeywordRequest);
        if (response == null) {
            return;
        }

        List<Long> targetList = deleteKeywordRequest.getKeywordIds();

        ResHeader resHeader = ResHeaderUtil.getJsonResHeader(false);
        if (response.getResult() == 1) {
            logDAO.deleteByBids(deleteKeywordRequest.getKeywordIds());
        } else {
            targetList.removeAll(getFailedIds());
        }
        removeFailed(targetList);
    }

    private void doInsert(AddKeywordRequest addKeywordRequest, KeywordService keywordService, List<KeywordEntity> oidList) {
        AddKeywordResponse response = keywordService.addKeyword(addKeywordRequest);
        if (response == null) {
            return;
        }
        List<String> toberemoved = new ArrayList<>();

        int i = 0;
        for (KeywordType keywordType : response.getKeywordTypes()) {
            Long keywordId = keywordType.getKeywordId();
            KeywordEntity keywordEntity = oidList.get(i++);
            if (keywordId == 0) {
                continue;
            }

            keywordEntity.setKeywordId(keywordId);

            keywordDAO.update(keywordEntity);

            toberemoved.add(keywordEntity.getId());
        }

        logDAO.deleteByIds(toberemoved);

    }

    private void uploadAdgroup(List<LogEntity> logEntityList, CommonService commonService) {
        try {
            List<AdgroupEntity> oidList = new ArrayList<>();
            List<String> toberemoved = new ArrayList<>();

            AdgroupService adgroupService = commonService.getService(AdgroupService.class);

            AddAdgroupRequest addAdgroupRequest = new AddAdgroupRequest();
            addAdgroupRequest.setAdgroupTypes(new ArrayList<AdgroupType>());

            DeleteAdgroupRequest deleteAdgroupRequest = new DeleteAdgroupRequest();
            deleteAdgroupRequest.setAdgroupIds(new ArrayList<Long>());

            UpdateAdgroupRequest updateAdgroupRequest = new UpdateAdgroupRequest();
            updateAdgroupRequest.setAdgroupTypes(new ArrayList<AdgroupType>());


            // 遍历所有计划日志,筛选出新增,删除,更新对象
            for (LogEntity logEntity : logEntityList) {
                if (logEntity.getOid() != null) {

                    AdgroupEntity adgroupEntity = adgroupDAO.findByObjId(logEntity.getOid());

                    if (adgroupEntity == null) {
                        toberemoved.add(logEntity.getId());
                        continue;
                    }

                    AdgroupType adgroupType = new AdgroupType();
                    BeanUtils.copyProperties(adgroupEntity, adgroupType);
                    addAdgroupRequest.addAdgroupType(adgroupType);
                    oidList.add(adgroupEntity);

                } else if (logEntity.getOpt() == LogStatusConstant.OPT_DELETE) {
                    deleteAdgroupRequest.addAdgroupId(logEntity.getBid());

                } else if (logEntity.getOpt() == LogStatusConstant.OPT_UPDATE) {
                    AdgroupEntity adgroupEntity = adgroupDAO.findOne(logEntity.getBid());

                    if (adgroupEntity == null) {
                        toberemoved.add(logEntity.getId());
                    }
                    AdgroupType adgroupType = new AdgroupType();
                    BeanUtils.copyProperties(adgroupEntity, adgroupType);
                    updateAdgroupRequest.addAdgroupType(adgroupType);

                }
            }

            doInsert(addAdgroupRequest, adgroupService, oidList);
            doDelete(deleteAdgroupRequest, adgroupService);
            doUpdate(updateAdgroupRequest, adgroupService);
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private void doUpdate(UpdateAdgroupRequest updateAdgroupRequest, AdgroupService adgroupService) {
        UpdateAdgroupResponse updateAdgroupResponse = adgroupService.updateAdgroup(updateAdgroupRequest);
        if (updateAdgroupResponse == null) {
            return;
        }
        List<Long> toberemoved = new ArrayList<>();

        List<AdgroupType> adgroupTypes = updateAdgroupResponse.getAdgroupTypes();
        for (AdgroupType adgroupType : adgroupTypes) {
            toberemoved.add(adgroupType.getCampaignId());
        }
        logDAO.deleteByBids(toberemoved);
    }

    private void doDelete(DeleteAdgroupRequest deleteAdgroupRequest, AdgroupService adgroupService) {
        DeleteAdgroupResponse response = adgroupService.deleteAdgroup(deleteAdgroupRequest);
        if (response == null) {
            return;
        }

        List<Long> targetList = deleteAdgroupRequest.getAdgroupIds();
        ResHeader resHeader = ResHeaderUtil.getJsonResHeader(false);
        if (resHeader.getFailures().isEmpty()) {
            logDAO.deleteByBids(deleteAdgroupRequest.getAdgroupIds());
        } else {
            targetList.removeAll(getFailedIds());
        }
        removeFailed(targetList);
    }

    private void doInsert(AddAdgroupRequest addAdgroupRequest, AdgroupService adgroupService, List<AdgroupEntity> oidList) {
        AddAdgroupResponse response = adgroupService.addAdgroup(addAdgroupRequest);
        if (response == null) {
            return;
        }
        List<String> toberemoved = new ArrayList<>();

        int i = 0;
        for (AdgroupType adgroupType : response.getAdgroupTypes()) {
            Long adgroupId = adgroupType.getAdgroupId();

            AdgroupEntity adgroupEntity = oidList.get(i++);

            if (adgroupId == 0) {
                continue;
            }

            adgroupEntity.setAdgroupId(adgroupId);

            adgroupDAO.update(adgroupEntity);

            keywordDAO.updateAdgroupIdByOid(adgroupEntity.getId(), adgroupId);
            creativeDAO.updateAdgroupIdByOid(adgroupEntity.getId(), adgroupId);

            toberemoved.add(adgroupEntity.getId());
        }

        logDAO.deleteByIds(toberemoved);

    }

    private boolean uploadCampaign(List<LogEntity> campaignList, CommonService commonService) {
        try {
            List<CampaignEntity> oidList = new ArrayList<>();
            List<String> toberemoved = new ArrayList<>();

            CampaignService campaignService = commonService.getService(CampaignService.class);

            AddCampaignRequest addCampaignRequest = new AddCampaignRequest();
            addCampaignRequest.setCampaignTypes(new ArrayList<CampaignType>());

            DeleteCampaignRequest deleteCampaignRequest = new DeleteCampaignRequest();
            deleteCampaignRequest.setCampaignIds(new ArrayList<Long>());

            UpdateCampaignRequest updateCampaignRequest = new UpdateCampaignRequest();
            updateCampaignRequest.setCampaignTypes(new ArrayList<CampaignType>());


            // 遍历所有计划日志,筛选出新增,删除,更新对象
            for (LogEntity logEntity : campaignList) {
                if (logEntity.getOid() != null) {

                    CampaignEntity campaignEntity = campaignDAO.findByObjectId(logEntity.getOid());

                    if (campaignEntity == null) {
                        toberemoved.add(logEntity.getId());
                        continue;
                    }

                    CampaignType campaignType = new CampaignType();
                    BeanUtils.copyProperties(campaignEntity, campaignType);
                    addCampaignRequest.addCampaignType(campaignType);
                    oidList.add(campaignEntity);

                } else if (logEntity.getOpt() == LogStatusConstant.OPT_DELETE) {
                    deleteCampaignRequest.addCampaignId(logEntity.getBid());

                } else if (logEntity.getOpt() == LogStatusConstant.OPT_UPDATE) {
                    CampaignEntity campaignEntity = campaignDAO.findOne(logEntity.getBid());

                    if (campaignEntity == null) {
                        toberemoved.add(logEntity.getId());
                    }
                    CampaignType campaignType = new CampaignType();
                    BeanUtils.copyProperties(campaignEntity, campaignType);
                    updateCampaignRequest.addCampaignType(campaignType);
                }
            }

            doInsert(addCampaignRequest, campaignService, oidList);
            doDelete(deleteCampaignRequest, campaignService);
            doUpdate(updateCampaignRequest, campaignService);
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 计划更新方法
     *
     * @param updateCampaignRequest
     * @param campaignService
     */
    private void doUpdate(UpdateCampaignRequest updateCampaignRequest, CampaignService campaignService) {

        UpdateCampaignResponse updateCampaignResponse = campaignService.updateCampaign(updateCampaignRequest);
        if (updateCampaignResponse == null) {
            return;
        }
        List<Long> toberemoved = new ArrayList<>();

        List<CampaignType> campaignTypes = updateCampaignResponse.getCampaignTypes();
        for (CampaignType campaignType : campaignTypes) {
            toberemoved.add(campaignType.getCampaignId());
        }
        logDAO.deleteByBids(toberemoved);
    }

    private void doDelete(DeleteCampaignRequest deleteCampaignRequest, CampaignService campaignService) {
        DeleteCampaignResponse response = campaignService.deleteCampaign(deleteCampaignRequest);
        if (response == null) {
            return;
        }

        List<Long> targetList = deleteCampaignRequest.getCampaignIds();
        if (response.getResult() == 1) {
            logDAO.deleteByBids(deleteCampaignRequest.getCampaignIds());
        } else {
            // TODO 删除失败的操作
            targetList.removeAll(getFailedIds());
        }
        removeFailed(targetList);
    }

    private void removeFailed(List<Long> targetList) {
        logDAO.deleteByBids(targetList);
    }

    /**
     * 删除失败之后的ID
     *
     * @return
     */
    private Collection<Long> getFailedIds() {
        ResHeader resHeader = ResHeaderUtil.getJsonResHeader(false);
        resHeader.getFailures();
        return Collections.EMPTY_LIST;
    }

    /**
     * 計劃新增方法
     *
     * @param addCampaignRequest
     * @param campaignService
     * @param oidList
     * @return
     */
    private List<String> doInsert(AddCampaignRequest addCampaignRequest, CampaignService campaignService, List<CampaignEntity> oidList) {
        AddCampaignResponse campaignResponse = campaignService.addCampaign(addCampaignRequest);
        if (campaignResponse == null) {
            return null;
        }
        List<String> toberemoved = new ArrayList<>();

        int i = 0;
        for (CampaignType campaignType : campaignResponse.getCampaignTypes()) {
            Long campaignId = campaignType.getCampaignId();

            CampaignEntity campaignEntity = oidList.get(i++);

            if (campaignId == 0) {
                continue;
            }

            campaignEntity.setCampaignId(campaignId);

            campaignDAO.update(campaignEntity);

            adgroupDAO.updateCampaignIdByOid(campaignEntity.getId(), campaignId);
            toberemoved.add(campaignEntity.getId());
        }

        logDAO.deleteByIds(toberemoved);

        return toberemoved;
    }

}

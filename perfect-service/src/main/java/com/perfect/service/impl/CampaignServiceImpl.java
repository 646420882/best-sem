package com.perfect.service.impl;

import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dto.backup.CampaignBackUpDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.CampaignService;
import com.perfect.utils.CharsetUtils;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SubDong on 2014/11/26.
 */
@Repository("campaignService")
public class CampaignServiceImpl implements CampaignService {

    @Resource
    private CampaignDAO campaignDAO;
    @Resource
    private AccountManageDAO accountManageDAO;


    @Override
    public CampaignDTO findOne(Long campaignId) {
        return campaignDAO.findOne(campaignId);
    }

    @Override
    public Iterable<CampaignDTO> findAll() {
        return campaignDAO.findAll();
    }

    @Override
    public List<CampaignDTO> findHasLocalStatus() {
        return campaignDAO.findHasLocalStatus();
    }

    @Override
    public List<CampaignDTO> findHasLocalStatusByStrings(List<String> cids) {
        return campaignDAO.findHasLocalStatusByStrings(cids);
    }

    @Override
    public List<CampaignDTO> findHasLocalStatusByLongs(List<Long> cids) {
        return campaignDAO.findHasLocalStatusByLongs(cids);
    }

    @Override
    public Iterable<CampaignDTO> findAllDownloadCampaign() {
        return campaignDAO.findAllDownloadCampaign();
    }

    @Override
    public void insertAll(List<CampaignDTO> list) {
        campaignDAO.insertAll(list);
    }

    @Override
    public void update(CampaignDTO campaignEntity) {
        campaignDAO.update(campaignEntity);
    }

    @Override
    public void updateCampaign(CampaignDTO dto) {
        CampaignDTO newCampaignDTO;

        if (dto.getCampaignId() == null) {
            newCampaignDTO = campaignDAO.findByObjectId(dto.getId());
        } else {
            newCampaignDTO = campaignDAO.findByLongId(dto.getCampaignId());
        }

        CampaignDTO backUpDTO = new CampaignDTO();
        BeanUtils.copyProperties(newCampaignDTO, backUpDTO);

        if (newCampaignDTO.getCampaignId() == null) {
            newCampaignDTO.setLocalStatus(1);
        } else {
            newCampaignDTO.setLocalStatus(2);
        }

        if (dto.getCampaignName() != null) {
            newCampaignDTO.setCampaignName(dto.getCampaignName());
        }

        campaignDAO.updateByMongoId(newCampaignDTO, backUpDTO);

    }

    @Override
    public void delete(Long campaignId) {
        campaignDAO.delete(campaignId);
    }

    @Override
    public void deleteByIds(List<Long> campaignIds) {
        campaignDAO.deleteByIds(campaignIds);
    }

    @Override
    public PagerInfo findByPageInfo(Long accountId, int pageSize, int pageNo) {
        return campaignDAO.findByPageInfo(accountId, pageSize, pageNo);
    }

    @Override
    public CampaignDTO findByObjectId(String oid) {
        return campaignDAO.findByObjectId(oid);
    }

    @Override
    public void softDel(long id) {
        campaignDAO.softDel(id);
    }

    @Override
    public void deleteByMongoId(String id) {
        campaignDAO.deleteByMongoId(id);
    }

    @Override
    public void save(CampaignDTO dto) {
        campaignDAO.save(dto);
    }

    @Override
    public void updateByMongoId(CampaignDTO newCampaign, CampaignDTO campaignEntity) {
        campaignDAO.updateByMongoId(newCampaign, campaignEntity);
    }

    @Override
    public List<String> getCampaignStrIdByCampaignLongId(List<Long> campaignIds) {
        return campaignDAO.getCampaignStrIdByCampaignLongId(campaignIds);
    }

    @Override
    public String insertReturnId(CampaignDTO campaignEntity) {
        return campaignDAO.insertReturnId(campaignEntity);
    }

    @Override
    public List<CampaignDTO> uploadAdd(String cid) {
        List<CampaignDTO> returnDtos = new ArrayList<>();
        CampaignDTO dto = campaignDAO.findByObjectId(cid);
        CampaignType campaignType = new CampaignType();
        if (CharsetUtils.getChar(dto.getCampaignName()) < 30) {
            campaignType.setCampaignName(dto.getCampaignName());
            if (dto.getBudget() == null) {
                campaignType.setBudget(null);
            } else {
                if (dto.getBudget() <= 49) {
                    campaignType.setBudget(null);
                } else {
                    campaignType.setBudget(dto.getBudget());
                }
            }
            campaignType.setRegionTarget(dto.getRegionTarget());
            campaignType.setExcludeIp(dto.getExcludeIp());
            campaignType.setShowProb(dto.getShowProb());
            campaignType.setNegativeWords(dto.getNegativeWords());
            campaignType.setExactNegativeWords(dto.getExactNegativeWords());
            List<ScheduleType> scheduleTypes = ObjectUtils.convert(dto.getSchedule(), ScheduleType.class);
            campaignType.setSchedule(scheduleTypes);
            List<OfflineTimeType> offlineTimeTypes = ObjectUtils.convert(dto.getBudgetOfflineTime(), OfflineTimeType.class);
            campaignType.setBudgetOfflineTime(offlineTimeTypes);
            campaignType.setShowProb(dto.getShowProb());
            campaignType.setDevice(dto.getDevice());
            if (dto.getDevice() != null && dto.getDevice() == 0) {
                if (dto.getPriceRatio() >= 0.1 && dto.getPriceRatio() <= 10.0) {
                    campaignType.setPriceRatio(dto.getPriceRatio());
                } else {
                    campaignType.setPriceRatio(1.0);
                }
            } else {
                campaignType.setPriceRatio(1.0);
            }
            campaignType.setPause(dto.getPause());
            campaignType.setStatus(dto.getStatus());
            campaignType.setIsDynamicCreative(dto.getIsDynamicCreative());
        }
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            com.perfect.autosdk.sms.v3.CampaignService campaignService = commonService.getService(com.perfect.autosdk.sms.v3.CampaignService.class);
            AddCampaignRequest addCampaignRequest = new AddCampaignRequest();
            addCampaignRequest.setCampaignTypes(Arrays.asList(campaignType));
            AddCampaignResponse addCampaignResponse = campaignService.addCampaign(addCampaignRequest);
            List<CampaignType> campaignTypes = addCampaignResponse.getCampaignTypes();
            campaignTypes.stream().filter(s -> s.getCampaignId() != null).forEach(s -> {
                CampaignDTO campaignDTO = new CampaignDTO();
                campaignDTO.setCampaignId(s.getCampaignId());
                campaignDTO.setStatus(s.getStatus());
                campaignDTO.setPause(s.getPause());
                returnDtos.add(campaignDTO);
            });
            return returnDtos;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return returnDtos;
    }

    @Override
    public int uploadDel(List<Long> campaignIds) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            com.perfect.autosdk.sms.v3.CampaignService campaignService = commonService.getService(com.perfect.autosdk.sms.v3.CampaignService.class);
            DeleteCampaignRequest deleteCampaignRequest = new DeleteCampaignRequest();
            deleteCampaignRequest.setCampaignIds(campaignIds);
            DeleteCampaignResponse deleteCampaignResponse = campaignService.deleteCampaign(deleteCampaignRequest);
            int result = deleteCampaignResponse.getResult();
            if (result != 0) {
                campaignIds.stream().forEach(s -> campaignDAO.deleteByCampaignId(s));
                return result;
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Long> uploadUpdate(List<Long> campaignIds) {
        List<CampaignType> campaignTypeList = new ArrayList<>();
        List<Long> returnCampaignIds = new ArrayList<>();
        List<CampaignDTO> dtos = new ArrayList<>();
        campaignIds.stream().forEach(s -> dtos.add(campaignDAO.findByLongId(s)));
        for (CampaignDTO dto : dtos) {
            CampaignType campaignType = new CampaignType();
            campaignType.setCampaignId(dto.getCampaignId());
            campaignType.setCampaignName(dto.getCampaignName());
            campaignType.setBudget(dto.getBudget());
            campaignType.setRegionTarget(dto.getRegionTarget());
            campaignType.setExcludeIp(dto.getExcludeIp());
            campaignType.setNegativeWords(dto.getNegativeWords());
            campaignType.setExactNegativeWords(dto.getExactNegativeWords());
            List<ScheduleType> scheduleTypes = ObjectUtils.convert(dto.getSchedule(), ScheduleType.class);
            campaignType.setSchedule(scheduleTypes);
            List<OfflineTimeType> offlineTimeTypes = ObjectUtils.convert(dto.getBudgetOfflineTime(), OfflineTimeType.class);
            campaignType.setBudgetOfflineTime(offlineTimeTypes);
            campaignType.setShowProb(dto.getShowProb());
            campaignType.setDevice(dto.getDevice());
            campaignType.setPriceRatio(dto.getPriceRatio());
            campaignType.setPause(dto.getPause());
            campaignType.setStatus(dto.getStatus());
            campaignType.setIsDynamicCreative(dto.getIsDynamicCreative());
            campaignTypeList.add(campaignType);
        }
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            com.perfect.autosdk.sms.v3.CampaignService campaignService = commonService.getService(com.perfect.autosdk.sms.v3.CampaignService.class);
            UpdateCampaignRequest updateCampaignRequest = new UpdateCampaignRequest();
            updateCampaignRequest.setCampaignTypes(campaignTypeList);
            UpdateCampaignResponse updateCampaignResponse = campaignService.updateCampaign(updateCampaignRequest);
            List<CampaignType> campaignTypes = updateCampaignResponse.getCampaignTypes();
            campaignTypes.stream().forEach(s -> returnCampaignIds.add(s.getCampaignId()));
            return returnCampaignIds;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return returnCampaignIds;
    }

    @Override
    public void update(CampaignDTO dto, String objId) {
        campaignDAO.update(dto, objId);
    }

    @Override
    public void updateRemoveLs(List<String> afterUpdateStr) {
        campaignDAO.updateRemoveLs(afterUpdateStr);
    }

    @Override
    public List<CampaignDTO> getOperateCamp() {
        return campaignDAO.getOperateCamp();
    }

    @Override
    public void deleteByCampaignId(Long cid) {
        campaignDAO.deleteByCampaignId(cid);
    }
}

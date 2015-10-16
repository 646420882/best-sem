package com.perfect.service.impl;

import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.*;
import com.perfect.service.AdgroupService;
import com.perfect.service.CampaignService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-26.
 * 2014-12-2 refactor
 */
@Service("adgroupService")
public class AdgroupServiceImpl implements AdgroupService {
    private static Integer OBJ_SIZE = 18;//判断百度id跟本地id长度大小

    @Resource
    private AdgroupDAO adgroupDAO;
    @Resource
    AccountManageDAO accountManageDAO;

    @Resource
    CampaignDAO campaignDAO;

    @Resource
    private CampaignService campaignService;

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        return adgroupDAO.getAdgroupByCampaignId(campaignId, params, skip, limit);
    }

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId) {
        return adgroupDAO.getAdgroupByCampaignObjId(campaignObjId);
    }

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignObjId) {
        return adgroupDAO.getAdgroupByCampaignId(campaignObjId);
    }

    @Override
    public Iterable<AdgroupDTO> findAll() {
        return adgroupDAO.findAll();
    }

    @Override
    public List<AdgroupDTO> findHasLocalStatus() {
        return adgroupDAO.findHasLocalStatus();
    }

    @Override
    public List<AdgroupDTO> findHasLocalStatusStr(List<CampaignDTO> campaignDTOStr) {
        List<String> strs = new ArrayList<>();
        for (CampaignDTO camp : campaignDTOStr) {
            if (camp.getCampaignId() == null) {
                strs.add(camp.getId());
            }
        }
        return adgroupDAO.findHasLocalStatusStr(strs);
    }

    @Override
    public List<AdgroupDTO> findHasLocalStatusLong(List<CampaignDTO> campaignDTOLong) {
        List<Long> longs = new ArrayList<>();
        for (CampaignDTO camp : campaignDTOLong) {
            if (camp.getCampaignId() != null) {
                longs.add(camp.getCampaignId());
            }
        }
        return adgroupDAO.findHasLocalStatusLong(longs);
    }

    @Override
    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        return adgroupDAO.getAdgroupIdByCampaignId(campaignId);
    }

    @Override
    public List<Long> getAdgroupIdByCampaignObj(String campaignId) {
        return adgroupDAO.getAdgroupIdByCampaignObj(campaignId);
    }

    @Override
    public AdgroupDTO findOne(Long id) {
        return adgroupDAO.findOne(id);
    }

    @Override
    public List<AdgroupDTO> find(Map<String, Object> params, int skip, int limit) {
        return adgroupDAO.find(params, skip, limit);
    }

    @Override
    public void insertAll(List<AdgroupDTO> entities) {
        adgroupDAO.save(entities);
    }

    @Override
    public void update(AdgroupDTO adgroupDTO) {
        AdgroupBackupDTO adgroupBackupDTO = new AdgroupBackupDTO();
        BeanUtils.copyProperties(adgroupDTO, adgroupBackupDTO);
        adgroupDAO.update(adgroupDTO, adgroupBackupDTO);
    }

    @Override
    public void updateAdgroup(AdgroupDTO dto) {
        AdgroupDTO adgroupDTO;

        if (dto.getAdgroupId() == null) {
            adgroupDTO = adgroupDAO.findOne(dto.getAdgroupId());
        } else {
            adgroupDTO = adgroupDAO.findByObjId(dto.getId());
        }

        AdgroupBackupDTO adgroupBackupDTO = new AdgroupBackupDTO();
        BeanUtils.copyProperties(adgroupDTO, adgroupBackupDTO);

        if (adgroupDTO.getAdgroupId() == null) {
            adgroupDTO.setLocalStatus(1);
        } else {
            adgroupDTO.setLocalStatus(2);
        }

        if (dto.getAdgroupName() != null) {
            adgroupDTO.setAdgroupName(dto.getAdgroupName());
        }
        if (dto.getMaxPrice() != null) {
            adgroupDTO.setMaxPrice(dto.getMaxPrice());
        }
        if (dto.getNegativeWords() != null) {
            adgroupDTO.setNegativeWords(dto.getNegativeWords());
        }
        if (dto.getExactNegativeWords() != null) {
            adgroupDTO.setExactNegativeWords(dto.getExactNegativeWords());
        }

        adgroupDAO.update(adgroupDTO, adgroupBackupDTO);


    }

    @Override
    public void delete(Long id) {
        adgroupDAO.delete(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        adgroupDAO.deleteByIds(ids);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize) {
        return adgroupDAO.findByPagerInfo(params, nowPage, pageSize);
    }

    @Override
    public Object insertOutId(AdgroupDTO adgroupEntity) {
        return adgroupDAO.insertOutId(adgroupEntity);
    }

    @Override
    public void deleteByObjId(String oid) {
        adgroupDAO.deleteByObjId(oid);
    }

    @Override
    public void deleteByObjId(Long adgroupId) {
        adgroupDAO.deleteByObjId(adgroupId);
    }

    @Override
    public AdgroupDTO findByObjId(String oid) {
        return adgroupDAO.findByObjId(oid);
    }

    @Override
    public void updateByObjId(AdgroupDTO adgroupEntity) {
        adgroupDAO.updateByObjId(adgroupEntity);
    }

    @Override
    public void update(AdgroupDTO adgroupEntity, AdgroupDTO bakAdgroupEntity) {
        adgroupDAO.update(adgroupEntity, bakAdgroupEntity);
    }

    @Override
    public void delBack(Long oid) {
        adgroupDAO.delBack(oid);
    }

    @Override
    public AdgroupDTO fndEntity(Map<String, Object> params) {
        return adgroupDAO.fndEntity(params);
    }

    @Override
    public void save(AdgroupDTO adgroupDTO) {
        adgroupDAO.insert(adgroupDTO);
    }

    @Override
    public double findPriceRatio(Long cid) {
        return adgroupDAO.findPriceRatio(cid);
    }

    @Override
    public List<AdgroupDTO> uploadAdd(List<String> aids) {
        List<AdgroupDTO> returnDto = new ArrayList<>();
        List<AdgroupType> adgroupTypes = new ArrayList<>();
        aids.stream().forEach(s -> {
            AdgroupDTO dto = adgroupDAO.findByObjId(s);
            CampaignDTO campaignDTO = campaignDAO.findByLongId(dto.getCampaignId());
            if (campaignDTO.getCampaignId() != null) {//判断如果需要上传的单元的计划编号没有更新，则无法更新该条记录
                AdgroupType adgroupType = new AdgroupType();
                adgroupType.setAdgroupName(dto.getAdgroupName());
                adgroupType.setMaxPrice(dto.getMaxPrice());
                adgroupType.setCampaignId(dto.getCampaignId());
                adgroupType.setPause(dto.getPause());
                adgroupType.setNegativeWords(dto.getNegativeWords());
                adgroupType.setExactNegativeWords(dto.getExactNegativeWords());
                adgroupTypes.add(adgroupType);
            }
        });
        if (adgroupTypes.size() > 0) {
            BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
            try {
                com.perfect.autosdk.sms.v3.AdgroupService adgroupService = commonService.getService(com.perfect.autosdk.sms.v3.AdgroupService.class);
                AddAdgroupRequest addAdgroupRequest = new AddAdgroupRequest();
                addAdgroupRequest.setAdgroupTypes(adgroupTypes);
                AddAdgroupResponse addAdgroupResponse = adgroupService.addAdgroup(addAdgroupRequest);
                List<AdgroupType> returnAdgroupType = addAdgroupResponse.getAdgroupTypes();
                returnAdgroupType.stream().filter(s -> s.getAdgroupId() != null).forEach(s -> {
                    AdgroupDTO dto = new AdgroupDTO();
                    dto.setAdgroupId(s.getAdgroupId());
                    dto.setStatus(s.getStatus());
                    dto.setPause(s.getPause());
                    returnDto.add(dto);
                });
                return returnDto;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return returnDto;
    }

    @Override
    public void update(String oid, AdgroupDTO dto) {
        adgroupDAO.update(oid, dto);
    }

    @Override
    public String uploadDel(Long aid) {
        String result = null;
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());

        try {
            com.perfect.autosdk.sms.v3.AdgroupService adgroupService = commonService.getService(com.perfect.autosdk.sms.v3.AdgroupService.class);
            DeleteAdgroupRequest adgroupRequest = new DeleteAdgroupRequest();
            adgroupRequest.setAdgroupIds(new ArrayList<Long>() {{
                add(aid);
            }});
            DeleteAdgroupResponse adgroupResponse = adgroupService.deleteAdgroup(adgroupRequest);
            result = adgroupResponse.getResponse();
            return result;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AdgroupDTO> uploadUpdate(List<Long> aid) {
        List<AdgroupDTO> returnAdgroupDTO = new ArrayList<>();
        List<AdgroupType> adgroupTypes = new ArrayList<>();
        aid.stream().forEach(s -> {
            AdgroupDTO adgroupDTOFind = adgroupDAO.findOne(s);
            AdgroupType adgroupType = new AdgroupType();
            adgroupType.setAdgroupId(adgroupDTOFind.getAdgroupId());
            adgroupType.setAdgroupName(adgroupDTOFind.getAdgroupName());
            adgroupType.setMaxPrice(adgroupDTOFind.getMaxPrice());
            adgroupType.setNegativeWords(adgroupDTOFind.getNegativeWords());
            adgroupType.setExactNegativeWords(adgroupDTOFind.getExactNegativeWords());
            adgroupType.setPause(adgroupDTOFind.getPause());
            adgroupType.setStatus(adgroupDTOFind.getStatus());
            adgroupTypes.add(adgroupType);
        });
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            com.perfect.autosdk.sms.v3.AdgroupService adgroupService = commonService.getService(com.perfect.autosdk.sms.v3.AdgroupService.class);
            UpdateAdgroupRequest adgroupRequest = new UpdateAdgroupRequest();
            adgroupRequest.setAdgroupTypes(adgroupTypes);
            UpdateAdgroupResponse updateCampaignResponse = adgroupService.updateAdgroup(adgroupRequest);
            List<AdgroupType> returnAdgroupTypes = updateCampaignResponse.getAdgroupTypes();
            returnAdgroupTypes.stream().filter(s -> s.getAdgroupId() != null).forEach(s -> {
                AdgroupDTO adgroupDTO = new AdgroupDTO();
                adgroupDTO.setStatus(s.getStatus());
                adgroupDTO.setPause(s.getPause());
                returnAdgroupDTO.add(adgroupDTO);
            });
            return returnAdgroupDTO;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return returnAdgroupDTO;
    }

    @Override
    public List<AdgroupDTO> uploadAddByUp(String aid) {
        List<AdgroupDTO> returnAdgroupDto = new ArrayList<>();
        AdgroupDTO adgroupDTOFind = adgroupDAO.findByObjId(aid);
        if (adgroupDTOFind != null) {//如果本地数据库存在该数据

            //计划级联上传 star
            //计划表中查询这条数据，用以cid是否存在，如果存在，嘿嘿...
            if (adgroupDTOFind.getCampaignId() == null) {//如果计划cid已经有了，则不需要再上传了
                List<CampaignDTO> dtos = campaignService.uploadAdd(adgroupDTOFind.getCampaignObjId());
                dtos.stream().forEach(j -> campaignService.update(j, adgroupDTOFind.getCampaignObjId()));
            }
            //计划级联上传 end

            //单元级联上传 star
            //如果上面判断了计划，则肯定只有单元没有上传了，这里就不需要判断了，如果有agid则根本不会进入这个方法，所以不用判断单元是否上传
            returnAdgroupDto = uploadAdd(new ArrayList<String>() {{
                add(aid);
            }});
            //上传完毕后执行修改单元操作
            //单元级联上传 end
        }
        return returnAdgroupDto;
    }

    @Override
    public void updateUpdate(Long aid, AdgroupDTO dto) {
        adgroupDAO.pdateUpdate(aid, dto);
    }

    @Override
    public void deleteBubLinks(Long aid) {
        adgroupDAO.deleteBubLinks(aid);
    }

    @Override
    public double getCampBgt(String cid) {
        return adgroupDAO.getCampBgt(cid);
    }

    @Override
    public double getCampBgt(Long cid) {
        return adgroupDAO.getCampBgt(cid);
    }

    @Override
    public void cut(AdgroupDTO dto, String cid) {
        AdgroupBackupDTO adgroupBackupDTO=new AdgroupBackupDTO();
        BeanUtils.copyProperties(dto,adgroupBackupDTO);
        if(cid.length()>OBJ_SIZE){
            dto.setCampaignObjId(cid);
            dto.setLocalStatus(1);
        }else{
            dto.setCampaignId(Long.valueOf(cid));
            dto.setLocalStatus(2);
        }
        adgroupDAO.update(dto,adgroupBackupDTO);
    }
}

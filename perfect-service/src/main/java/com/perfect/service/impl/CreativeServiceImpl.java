package com.perfect.service.impl;

import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.service.CreativeService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/26.
 */
@Service("creativeService")
public class CreativeServiceImpl implements CreativeService {

    @Autowired
    private CreativeDAO creativeDAO;
    @Resource
    private AccountManageDAO accountManageDAO;
    @Resource
    private AdgroupDAO adgroupDAO;

    @Override
    public List<Long> getCreativeIdByAdgroupId(Long adgroupId) {
        return creativeDAO.getCreativeIdByAdgroupId(adgroupId);
    }

    @Override
    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        return creativeDAO.getCreativeByAdgroupId(adgroupId, params, skip, limit);
    }

    @Override
    public CreativeDTO findOne(Long creativeId) {
        return creativeDAO.findOne(creativeId);
    }

    @Override
    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit) {
        return find(params, skip, limit);
    }

    @Override
    public void insertAll(List<CreativeDTO> list) {
        creativeDAO.insertAll(list);
    }

    @Override
    public void update(CreativeDTO creativeDTO) {
        creativeDAO.update(creativeDTO);
    }

    @Override
    public void delete(Long creativeId) {
        creativeDAO.delete(creativeId);
    }

    @Override
    public void deleteByIds(List<Long> creativeIds) {
        creativeDAO.deleteByIds(creativeIds);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> map, int nowPage, int pageSize) {
        return creativeDAO.findByPagerInfo(map,nowPage,pageSize);
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize) {
        return creativeDAO.findByPagerInfo(l,nowPage,pageSize);
    }


    @Override
    public PagerInfo findByPagerInfoForLong(List<Long> longs, int nowpage, int pageSize) {
        return creativeDAO.findByPagerInfoForLong(longs,nowpage,pageSize);
    }

    @Override
    public String insertOutId(CreativeDTO creativeEntity) {
        return creativeDAO.insertOutId(creativeEntity);
    }

    @Override
    public void deleteByCacheId(Long cacheCreativeId) {
        creativeDAO.deleteByCacheId(cacheCreativeId);
}

    @Override
    public void deleteByCacheId(String cacheCreativeId) {
        creativeDAO.deleteByCacheId(cacheCreativeId);
    }

    @Override
    public CreativeDTO findByObjId(String obj) {
        return creativeDAO.findByObjId(obj);
    }

    @Override
    public void updateByObjId(CreativeDTO creativeEntity) {
        creativeDAO.updateByObjId(creativeEntity);
    }

    @Override
    public void update(CreativeDTO newCreativeEntity, CreativeDTO creativeBackUpEntity) {
        creativeDAO.update(newCreativeEntity, creativeBackUpEntity);
    }

    @Override
    public void delBack(Long oid) {
        creativeDAO.delBack(oid);
    }

    @Override
    public CreativeDTO getAllsBySomeParams(Map<String, Object> params) {
        return creativeDAO.getAllsBySomeParams(params);
    }

    @Override
    public List<CreativeDTO> uploadAdd(List<String> crid) {
        List<CreativeDTO> returnCreativeDTOs = new ArrayList<>();
        List<CreativeType> creativeTypes = new ArrayList<>();
        crid.parallelStream().forEach(s -> {
            CreativeDTO creativeDTOFind = creativeDAO.findByObjId(s);
//            AdgroupDTO adgroupDTO = adgroupDAO.findOne(creativeDTOFind.getAdgroupId());
            if (creativeDTOFind.getAdgroupId() != null) {
                CreativeType creativeType = new CreativeType();
                creativeType.setAdgroupId(creativeDTOFind.getAdgroupId());
                creativeType.setTitle(creativeDTOFind.getTitle());
                creativeType.setDescription1(creativeDTOFind.getDescription1());
                creativeType.setDescription2(creativeDTOFind.getDescription2());
                creativeType.setPcDisplayUrl(creativeDTOFind.getPcDisplayUrl());
                creativeType.setPcDestinationUrl(creativeDTOFind.getPcDestinationUrl());
                creativeType.setMobileDisplayUrl(creativeDTOFind.getMobileDisplayUrl());
                creativeType.setMobileDestinationUrl(creativeDTOFind.getMobileDestinationUrl());
                creativeType.setDevicePreference(creativeDTOFind.getDevicePreference());
                creativeTypes.add(creativeType);
            }
        });
        if (creativeTypes.size() > 0) {
            BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
            try {
                com.perfect.autosdk.sms.v3.CreativeService creativeService = commonService.getService(com.perfect.autosdk.sms.v3.CreativeService.class);
                AddCreativeRequest addCreativeRequest = new AddCreativeRequest();
                addCreativeRequest.setCreativeTypes(creativeTypes);
                AddCreativeResponse addCreativeResponse = creativeService.addCreative(addCreativeRequest);
                List<CreativeType> returnCreativeTypes = addCreativeResponse.getCreativeTypes();
                returnCreativeTypes.parallelStream().filter(s -> s.getCreativeId() != null).forEach(s -> {
                    CreativeDTO creativeDTO = new CreativeDTO();
                    creativeDTO.setCreativeId(s.getCreativeId());
                    creativeDTO.setStatus(s.getStatus());
                    returnCreativeDTOs.add(creativeDTO);
                });
                return returnCreativeDTOs;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

        return returnCreativeDTOs;
    }

    @Override
    public void update(String crid, CreativeDTO dto) {
        creativeDAO.update(crid, dto);
    }

    @Override
    public Integer uploadDel(Long crid) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            com.perfect.autosdk.sms.v3.CreativeService creativeService = commonService.getService(com.perfect.autosdk.sms.v3.CreativeService.class);
            DeleteCreativeRequest deleteCreativeRequest = new DeleteCreativeRequest();
            deleteCreativeRequest.setCreativeIds(new ArrayList<Long>() {{
                add(crid);
            }});
            DeleteCreativeResponse deleteCreativeResponse = creativeService.deleteCreative(deleteCreativeRequest);
            return deleteCreativeResponse.getResult();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteByLongId(Long crid) {
        creativeDAO.deleteByLongId(crid);
    }

    @Override
    public List<CreativeDTO> uploadUpdate(List<Long> crids) {
        List<CreativeDTO> returnCreativeDTOs = new ArrayList<>();
        List<CreativeType> creativeTypes = new ArrayList<>();
        crids.parallelStream().forEach(s -> {
            CreativeDTO creativeDTOFind = creativeDAO.findOne(s);
            if (creativeDTOFind.getAdgroupId() != null) {
                CreativeType creativeType = new CreativeType();
                creativeType.setCreativeId(creativeDTOFind.getCreativeId());
                creativeType.setTitle(creativeDTOFind.getTitle());
                creativeType.setDescription1(creativeDTOFind.getDescription1());
                creativeType.setDescription2(creativeDTOFind.getDescription2());
                creativeType.setPcDestinationUrl(creativeDTOFind.getPcDestinationUrl());
                creativeType.setPcDisplayUrl(creativeDTOFind.getPcDisplayUrl());
                creativeType.setMobileDestinationUrl(creativeDTOFind.getMobileDestinationUrl());
                creativeType.setMobileDisplayUrl(creativeDTOFind.getMobileDisplayUrl());
                creativeType.setPause(creativeDTOFind.getPause());
                creativeType.setDevicePreference(creativeDTOFind.getDevicePreference());
                creativeTypes.add(creativeType);
            }
        });
        if (creativeTypes.size() > 0) {
            BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
            try {
                com.perfect.autosdk.sms.v3.CreativeService creativeService = commonService.getService(com.perfect.autosdk.sms.v3.CreativeService.class);
                UpdateCreativeRequest updateCreativeRequest=new UpdateCreativeRequest();
                updateCreativeRequest.setCreativeTypes(creativeTypes);
                UpdateCreativeResponse updateCreativeResponse=creativeService.updateCreative(updateCreativeRequest);
                List<CreativeType> returnCreativeTypes=updateCreativeResponse.getCreativeTypes();
                returnCreativeTypes.parallelStream().filter(s -> s.getCreativeId() != null).forEach(s->{
                    CreativeDTO returnCreativeDTO=new CreativeDTO();
                    returnCreativeDTO.setCreativeId(s.getCreativeId());
                    returnCreativeDTO.setStatus(s.getStatus());
                    returnCreativeDTOs.add(returnCreativeDTO);
                });
                return returnCreativeDTOs;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return returnCreativeDTOs;
    }

    @Override
    public void updateLs(Long crid, CreativeDTO dto) {
        creativeDAO.updateLs(crid,dto);
    }
}

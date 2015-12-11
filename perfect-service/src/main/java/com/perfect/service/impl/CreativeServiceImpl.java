package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.param.EnableOrPauseParam;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.param.SearchFilterParam;
import com.perfect.service.*;
import com.perfect.service.AdgroupService;
import com.perfect.service.CampaignService;
import com.perfect.service.CreativeService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by SubDong on 2014/11/26.
 */
@Service("creativeService")
public class CreativeServiceImpl implements CreativeService {
    private static Integer OBJ_SIZE = 18;//判断百度id跟本地id长度大小
    @Autowired
    private CreativeDAO creativeDAO;
    @Resource
    private AccountManageDAO accountManageDAO;
    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private CampaignService campaignService;
    @Resource
    private AdgroupService adgroupService;

    @Resource
    private LogSaveService logSaveService;

    @Override
    public List<Long> getCreativeIdByAdgroupId(Long adgroupId) {
        return creativeDAO.getCreativeIdByAdgroupId(adgroupId);
    }

    @Override
    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        return creativeDAO.getCreativeByAdgroupId(adgroupId, params, skip, limit);
    }

    @Override
    public Iterable<CreativeDTO> findAll() {
        return creativeDAO.findAll();
    }

    @Override
    public List<CreativeDTO> findHasLocalStatus() {
        return creativeDAO.findHasLocalStatus();
    }

    @Override
    public List<CreativeDTO> findHasLocalStatusStr(List<AdgroupDTO> adgroupDTOStr) {
        List<String> strs = new ArrayList<>();
        for (AdgroupDTO str : adgroupDTOStr) {
            if (str.getAdgroupId() == null) {
                strs.add(str.getId());
            }
        }
        return creativeDAO.findHasLocalStatusStr(strs);
    }

    @Override
    public List<CreativeDTO> findHasLocalStatusLong(List<AdgroupDTO> adgroupDTOLong) {
        List<Long> longs = new ArrayList<>();
        for (AdgroupDTO str : adgroupDTOLong) {
            if (str.getAdgroupId() != null) {
                longs.add(str.getAdgroupId());
            }
        }
        return creativeDAO.findHasLocalStatusLong(longs);
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
    public void updateCreative(CreativeDTO creativeDTO) {
        CreativeDTO newCreativeDTO;

        if (creativeDTO.getCreativeId() == null) {
            newCreativeDTO = creativeDAO.findByObjId(creativeDTO.getId());
        } else {
            newCreativeDTO = creativeDAO.findOne(creativeDTO.getCreativeId());
        }

        CreativeBackUpDTO creativeBackUpDTO = new CreativeBackUpDTO();
        BeanUtils.copyProperties(newCreativeDTO, creativeBackUpDTO);

        if (newCreativeDTO.getCreativeId() == null) {
            newCreativeDTO.setLocalStatus(1);
        } else {
            newCreativeDTO.setLocalStatus(2);
        }

        if (creativeDTO.getTitle() != null) {
            newCreativeDTO.setTitle(creativeDTO.getTitle());
        }

        if (creativeDTO.getDescription1() != null) {
            newCreativeDTO.setDescription1(creativeDTO.getDescription1());
        }

        if (creativeDTO.getDescription2() != null) {
            newCreativeDTO.setDescription2(creativeDTO.getDescription2());
        }

        if (creativeDTO.getPcDestinationUrl() != null) {
            newCreativeDTO.setPcDestinationUrl(creativeDTO.getPcDestinationUrl());
        }

        if (creativeDTO.getPcDisplayUrl() != null) {
            newCreativeDTO.setPcDisplayUrl(creativeDTO.getPcDisplayUrl());
        }

        if (creativeDTO.getMobileDestinationUrl() != null) {
            newCreativeDTO.setMobileDestinationUrl(creativeDTO.getMobileDestinationUrl());
        }

        if (creativeDTO.getMobileDisplayUrl() != null) {
            newCreativeDTO.setMobileDisplayUrl(creativeDTO.getMobileDisplayUrl());
        }

        if (creativeDTO.getPause() != null) {
            newCreativeDTO.setPause(creativeDTO.getPause());
        }

        if (creativeDTO.getDevicePreference() != null) {
            newCreativeDTO.setDevicePreference(creativeDTO.getDevicePreference());
        }

        creativeDAO.update(newCreativeDTO, creativeBackUpDTO);

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
    public PagerInfo findByPagerInfo(Map<String, Object> map, int nowPage, int pageSize, SearchFilterParam sp) {
        return creativeDAO.findByPagerInfo(map, nowPage, pageSize, sp);
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
        return creativeDAO.findByPagerInfo(l, nowPage, pageSize, sp);
    }


    @Override
    public PagerInfo findByPagerInfoForLong(List<Long> longs, int nowpage, int pageSize, SearchFilterParam sp) {
        return creativeDAO.findByPagerInfoForLong(longs, nowpage, pageSize, sp);
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
        List<OperationRecordModel> logs = Lists.newArrayList();
        crid.stream().forEach(s -> {
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
                OperationRecordModel orm = logSaveService.addCreative(creativeType);
                if (orm != null) {
                    logs.add(orm);
                }
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
                returnCreativeTypes.stream().filter(s -> s.getCreativeId() != null).forEach(s -> {
                    CreativeDTO creativeDTO = new CreativeDTO();
                    creativeDTO.setCreativeId(s.getCreativeId());
                    creativeDTO.setStatus(s.getStatus());
                    returnCreativeDTOs.add(creativeDTO);
                    logs.stream().forEach(c -> {
                        if (c.getOptContent().equals(s.getTitle()) && s.getCreativeId() != null) {
                            logSaveService.saveLog(c);
                        }
                    });
                });
                return returnCreativeDTOs;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

        return returnCreativeDTOs;
    }

    @Override
    public List<CreativeDTO> uploadAddByUp(String crid) {
        List<CreativeDTO> returnCreativeDTO = new ArrayList<>();
        CreativeDTO creativeDTOFind = creativeDAO.findByObjId(crid);//查询出要上传的创意的oagid，根据oagid去查询本地的单元，根据oaid查询ocid查询出计划，并两者上传
        AdgroupDTO adgroupDTOFind = adgroupDAO.findByObjId(creativeDTOFind.getAdgroupObjId());//根据关键字的oagid查询到本地的单元记录
        if (adgroupDTOFind != null) {//如果本地的数据还存在
            //计划级联上传 star
            //计划表中查询这条数据，用以cid是否存在，如果存在，嘿嘿...
            if (adgroupDTOFind.getCampaignId() == null) {//如果计划cid已经有了，则不需要再上传了
                List<CampaignDTO> dtos = campaignService.uploadAdd(adgroupDTOFind.getCampaignObjId());
                dtos.stream().forEach(j -> campaignService.update(j, adgroupDTOFind.getCampaignObjId()));
            }
            //计划级联上传 end

            //单元级联上传 star
            //如果上面判断了计划，则肯定只有单元没有上传了，这里就不需要判断了，如果有agid则根本不会进入这个方法，所以不用判断单元是否上传
            List<AdgroupDTO> returnAids = adgroupService.uploadAdd(new ArrayList<String>() {{
                add(creativeDTOFind.getAdgroupObjId());
            }});
            //上传完毕后执行修改单元操作
            returnAids.stream().forEach(f -> adgroupService.update(creativeDTOFind.getAdgroupObjId(), f));
            //单元级联上传 end

            //最后上传创意
            returnCreativeDTO = uploadAdd(new ArrayList<String>() {{
                add(crid);
            }});
        }

        return returnCreativeDTO;
    }

    @Override
    public void update(String crid, CreativeDTO dto) {
        creativeDAO.update(crid, dto);
    }

    @Override
    public Integer uploadDel(Long crid) {
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        CreativeDTO creativeDTO = creativeDAO.findOne(crid);
        OperationRecordModel orm = logSaveService.removeCreative(creativeDTO);
        try {
            com.perfect.autosdk.sms.v3.CreativeService creativeService = commonService.getService(com.perfect.autosdk.sms.v3.CreativeService.class);
            DeleteCreativeRequest deleteCreativeRequest = new DeleteCreativeRequest();
            deleteCreativeRequest.setCreativeIds(new ArrayList<Long>() {{
                add(crid);
            }});
            if (orm != null) {
                logSaveService.saveLog(orm);
            }
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
        List<OperationRecordModel> logs = Lists.newArrayList();
        crids.stream().forEach(s -> {
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
                OperationRecordModel orm = logSaveService.updateCreativeLogAll(creativeType);
                if (orm != null) {
                    logs.add(orm);
                }
                creativeTypes.add(creativeType);
            }
        });
        if (creativeTypes.size() > 0) {
            BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
            try {
                com.perfect.autosdk.sms.v3.CreativeService creativeService = commonService.getService(com.perfect.autosdk.sms.v3.CreativeService.class);
                UpdateCreativeRequest updateCreativeRequest = new UpdateCreativeRequest();
                updateCreativeRequest.setCreativeTypes(creativeTypes);
                UpdateCreativeResponse updateCreativeResponse = creativeService.updateCreative(updateCreativeRequest);
                List<CreativeType> returnCreativeTypes = updateCreativeResponse.getCreativeTypes();
                returnCreativeTypes.stream().filter(s -> s.getCreativeId() != null).forEach(s -> {
                    CreativeDTO returnCreativeDTO = new CreativeDTO();
                    returnCreativeDTO.setCreativeId(s.getCreativeId());
                    returnCreativeDTO.setStatus(s.getStatus());
                    returnCreativeDTOs.add(returnCreativeDTO);
                    if (logs.size() > 0) {
                        logs.stream().forEach(l -> {
                            if (l.getOptComprehensiveID() == s.getCreativeId()) {
                                logSaveService.saveLog(l);
                            }
                        });
                    }
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
        creativeDAO.updateLs(crid, dto);
    }

    @Override
    public List<CreativeDTO> getByCampaignIdStr(String cid) {
        List<String> adgroupIds = adgroupDAO.getAdgroupIdByCampaignId(cid);
        List<CreativeDTO> creativeDTOs = creativeDAO.getAllsByAdgroupIdsForString(adgroupIds, null);
        return creativeDTOs;
    }

    @Override
    public List<CreativeDTO> getByCampaignIdLong(Long cid) {
        List<Long> adgroupIds = adgroupDAO.getAdgroupIdByCampaignId(cid);
        List<CreativeDTO> creativeDTOs = creativeDAO.getAllsByAdgroupIds(adgroupIds, null);
        return creativeDTOs;
    }

    @Override
    public List<CreativeDTO> getAll(FindOrReplaceParam forp) {
        List<Long> adgroupLongIds = adgroupDAO.getAllAdgroupId();
        List<String> adgroupStringIds = adgroupDAO.getAllAdgroupIdStr();

        List<CreativeDTO> creativeLongs = creativeDAO.getAllsByAdgroupIds(adgroupLongIds, forp);
        List<CreativeDTO> creativeStrings = creativeDAO.getAllsByAdgroupIdsForString(adgroupStringIds, forp);

        creativeLongs.addAll(creativeStrings);
        return creativeLongs;
    }

    @Override
    public void batchDelete(FindOrReplaceParam param) {
        if (param != null) {
            List<String> asList = new ArrayList<>();
            if (param.getCheckData() != null) {
                String[] list = param.getCheckData().split(",");
                Collections.addAll(asList, list);
            }

            if (param.getForType() != 0) {
                String dataId = param.getAdgroupId() != null ? param.getAdgroupId() : param.getCampaignId();
                if (param.getAdgroupId() != null) {
                    List<CreativeDTO> keywordDTOs;
                    if (dataId.length() < 24) {
                        List<Long> longs = Lists.newArrayList(Long.valueOf(param.getAdgroupId()));
                        keywordDTOs = creativeDAO.getAllsByAdgroupIds(longs, null);
                    } else {
                        List<String> strings = Lists.newArrayList(param.getAdgroupId());
                        keywordDTOs = creativeDAO.getAllsByAdgroupIdsForString(strings, null);
                    }
                    asList.clear();
                    keywordDTOs.forEach(e -> {
                        if (e.getCreativeId() != null) asList.add(String.valueOf(e.getCreativeId()));
                        else asList.add(e.getId());
                    });
                } else {
                    List<CreativeDTO> creativeDTOs;
                    if (dataId.length() < 24) {
                        List<String> strings = Lists.newArrayList();
                        List<Long> longs = Lists.newArrayList();
                        adgroupDAO.findByCampaignId(Long.valueOf(param.getCampaignId())).forEach(e -> {
                            if (e.getAdgroupId() != null) longs.add(e.getAdgroupId());
                            else strings.add(e.getId());

                        });
                        creativeDTOs = creativeDAO.getAllsByAdgroupIds(longs, null);
                        List<CreativeDTO> dtos = creativeDAO.getAllsByAdgroupIdsForString(strings, null);
                        if (!Objects.isNull(dtos)) creativeDTOs.addAll(dtos);
                    } else {
                        List<String> strings = Lists.newArrayList();
                        List<Long> longs = Lists.newArrayList();
                        adgroupDAO.findByCampaignOId(param.getCampaignId()).forEach(e -> {
                            if (e.getAdgroupId() != null) longs.add(e.getAdgroupId());
                            else strings.add(e.getId());
                        });
                        creativeDTOs = creativeDAO.getAllsByAdgroupIds(longs, null);
                        List<CreativeDTO> dtos = creativeDAO.getAllsByAdgroupIdsForString(strings, null);
                        if (!Objects.isNull(dtos)) creativeDTOs.addAll(dtos);
                    }
                    asList.clear();
                    creativeDTOs.forEach(e -> {
                        if (e.getCreativeId() != null) asList.add(String.valueOf(e.getCreativeId()));
                        else asList.add(e.getId());
                    });
                }
            }
            creativeDAO.batchDelete(asList);
        }
    }

    @Override
    public void enableOrPauseCreative(EnableOrPauseParam param) {
        if (param != null) {
            List<String> strings = new ArrayList<>();
            if (param.getEnableOrPauseData() != null) {
                String[] list = param.getEnableOrPauseData().split(",");
                Collections.addAll(strings, list);
            }
            if (param.getEnableOrPauseStatus() == 0) {
                creativeDAO.enableOrPauseCreative(strings, false);
            } else {
                creativeDAO.enableOrPauseCreative(strings, true);
            }
        }
    }

    public void cut(CreativeDTO dto, String aid) {
        CreativeBackUpDTO backUpDTO = new CreativeBackUpDTO();
        BeanUtils.copyProperties(dto, backUpDTO);
        if (aid.length() > OBJ_SIZE) {
            dto.setAdgroupObjId(aid);
            dto.setLocalStatus(1);
        } else {
            dto.setAdgroupId(Long.parseLong(aid));
            dto.setLocalStatus(2);
        }
        creativeDAO.update(dto, backUpDTO);
    }

    @Override
    public List<CreativeDTO> findExistCreative(List<CreativeDTO> safeList) {
        List<CreativeDTO> dbExistCreative = new ArrayList<>();
        if (safeList.size() > 0) {
            safeList.stream().forEach(c -> {
                Map<String, Object> params = new HashMap<>();
                params.put("t", c.getTitle());
                params.put("desc1", c.getDescription1());
                params.put("desc2", c.getDescription2());
                AdgroupDTO adgroupDTO = adgroupDAO.findByAdgroupName(c.getAdgroupName());
                if (adgroupDTO != null) {
                    if (adgroupDTO.getAdgroupId() != null) {
                        params.put(MongoEntityConstants.ADGROUP_ID, adgroupDTO.getAdgroupId());
                    }
                    CreativeDTO existDto = creativeDAO.existDTO(params);
                    if (existDto != null) {
                        existDto.setCampaignName(c.getCampaignName());
                        existDto.setAdgroupName(c.getAdgroupName());
                        dbExistCreative.add(existDto);
                    }
                }
            });
            return dbExistCreative;
        } else {
            return null;
        }
    }
}

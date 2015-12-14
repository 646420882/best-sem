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
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.param.EnableOrPauseParam;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.param.SearchFilterParam;
import com.perfect.service.*;
import com.perfect.service.AdgroupService;
import com.perfect.service.CampaignService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    private KeywordDAO keywordDAO;

    @Resource
    private CreativeDAO creativeDAO;

    @Resource
    CampaignDAO campaignDAO;

    @Resource
    private CampaignService campaignService;

    @Resource
    private UserOperationLogService userOperationLogService;

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
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
        return adgroupDAO.findByPagerInfo(params, nowPage, pageSize, sp);
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
    public void ormByCreative(CreativeDTO creativeDTO, UserOperationLogDTO orm) {
//        AdgroupDTO adgroupDTO = adgroupDAO.findOne(creativeDTO.getAdgroupId());
//        CampaignDTO campaignDTO = campaignDAO.findOne(adgroupDTO.getCampaignId());
//        orm.setUserId(AppContext.getAccountId());
//        orm.setUnitId(adgroupDTO.getAdgroupId());
//        orm.setUnitName(adgroupDTO.getAdgroupName());
//        orm.setPlanId(campaignDTO.getCampaignId());
//        orm.setPlanName(campaignDTO.getCampaignName());
    }

    @Override
    public double findPriceRatio(Long cid) {
        return adgroupDAO.findPriceRatio(cid);
    }

    @Override
    public List<AdgroupDTO> uploadAdd(List<String> aids) {
        List<AdgroupDTO> returnDto = new ArrayList<>();
        List<AdgroupType> adgroupTypes = new ArrayList<>();
        List<UserOperationLogDTO> logs = Lists.newArrayList();
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
                UserOperationLogDTO orm = userOperationLogService.addAdgroup(adgroupType);
                if (orm != null) {
                    logs.add(orm);
                }
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
                    if (logs.size() > 0) {
                        logs.stream().forEach(l -> {
                            if (l.getName().equals(s.getAdgroupName()) && s.getAdgroupId() != null) {
                                userOperationLogService.saveLog(l);
                            }
                        });
                    }
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
        AdgroupDTO adgroupDTO = adgroupDAO.findOne(aid);
        UserOperationLogDTO orm=null;
        if(adgroupDTO!=null){
            orm= userOperationLogService.removeAdgroup(adgroupDTO);
        }
        try {
            com.perfect.autosdk.sms.v3.AdgroupService adgroupService = commonService.getService(com.perfect.autosdk.sms.v3.AdgroupService.class);
            DeleteAdgroupRequest adgroupRequest = new DeleteAdgroupRequest();
            adgroupRequest.setAdgroupIds(new ArrayList<Long>() {{
                add(aid);
            }});
            DeleteAdgroupResponse adgroupResponse = adgroupService.deleteAdgroup(adgroupRequest);
            result = adgroupResponse.getResponse();
            if (orm != null) {
//                userOperationLogService.saveLog(orm);
            }
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
        List<UserOperationLogDTO> logs = Lists.newArrayList();
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
            UserOperationLogDTO orm = userOperationLogService.updateAdgroupAll(adgroupType);
            if (orm != null) {
                logs.add(orm);
            }
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
                if (logs.size() > 0) {
                    logs.stream().forEach(l -> {
                        if (l.getOid().equals(Long.toString(s.getAdgroupId()))) {
                            userOperationLogService.saveLog(l);
                        }
                    });
                }
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
    public void batchDelete(FindOrReplaceParam param) {
        if (param != null) {
            List<String> asList = new ArrayList<>();
            List<String> keywordDatas = new ArrayList<>();
            List<String> creativeDatas = new ArrayList<>();
            if (param.getCheckData() != null) {
                String[] list = param.getCheckData().split(",");
                Collections.addAll(asList, list);
                asList.forEach(e -> {
                    List<KeywordDTO> keywordDTOs;
                    List<CreativeDTO> creativeDTOs;
                    if (e.length() < 24) {
                        List<Long> longs = Lists.newArrayList();
                        longs.add(Long.valueOf(e));
                        keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(longs, null);
                        creativeDTOs = creativeDAO.getAllsByAdgroupIds(longs, null);
                    } else {
                        List<String> strings = Lists.newArrayList();
                        strings.add(e);
                        keywordDTOs = keywordDAO.findKeywordByAdgroupIdsStr(strings, null);
                        creativeDTOs = creativeDAO.getAllsByAdgroupIdsForString(strings, null);
                    }
                    keywordDatas.clear();
                    creativeDatas.clear();
                    keywordDTOs.forEach(a -> {
                        if (a.getKeywordId() != null) keywordDatas.add(String.valueOf(a.getKeywordId()));
                        else keywordDatas.add(a.getId());
                    });
                    creativeDTOs.forEach(a -> {
                        if (a.getCreativeId() != null) creativeDatas.add(String.valueOf(a.getCreativeId()));
                        else creativeDatas.add(a.getId());
                    });
                });
            }

            if (param.getForType() != 0) {
                String dataId = param.getCampaignId();
                List<CreativeDTO> creativeDTOs;
                List<KeywordDTO> keywordDTOs;
                if (dataId.length() < 24) {
                    List<String> strings = Lists.newArrayList();
                    List<Long> longs = Lists.newArrayList();
                    adgroupDAO.findByCampaignId(Long.valueOf(param.getCampaignId())).forEach(e -> {
                        if (e.getAdgroupId() != null) {
                            longs.add(e.getAdgroupId());
                            asList.add(String.valueOf(e.getAdgroupId()));
                        } else {
                            strings.add(e.getId());
                            asList.add(String.valueOf(e.getId()));
                        }

                    });
                    creativeDTOs = creativeDAO.getAllsByAdgroupIds(longs, null);
                    keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(longs, null);
                    List<CreativeDTO> dtos = creativeDAO.getAllsByAdgroupIdsForString(strings, null);
                    List<KeywordDTO> keydtos = keywordDAO.findKeywordByAdgroupIdsStr(strings, null);
                    if (!Objects.isNull(keydtos)) keywordDTOs.addAll(keydtos);
                    if (!Objects.isNull(dtos)) creativeDTOs.addAll(dtos);
                } else {
                    List<String> strings = Lists.newArrayList();
                    List<Long> longs = Lists.newArrayList();
                    adgroupDAO.findByCampaignOId(param.getCampaignId()).forEach(e -> {
                        if (e.getAdgroupId() != null) {
                            longs.add(e.getAdgroupId());
                            asList.add(String.valueOf(e.getAdgroupId()));
                        } else {
                            strings.add(e.getId());
                            asList.add(String.valueOf(e.getId()));
                        }
                    });
                    creativeDTOs = creativeDAO.getAllsByAdgroupIds(longs, null);
                    keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(longs, null);
                    List<CreativeDTO> dtos = creativeDAO.getAllsByAdgroupIdsForString(strings, null);
                    List<KeywordDTO> keydtos = keywordDAO.findKeywordByAdgroupIdsStr(strings, null);
                    if (!Objects.isNull(keydtos)) keywordDTOs.addAll(keydtos);
                    if (!Objects.isNull(dtos)) creativeDTOs.addAll(dtos);
                }
                keywordDatas.clear();
                creativeDatas.clear();
                keywordDTOs.forEach(a -> {
                    if (a.getKeywordId() != null) keywordDatas.add(String.valueOf(a.getKeywordId()));
                    else keywordDatas.add(a.getId());
                });
                creativeDTOs.forEach(a -> {
                    if (a.getCreativeId() != null) creativeDatas.add(String.valueOf(a.getCreativeId()));
                    else creativeDatas.add(a.getId());
                });

            }
            adgroupDAO.batchDelete(asList, keywordDatas, creativeDatas);
        }
    }

    @Override
    public void enableOrPauseAdgroup(EnableOrPauseParam param) {
        if (param != null) {
            List<String> strings = new ArrayList<>();
            if (param.getEnableOrPauseData() != null) {
                String[] list = param.getEnableOrPauseData().split(",");
                Collections.addAll(strings, list);
            }

            List<String> objId = new ArrayList<>();
            List<Long> baidId = new ArrayList<>();
            strings.forEach(e -> {
                if (e.length() < 24) {
                    baidId.add(Long.parseLong(e));
                } else {
                    objId.add(e);
                }
            });
            List<KeywordDTO> keywordByAdgroupIdsLong = new ArrayList<>(keywordDAO.findKeywordByAdgroupIdsLong(baidId));
            List<KeywordDTO> keywordByAdgroupIdsStr = keywordDAO.findKeywordByAdgroupIdsStr(objId);

            List<CreativeDTO> allsByAdgroupIds = new ArrayList<>(creativeDAO.getAllsByAdgroupIds(baidId, null));
            List<CreativeDTO> allsByAdgroupIdsForString = creativeDAO.getAllsByAdgroupIdsForString(objId, null);

            keywordByAdgroupIdsLong.addAll(keywordByAdgroupIdsStr);
            allsByAdgroupIds.addAll(allsByAdgroupIdsForString);
            //获取关键词本地及百度ID
            List<String> keywordId = keywordByAdgroupIdsLong.stream().map(e -> {
                if (e.getKeyword() != null) return e.getKeywordId().toString();
                else return e.getId();
            }).collect(Collectors.toList());
            //获取创意本地及百度ID
            List<String> creativeId = allsByAdgroupIds.stream().map(o -> {
                if (o.getCreativeId() != null) return o.getCreativeId().toString();
                else return o.getId();
            }).collect(Collectors.toList());

            if (param.getEnableOrPauseStatus() == 0) {
                keywordDAO.enableOrPause(keywordId, false);
                creativeDAO.enableOrPauseCreative(creativeId, false);
                adgroupDAO.enableOrPauseAdgroup(strings, false);
            } else {
                keywordDAO.enableOrPause(keywordId, true);
                creativeDAO.enableOrPauseCreative(creativeId, true);
                adgroupDAO.enableOrPauseAdgroup(strings, true);
            }
        }
    }

    public void cut(AdgroupDTO dto, String cid) {
        AdgroupBackupDTO adgroupBackupDTO = new AdgroupBackupDTO();
        BeanUtils.copyProperties(dto, adgroupBackupDTO);
        if (cid.length() > OBJ_SIZE) {
            dto.setCampaignObjId(cid);
            dto.setLocalStatus(1);
        } else {
            dto.setCampaignId(Long.valueOf(cid));
            dto.setLocalStatus(2);
        }
        adgroupDAO.update(dto, adgroupBackupDTO);
    }

    @Override
    public AdgroupDTO autoBAG(String cname, String aname) {
        AdgroupDTO adgroupDTO = null;
        CampaignDTO campaignDTO = campaignDAO.findCampaignByName(cname);
        if (campaignDTO != null) {
            Map<String, Object> mapParams = new HashMap<>();
            if (campaignDTO.getCampaignId() != null) {
                mapParams.put(MongoEntityConstants.CAMPAIGN_ID, campaignDTO.getCampaignId());
            } else {
                mapParams.put(MongoEntityConstants.OBJ_CAMPAIGN_ID, campaignDTO.getId());
            }
            mapParams.put("name", aname);
            adgroupDTO = adgroupDAO.fndEntity(mapParams);
            return adgroupDTO;
        } else {
            CampaignDTO newCampaign = new CampaignDTO();
            newCampaign.setCampaignName(cname);
            newCampaign.setPause(true);
            newCampaign.setShowProb(1);
            newCampaign.setLocalStatus(1);
            newCampaign.setStatus(-1);
            newCampaign.setAccountId(AppContext.getAccountId());
            String id = campaignDAO.insertReturnId(newCampaign);
            AdgroupDTO adgroupDTOFind = adgroupDAO.findByAdgroupName(aname);
            if (adgroupDTOFind != null) {
                return adgroupDTOFind;
            } else {
                AdgroupDTO newAdgroup = new AdgroupDTO();
                newAdgroup.setLocalStatus(1);
                newAdgroup.setCampaignObjId(id);
                newAdgroup.setAdgroupName(aname);
                newAdgroup.setPause(false);
                newAdgroup.setAccountId(AppContext.getAccountId());
                Object adgroupStrId = adgroupDAO.insertOutId(newAdgroup);
                newAdgroup.setId(adgroupStrId.toString());
                return newAdgroup;
            }
        }
    }
}


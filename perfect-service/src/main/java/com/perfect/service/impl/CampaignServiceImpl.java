package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.param.EnableOrPauseParam;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.param.SearchFilterParam;
import com.perfect.service.AdgroupService;
import com.perfect.service.CampaignService;
import com.perfect.utils.CharsetUtils;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SubDong on 2014/11/26.
 */
@Repository("campaignService")
public class CampaignServiceImpl implements CampaignService {

    @Resource
    private CampaignDAO campaignDAO;
    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private CreativeDAO creativeDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private AdgroupService adgroupService;

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
    public PagerInfo findByPageInfo(Long accountId, int pageSize, int pageNo, SearchFilterParam sp) {
        return campaignDAO.findByPageInfo(accountId, pageSize, pageNo, sp);
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

    @Override
    public void batchDelete(FindOrReplaceParam param) {
        if (param != null) {
            List<String> asList = new ArrayList<>();
            List<String> adgroupDatas = new ArrayList<>();
            List<String> keywordDatas = new ArrayList<>();
            List<String> creativeDatas = new ArrayList<>();
            if (param.getCheckData() == null) asList.add(param.getCampaignId());
            else Collections.addAll(asList, param.getCheckData().split(","));
            List<CreativeDTO> creativeDTOs = new ArrayList<>();
            List<KeywordDTO> keywordDTOs = new ArrayList<>();
            for (String s : asList) {
                if (s.length() < 24) {
                    List<String> strings = Lists.newArrayList();
                    List<Long> longs = Lists.newArrayList();
                    adgroupDAO.findByCampaignId(Long.valueOf(s)).forEach(e -> {
                        if (e.getAdgroupId() != null) {
                            longs.add(e.getAdgroupId());
                            adgroupDatas.add(String.valueOf(e.getAdgroupId()));
                        } else {
                            strings.add(e.getId());
                            adgroupDatas.add(e.getId());
                        }
                    });
                    List<CreativeDTO> dtos1 = creativeDAO.getAllsByAdgroupIds(longs,null);
                    List<KeywordDTO> keydtos1 = keywordDAO.findKeywordByAdgroupIdsLong(longs,null);
                    List<CreativeDTO> dtos = creativeDAO.getAllsByAdgroupIdsForString(strings,null);
                    List<KeywordDTO> keydtos = keywordDAO.findKeywordByAdgroupIdsStr(strings,null);

                    if (Objects.nonNull(keydtos1)) keywordDTOs.addAll(keydtos1);
                    if (Objects.nonNull(keydtos)) keywordDTOs.addAll(keydtos);
                    if (Objects.nonNull(dtos1)) creativeDTOs.addAll(dtos1);
                    if (Objects.nonNull(dtos)) creativeDTOs.addAll(dtos);
                } else {
                    List<String> strings = Lists.newArrayList();
                    List<Long> longs = Lists.newArrayList();
                    adgroupDAO.findByCampaignOId(s).forEach(e -> {
                        if (e.getAdgroupId() != null) {
                            longs.add(e.getAdgroupId());
                            adgroupDatas.add(String.valueOf(e.getAdgroupId()));
                        } else {
                            strings.add(e.getId());
                            adgroupDatas.add(String.valueOf(e.getId()));
                        }
                    });
                    List<CreativeDTO> dtos1 = creativeDAO.getAllsByAdgroupIds(longs,null);
                    List<KeywordDTO> keydtos1 = keywordDAO.findKeywordByAdgroupIdsLong(longs,null);
                    List<CreativeDTO> dtos = creativeDAO.getAllsByAdgroupIdsForString(strings,null);
                    List<KeywordDTO> keydtos = keywordDAO.findKeywordByAdgroupIdsStr(strings,null);

                    if (Objects.nonNull(keydtos1)) keywordDTOs.addAll(keydtos1);
                    if (Objects.nonNull(keydtos)) keywordDTOs.addAll(keydtos);
                    if (Objects.nonNull(dtos1)) creativeDTOs.addAll(dtos1);
                    if (Objects.nonNull(dtos)) creativeDTOs.addAll(dtos);
                }
            }
            keywordDatas.clear();
            creativeDatas.clear();

            if (keywordDTOs != null) {
                keywordDTOs.forEach(a -> {
                    if (a.getKeywordId() != null) keywordDatas.add(String.valueOf(a.getKeywordId()));
                    else keywordDatas.add(a.getId());
                });
            }
            if (creativeDTOs != null) {
                creativeDTOs.forEach(a -> {
                    if (a.getCreativeId() != null) creativeDatas.add(String.valueOf(a.getCreativeId()));
                    else creativeDatas.add(a.getId());
                });
            }
            campaignDAO.batchDelete(asList, adgroupDatas, keywordDatas, creativeDatas);
        }
    }

    @Override
    public void enableOrPauseCampaign(EnableOrPauseParam param) {
        if (param != null) {
            List<String> strings = Lists.newArrayList();
            if (param.getEnableOrPauseData() != null) {
                String[] list = param.getEnableOrPauseData().split(",");
                Collections.addAll(strings, list);
            }
            List<String> objId = Lists.newArrayList();
            List<Long> baidId = Lists.newArrayList();
            strings.forEach(e -> {
                if (e.length() < 24) {
                    baidId.add(Long.parseLong(e));
                } else {
                    objId.add(e);
                }
            });
            List<Long> adgroupLong = adgroupDAO.getAdgroupIdByCampaignIdListLong(baidId);
            List<String> adgroupString = adgroupDAO.getAdgroupIdByCampaignIdListString(objId);


            EnableOrPauseParam enableOrPauseParam = new EnableOrPauseParam();
            if(adgroupString.size() == 0){
                enableOrPauseParam.setEnableOrPauseData(adgroupLong.toString().replaceAll("[\\[|\\]| ]", ""));
            }else{
                List<String> result = Stream.concat(adgroupLong.stream().map(Object::toString), adgroupString.stream()).collect(Collectors.toList());
                enableOrPauseParam.setEnableOrPauseData(result.toString().replaceAll("[\\[|\\]| ]", ""));
            }

            enableOrPauseParam.setEnableOrPauseStatus(param.getEnableOrPauseStatus());
            enableOrPauseParam.setEnableOrPauseType(param.getEnableOrPauseType());

            adgroupService.enableOrPauseAdgroup(enableOrPauseParam);

            if (param.getEnableOrPauseStatus() == 0) {
                campaignDAO.enableOrPauseCampaign(strings, false);
            } else {
                campaignDAO.enableOrPauseCampaign(strings, true);
            }
        }

    }

	@Override
	public List<Map<String,Object>> findAllDownloadCampaignByBaiduAccountId(Integer type,long id) {
	
		List<Map<String,Object>> temp_list=new ArrayList<Map<String,Object>>();
		if(type.intValue()==8){
			List<CampaignDTO> list= this.campaignDAO.findAllDownloadCampaignByBaiduAccountId();
			
			
			if(list!=null && list.size()>0){
				for(CampaignDTO dto:list){
					Map<String,Object> map= new HashMap<String,Object>();
					map.put("account_id",dto.getAccountId());
					map.put("id",dto.getCampaignId());
					map.put("name",dto.getCampaignName());
					temp_list.add(map);
				}
			}
		}
		
		
		if(type.intValue()==7){
			List<AdgroupDTO> list=this.adgroupService.getAdgroupByCampaignId(id);
			
			if(list!=null && list.size()>0){
				for(AdgroupDTO dto:list){
					Map<String,Object> map= new HashMap<String,Object>();
					map.put("account_id",dto.getAccountId());
					map.put("id",dto.getAdgroupId());
					map.put("name",dto.getAdgroupName());
					temp_list.add(map);
				}
			}
		}
		
		if(type.intValue()==5){
			List<KeywordDTO> list=this.keywordDAO.findByAdgroupId(AppContext.getAccountId(), id);
			if(list!=null && list.size()>0){
				for(KeywordDTO dto:list){
					Map<String,Object> map= new HashMap<String,Object>();
					map.put("account_id",dto.getAccountId());
					map.put("id",dto.getKeywordId());
					map.put("name",dto.getKeyword());
					temp_list.add(map);
				}
			}
		}
		
		if(type.intValue()==2){
			List<CreativeDTO> list=this.creativeDAO.findByAdgroupId(AppContext.getAccountId(), id);
			if(list!=null && list.size()>0){
				for(CreativeDTO dto:list){
					Map<String,Object> map= new HashMap<String,Object>();
					map.put("account_id",dto.getAccountId());
					map.put("id",dto.getCreativeId());
					map.put("name",dto.getTitle());
					temp_list.add(map);
				}
			}
		}
		
		
		return temp_list;
		
	}
}

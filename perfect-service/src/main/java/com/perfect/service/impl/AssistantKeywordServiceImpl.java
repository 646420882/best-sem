package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dao.monitoring.MonitoringDao;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.campaign.CampaignTreeDTO;
import com.perfect.dto.keyword.AssistantKeywordIgnoreDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.param.SearchFilterParam;
import com.perfect.service.*;
import com.perfect.service.AdgroupService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

//import com.perfect.entity.adgroup.AdgroupEntity;
//import com.perfect.entity.campaign.CampaignEntity;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by john on 2014/8/19.
 * 2014-12-2 refactor XiaoWei
 */
@Service("assistantKeywordService")
public class AssistantKeywordServiceImpl implements AssistantKeywordService {
    private static Integer OBJ_SIZE = 18;//判断百度id跟本地id长度大小

    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private KeywordBackUpService keywordBackUpService;

    @Resource
    private com.perfect.service.CampaignService campaignService;

    @Resource
    private AdgroupService adgroupService;

    @Resource
    private MonitoringDao monitoringDao;


    //推广计划名称
    private Map<String, CampaignDTO> campaignMap = new HashMap<>();

    //推广单元名称
    private Map<String, AdgroupDTO> adgroupMap = new HashMap<>();

    public Iterable<CampaignDTO> getCampaignByAccountId() {
        return campaignDAO.findAll();
    }

    public Iterable<AdgroupDTO> getAdgroupByCid(String cid) {
        return cid.matches("^\\d+$") ? adgroupDAO.findByCampaignId(Long.parseLong(cid)) : adgroupDAO.findByCampaignOId(cid);
    }

    public void saveSearchwordKeyword(List<KeywordDTO> list) {
        keywordDAO.insertAll(list);
    }


    /**
     * 根据传过来的关键词的多个 long id 查询关键词
     *
     * @param ids
     * @return
     */
    public List<KeywordInfoDTO> getKeywordListByIds(List<Long> ids) {
        List<KeywordDTO> list = keywordDAO.findKeywordByIds(ids);
        List<KeywordInfoDTO> dtoList = new ArrayList<>();
        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> getMap;
        CampaignDTO cam;
        for (KeywordDTO kwd : list) {
            if (!(new ArrayList<>(map.keySet()).contains(kwd.getAdgroupObjId()) || new ArrayList<>(map.keySet()).contains(kwd.getAdgroupId() + ""))) {
                AdgroupDTO ad = kwd.getAdgroupId() == null ? adgroupDAO.findByObjId(kwd.getAdgroupObjId()) : adgroupDAO.findOne(kwd.getAdgroupId());
                cam = ad.getCampaignId() == null ? campaignDAO.findByObjectId(ad.getCampaignObjId()) : campaignDAO.findOne(ad.getCampaignId());
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("adgroup", ad);
                tempMap.put("campaign", cam);
                map.put(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "", tempMap);
            }

            getMap = map.get(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "");

            kwd.setPrice(kwd.getPrice() == null ? BigDecimal.ZERO : kwd.getPrice());
            KeywordInfoDTO dto = new KeywordInfoDTO();
            dto.setCampaignName(((CampaignDTO) getMap.get("campaign")).getCampaignName());
            dto.setAdgroupName(((AdgroupDTO) getMap.get("adgroup")).getAdgroupName());
            dto.setCampaignId(((CampaignDTO) getMap.get("campaign")).getCampaignId());
            dto.setObject(kwd);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public KeywordDTO findByParams(Map<String, Object> params) {
        return keywordDAO.findByParamsObject(params);
    }

    @Override
    public KeywordDTO findByObjId(String obj) {
        return keywordDAO.findByObjectId(obj);
    }

    @Override
    public KeywordInfoDTO findByInfoStrId(String obj) {
        KeywordDTO kwd = keywordDAO.findByObjectId(obj);
        KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
        AdgroupDTO ad = kwd.getAdgroupId() == null ? adgroupDAO.findByObjId(kwd.getAdgroupObjId()) : adgroupDAO.findOne(kwd.getAdgroupId());
        CampaignDTO cam = ad.getCampaignId() == null ? campaignDAO.findByObjectId(ad.getCampaignObjId()) : campaignDAO.findOne(ad.getCampaignId());

        keywordInfoDTO.setObject(kwd);//设置keyword对象

        keywordInfoDTO.setFolderCount(kwd.getKeywordId() == null ? 0l : monitoringDao.getForlderCountByKwid(kwd.getKeywordId()));//设置监控文件夹个数
        keywordInfoDTO.setCampaignName(cam.getCampaignName());
        keywordInfoDTO.setCampaignId(cam.getCampaignId());

        return keywordInfoDTO;
    }

    @Override
    public KeywordInfoDTO findByInfoLongId(Long id) {


        KeywordDTO kwd = keywordDAO.findByLongId(id);

        KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
        AdgroupDTO ad = adgroupDAO.findOne(kwd.getAdgroupId());
        CampaignDTO cam = campaignDAO.findOne(ad.getCampaignId());

        keywordInfoDTO.setObject(kwd);//设置keyword对象

        keywordInfoDTO.setFolderCount(kwd.getKeywordId() == null ? 0l : monitoringDao.getForlderCountByKwid(kwd.getKeywordId()));//设置监控文件夹个数
        keywordInfoDTO.setCampaignName(cam.getCampaignName());
        keywordInfoDTO.setCampaignId(cam.getCampaignId());


        //设置关键词质量度
        BaiduAccountInfoDTO baiduAccountInfoDTO = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
        BaiduApiService apiService = new BaiduApiService(commonService);

        if (kwd.getKeywordId() != null) {//添加质量度相关数据
            List<Long> ids = new ArrayList<>();
            ids.add(kwd.getKeywordId());
            List<QualityType> qualityList = apiService.getKeywordQuality(ids);
            for (QualityType qualityType : qualityList) {
                if (keywordInfoDTO.getObject().getKeywordId() != null && qualityType.getId().longValue() == keywordInfoDTO.getObject().getKeywordId().longValue()) {
                    keywordInfoDTO.setQuality(qualityType.getQuality());
                    keywordInfoDTO.setMobileQuality(qualityType.getMobileQuality());
                    break;
                }
            }
        }
        return keywordInfoDTO;
    }

    @Override
    public KeywordDTO findByLongId(Long id) {
        return keywordDAO.findOne(id);
    }

    @Override
    public void updateByObjId(KeywordDTO dto) {
        keywordDAO.updateByObjId(dto);
    }

    @Override
    public void update(KeywordDTO keywordDTO, KeywordDTO keywordBackUpDTO) {
        keywordDAO.update(keywordDTO, keywordBackUpDTO);
    }

    @Override
    public void insert(KeywordDTO dto) {
        keywordDAO.save(dto);
    }


    public void setNeigWord(String agid, String keywords, Integer neigType) {
        String[] kwds = keywords.split("\n");

        List<String> list = new ArrayList<>();
        for (String kwd : kwds) {
            list.add(kwd);
        }
        AdgroupDTO adgroupDTO = agid.matches("^\\d+$") ? adgroupDAO.findOne(Long.parseLong(agid)) : adgroupDAO.findByObjId(agid);
        AdgroupDTO newAdgroupDTO = new AdgroupDTO();
        BeanUtils.copyProperties(adgroupDTO, newAdgroupDTO);
        if (neigType == 1) {
            adgroupDTO.setNegativeWords(list);
        } else {
            adgroupDTO.setExactNegativeWords(list);
        }
        adgroupDTO.setLocalStatus(2);
        adgroupDAO.update(adgroupDTO, newAdgroupDTO);
    }


    /**
     * 批量添加或者更新关键词
     *
     * @param insertDtos
     * @param updateDtos
     * @param isReplace
     */
    public void batchAddUpdateKeyword(List<KeywordInfoDTO> insertDtos, List<KeywordInfoDTO> updateDtos, Boolean isReplace) {

        List<KeywordDTO> list = new ArrayList<>();
        for (KeywordInfoDTO dto : insertDtos) {
            list.add(dto.getObject());
        }
        //若isReplace值为true 就将替换该单元下的所有关键词,为false就只添加或者更新
        if (isReplace == false) {
            keywordDAO.insertAll(list);

            for (KeywordInfoDTO dto : updateDtos) {
                KeywordDTO keywordDTO = dto.getObject();
                KeywordBackUpDTO backUpEntity = new KeywordBackUpDTO();
                KeywordDTO sourceKeywordEntity = keywordDAO.findByObjectId(keywordDTO.getId());
                BeanUtils.copyProperties(sourceKeywordEntity, backUpEntity);
                keywordDAO.update(keywordDTO, backUpEntity);
            }
        } else {
            Map<String, Object> adgroupIdMap = getNoRepeatAdgroupId(insertDtos, updateDtos);

            //不是本地新增的关键词将要备份
            List<KeywordDTO> keywordBackups = keywordDAO.findByObjectIds(getKwdObjIdByKeywordList(getKwdListByDTO(updateDtos)));

            //根据String id删除该单元的关键词(硬删除)
            keywordDAO.deleteByObjectAdgroupIds(new ArrayList<String>((Set<String>) adgroupIdMap.get("strSet")));
            //根据long id删除该单元的关键词(软删除)
            keywordDAO.softDeleteByLongAdgroupIds(new ArrayList<Long>((Set<Long>) adgroupIdMap.get("longSet")));
            keywordDAO.insertAll(list);

            //开始备份需要备份的关键词
            List<KeywordBackUpDTO> keyWordBackUpEntities = copyKeywordEntityToKeywordBack(keywordBackups);
            keywordBackUpService.myInsertAll(keyWordBackUpEntities);
            keywordDAO.save(getKwdListByDTO(updateDtos));
        }
    }


    /**
     * 根据多个关键词得到这些关键词的所在单元的id(无重复的)
     *
     * @param insertList
     * @param updateList
     * @return
     */
    private Map<String, Object> getNoRepeatAdgroupId(List<KeywordInfoDTO> insertList, List<KeywordInfoDTO> updateList) {
        Set<String> strSet = new HashSet<>();
        Set<Long> longSet = new HashSet<>();
        for (KeywordInfoDTO insertDTO : insertList) {
            if (insertDTO.getObject().getAdgroupId() == null) {
                strSet.add(insertDTO.getObject().getAdgroupObjId());
            } else {
                longSet.add(insertDTO.getObject().getAdgroupId());
            }
        }
        for (KeywordInfoDTO updateDTO : updateList) {
            if (updateDTO.getObject().getAdgroupId() == null) {
                strSet.add(updateDTO.getObject().getAdgroupObjId());
            } else {
                longSet.add(updateDTO.getObject().getAdgroupId());
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("strSet", strSet);
        map.put("longSet", longSet);
        return map;
    }


    private List<KeywordDTO> getKwdListByDTO(List<KeywordInfoDTO> updateDtos) {
        List<KeywordDTO> list = new ArrayList<>();
        for (KeywordInfoDTO dto : updateDtos) {
            list.add(dto.getObject());
        }
        return list;
    }

    private List<String> getKwdObjIdByKeywordList(List<KeywordDTO> list) {
        List<String> objIds = new ArrayList<>();
        for (KeywordDTO kwd : list) {
            objIds.add(kwd.getId());
        }
        return objIds;
    }

    /**
     * 将List<KeywordEntity> 转换为List<KeyWordBackUpEntity>
     *
     * @param list
     * @return
     */
    private List<KeywordBackUpDTO> copyKeywordEntityToKeywordBack(List<KeywordDTO> list) {
        List<KeywordBackUpDTO> backList = new ArrayList<>();
        for (KeywordDTO kwd : list) {
            KeywordBackUpDTO kwdBack = new KeywordBackUpDTO();
            kwdBack.setId(kwd.getId());
            kwdBack.setKeywordId(kwd.getKeywordId());
            kwdBack.setAdgroupId(kwd.getAdgroupId());
            kwdBack.setAdgroupObjId(kwd.getAdgroupObjId());
            kwdBack.setKeyword(kwd.getKeyword());
            kwdBack.setPrice(kwd.getPrice());
            kwdBack.setPcDestinationUrl(kwd.getPcDestinationUrl());
            kwdBack.setMobileDestinationUrl(kwd.getMobileDestinationUrl());
            kwdBack.setMatchType(kwd.getMatchType());
            kwdBack.setPause(kwd.getPause());
            kwdBack.setStatus(kwd.getStatus());
            kwdBack.setPhraseType(kwd.getPhraseType());
            kwdBack.setLocalStatus(kwd.getLocalStatus());
            kwdBack.setOrderBy(kwd.getOrderBy());
            kwdBack.setAccountId(kwd.getAccountId());
            backList.add(kwdBack);
        }
        return backList;
    }


    /**
     * 根据账户id得到关键词
     *
     * @return
     */

    @Override
    public PagerInfo getKeyWords(String cid, String aid, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
        String regex = "^\\d+$";
        if (nowPage == null) {
            nowPage = 0;
        }
        PagerInfo page = null;

        CampaignDTO campaignDTO = null;
        if (cid != null && !"".equals(cid)) {
            if (cid.matches(regex)) {
                campaignDTO = campaignDAO.findOne(Long.parseLong(cid));
            } else {
                campaignDTO = campaignDAO.findByObjectId(cid);
            }

            if (campaignDTO == null) {
                return new PagerInfo();
            }
        }


        Map<String, Object> map = null;

        //若cid和aid都不为空，就是查询某单元下的关键词,在aid为空的时候就查询该计划下的关键词
        if (cid != null && !"".equals(cid) && aid != null && !"".equals(aid)) {
            if (aid.matches(regex)) {
                page = keywordDAO.findByPageInfoForLongId(Long.parseLong(aid), pageSize, nowPage, sp);
            } else {
                page = keywordDAO.findByPageInfoForStringId(aid, pageSize, nowPage, sp);
            }
        } else if (cid != null && !"".equals(cid) && (aid == null || "".equals(aid))) {
            if (campaignDTO.getCampaignId() != null) {
                List<Long> longIds = new ArrayList<>();
                longIds.addAll(adgroupDAO.getAdgroupIdByCampaignId(campaignDTO.getCampaignId()));
                page = keywordDAO.findByPageInfoForLongIds(longIds, pageSize, nowPage, sp);
            } else {
                List<String> objIds = new ArrayList<>();
                objIds.addAll(adgroupDAO.getAdgroupIdByCampaignId(campaignDTO.getId()));
                page = keywordDAO.findByPageInfoForStringIds(objIds, pageSize, nowPage, sp);
            }
        } else {
            page = keywordDAO.findByPageInfoForAcctounId(pageSize, nowPage, sp);
        }

        page.setList(setCampaignNameByKeywordEntitys((List<KeywordDTO>) page.getList(), campaignDTO));

        return page;
    }


    private List<KeywordInfoDTO> setCampaignNameByKeywordEntitys(List<KeywordDTO> list, CampaignDTO camp) {
        List<KeywordInfoDTO> dtoList = new ArrayList<>();
        List<Long> keywordIds = new ArrayList<>();

        Map<String, CampaignDTO> map = new HashMap<>();
        CampaignDTO cam = null;
        for (KeywordDTO kwd : list) {
            if (kwd.getKeywordId() != null) {
                keywordIds.add(kwd.getKeywordId());
            }

            if (camp == null) {
                if (!(new ArrayList<>(map.keySet()).contains(kwd.getAdgroupObjId()) || new ArrayList<>(map.keySet()).contains(kwd.getAdgroupId() + ""))) {
                    AdgroupDTO ad = kwd.getAdgroupId() == null ? adgroupDAO.findByObjId(kwd.getAdgroupObjId()) : adgroupDAO.findOne(kwd.getAdgroupId());
                    cam = ad.getCampaignId() == null ? campaignDAO.findByObjectId(ad.getCampaignObjId()) : campaignDAO.findOne(ad.getCampaignId());
                    map.put(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "", cam);
                }

                cam = map.get(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "");
                KeywordInfoDTO dto = new KeywordInfoDTO();
                dto.setFolderCount(0l);
                dto.setCampaignName(cam.getCampaignName());
                dto.setObject(kwd);
                dto.setCampaignId(cam.getCampaignId());
                dtoList.add(dto);
            } else {
                KeywordInfoDTO dto = new KeywordInfoDTO();
                dto.setFolderCount(kwd.getKeywordId() == null ? 0l : monitoringDao.getForlderCountByKwid(kwd.getKeywordId()));
                dto.setCampaignName(camp.getCampaignName());
                dto.setObject(kwd);
                dto.setCampaignId(camp.getCampaignId());
                dtoList.add(dto);
            }

        }
        //在百度上得到关键词的质量度
        BaiduAccountInfoDTO baiduAccountInfoDTO = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
        BaiduApiService apiService = new BaiduApiService(commonService);
        List<QualityType> qualityList = apiService.getKeywordQuality(keywordIds);
        for (QualityType qualityType : qualityList) {
            for (KeywordInfoDTO dto : dtoList) {
                if (dto.getObject().getKeywordId() != null && qualityType.getId().longValue() == dto.getObject().getKeywordId().longValue()) {
                    dto.setQuality(qualityType.getQuality());
                    dto.setMobileQuality(qualityType.getMobileQuality());
                    break;
                }
            }
        }

        return dtoList;
    }


    /**
     * 根据关键词id删除关键词
     *
     * @param kwids
     */
    @Override
    public void deleteByKwIds(List<String> kwids) {
        String regex = "^\\d+$";
        for (String id : kwids) {
            if (id.matches(regex)) {
                keywordDAO.softDelete(Long.parseLong(id));
            } else {
                keywordDAO.deleteById(id);
            }
        }
    }


    /**
     * 修改关键词信息
     *
     * @param kwd
     */
    @Override
    public KeywordDTO updateKeyword(KeywordDTO kwd) {

        KeywordDTO newKeywordDTO;

        if (kwd.getKeywordId() == null) {
            newKeywordDTO = keywordDAO.findByObjectId(kwd.getId());
        } else {
            newKeywordDTO = keywordDAO.findOne(kwd.getKeywordId());
        }
        KeywordBackUpDTO keywordBackUpDTO = new KeywordBackUpDTO();
        BeanUtils.copyProperties(newKeywordDTO, keywordBackUpDTO);


        if (newKeywordDTO.getKeywordId() == null) {
            newKeywordDTO.setLocalStatus(1);
        } else {
            newKeywordDTO.setLocalStatus(2);
        }

        if (kwd.getKeyword() != null) {
            newKeywordDTO.setKeyword(kwd.getKeyword());
        }

        if (kwd.getPrice() != null) {
            newKeywordDTO.setPrice(kwd.getPrice());
        }
        if (kwd.getPcDestinationUrl() != null) {
            newKeywordDTO.setPcDestinationUrl(kwd.getPcDestinationUrl());
        }
        if (kwd.getMobileDestinationUrl() != null) {
            newKeywordDTO.setMobileDestinationUrl(kwd.getMobileDestinationUrl());
        }
        if (kwd.getMatchType() != null) {
            newKeywordDTO.setMatchType(kwd.getMatchType());
        }
        if (kwd.getPhraseType() != null) {
            newKeywordDTO.setPhraseType(kwd.getPhraseType());
        }
        if (kwd.getPause() != null) {
            newKeywordDTO.setPause(kwd.getPause());
        }

        if (kwd.getAdgroupId() != null) {
            newKeywordDTO.setAdgroupId(kwd.getAdgroupId());
        }
        if (kwd.getAdgroupObjId() != null) {
            newKeywordDTO.setAdgroupObjId(kwd.getAdgroupObjId());
        }

        keywordDAO.update(newKeywordDTO, keywordBackUpDTO);
        return newKeywordDTO;
    }


    /**
     * 查询推广计划
     *
     * @param query
     * @return
     */
//    public List<CampaignDTO> findByQuery(Query query) {
//        return campaignDAO.find(query);
//    }


    /**
     * 根据当前账户id得到该账户下的推广计划树
     *
     * @param accountId
     * @return
     */
    @Override
    public List<CampaignTreeDTO> getCampaignTree(Long accountId) {
        List<CampaignTreeDTO> campaignTreeList = new ArrayList<>();
        Iterable<CampaignDTO> campaignList = campaignDAO.findAll();

        for (CampaignDTO campaignDTO : campaignList) {
            List<AdgroupDTO> adgroupList = adgroupDAO.findByTwoParams(campaignDTO.getCampaignId(), AppContext.getAccountId());
            CampaignTreeDTO campaignTree = new CampaignTreeDTO();
            campaignTree.setRootNode(campaignDTO);//设置根节点
            campaignTree.setChildNode(adgroupList);//设置子节点
            campaignTreeList.add(campaignTree);
        }
        return campaignTreeList;
    }

    @Override
    public Iterable<KeywordDTO> findAll() {
        return keywordDAO.findAll();
    }

    @Override
    public List<KeywordDTO> findHasLocalStatus() {
        return keywordDAO.findHasLocalStatus();
    }

    @Override
    public List<KeywordDTO> findHasLocalStatusStr(List<AdgroupDTO> adgroupDTOStr) {
        List<String> strs = new ArrayList<>();
        for (AdgroupDTO str : adgroupDTOStr) {
            if (str.getAdgroupId() == null) {
                strs.add(str.getId());
            }
        }
        return keywordDAO.findHasLocalStatusStr(strs);
    }

    @Override
    public List<KeywordDTO> findHasLocalStatusLong(List<AdgroupDTO> adgroupDTOLong) {
        List<Long> longs = new ArrayList<>();
        for (AdgroupDTO str : adgroupDTOLong) {
            if (str.getAdgroupId() != null) {
                longs.add(str.getAdgroupId());
            }
        }
        return keywordDAO.findHasLocalStatusLong(longs);
    }


    /**
     * (用户的选择计划，单元，输入的关键词的方式)
     *
     * @param chooseInfos
     * @param keywordNames
     */
    @Override
    public Map<String, Object> validateDeleteKeywordByChoose(Long accountId, String chooseInfos, String keywordNames, Integer nowPage, Integer pageSize) {
        String regex = "^\\d+$";
        String[] everyChoose = chooseInfos.split("-");
        String[] names = keywordNames.split("\n");

        Map<String, Object> map = new HashMap<>();
        //被忽略删除的关键词
        List<AssistantKeywordIgnoreDTO> ignoreList = new ArrayList<>();

        //可删除的关键词集合
        List<KeywordInfoDTO> deleteKwd = new ArrayList<>();

        for (String row : everyChoose) {
            String[] fileds = row.split(",");//fileds[0]推广计划id，fileds[1]推广单元id
            adgroupMap.clear();
            for (String name : names) {
                List<KeywordDTO> list;
                if (fileds[1].matches(regex)) {
                    list = keywordDAO.findByParams(new HashMap<String, Object>() {{
                        put(MongoEntityConstants.ADGROUP_ID, Long.parseLong(fileds[1]));
                        put("name", name);
                    }});
//                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(Long.parseLong(fileds[1])).and("name").is(name)));
                } else {
                    list = keywordDAO.findByParams(new HashMap<String, Object>() {{
                        put(MongoEntityConstants.OBJ_ADGROUP_ID, fileds[1]);
                        put("name", name);
                    }});
//                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(.MongoEntityConstantsOBJ_ADGROUP_ID).is(fileds[1]).and("name").is(name)));
                }


                if (!(new ArrayList<>(campaignMap.keySet()).contains(fileds[0]))) {
                    CampaignDTO camName = fileds[0].matches(regex) ? campaignDAO.findOne(Long.parseLong(fileds[0])) : campaignDAO.findByObjectId(fileds[0]);
                    campaignMap.put(fileds[0], camName);
                }

                if (!(new ArrayList<>(adgroupMap.keySet()).contains(fileds[1]))) {
                    AdgroupDTO adgName = fileds[1].matches(regex) ? adgroupDAO.findOne(Long.parseLong(fileds[1])) : adgroupDAO.findByObjId(fileds[0]);
                    adgroupMap.put(fileds[1], adgName);
                }


                if (list.size() != 0) {
                    for (KeywordDTO entity : list) {
                        KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
                        keywordInfoDTO.setCampaignName(campaignMap.get(fileds[0]).getCampaignName());
                        keywordInfoDTO.setAdgroupName(adgroupMap.get(fileds[1]).getAdgroupName());
                        keywordInfoDTO.setObject(entity);
                        deleteKwd.add(keywordInfoDTO);
                    }
                } else {
                    AssistantKeywordIgnoreDTO assistantKeywordIgnoreDTO = new AssistantKeywordIgnoreDTO();
                    assistantKeywordIgnoreDTO.setCampaignName(campaignMap.get(fileds[0]).getCampaignName());
                    assistantKeywordIgnoreDTO.setAdgroupName(adgroupMap.get(fileds[1]).getAdgroupName());
                    assistantKeywordIgnoreDTO.setKeywordName(name);
                    ignoreList.add(assistantKeywordIgnoreDTO);
                }
            }
        }

        map.put("ignoreList", ignoreList);
        map.put("deleteKwd", deleteKwd);

        return map;
    }


    /**
     * (输入的方式)
     * 根据用户输入的删除信息（计划名称，单元名称，关键词名称）批量删除关键词
     *
     * @param accountId
     * @param deleteInfos
     */
    @Override
    public Map<String, Object> validateDeleteByInput(Long accountId, String deleteInfos) {
        Map<String, Object> map = new HashMap<>();

        //被忽略删除的关键词
        List<AssistantKeywordIgnoreDTO> ignoreList = new ArrayList<>();

        //可删除的关键词集合
        List<KeywordInfoDTO> deleteKwd = new ArrayList<>();

        String[] everyDeleInfo = deleteInfos.split("\n");

        Map<String, CampaignDTO> camMap = new HashMap<>();
        Map<String, AdgroupDTO> adgMap = new HashMap<>();


        for (String str : everyDeleInfo) {
            String[] fields = str.split(",|，|\t");

//            if (!(new ArrayList<>(camMap.keySet()).contains(fields[0]))) {
//                List<CampaignDTO> campaignEntityList = findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[0])));
//                CampaignDTO campaignEntity = campaignEntityList == null || campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);
//                camMap.put(fields[0], campaignEntity);
//            }
            if (camMap.get(fields[0]) != null) {

//                if (!(new ArrayList<>(adgMap.keySet()).contains(fields[1]))) {
//                    List<AdgroupDTO> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[1])));
//                    AdgroupDTO adgroupEntity = adgroupList == null || adgroupList.size() == 0 ? null : adgroupList.get(0);
//                    adgMap.put(fields[1], adgroupEntity);
//                }

                if (adgMap.get(fields[1]) != null) {
                    List<KeywordDTO> keywordList;
//                    if (adgMap.get(fields[1]).getAdgroupId() == null) {
//                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(OBJ_ADGROUP_ID).is(adgMap.get(fields[1]).getId()).and("name").is(fields[2])));
//                    } else {
//                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(adgMap.get(fields[1]).getAdgroupId()).and("name").is(fields[2])));
//                    }
//                    if (keywordList.size() != 0) {
//                        for (KeywordDTO entity : keywordList) {
//                            KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
//                            keywordInfoDTO.setCampaignName(fields[0]);
//                            keywordInfoDTO.setAdgroupName(fields[1]);
//                            keywordInfoDTO.setObject(entity);
//                            deleteKwd.add(keywordInfoDTO);
//                        }
//                    } else {
//                        ignoreList.add(setFiledIgnore(fields));
//                    }
                } else {
                    ignoreList.add(setFiledIgnore(fields));
                }
            } else {
                ignoreList.add(setFiledIgnore(fields));
            }
        }

        map.put("ignoreList", ignoreList);
        map.put("deleteKwd", deleteKwd);

        return map;
    }


    public AssistantKeywordIgnoreDTO setFiledIgnore(String[] fields) {
        AssistantKeywordIgnoreDTO ignoreDeleEntity = new AssistantKeywordIgnoreDTO();
        ignoreDeleEntity.setCampaignName(fields[0]);
        ignoreDeleEntity.setAdgroupName(fields[1]);
        ignoreDeleEntity.setKeywordName(fields[2]);
        ignoreDeleEntity.setMatchModel("广泛");
        return ignoreDeleEntity;
    }


    /**
     * （用户选择计划，单元，输入关键词的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     *
     * @param accountId    当前账户id
     * @param isReplace    是否将用户输入的信息替换该单元下相应的内容
     * @param chooseInfos  用户选择的推广计划和退关单元
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    public Map<String, Object> batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos) {
//        String regex = "^\\d+$";
//
//        //可被添加的关键词list
//        List<KeywordInfoDTO> insertList = new ArrayList<>();
//
//        //可被修改的关键词list
//        List<KeywordInfoDTO> updateList = new ArrayList<>();
//
//        //可被忽略的关键词list
//        List<AssistantKeywordIgnoreDTO> igoneList = new ArrayList<>();
//
//        String[] everyRow = chooseInfos.split("-");
//        String[] everyInfo = keywordInfos.split("\n");
//
//        for (String row : everyRow) {
//            //切割出推广计划和推广单元ID
//            String[] fieds = row.split(",");
//            for (String info : everyInfo) {
//                String[] kwInfo = info.split(",|，|\t");
//                KeywordDTO keywordDTO = validateKewword(kwInfo);
//                List<KeywordDTO> list;
//                if (fieds[1].matches(regex)) {
//                    list = keywordDAO.findByParams(new HashMap<String, Object>() {{
//                        put(MongoEntityConstants.ADGROUP_ID, Long.parseLong(fieds[1]));
//                        put("name",keywordDTO.getKeyword());
//                    }});
////                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(Long.parseLong(fieds[1])).and("name").is(keywordDTO.getKeyword())));
//                } else {
//                    list = keywordDAO.findByParams(new HashMap<String, Object>() {{
//                        put(MongoEntityConstants.OBJ_ADGROUP_ID, fieds[1]);
//                        put("name",keywordDTO.getKeyword());
//                    }});
////                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(fieds[1]).and("name").is(keywordDTO.getKeyword())));
//                }
//
//                if (list.size() > 0 && kwInfo.length == 1) {
//                    AssistantKeywordIgnoreDTO dto = new AssistantKeywordIgnoreDTO();
//
//                    if (fieds[0].matches(regex)) {
//                        dto.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
//                    } else {
//                        dto.setCampaignName(campaignDAO.findByObjectId(fieds[0]).getCampaignName());
//                    }
//
//                    if (fieds[1].matches(regex)) {
//                        dto.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());
//                    } else {
//                        dto.setAdgroupName(adgroupDAO.findByObjId(fieds[1]).getAdgroupName());
//                    }
//                    dto.setKeywordName(kwInfo[0]);
//                    igoneList.add(dto);
//                    continue;
//                }
//
//                if (list.size() == 0) {
//                    insertList.add(setFieldToDTO(fieds, keywordDTO, null));
//                } else {
//                    for (KeywordDTO entity : list) {
//                        updateList.add(setFieldToDTO(fieds, keywordDTO, entity));
//                    }
//                }
//            }
//        }
//
        Map<String, Object> map = new HashMap<>();
//        map.put("insertList", insertList);
//        map.put("updateList", updateList);
//        map.put("igoneList", igoneList);

        return map;
    }


    private KeywordInfoDTO setFieldToDTO(String[] fieds, KeywordDTO updateKeywordDTO, KeywordDTO beforeKeywordDTO) {
        String regex = "^\\d+$";
        KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();

        if (!(new ArrayList<>(campaignMap.keySet()).contains(fieds[0]))) {
            CampaignDTO campaign = fieds[0].matches(regex) ? campaignDAO.findOne(Long.parseLong(fieds[0])) : campaignDAO.findByObjectId(fieds[0]);
            campaignMap.put(fieds[0], campaign);
        }

        if (!(new ArrayList<>(adgroupMap.keySet()).contains(fieds[1]))) {
            AdgroupDTO adgroupName = fieds[1].matches(regex) ? adgroupDAO.findOne(Long.parseLong(fieds[1])) : adgroupDAO.findByObjId(fieds[1]);
            adgroupMap.put(fieds[1], adgroupName);
        }

        keywordInfoDTO.setCampaignName(campaignMap.get(fieds[0]).getCampaignName());
        keywordInfoDTO.setAdgroupName(adgroupMap.get(fieds[1]).getAdgroupName());


        if (beforeKeywordDTO == null) {
            updateKeywordDTO.setAccountId(AppContext.getAccountId());
            updateKeywordDTO.setStatus(-1);
            updateKeywordDTO.setLocalStatus(1);
            if (adgroupMap.get(fieds[1]).getCampaignId() == null) {
                updateKeywordDTO.setAdgroupObjId(adgroupMap.get(fieds[1]).getCampaignObjId());
            } else {
                updateKeywordDTO.setAdgroupId(adgroupMap.get(fieds[1]).getAdgroupId());
            }
            keywordInfoDTO.setObject(updateKeywordDTO);
        } else {
            beforeKeywordDTO.setPause(updateKeywordDTO.getPause());

            if (updateKeywordDTO.getMatchType() != null) {
                beforeKeywordDTO.setMatchType(updateKeywordDTO.getMatchType());
            } else {
                beforeKeywordDTO.setMatchType(2);
            }
            if (updateKeywordDTO.getPhraseType() != null) {
                beforeKeywordDTO.setPhraseType(updateKeywordDTO.getPhraseType());
            }
            if (updateKeywordDTO.getPrice() != null) {
                beforeKeywordDTO.setPrice(updateKeywordDTO.getPrice());
            }
            if (updateKeywordDTO.getPcDestinationUrl() != null) {
                beforeKeywordDTO.setPcDestinationUrl(updateKeywordDTO.getPcDestinationUrl());
            }
            if (updateKeywordDTO.getMobileDestinationUrl() != null) {
                beforeKeywordDTO.setMobileDestinationUrl(updateKeywordDTO.getMobileDestinationUrl());
            }

            if (beforeKeywordDTO.getLocalStatus() == null) {
                beforeKeywordDTO.setLocalStatus(2);
            }

            keywordInfoDTO.setObject(beforeKeywordDTO);
        }

        return keywordInfoDTO;
    }


    /**
     * 验证输入的关键词数据，若输入的不合法，就赋一个默认值
     *
     * @param kwInfo
     * @return
     */
    private KeywordDTO validateKewword(String[] kwInfo) {
        KeywordDTO keywordDTO = new KeywordDTO();

        keywordDTO.setKeyword(kwInfo[0]);
        for (int i = 1; i < kwInfo.length; i++) {
            if (i == 1) {
                if ("广泛".equals(kwInfo[i])) {
                    keywordDTO.setMatchType(3);
                } else if ("短语-同义包含".equals(kwInfo[i])) {
                    keywordDTO.setMatchType(2);
                    keywordDTO.setPhraseType(1);
                } else if ("短语-精确包含".equals(kwInfo[i])) {
                    keywordDTO.setMatchType(2);
                    keywordDTO.setPhraseType(2);
                } else if ("短语-核心包含".equals(kwInfo[i])) {
                    keywordDTO.setMatchType(2);
                    keywordDTO.setPhraseType(3);
                } else {
                    keywordDTO.setMatchType(1);
                }
            } else if (i == 2) {
                if (kwInfo[i].matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$")) {
                    keywordDTO.setPrice(BigDecimal.valueOf(Double.parseDouble(kwInfo[i])));
                } else {
                    keywordDTO.setPrice(null);
                }
            } else if (i == 3) {
                keywordDTO.setPcDestinationUrl(kwInfo[i]);
            } else if (i == 4) {
                keywordDTO.setMobileDestinationUrl(kwInfo[i]);
            } else if (i == 5) {
                if ("暂停".equals(kwInfo[i])) {
                    keywordDTO.setPause(true);
                } else {
                    keywordDTO.setPause(false);
                }
            }
        }

        if (keywordDTO.getPause() == null) {
            keywordDTO.setPause(false);
        }
        if (keywordDTO.getMatchType() == null) {
            keywordDTO.setMatchType(1);
        }

        return keywordDTO;
    }

    @Override
    public List<KeywordDTO> uploadAdd(List<String> kids) {
        List<KeywordDTO> retrunKeywordDTOs = new ArrayList<>();
        List<KeywordType> keywordTypes = new ArrayList<>();
        kids.stream().forEach(s -> {
            KeywordDTO keywordDTO = keywordDAO.findByObjectId(s);
//            AdgroupDTO adgroupDTO = adgroupDAO.findOne(keywordDTO.getAdgroupId());
            if (keywordDTO.getAdgroupId() != null) {
                KeywordType keywordType = new KeywordType();
                keywordType.setAdgroupId(keywordDTO.getAdgroupId());
                keywordType.setKeyword(keywordDTO.getKeyword());
                keywordType.setMatchType(keywordDTO.getMatchType());
                keywordType.setPrice(Double.parseDouble(keywordDTO.getPrice() + ""));
                keywordType.setPcDestinationUrl(keywordDTO.getPcDestinationUrl());
                keywordType.setMobileDestinationUrl(keywordDTO.getMobileDestinationUrl());
                keywordType.setPause(keywordDTO.getPause());
                keywordTypes.add(keywordType);
            }
        });
        //这里需要告诉用户哪些关键字没有上传成功，原因是该关键字的上级单元也没有上传，所以，这个方法以后要改为map类型的返回值，一个返回成功的List，一个返回失败的List
        if (keywordTypes.size() > 0) {//这里判断是否有符合条件的关键词
            BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());

            try {
                KeywordService keywordService = commonService.getService(KeywordService.class);
                AddKeywordRequest addKeywordRequest = new AddKeywordRequest();
                addKeywordRequest.setKeywordTypes(keywordTypes);
                AddKeywordResponse addKeywordResponse = keywordService.addKeyword(addKeywordRequest);
                List<KeywordType> returnKeywordList = addKeywordResponse.getKeywordTypes();
                returnKeywordList.stream().filter(s -> s.getKeywordId() != null).forEach(s -> {
                    KeywordDTO returnKeywordDTO = new KeywordDTO();
                    returnKeywordDTO.setKeywordId(s.getKeywordId());
                    returnKeywordDTO.setStatus(s.getStatus());
                    retrunKeywordDTOs.add(returnKeywordDTO);
                });
                return retrunKeywordDTOs;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return retrunKeywordDTOs;
    }

    @Override
    public List<KeywordDTO> uploadAddByUp(String kid) {
        List<KeywordDTO> returnKeywordDTOs = new ArrayList<>();
        KeywordDTO keywordDTOFind = keywordDAO.findByObjectId(kid);//查询出要上传的关键字的oagid，根据oagid去查询本地的单元，根据oaid查询ocid查询出计划，并两者上传
        AdgroupDTO adgroupDTOFind = adgroupDAO.findByObjId(keywordDTOFind.getAdgroupObjId());//根据关键字的oagid查询到本地的单元记录
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
                add(keywordDTOFind.getAdgroupObjId());
            }});
            //上传完毕后执行修改单元操作
            returnAids.stream().forEach(f -> adgroupService.update(keywordDTOFind.getAdgroupObjId(), f));
            //单元级联上传 end

            //最后上传关键字
            returnKeywordDTOs = uploadAdd(new ArrayList<String>() {{
                add(kid);
            }});
        }
        return returnKeywordDTOs;
    }

    @Override
    public void update(String oid, KeywordDTO dto) {
        keywordDAO.update(oid, dto);
    }

    @Override
    public Integer uploadDel(Long kid) {
        Integer result = 0;
        BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            DeleteKeywordRequest deleteKeywordRequest = new DeleteKeywordRequest();
            deleteKeywordRequest.setKeywordIds(new ArrayList<Long>() {{
                add(kid);
            }});
            DeleteKeywordResponse deleteKeywordResponse = keywordService.deleteKeyword(deleteKeywordRequest);
            if (deleteKeywordResponse.getResult() == 1) {//如果全部删除成功，则执行删除本地的关键字
                keywordDAO.deleteByIds(new ArrayList<Long>() {{
                    add(kid);
                }});
            }
            return deleteKeywordResponse.getResult();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<KeywordDTO> uploadUpdate(List<Long> kid) {
        List<KeywordDTO> returnKeywordDTOs = new ArrayList<>();
        List<KeywordType> keywordTypes = new ArrayList<>();
        kid.stream().forEach(s -> {
            KeywordDTO dtoFind = keywordDAO.findOne(s);
            if (dtoFind != null) {
                KeywordType keywordType = new KeywordType();
                keywordType.setKeywordId(dtoFind.getKeywordId());
                keywordType.setAdgroupId(dtoFind.getAdgroupId());
                keywordType.setMatchType(dtoFind.getMatchType());
                keywordType.setPrice(Double.parseDouble(dtoFind.getPrice() + ""));
                keywordType.setPhraseType(dtoFind.getPhraseType());
                keywordType.setPcDestinationUrl(dtoFind.getPcDestinationUrl());
                keywordType.setMobileDestinationUrl(dtoFind.getMobileDestinationUrl());
                keywordType.setPause(dtoFind.getPause());
                keywordTypes.add(keywordType);
            }
        });
        if (keywordTypes.size() > 0) {
            BaiduAccountInfoDTO bad = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
            try {
                KeywordService keywordService = commonService.getService(KeywordService.class);
                UpdateKeywordRequest updateKeywordRequest = new UpdateKeywordRequest();
                updateKeywordRequest.setKeywordTypes(keywordTypes);
                UpdateKeywordResponse updateKeywordResponse = keywordService.updateKeyword(updateKeywordRequest);
                List<KeywordType> returnKeywordTypes = updateKeywordResponse.getKeywordTypes();
                returnKeywordTypes.stream().filter(s -> s != null).forEach(s -> {//这里进行判定，如果返回不为null，则进行修改本地的ls为null，表示上传修改操作已经完成
                    KeywordDTO keywordDTO = new KeywordDTO();
                    keywordDTO.setKeywordId(s.getKeywordId());
                    keywordDTO.setStatus(s.getStatus());
                    keywordDAO.updateLs(keywordDTO);
                    returnKeywordDTOs.add(keywordDTO);
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return returnKeywordDTOs;
    }

    @Override
    public Map<String, Map<String, List<String>>> getNoKeywords(Long aid) {
        return keywordDAO.getNoKeywords(aid);
    }

    @Override
    public Map<String, Map<String, List<String>>> getNoKeywords(String aid) {
        return keywordDAO.getNoKeywords(aid);
    }

    @Override
    public List<KeywordInfoDTO> getKeywordInfoByCampaignIdStr(String cid) {
        List<KeywordInfoDTO> keywordInfoDTOs = new ArrayList<>();

        List<String> adgroupIds = adgroupDAO.getAdgroupIdByCampaignId(cid);

        List<KeywordDTO> keywordDTOs = keywordDAO.findKeywordByAdgroupIdsStr(adgroupIds);

        keywordDTOs.stream().forEach(s -> {
            KeywordDTO kwd = s;
            KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
            AdgroupDTO ad = kwd.getAdgroupId() == null ? adgroupDAO.findByObjId(kwd.getAdgroupObjId()) : adgroupDAO.findOne(kwd.getAdgroupId());
            CampaignDTO cam = ad.getCampaignId() == null ? campaignDAO.findByObjectId(ad.getCampaignObjId()) : campaignDAO.findOne(ad.getCampaignId());

            keywordInfoDTO.setObject(kwd);//设置keyword对象

            keywordInfoDTO.setFolderCount(kwd.getKeywordId() == null ? 0l : monitoringDao.getForlderCountByKwid(kwd.getKeywordId()));//设置监控文件夹个数
            keywordInfoDTO.setCampaignName(cam.getCampaignName());
            keywordInfoDTO.setCampaignId(cam.getCampaignId());
            keywordInfoDTOs.add(keywordInfoDTO);
        });
        return keywordInfoDTOs;
    }

    @Override
    public List<KeywordInfoDTO> getKeywordInfoByCampaignIdLong(Long cid) {
        List<KeywordInfoDTO> keywordInfoDTOs = new ArrayList<>();

        List<Long> adgroupIds = adgroupDAO.getAdgroupIdByCampaignId(cid);

        List<KeywordDTO> keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(adgroupIds);

        keywordDTOs.stream().forEach(s -> {
            KeywordDTO kwd = s;

            KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
            AdgroupDTO ad = adgroupDAO.findOne(kwd.getAdgroupId());
            CampaignDTO cam = campaignDAO.findOne(ad.getCampaignId());

            keywordInfoDTO.setObject(kwd);//设置keyword对象

            keywordInfoDTO.setFolderCount(kwd.getKeywordId() == null ? 0l : monitoringDao.getForlderCountByKwid(kwd.getKeywordId()));//设置监控文件夹个数
            keywordInfoDTO.setCampaignName(cam.getCampaignName());
            keywordInfoDTO.setCampaignId(cam.getCampaignId());


            //设置关键词质量度
            BaiduAccountInfoDTO baiduAccountInfoDTO = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);

            if (kwd.getKeywordId() != null) {//添加质量度相关数据
                List<Long> ids = new ArrayList<>();
                ids.add(kwd.getKeywordId());
                List<QualityType> qualityList = apiService.getKeywordQuality(ids);
                for (QualityType qualityType : qualityList) {
                    if (keywordInfoDTO.getObject().getKeywordId() != null && qualityType.getId().longValue() == keywordInfoDTO.getObject().getKeywordId().longValue()) {
                        keywordInfoDTO.setQuality(qualityType.getQuality());
                        keywordInfoDTO.setMobileQuality(qualityType.getMobileQuality());
                        break;
                    }
                }
            }
            keywordInfoDTOs.add(keywordInfoDTO);
        });
        return keywordInfoDTOs;
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
                    List<KeywordDTO> keywordDTOs;
                    if (dataId.length() < 24) {
                        List<Long> longs = Lists.newArrayList(Long.valueOf(param.getAdgroupId()));
                        keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(longs);
                    } else {
                        List<String> strings = Lists.newArrayList(param.getAdgroupId());
                        keywordDTOs = keywordDAO.findKeywordByAdgroupIdsStr(strings);
                    }
                    asList.clear();
                    keywordDTOs.forEach(e -> {
                        if (e.getKeywordId() != null) {
                            asList.add(String.valueOf(e.getKeywordId()));
                        } else {
                            asList.add(e.getId());
                        }
                    });
                } else {
                    List<KeywordDTO> keywordDTOs;
                    if (dataId.length() < 24) {
                        List<String> strings = Lists.newArrayList();
                        List<Long> longs = Lists.newArrayList();
                        adgroupDAO.findByCampaignId(Long.valueOf(param.getCampaignId())).forEach(e -> {
                            if (e.getAdgroupId() != null) longs.add(e.getAdgroupId());
                            else strings.add(e.getId());

                        });
                        keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(longs);
                        List<KeywordDTO> dtos = keywordDAO.findKeywordByAdgroupIdsStr(strings);
                        if (!Objects.isNull(dtos)) keywordDTOs.addAll(dtos);
                    } else {
                        List<String> strings = Lists.newArrayList();
                        List<Long> longs = Lists.newArrayList();
                        adgroupDAO.findByCampaignOId(param.getCampaignId()).forEach(e -> {
                            if (e.getAdgroupId() != null) {
                                longs.add(e.getAdgroupId());
                            } else {
                                strings.add(e.getId());
                            }
                        });
                        keywordDTOs = keywordDAO.findKeywordByAdgroupIdsLong(longs);
                        List<KeywordDTO> dtos = keywordDAO.findKeywordByAdgroupIdsStr(strings);
                        if (!Objects.isNull(dtos)) keywordDTOs.addAll(dtos);
                    }
                    asList.clear();
                    keywordDTOs.forEach(e -> {
                        if (e.getKeywordId() != null) {
                            asList.add(String.valueOf(e.getKeywordId()));
                        } else {
                            asList.add(e.getId());
                        }
                    });
                }
            }
            keywordDAO.batchDelete(asList);
        }
    }

    @Override
    public void cut(KeywordDTO dto, String aid) {
        KeywordBackUpDTO backUpDTO = new KeywordBackUpDTO();
        BeanUtils.copyProperties(dto, backUpDTO);
        if (aid.length() > OBJ_SIZE) {
            dto.setAdgroupObjId(aid);
            dto.setLocalStatus(1);
        } else {
            dto.setAdgroupId(Long.valueOf(aid));
            dto.setLocalStatus(2);
        }
        keywordDAO.update(dto, backUpDTO);
    }


}

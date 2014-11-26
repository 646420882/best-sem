package com.perfect.service.impl;

import com.perfect.api.baidu.QualityTypeService;
import com.perfect.autosdk.sms.v3.QualityType;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dao.MonitoringDao;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeyWordBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.campaign.CampaignTreeDTO;
import com.perfect.dto.keyword.AssistantKeywordIgnoreDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.service.AssistantKeywordService;
import com.perfect.service.KeyWordBackUpService;
import com.perfect.utils.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by john on 2014/8/19.
 */
@Service("assistantKeywordService")
public class AssistantKeywordServiceImpl implements AssistantKeywordService {

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private KeyWordBackUpService keyWordBackUpService;

    @Resource
    private QualityTypeService qualityTypeService;

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
            dto.setCampaignName(((CampaignEntity) getMap.get("campaign")).getCampaignName());
            dto.setAdgroupName(((AdgroupEntity) getMap.get("adgroup")).getAdgroupName());
            dto.setCampaignId(((CampaignEntity) getMap.get("campaign")).getCampaignId());
            dto.setObject(kwd);
            dtoList.add(dto);
        }

        return dtoList;
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
                KeyWordBackUpDTO backUpEntity = new KeyWordBackUpDTO();
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
            List<KeyWordBackUpDTO> keyWordBackUpEntities = copyKeywordEntityToKeywordBack(keywordBackups);
            keyWordBackUpService.insertAll(keyWordBackUpEntities);
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
    private List<KeyWordBackUpDTO> copyKeywordEntityToKeywordBack(List<KeywordDTO> list) {
        List<KeyWordBackUpDTO> backList = new ArrayList<>();
        for (KeywordDTO kwd : list) {
            KeyWordBackUpDTO kwdBack = new KeyWordBackUpDTO();
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
    public PagerInfo getKeyWords(String cid, String aid, Integer nowPage, Integer pageSize) {
        String regex = "^\\d+$";
        if (nowPage == null) {
            nowPage = 0;
        }
        PagerInfo page = null;
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));

        CampaignDTO campaignDTO = null;
        if (cid != null && !"".equals(cid)) {
            if (cid.matches(regex) == true) {
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
            if (aid.matches(regex) == true) {
                query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(Long.parseLong(aid)));
                page = keywordDAO.findByPageInfo(query, pageSize, nowPage);
            } else {
                query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(aid));
                page = keywordDAO.findByPageInfo(query, pageSize, nowPage);
            }
        } else if (cid != null && !"".equals(cid) && (aid == null || "".equals(aid))) {
            Query adQuery = new Query();
            if (campaignDTO.getCampaignId() != null) {
                List<Long> longIds = new ArrayList<>();
                longIds.addAll(adgroupDAO.getAdgroupIdByCampaignId(campaignDTO.getCampaignId()));
                adQuery.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(longIds));
            } else {
                List<String> objIds = new ArrayList<>();
                objIds.addAll(adgroupDAO.getAdgroupIdByCampaignId(campaignDTO.getId()));
                adQuery.addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(objIds));
            }
            page = keywordDAO.findByPageInfo(adQuery, pageSize, nowPage);
        } else {
            page = keywordDAO.findByPageInfo(query, pageSize, nowPage);
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
                dto.setFolderCount(kwd.getKeywordId()==null?0l:monitoringDao.getForlderCountByKwid(kwd.getKeywordId()));
                dto.setCampaignName(camp.getCampaignName());
                dto.setObject(kwd);
                dto.setCampaignId(camp.getCampaignId());
                dtoList.add(dto);
            }

        }
        //在百度上得到关键词的质量度
        List<QualityType> qualityList = qualityTypeService.getQualityType(keywordIds);
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
            if (id.matches(regex) == true) {
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
        KeyWordBackUpDTO keyWordBackUpDTO = new KeyWordBackUpDTO();
        BeanUtils.copyProperties(newKeywordDTO, keyWordBackUpDTO);

        if (newKeywordDTO.getKeywordId() == null) {
            newKeywordDTO.setLocalStatus(1);
        } else {
            newKeywordDTO.setLocalStatus(2);
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

        keywordDAO.update(newKeywordDTO, keyWordBackUpDTO);
        return newKeywordDTO;
    }


    /**
     * 查询推广计划
     *
     * @param query
     * @return
     */
    public List<CampaignDTO> findByQuery(Query query) {
        return campaignDAO.find(query);
    }


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
            List<AdgroupDTO> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(CAMPAIGN_ID).is(campaignDTO.getCampaignId()).and(ACCOUNT_ID).is(accountId)));
            CampaignTreeDTO campaignTree = new CampaignTreeDTO();
            campaignTree.setRootNode(campaignDTO);//设置根节点
            campaignTree.setChildNode(adgroupList);//设置子节点
            campaignTreeList.add(campaignTree);
        }
        return campaignTreeList;
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
                if (fileds[1].matches(regex) == true) {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ADGROUP_ID).is(Long.parseLong(fileds[1])).and("name").is(name)));
                } else {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(OBJ_ADGROUP_ID).is(fileds[1]).and("name").is(name)));
                }


                if (!(new ArrayList<>(campaignMap.keySet()).contains(fileds[0]))) {
                    CampaignDTO camName = fileds[0].matches(regex) == true ? campaignDAO.findOne(Long.parseLong(fileds[0])) : campaignDAO.findByObjectId(fileds[0]);
                    campaignMap.put(fileds[0], camName);
                }

                if (!(new ArrayList<>(adgroupMap.keySet()).contains(fileds[1]))) {
                    AdgroupDTO adgName = fileds[1].matches(regex) == true ? adgroupDAO.findOne(Long.parseLong(fileds[1])) : adgroupDAO.findByObjId(fileds[0]);
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

            if (!(new ArrayList<>(camMap.keySet()).contains(fields[0]))) {
                List<CampaignDTO> campaignEntityList = findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[0])));
                CampaignDTO campaignEntity = campaignEntityList == null || campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);
                camMap.put(fields[0], campaignEntity);
            }
            if (camMap.get(fields[0]) != null) {

                if (!(new ArrayList<>(adgMap.keySet()).contains(fields[1]))) {
                    List<AdgroupDTO> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[1])));
                    AdgroupDTO adgroupEntity = adgroupList == null || adgroupList.size() == 0 ? null : adgroupList.get(0);
                    adgMap.put(fields[1], adgroupEntity);
                }

                if (adgMap.get(fields[1]) != null) {
                    List<KeywordDTO> keywordList;
                    if (adgMap.get(fields[1]).getAdgroupId() == null) {
                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(OBJ_ADGROUP_ID).is(adgMap.get(fields[1]).getId()).and("name").is(fields[2])));
                    } else {
                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(adgMap.get(fields[1]).getAdgroupId()).and("name").is(fields[2])));
                    }
                    if (keywordList.size() != 0) {
                        for (KeywordDTO entity : keywordList) {
                            KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
                            keywordInfoDTO.setCampaignName(fields[0]);
                            keywordInfoDTO.setAdgroupName(fields[1]);
                            keywordInfoDTO.setObject(entity);
                            deleteKwd.add(keywordInfoDTO);
                        }
                    } else {
                        ignoreList.add(setFiledIgnore(fields));
                    }
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
        String regex = "^\\d+$";

        //可被添加的关键词list
        List<KeywordInfoDTO> insertList = new ArrayList<>();

        //可被修改的关键词list
        List<KeywordInfoDTO> updateList = new ArrayList<>();

        //可被忽略的关键词list
        List<AssistantKeywordIgnoreDTO> igoneList = new ArrayList<>();

        String[] everyRow = chooseInfos.split("-");
        String[] everyInfo = keywordInfos.split("\n");

        for (String row : everyRow) {
            //切割出推广计划和推广单元ID
            String[] fieds = row.split(",");
            for (String info : everyInfo) {
                String[] kwInfo = info.split(",|，|\t");
                KeywordDTO keywordDTO = validateKewword(kwInfo);
                List<KeywordDTO> list;
                if (fieds[1].matches(regex) == true) {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(Long.parseLong(fieds[1])).and("name").is(keywordDTO.getKeyword())));
                } else {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(fieds[1]).and("name").is(keywordDTO.getKeyword())));
                }

                if (list.size() > 0 && kwInfo.length == 1) {
                    AssistantKeywordIgnoreDTO dto = new AssistantKeywordIgnoreDTO();

                    if (fieds[0].matches(regex) == true) {
                        dto.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
                    } else {
                        dto.setCampaignName(campaignDAO.findByObjectId(fieds[0]).getCampaignName());
                    }

                    if (fieds[1].matches(regex) == true) {
                        dto.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());
                    } else {
                        dto.setAdgroupName(adgroupDAO.findByObjId(fieds[1]).getAdgroupName());
                    }
                    dto.setKeywordName(kwInfo[0]);
                    igoneList.add(dto);
                    continue;
                }

                if (list.size() == 0) {
                    insertList.add(setFieldToDTO(fieds, keywordDTO, null));
                } else {
                    for (KeywordDTO entity : list) {
                        updateList.add(setFieldToDTO(fieds, keywordDTO, entity));
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("insertList", insertList);
        map.put("updateList", updateList);
        map.put("igoneList", igoneList);

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
                if (kwInfo[i].matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$") == true) {
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


}

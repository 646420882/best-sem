package com.perfect.service.impl;

import com.perfect.api.baidu.QualityTypeService;
import com.perfect.autosdk.sms.v3.QualityType;
import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dao.MonitoringDao;
import com.perfect.dto.AssistantKeywordIgnoreDTO;
import com.perfect.dto.CampaignTreeDTO;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.dao.mongodb.utils.EntityConstants;
import com.perfect.dao.mongodb.utils.PagerInfo;
import com.perfect.service.AssistantKeywordService;
import com.perfect.service.KeyWordBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.perfect.dao.mongodb.utils.EntityConstants.*;

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
    private Map<String, CampaignEntity> campaignMap = new HashMap<>();

    //推广单元名称
    private Map<String, AdgroupEntity> adgroupMap = new HashMap<>();

    public Iterable<CampaignEntity> getCampaignByAccountId() {
        return campaignDAO.findAll();
    }

    public Iterable<AdgroupEntity> getAdgroupByCid(String cid) {
        return cid.matches("^\\d+$") ? adgroupDAO.findByCampaignId(Long.parseLong(cid)) : adgroupDAO.findByCampaignOId(cid);
    }

    public void saveSearchwordKeyword(List<KeywordEntity> list) {
        keywordDAO.insertAll(list);
    }


    /**
     * 根据传过来的关键词的多个 long id 查询关键词
     *
     * @param ids
     * @return
     */
    public List<KeywordDTO> getKeywordListByIds(List<Long> ids) {
        List<KeywordEntity> list = keywordDAO.findKeywordByIds(ids);
        List<KeywordDTO> dtoList = new ArrayList<>();
        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> getMap;
        CampaignEntity cam;
        for (KeywordEntity kwd : list) {
            if (!(new ArrayList<>(map.keySet()).contains(kwd.getAdgroupObjId()) || new ArrayList<>(map.keySet()).contains(kwd.getAdgroupId() + ""))) {
                AdgroupEntity ad = kwd.getAdgroupId() == null ? adgroupDAO.findByObjId(kwd.getAdgroupObjId()) : adgroupDAO.findOne(kwd.getAdgroupId());
                cam = ad.getCampaignId() == null ? campaignDAO.findByObjectId(ad.getCampaignObjId()) : campaignDAO.findOne(ad.getCampaignId());
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("adgroup", ad);
                tempMap.put("campaign", cam);
                map.put(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "", tempMap);
            }

            getMap = map.get(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "");

            kwd.setPrice(kwd.getPrice() == null ? BigDecimal.ZERO : kwd.getPrice());
            KeywordDTO dto = new KeywordDTO();
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
        AdgroupEntity adgroupEntity = agid.matches("^\\d+$") ? adgroupDAO.findOne(Long.parseLong(agid)) : adgroupDAO.findByObjId(agid);
        AdgroupEntity newAdgroupEntity = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupEntity, newAdgroupEntity);
        if (neigType == 1) {
            adgroupEntity.setNegativeWords(list);
        } else {
            adgroupEntity.setExactNegativeWords(list);
        }
        adgroupEntity.setLocalStatus(2);
        adgroupDAO.update(adgroupEntity, newAdgroupEntity);
    }


    /**
     * 批量添加或者更新关键词
     *
     * @param insertDtos
     * @param updateDtos
     * @param isReplace
     */
    public void batchAddUpdateKeyword(List<KeywordDTO> insertDtos, List<KeywordDTO> updateDtos, Boolean isReplace) {

        List<KeywordEntity> list = new ArrayList<>();
        for (KeywordDTO dto : insertDtos) {
            list.add(dto.getObject());
        }
        //若isReplace值为true 就将替换该单元下的所有关键词,为false就只添加或者更新
        if (isReplace == false) {
            keywordDAO.insertAll(list);

            for (KeywordDTO dto : updateDtos) {
                KeywordEntity keywordEntity = dto.getObject();
                KeyWordBackUpEntity backUpEntity = new KeyWordBackUpEntity();
                KeywordEntity sourceKeywordEntity = keywordDAO.findByObjectId(keywordEntity.getId());
                BeanUtils.copyProperties(sourceKeywordEntity, backUpEntity);
                keywordDAO.update(keywordEntity, backUpEntity);
            }
        } else {
            Map<String, Object> adgroupIdMap = getNoRepeatAdgroupId(insertDtos, updateDtos);

            //不是本地新增的关键词将要备份
            List<KeywordEntity> keywordBackups = keywordDAO.findByObjectIds(getKwdObjIdByKeywordList(getKwdListByDTO(updateDtos)));

            //根据String id删除该单元的关键词(硬删除)
            keywordDAO.deleteByObjectAdgroupIds(new ArrayList<String>((Set<String>) adgroupIdMap.get("strSet")));
            //根据long id删除该单元的关键词(软删除)
            keywordDAO.softDeleteByLongAdgroupIds(new ArrayList<Long>((Set<Long>) adgroupIdMap.get("longSet")));
            keywordDAO.insertAll(list);

            //开始备份需要备份的关键词
            List<KeyWordBackUpEntity> keyWordBackUpEntities = copyKeywordEntityToKeywordBack(keywordBackups);
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
    private Map<String, Object> getNoRepeatAdgroupId(List<KeywordDTO> insertList, List<KeywordDTO> updateList) {
        Set<String> strSet = new HashSet<>();
        Set<Long> longSet = new HashSet<>();
        for (KeywordDTO insertDTO : insertList) {
            if (insertDTO.getObject().getAdgroupId() == null) {
                strSet.add(insertDTO.getObject().getAdgroupObjId());
            } else {
                longSet.add(insertDTO.getObject().getAdgroupId());
            }
        }
        for (KeywordDTO updateDTO : updateList) {
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


    private List<KeywordEntity> getKwdListByDTO(List<KeywordDTO> updateDtos) {
        List<KeywordEntity> list = new ArrayList<>();
        for (KeywordDTO dto : updateDtos) {
            list.add(dto.getObject());
        }
        return list;
    }

    private List<String> getKwdObjIdByKeywordList(List<KeywordEntity> list) {
        List<String> objIds = new ArrayList<>();
        for (KeywordEntity kwd : list) {
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
    private List<KeyWordBackUpEntity> copyKeywordEntityToKeywordBack(List<KeywordEntity> list) {
        List<KeyWordBackUpEntity> backList = new ArrayList<>();
        for (KeywordEntity kwd : list) {
            KeyWordBackUpEntity kwdBack = new KeyWordBackUpEntity();
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
        query.addCriteria(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));

        CampaignEntity campaignEntity = null;
        if (cid != null && !"".equals(cid)) {
            if (cid.matches(regex) == true) {
                campaignEntity = campaignDAO.findOne(Long.parseLong(cid));
            } else {
                campaignEntity = campaignDAO.findByObjectId(cid);
            }

            if (campaignEntity == null) {
                return new PagerInfo();
            }
        }


        Map<String, Object> map = null;

        //若cid和aid都不为空，就是查询某单元下的关键词,在aid为空的时候就查询该计划下的关键词
        if (cid != null && !"".equals(cid) && aid != null && !"".equals(aid)) {
            if (aid.matches(regex) == true) {
                query.addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).is(Long.parseLong(aid)));
                page = keywordDAO.findByPageInfo(query, pageSize, nowPage);
            } else {
                query.addCriteria(Criteria.where(EntityConstants.OBJ_ADGROUP_ID).is(aid));
                page = keywordDAO.findByPageInfo(query, pageSize, nowPage);
            }
        } else if (cid != null && !"".equals(cid) && (aid == null || "".equals(aid))) {
            Query adQuery = new Query();
            if (campaignEntity.getCampaignId() != null) {
                List<Long> longIds = new ArrayList<>();
                longIds.addAll(adgroupDAO.getAdgroupIdByCampaignId(campaignEntity.getCampaignId()));
                adQuery.addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).in(longIds));
            } else {
                List<String> objIds = new ArrayList<>();
                objIds.addAll(adgroupDAO.getAdgroupIdByCampaignId(campaignEntity.getId()));
                adQuery.addCriteria(Criteria.where(EntityConstants.OBJ_ADGROUP_ID).in(objIds));
            }
            page = keywordDAO.findByPageInfo(adQuery, pageSize, nowPage);
        } else {
            page = keywordDAO.findByPageInfo(query, pageSize, nowPage);
        }

        page.setList(setCampaignNameByKeywordEntitys((List<KeywordEntity>) page.getList(), campaignEntity));

        return page;
    }


    private List<KeywordDTO> setCampaignNameByKeywordEntitys(List<KeywordEntity> list, CampaignEntity camp) {
        List<KeywordDTO> dtoList = new ArrayList<>();
        List<Long> keywordIds = new ArrayList<>();

        Map<String, CampaignEntity> map = new HashMap<>();
        CampaignEntity cam = null;
        for (KeywordEntity kwd : list) {
            if (kwd.getKeywordId() != null) {
                keywordIds.add(kwd.getKeywordId());
            }

            if (camp == null) {
                if (!(new ArrayList<>(map.keySet()).contains(kwd.getAdgroupObjId()) || new ArrayList<>(map.keySet()).contains(kwd.getAdgroupId() + ""))) {
                    AdgroupEntity ad = kwd.getAdgroupId() == null ? adgroupDAO.findByObjId(kwd.getAdgroupObjId()) : adgroupDAO.findOne(kwd.getAdgroupId());
                    cam = ad.getCampaignId() == null ? campaignDAO.findByObjectId(ad.getCampaignObjId()) : campaignDAO.findOne(ad.getCampaignId());
                    map.put(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "", cam);
                }

                cam = map.get(kwd.getAdgroupId() == null ? kwd.getAdgroupObjId() : kwd.getAdgroupId() + "");
                KeywordDTO dto = new KeywordDTO();
                dto.setFolderCount(0l);
                dto.setCampaignName(cam.getCampaignName());
                dto.setObject(kwd);
                dto.setCampaignId(cam.getCampaignId());
                dtoList.add(dto);
            } else {
                KeywordDTO dto = new KeywordDTO();
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
            for (KeywordDTO dto : dtoList) {
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
    public KeywordEntity updateKeyword(KeywordEntity kwd) {

        KeywordEntity newKeywordEntity;

        if (kwd.getKeywordId() == null) {
            newKeywordEntity = keywordDAO.findByObjectId(kwd.getId());
        } else {
            newKeywordEntity = keywordDAO.findOne(kwd.getKeywordId());
        }
        KeyWordBackUpEntity keyWordBackUpEntity = new KeyWordBackUpEntity();
        BeanUtils.copyProperties(newKeywordEntity, keyWordBackUpEntity);

        if (newKeywordEntity.getKeywordId() == null) {
            newKeywordEntity.setLocalStatus(1);
        } else {
            newKeywordEntity.setLocalStatus(2);
        }
        if (kwd.getPrice() != null) {
            newKeywordEntity.setPrice(kwd.getPrice());
        }
        if (kwd.getPcDestinationUrl() != null) {
            newKeywordEntity.setPcDestinationUrl(kwd.getPcDestinationUrl());
        }
        if (kwd.getMobileDestinationUrl() != null) {
            newKeywordEntity.setMobileDestinationUrl(kwd.getMobileDestinationUrl());
        }
        if (kwd.getMatchType() != null) {
            newKeywordEntity.setMatchType(kwd.getMatchType());
        }
        if (kwd.getPhraseType() != null) {
            newKeywordEntity.setPhraseType(kwd.getPhraseType());
        }
        if (kwd.getPause() != null) {
            newKeywordEntity.setPause(kwd.getPause());
        }

        keywordDAO.update(newKeywordEntity, keyWordBackUpEntity);
        return newKeywordEntity;
    }


    /**
     * 查询推广计划
     *
     * @param query
     * @return
     */
    public List<CampaignEntity> findByQuery(Query query) {
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
        Iterable<CampaignEntity> campaignList = campaignDAO.findAll();

        for (CampaignEntity campaignEntity : campaignList) {
            List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(CAMPAIGN_ID).is(campaignEntity.getCampaignId()).and(ACCOUNT_ID).is(accountId)));
            CampaignTreeDTO campaignTree = new CampaignTreeDTO();
            campaignTree.setRootNode(campaignEntity);//设置根节点
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
        List<KeywordDTO> deleteKwd = new ArrayList<>();

        for (String row : everyChoose) {
            String[] fileds = row.split(",");//fileds[0]推广计划id，fileds[1]推广单元id
            adgroupMap.clear();
            for (String name : names) {
                List<KeywordEntity> list;
                if (fileds[1].matches(regex) == true) {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ADGROUP_ID).is(Long.parseLong(fileds[1])).and("name").is(name)));
                } else {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(OBJ_ADGROUP_ID).is(fileds[1]).and("name").is(name)));
                }


                if (!(new ArrayList<>(campaignMap.keySet()).contains(fileds[0]))) {
                    CampaignEntity camName = fileds[0].matches(regex) == true ? campaignDAO.findOne(Long.parseLong(fileds[0])) : campaignDAO.findByObjectId(fileds[0]);
                    campaignMap.put(fileds[0], camName);
                }

                if (!(new ArrayList<>(adgroupMap.keySet()).contains(fileds[1]))) {
                    AdgroupEntity adgName = fileds[1].matches(regex) == true ? adgroupDAO.findOne(Long.parseLong(fileds[1])) : adgroupDAO.findByObjId(fileds[0]);
                    adgroupMap.put(fileds[1], adgName);
                }


                if (list.size() != 0) {
                    for (KeywordEntity entity : list) {
                        KeywordDTO keywordDTO = new KeywordDTO();
                        keywordDTO.setCampaignName(campaignMap.get(fileds[0]).getCampaignName());
                        keywordDTO.setAdgroupName(adgroupMap.get(fileds[1]).getAdgroupName());
                        keywordDTO.setObject(entity);
                        deleteKwd.add(keywordDTO);
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
        List<KeywordDTO> deleteKwd = new ArrayList<>();

        String[] everyDeleInfo = deleteInfos.split("\n");

        Map<String, CampaignEntity> camMap = new HashMap<>();
        Map<String, AdgroupEntity> adgMap = new HashMap<>();


        for (String str : everyDeleInfo) {
            String[] fields = str.split(",|，|\t");

            if (!(new ArrayList<>(camMap.keySet()).contains(fields[0]))) {
                List<CampaignEntity> campaignEntityList = findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[0])));
                CampaignEntity campaignEntity = campaignEntityList == null || campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);
                camMap.put(fields[0], campaignEntity);
            }
            if (camMap.get(fields[0]) != null) {

                if (!(new ArrayList<>(adgMap.keySet()).contains(fields[1]))) {
                    List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[1])));
                    AdgroupEntity adgroupEntity = adgroupList == null || adgroupList.size() == 0 ? null : adgroupList.get(0);
                    adgMap.put(fields[1], adgroupEntity);
                }

                if (adgMap.get(fields[1]) != null) {
                    List<KeywordEntity> keywordList;
                    if (adgMap.get(fields[1]).getAdgroupId() == null) {
                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(OBJ_ADGROUP_ID).is(adgMap.get(fields[1]).getId()).and("name").is(fields[2])));
                    } else {
                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(adgMap.get(fields[1]).getAdgroupId()).and("name").is(fields[2])));
                    }
                    if (keywordList.size() != 0) {
                        for (KeywordEntity entity : keywordList) {
                            KeywordDTO keywordDTO = new KeywordDTO();
                            keywordDTO.setCampaignName(fields[0]);
                            keywordDTO.setAdgroupName(fields[1]);
                            keywordDTO.setObject(entity);
                            deleteKwd.add(keywordDTO);
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
        List<KeywordDTO> insertList = new ArrayList<>();

        //可被修改的关键词list
        List<KeywordDTO> updateList = new ArrayList<>();

        //可被忽略的关键词list
        List<AssistantKeywordIgnoreDTO> igoneList = new ArrayList<>();

        String[] everyRow = chooseInfos.split("-");
        String[] everyInfo = keywordInfos.split("\n");

        for (String row : everyRow) {
            //切割出推广计划和推广单元ID
            String[] fieds = row.split(",");
            for (String info : everyInfo) {
                String[] kwInfo = info.split(",|，|\t");
                KeywordEntity keywordEntity = validateKewword(kwInfo);
                List<KeywordEntity> list;
                if (fieds[1].matches(regex) == true) {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).is(Long.parseLong(fieds[1])).and("name").is(keywordEntity.getKeyword())));
                } else {
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.OBJ_ADGROUP_ID).is(fieds[1]).and("name").is(keywordEntity.getKeyword())));
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
                    insertList.add(setFieldToDTO(fieds, keywordEntity, null));
                } else {
                    for (KeywordEntity entity : list) {
                        updateList.add(setFieldToDTO(fieds, keywordEntity, entity));
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


    private KeywordDTO setFieldToDTO(String[] fieds, KeywordEntity updateKeywordEntity, KeywordEntity beforeKeywordEntity) {
        String regex = "^\\d+$";
        KeywordDTO keywordDTO = new KeywordDTO();

        if (!(new ArrayList<>(campaignMap.keySet()).contains(fieds[0]))) {
            CampaignEntity campaign = fieds[0].matches(regex) ? campaignDAO.findOne(Long.parseLong(fieds[0])) : campaignDAO.findByObjectId(fieds[0]);
            campaignMap.put(fieds[0], campaign);
        }

        if (!(new ArrayList<>(adgroupMap.keySet()).contains(fieds[1]))) {
            AdgroupEntity adgroupName = fieds[1].matches(regex) ? adgroupDAO.findOne(Long.parseLong(fieds[1])) : adgroupDAO.findByObjId(fieds[1]);
            adgroupMap.put(fieds[1], adgroupName);
        }

        keywordDTO.setCampaignName(campaignMap.get(fieds[0]).getCampaignName());
        keywordDTO.setAdgroupName(adgroupMap.get(fieds[1]).getAdgroupName());


        if (beforeKeywordEntity == null) {
            updateKeywordEntity.setAccountId(AppContext.getAccountId());
            updateKeywordEntity.setStatus(-1);
            updateKeywordEntity.setLocalStatus(1);
            if (adgroupMap.get(fieds[1]).getCampaignId() == null) {
                updateKeywordEntity.setAdgroupObjId(adgroupMap.get(fieds[1]).getCampaignObjId());
            } else {
                updateKeywordEntity.setAdgroupId(adgroupMap.get(fieds[1]).getAdgroupId());
            }
            keywordDTO.setObject(updateKeywordEntity);
        } else {
            beforeKeywordEntity.setPause(updateKeywordEntity.getPause());

            if (updateKeywordEntity.getMatchType() != null) {
                beforeKeywordEntity.setMatchType(updateKeywordEntity.getMatchType());
            } else {
                beforeKeywordEntity.setMatchType(2);
            }
            if (updateKeywordEntity.getPhraseType() != null) {
                beforeKeywordEntity.setPhraseType(updateKeywordEntity.getPhraseType());
            }
            if (updateKeywordEntity.getPrice() != null) {
                beforeKeywordEntity.setPrice(updateKeywordEntity.getPrice());
            }
            if (updateKeywordEntity.getPcDestinationUrl() != null) {
                beforeKeywordEntity.setPcDestinationUrl(updateKeywordEntity.getPcDestinationUrl());
            }
            if (updateKeywordEntity.getMobileDestinationUrl() != null) {
                beforeKeywordEntity.setMobileDestinationUrl(updateKeywordEntity.getMobileDestinationUrl());
            }

            if (beforeKeywordEntity.getLocalStatus() == null) {
                beforeKeywordEntity.setLocalStatus(2);
            }

            keywordDTO.setObject(beforeKeywordEntity);
        }

        return keywordDTO;
    }


    /**
     * 验证输入的关键词数据，若输入的不合法，就赋一个默认值
     *
     * @param kwInfo
     * @return
     */
    private KeywordEntity validateKewword(String[] kwInfo) {
        KeywordEntity keywordEntity = new KeywordEntity();

        keywordEntity.setKeyword(kwInfo[0]);
        for (int i = 1; i < kwInfo.length; i++) {
            if (i == 1) {
                if ("广泛".equals(kwInfo[i])) {
                    keywordEntity.setMatchType(3);
                } else if ("短语-同义包含".equals(kwInfo[i])) {
                    keywordEntity.setMatchType(2);
                    keywordEntity.setPhraseType(1);
                } else if ("短语-精确包含".equals(kwInfo[i])) {
                    keywordEntity.setMatchType(2);
                    keywordEntity.setPhraseType(2);
                } else if ("短语-核心包含".equals(kwInfo[i])) {
                    keywordEntity.setMatchType(2);
                    keywordEntity.setPhraseType(3);
                } else {
                    keywordEntity.setMatchType(1);
                }
            } else if (i == 2) {
                if (kwInfo[i].matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$") == true) {
                    keywordEntity.setPrice(BigDecimal.valueOf(Double.parseDouble(kwInfo[i])));
                } else {
                    keywordEntity.setPrice(null);
                }
            } else if (i == 3) {
                keywordEntity.setPcDestinationUrl(kwInfo[i]);
            } else if (i == 4) {
                keywordEntity.setMobileDestinationUrl(kwInfo[i]);
            } else if (i == 5) {
                if ("暂停".equals(kwInfo[i])) {
                    keywordEntity.setPause(true);
                } else {
                    keywordEntity.setPause(false);
                }
            }
        }

        if (keywordEntity.getPause() == null) {
            keywordEntity.setPause(false);
        }
        if (keywordEntity.getMatchType() == null) {
            keywordEntity.setMatchType(1);
        }

        return keywordEntity;
    }


}

package com.perfect.service.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dto.AssistantkwdIgnoreDeleDTO;
import com.perfect.dto.CampaignTreeDTO;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.service.AssistantKeywordService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.perfect.mongodb.utils.EntityConstants.*;

/**
 * Created by john on 2014/8/19.
 */
@Repository("assistantKeywordService")
public class AssistantKeywordServiceImpl implements AssistantKeywordService {

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;



    /**
     * 根据多个关键词id查询关键词
     * @param ids
     * @return
     */
    public List<KeywordEntity> getKeywordByIds(List<Long> ids){
        return keywordDAO.getKeywordByIds(ids);
    }

    public  Iterable<CampaignEntity> getCampaignByAccountId(){
        return campaignDAO.findAll();
    }

    public  Iterable<AdgroupEntity> getAdgroupByCid(String cid){
       return cid.matches("^\\d+$")?adgroupDAO.findByCampaignId(Long.parseLong(cid)):adgroupDAO.findByCampaignOId(cid);
    }

    public void saveSearchwordKeyword(List<KeywordEntity> list){
        keywordDAO.insertAll(list);
    }

    public void setNeigWord(String agid, String keywords, Integer neigType){
        String[] kwds = keywords.split("\n");

        List<String> list = new ArrayList<>();
        for(String kwd:kwds){
            list.add(kwd);
        }
        AdgroupEntity adgroupEntity = agid.matches("^\\d+$")?adgroupDAO.findOne(Long.parseLong(agid)):adgroupDAO.findByObjId(agid);
        AdgroupEntity newAdgroupEntity = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupEntity,newAdgroupEntity);
       if(neigType==1){
           adgroupEntity.setNegativeWords(list);
       }else{
           adgroupEntity.setExactNegativeWords(list);
       }
        adgroupEntity.setLocalStatus(2);
        adgroupDAO.update(adgroupEntity,newAdgroupEntity);
    }


    /**
     * 批量添加关键词
     * @param keywords
     */
   public void batchAddkeyword(String keywords){
       List<KeywordEntity> keywordEntityList = new ArrayList<>();
       String[] keywordArray = keywords.split(";");

       for(String kwd:keywordArray){
           KeywordEntity keywordEntity = new KeywordEntity();
           String[] kwdFields = kwd.split(",");
           CampaignEntity campaignEntity = campaignDAO.findCampaignByName(kwdFields[0]);
           AdgroupEntity adgroupEntity = adgroupDAO.getByCampaignIdAndName(campaignEntity.getCampaignId(),kwdFields[1]);
           keywordEntity.setAdgroupId(adgroupEntity.getAdgroupId());
           keywordEntity.setAdgroupObjId(adgroupEntity.getId());
           keywordEntity.setKeyword(kwdFields[2]);
           keywordEntity.setPrice(kwdFields[3].equals("null") ? null : BigDecimal.valueOf(Double.parseDouble(kwdFields[3])));
           keywordEntity.setPcDestinationUrl(kwdFields[4].equals("null") ? "" : kwdFields[4]);
           keywordEntity.setMobileDestinationUrl(kwdFields[5].equals("null") ? "" : kwdFields[5]);
           keywordEntity.setMatchType(kwdFields[6].equals("null") ? 1 : Integer.parseInt(kwdFields[6]));
           keywordEntity.setPause(kwdFields[7].equals("null") ? false : Boolean.parseBoolean(kwdFields[7]));
           keywordEntity.setPhraseType(kwdFields[8].equals("null") ? null : Integer.parseInt(kwdFields[8]));
           keywordEntity.setAccountId(AppContext.getAccountId());
           keywordEntity.setLocalStatus(1);
           keywordEntity.setStatus(-1);
           keywordEntityList.add(keywordEntity);
       }
       keywordDAO.insertAll(keywordEntityList);
    }


    /**
     * 批量修改关键词
     * @param keywords
     */
    public void batchUpdateKeyword(String keywords){
        List<KeywordEntity> keywordEntityList = new ArrayList<>();
        String[] kwds = keywords.split("\t");
       for(String k:kwds){
        String[] kwdField = k.split(",");
           KeywordEntity keywordEntity = new KeywordEntity();
           keywordEntity.setAccountId(AppContext.getAccountId());
           keywordEntity.setId(kwdField[0]);
           keywordEntity.setKeywordId(kwdField[1].equals("null")?null:Long.parseLong(kwdField[1]));
           keywordEntity.setAdgroupId(kwdField[2].equals("null")?null:Long.parseLong(kwdField[2]));
           keywordEntity.setAdgroupObjId(kwdField[3].equals("null")?"":kwdField[3]);
           keywordEntity.setKeyword(kwdField[4]);
           keywordEntity.setPrice(kwdField[5].equals("null")?null:BigDecimal.valueOf(Double.parseDouble(kwdField[5])));
           keywordEntity.setPcDestinationUrl(kwdField[6].equals("null")?"":kwdField[6]);
           keywordEntity.setMobileDestinationUrl(kwdField[7].equals("null")?"":kwdField[7]);
           keywordEntity.setMatchType(kwdField[8].equals("null")?1:Integer.parseInt(kwdField[8]));
           keywordEntity.setPause(kwdField[9].equals("null")?false:Boolean.parseBoolean(kwdField[9]));
           keywordEntity.setStatus(kwdField[10].equals("null")?null:Integer.parseInt(kwdField[10]));
           keywordEntity.setPhraseType(kwdField[11].equals("null")?null:Integer.parseInt(kwdField[11]));
          if(kwdField[12].equals("1")){
              keywordEntity.setLocalStatus(1);
          }else{
              keywordEntity.setLocalStatus(2);
          }
           keywordEntityList.add(keywordEntity);
       }

        keywordDAO.save(keywordEntityList);
    }


    /**
     * 根据账户id得到关键词
     * @return
     */

    @Override
    public PagerInfo getKeyWords(String cid,String aid,Integer nowPage,Integer pageSize) {
        String regex = "^\\d+$";
        if(nowPage==null){
            nowPage = 0;
        }

        PagerInfo page = null;

       Query query = new Query();
        query.addCriteria(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));

        //若cid和aid都不为空，就是查询某单元下的关键词,在aid为空的时候就查询该计划下的关键词
        if(cid!=null && !"".equals(cid) && aid!=null && !"".equals(aid)){
            if(aid.matches(regex)==true){
                query.addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).is(Long.parseLong(aid)));
                page = keywordDAO.findByPageInfo(query, pageSize,nowPage);
            }else{
                query.addCriteria(Criteria.where(EntityConstants.SYSTEM_ID).is(aid));
                page = keywordDAO.findByPageInfo(query, pageSize,nowPage);
            }
        }else if(cid!=null && !"".equals(cid) && (aid==null||"".equals(aid))){
            CampaignEntity campaignEntity;
            if(cid.matches(regex)==true){
                campaignEntity = campaignDAO.findOne(Long.parseLong(cid));
            }else{
                campaignEntity = campaignDAO.findByObjectId(cid);
            }
                List<AdgroupEntity> adgroupEntityList = new ArrayList<>();
                if(campaignEntity.getCampaignId()==null){
                   adgroupEntityList.addAll(adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(EntityConstants.OBJ_CAMPAIGN_ID).is(campaignEntity.getId())))) ;
                }else{
                    adgroupEntityList.addAll( adgroupDAO.findByCampaignId(campaignEntity.getCampaignId()));
                }


            //待？？？？？
            Query adQuery = new Query();
            List<Long> longIds = new ArrayList<>();
            List<String> objIds = new ArrayList<>();
            for(int i = 0;i<adgroupEntityList.size();i++){
                AdgroupEntity ad = adgroupEntityList.get(i);
                if(ad.getAdgroupId()==null){
                    objIds.add(ad.getId());
                }else{
                    longIds.add(ad.getAdgroupId());
                }
            }
            adQuery.addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).in(longIds));
            page = keywordDAO.findByPageInfo(adQuery, pageSize, nowPage);

        }else{
            page = keywordDAO.findByPageInfo(query, pageSize,nowPage);
        }

        return page;
    }


    /**
     * 根据关键词id删除关键词
     *
     * @param kwids
     */
    @Override
    public void deleteByKwIds(List<String> kwids) {
        String regex = "^\\d+$";
        for(String id : kwids){
            if(id.matches(regex)==true){
                keywordDAO.softDelete(Long.parseLong(id));
            }else{
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

        if(kwd.getKeywordId()==null){
            newKeywordEntity = keywordDAO.findByObjectId(kwd.getId());
        }else{
            newKeywordEntity = keywordDAO.findOne(kwd.getKeywordId());
        }
        KeyWordBackUpEntity keyWordBackUpEntity = new KeyWordBackUpEntity();
        BeanUtils.copyProperties(newKeywordEntity,keyWordBackUpEntity);

        if(newKeywordEntity.getKeywordId()==null){
            newKeywordEntity.setLocalStatus(1);
        }else{
            newKeywordEntity.setLocalStatus(2);
        }
        if(kwd.getPrice()!=null){
            newKeywordEntity.setPrice(kwd.getPrice());
        }
        if(kwd.getPcDestinationUrl()!=null){
            newKeywordEntity.setPcDestinationUrl(kwd.getPcDestinationUrl());
        }
        if(kwd.getMobileDestinationUrl()!=null){
            newKeywordEntity.setMobileDestinationUrl(kwd.getMobileDestinationUrl());
        }
        if(kwd.getMatchType()!=null){
            newKeywordEntity.setMatchType(kwd.getMatchType());
        }
        if(kwd.getPhraseType()!=null){
            newKeywordEntity.setPhraseType(kwd.getPhraseType());
        }
        if(kwd.getPause()!=null){
             newKeywordEntity.setPause(kwd.getPause());
        }

        keywordDAO.update(newKeywordEntity,keyWordBackUpEntity);
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
        List<CampaignEntity> campaignList = campaignDAO.find(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId)));

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
    public Map<String,Object> validateDeleteKeywordByChoose(Long accountId, String chooseInfos, String keywordNames,Integer nowPage,Integer pageSize) {
        String regex = "^\\d+$";
        String[] everyChoose = chooseInfos.split("-");
        String[] names = keywordNames.split("\n");

        Map<String, Object> map = new HashMap<>();
        //被忽略删除的关键词
        List<AssistantkwdIgnoreDeleDTO> ignoreList = new ArrayList<>();

        //可删除的关键词集合
        List<KeywordDTO>  deleteKwd = new ArrayList<>();


        for (String row : everyChoose) {
            String[] fileds = row.split(",");//fileds[0]推广计划id，fileds[1]推广单元id
            for (String name : names) {
                List<KeywordEntity>  list;
                if(fileds[1].matches(regex)==true){
                  list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ADGROUP_ID).is(Long.parseLong(fileds[1])).and("name").is(name)));
                }else{
                    list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(SYSTEM_ID).is(fileds[1]).and("name").is(name)));
                }
                if(list.size()!=0){
                    KeywordDTO keywordDTO = new KeywordDTO();
                    keywordDTO.setCampaignName(fileds[0].matches(regex)==true?campaignDAO.findOne(Long.parseLong(fileds[0])).getCampaignName():campaignDAO.findByObjectId(fileds[0]).getCampaignName());
                    keywordDTO.setAdgroupName(fileds[1].matches(regex) == true ? adgroupDAO.findOne(Long.parseLong(fileds[1])).getAdgroupName() : adgroupDAO.findByObjId(fileds[0]).getAdgroupName());
                    keywordDTO.setObject(list.get(0));
                    deleteKwd.add(keywordDTO);
                }else{
                    AssistantkwdIgnoreDeleDTO assistantkwdIgnoreDeleDTO = new AssistantkwdIgnoreDeleDTO();
                    assistantkwdIgnoreDeleDTO.setCampaignName(fileds[0].matches(regex)==true?campaignDAO.findOne(Long.parseLong(fileds[0])).getCampaignName():campaignDAO.findByObjectId(fileds[0]).getCampaignName());
                    assistantkwdIgnoreDeleDTO.setAdgroupName(fileds[1].matches(regex)==true?adgroupDAO.findOne(Long.parseLong(fileds[1])).getAdgroupName():adgroupDAO.findByObjId(fileds[0]).getAdgroupName());
                    assistantkwdIgnoreDeleDTO.setKeywordName(name);
                    ignoreList.add(assistantkwdIgnoreDeleDTO);
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
        List<AssistantkwdIgnoreDeleDTO> ignoreList = new ArrayList<>();

        //可删除的关键词集合
        List<KeywordDTO>  deleteKwd = new ArrayList<>();

        String[] everyDeleInfo = deleteInfos.split("\n");

        for (String str : everyDeleInfo) {
            String[] fields = str.split(",|\t");
            List<CampaignEntity> campaignEntityList = findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[0])));
            CampaignEntity campaignEntity = campaignEntityList == null || campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);

            if (campaignEntity != null) {
                List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fields[1])));
                AdgroupEntity adgroupEntity = adgroupList == null || adgroupList.size() == 0 ? null : adgroupList.get(0);

                if (adgroupEntity != null) {
                    List<KeywordEntity> keywordList;
                    if(adgroupEntity.getAdgroupId()==null){
                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(EntityConstants.OBJ_ADGROUP_ID).is(adgroupEntity.getId()).and("name").is(fields[2])));
                    }else{
                        keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(adgroupEntity.getAdgroupId()).and("name").is(fields[2])));
                    }
                    if (keywordList.size() != 0) {
                        KeywordDTO keywordDTO = new KeywordDTO();
                        keywordDTO.setCampaignName(fields[0]);
                        keywordDTO.setAdgroupName(fields[1]);
                        keywordDTO.setObject(keywordList.get(0));
                        deleteKwd.add(keywordDTO);
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


    public AssistantkwdIgnoreDeleDTO setFiledIgnore(String[] fields) {
        AssistantkwdIgnoreDeleDTO ignoreDeleEntity = new AssistantkwdIgnoreDeleDTO();
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
    public Map<String,Object> batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos) {
        String regex = "^\\d+$";

        //可被添加的关键词list
        List<KeywordDTO>  insertList = new ArrayList<>();

        //可被修改的关键词list
        List<KeywordDTO>  updateList = new ArrayList<>();

        //可被忽略的关键词list
        List<AssistantkwdIgnoreDeleDTO>  igoneList = new ArrayList<>();

        //可被删除的关键词list
        List<KeywordDTO>  delList = new ArrayList<>();


        String[] everyRow = chooseInfos.split("-");
        String[] everyInfo = keywordInfos.split("\n");


        for (String row : everyRow) {
            //切割出推广计划和推广单元ID
            String[] fieds = row.split(",");
            for (String info : everyInfo) {
                //若为true，将现在的关键词替换该单元下的所有相应内容,为false时，就将现在输入的关键词添加到数据库
                String[] kwInfo = info.split(",|\t");
                if (isReplace == true) {
                    KeywordEntity keywordEntity = validateKewword(kwInfo);

                    List<KeywordEntity> list = null;
                    if(fieds[1].matches(regex)==true){
                        list = keywordDAO.findByQuery(new Query(Criteria.where(EntityConstants.ADGROUP_ID).is(Long.parseLong(fieds[1])).and("name").is(kwInfo[0])));
                    }else{
                        list = keywordDAO.findByQuery(new Query(Criteria.where(EntityConstants.ADGROUP_ID).is(fieds[1]).and("name").is(kwInfo[0])));
                    }


                    if(list.size()>0 && kwInfo.length==1){
                        AssistantkwdIgnoreDeleDTO dto = new AssistantkwdIgnoreDeleDTO();

                        if(fieds[0].matches(regex)==true){
                            dto.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
                        }else{
                            dto.setCampaignName(campaignDAO.findByObjectId(fieds[0]).getCampaignName());
                        }

                        if(fieds[1].matches(regex)==true){
                            dto.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());
                        }else{
                            dto.setAdgroupName(adgroupDAO.findByObjId(fieds[1]).getAdgroupName());
                        }
                        dto.setKeywordName(kwInfo[0]);
                        igoneList.add(dto);
                        continue;
                    }



                    if(list.size()==0){
                        insertList.add(setFieldToDTO(fieds,keywordEntity,null));
                    }else{
                        updateList.add(setFieldToDTO(fieds,keywordEntity,list.get(0)));
                    }

                } else {
                    KeywordEntity keywordEntity = validateKewword(kwInfo);

                    List<KeywordEntity>  list = null;
                    if(fieds[1].matches(regex)==true){
                        list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).is(Long.parseLong(fieds[1])).and("name").is(keywordEntity.getKeyword())));
                    }else{
                        list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.SYSTEM_ID).is(fieds[1]).and("name").is(keywordEntity.getKeyword())));
                    }

                    if(list.size()>0 && kwInfo.length==1){
                        AssistantkwdIgnoreDeleDTO dto = new AssistantkwdIgnoreDeleDTO();

                        if(fieds[0].matches(regex)==true){
                            dto.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
                        }else{
                            dto.setCampaignName(campaignDAO.findByObjectId(fieds[0]).getCampaignName());
                        }

                        if(fieds[1].matches(regex)==true){
                            dto.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());
                        }else{
                            dto.setAdgroupName(adgroupDAO.findByObjId(fieds[1]).getAdgroupName());
                        }
                        dto.setKeywordName(kwInfo[0]);
                        igoneList.add(dto);
                        continue;
                    }


                    //若查询没有数据就添加这条数据,若有就更新这条数据
                    if(list.size()==0){
                        insertList.add(setFieldToDTO(fieds,keywordEntity,null));
                    }else{
                        updateList.add(setFieldToDTO(fieds,keywordEntity,list.get(0)));
                    }
                }
            }
        }



      /*  List<KeywordEntity> entities = new ArrayList<>();
        if(isReplace==true){

            for(String row:everyRow){
                String[] fieds = row.split(",");

                List<KeywordEntity>  list = null;
                if(fieds[1].matches(regex)==true){
                    list = keywordDAO.findByAdgroupId(Long.parseLong(fieds[1]),getBasePagintionParam());
                }else{
                    list = keywordDAO.findByAdgroupId(fieds[1],getBasePagintionParam());
                }

                Iterator ita = list.iterator();
                while (ita.hasNext()){
                    KeywordEntity keywordEntity = (KeywordEntity)ita.next();
                    for(AssistantkwdIgnoreDeleDTO dto:igoneList){
                        if(dto.getKeywordName().equals(keywordEntity.getKeyword())){
                                ita.remove();
                        }
                    }

                    for(KeywordDTO dto:updateList){
                       if(((KeywordEntity)dto.getObject()).getKeyword().equals(keywordEntity.getKeyword())){
                            ita.remove();
                        }
                    }
                }

                entities.addAll(list);
                KeywordDTO keywordDTO = new KeywordDTO();

                if(fieds[0].matches(regex)==true){
                    keywordDTO.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
                }else{
                    keywordDTO.setCampaignName(campaignDAO.findByObjectId(fieds[0]).getCampaignName());
                }

                if(fieds[1].matches(regex)==true){
                    keywordDTO.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());
                }else{
                    keywordDTO.setAdgroupName(adgroupDAO.findByObjId(fieds[1]).getAdgroupName());
                }
                keywordDTO.setObject(entities);
                delList.add(keywordDTO);
            }
        }*/

        Map<String,Object> map = new HashMap<>();
        map.put("insertList",insertList);
        map.put("updateList",updateList);
        map.put("igoneList",igoneList);
        map.put("delList",delList);

        return map;
    }

    public PaginationParam getBasePagintionParam(){
        PaginationParam page = new PaginationParam();
        page.setAsc(true);
        page.setLimit(Integer.MAX_VALUE);
        page.setStart(0);
        page.setOrderBy("name");
        return page;
    }

    private KeywordDTO setFieldToDTO(String[] fieds,KeywordEntity updateKeywordEntity,KeywordEntity beforeKeywordEntity){
        KeywordDTO keywordDTO = new KeywordDTO();
        keywordDTO.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
        keywordDTO.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());

        if(beforeKeywordEntity==null){
            keywordDTO.setObject(updateKeywordEntity);
        }else{
            beforeKeywordEntity.setPause(updateKeywordEntity.getPause());

           if(updateKeywordEntity.getMatchType()!=null){
               beforeKeywordEntity.setMatchType(updateKeywordEntity.getMatchType());
           }else{
               beforeKeywordEntity.setMatchType(2);
           }
           if(updateKeywordEntity.getPhraseType()!=null){
               beforeKeywordEntity.setPhraseType(updateKeywordEntity.getPhraseType());
           }
           if(updateKeywordEntity.getPrice()!=null){
               beforeKeywordEntity.setPrice(updateKeywordEntity.getPrice());
           }
            if(updateKeywordEntity.getPcDestinationUrl()!=null){
                beforeKeywordEntity.setPcDestinationUrl(updateKeywordEntity.getPcDestinationUrl());
            }
            if(updateKeywordEntity.getMobileDestinationUrl()!=null){
                 beforeKeywordEntity.setMobileDestinationUrl(updateKeywordEntity.getMobileDestinationUrl());
            }

            keywordDTO.setObject(beforeKeywordEntity);
        }

        return keywordDTO;
    }




    /**
     * 验证输入的关键词数据，若输入的不合法，就赋一个默认值
     * @param kwInfo
     * @return
     */
    private KeywordEntity validateKewword(String[] kwInfo){
        KeywordEntity keywordEntity = new KeywordEntity();

        keywordEntity.setKeyword(kwInfo[0]);
        for(int i = 1;i<kwInfo.length;i++){
            if(i==1){
                if("广泛".equals(kwInfo[i])){
                    keywordEntity.setMatchType(3);
                }else if("短语-同义包含".equals(kwInfo[i])){
                    keywordEntity.setMatchType(2);
                    keywordEntity.setPhraseType(1);
                }else if("短语-精确包含".equals(kwInfo[i])){
                    keywordEntity.setMatchType(2);
                    keywordEntity.setPhraseType(2);
                }else if("短语-核心包含".equals(kwInfo[i])){
                    keywordEntity.setMatchType(2);
                    keywordEntity.setPhraseType(3);
                }else{
                    keywordEntity.setMatchType(1);
                }
            }else if(i==2){
                if(kwInfo[i].matches("^\\d+|\\.\\d+$")==true){
                    keywordEntity.setPrice(BigDecimal.valueOf(Double.parseDouble(kwInfo[i])));
                }else{
                    keywordEntity.setPrice(null);
                }
            }else if(i==3){
                    keywordEntity.setPcDestinationUrl(kwInfo[i]);
            }else if(i==4){
                    keywordEntity.setMobileDestinationUrl(kwInfo[i]);
            }else if(i==5){
                if("暂停".equals(kwInfo[i])){
                    keywordEntity.setPause(true);
                }else{
                    keywordEntity.setPause(false);
                }
            }
        }

        if(keywordEntity.getPause()==null){
            keywordEntity.setPause(false);
        }

        return keywordEntity;
    }


    /**未完
     * （输入的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     *
     * @param accountId    当前账户id
     * @param isReplace    是否将用户输入的信息替换该单元下相应的内容
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    @Override
    public void batchAddOrUpdateKeywordByInput(Long accountId, Boolean isReplace, String keywordInfos) {
        String[] everyRow = keywordInfos.split("\r\n");

        for (String row : everyRow) {
            String[] fileds = row.split(",|\t");

            //根据计划名称得到一个推广计划对象
            List<CampaignEntity> campaignEntityList = campaignDAO.find(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fileds[0])));
            CampaignEntity campaignEntity = campaignEntityList == null || campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);

            if (campaignEntity != null) {

                //根据单元名称得到一个推广单元对象
                List<AdgroupEntity> adgrounp = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and("name").is(fileds[1]).and(CAMPAIGN_ID).is(campaignEntity.getCampaignId())));
                AdgroupEntity adgroupEntity = adgrounp == null || adgrounp.size() == 0 ? null : adgrounp.get(0);

                if (adgroupEntity != null) {

                    //若为true，将现在的关键词替换该单元下的所有相应内容,为false时，就将现在输入的关键词添加到数据库
                    if (isReplace == true) {
                        //删除现在该单元下的所有关键词
                        keywordDAO.remove(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(adgroupEntity.getAdgroupId())));

                        //开始添加现在用户输入的关键词

                    } else {
                        //开始添加现在用户输入的关键词
                    }

                }

            }
        }

    }
}

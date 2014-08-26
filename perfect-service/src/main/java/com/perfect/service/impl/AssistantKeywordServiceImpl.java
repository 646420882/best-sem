package com.perfect.service.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dto.AssistantkwdIgnoreDeleDTO;
import com.perfect.entity.*;
import com.perfect.service.AssistantKeywordService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
@Repository("assistantKeywordService")
public class AssistantKeywordServiceImpl implements AssistantKeywordService{

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    /**
     * 根据账户id得到关键词
     * @param accountId
     * @return
     */
    @Override
    public Iterable<KeywordEntity> getAllKeyWord(Long accountId) {
        return keywordDAO.findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId)));
    }


    /**
     * 根据关键词id删除关键词
     * @param kwids
     */
    @Override
    public void deleteByKwIds(List<Long> kwids) {
        keywordDAO.deleteByIds(kwids);
    }


    /**
     * 修改关键词信息
     * @param keywordEntity
     */
    @Override
    public void updateKeyword(KeywordEntity keywordEntity) {
        keywordDAO.update(keywordEntity);
    }


    /**
     * 查询推广计划
     * @param query
     * @return
     */
    @Override
    public List findByQuery(Query query) {
        return campaignDAO.find(query);
    }


    /**
     * 根据当前账户id得到该账户下的推广计划树
     * @param accountId
     * @return
     */
    @Override
    public List<CampaignTreeVoEntity> getCampaignTree(Long accountId){
        List<CampaignTreeVoEntity> campaignTreeList = new ArrayList<>();
        List<CampaignEntity> campaignList = campaignDAO.find(new Query().addCriteria(Criteria.where("aid").is(accountId)));

        for(CampaignEntity campaignEntity:campaignList){
            List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where("cid").is(campaignEntity.getCampaignId()).and("aid").is(accountId)));
            CampaignTreeVoEntity campaignTree = new CampaignTreeVoEntity();
            campaignTree.setRootNode(campaignEntity);//设置根节点
            campaignTree.setChildNode(adgroupList);//设置子节点
            campaignTreeList.add(campaignTree);
        }
        return campaignTreeList;
    }


    /**
     * (用户的选择计划，单元，输入的关键词的方式)
     * @param chooseInfos
     * @param keywordNames
     */
    @Override
    public  void deleteKeywordByNamesChoose(Long accountId,String chooseInfos, String keywordNames){
        String[] everyChoose = chooseInfos.split("-");
        String[] names = keywordNames.split("\r\n");

        for(String row:everyChoose){
            String[] fileds = row.split(",");//fileds[0]推广计划id，fileds[1]推广单元id
            for(String name:names){
                keywordDAO.remove(new Query().addCriteria(Criteria.where("aid").is(accountId).and("agid").is(fileds[1]).and("name").is(name)));
            }
        }

    }


    /**(输入的方式)
     * 根据用户输入的删除信息（计划名称，单元名称，关键词名称）批量删除关键词
     * @param accountId
     * @param deleteInfos
     */
    @Override
    public Map<String,Object> validateDeleteByInput(Long accountId,String deleteInfos) {
        Map<String,Object> map = new HashMap<>();

        //被忽略删除的关键词
        List<AssistantkwdIgnoreDeleDTO> ignoreList = new ArrayList<>();

        //可删除的关键词集合
        Map<String,Object> deleteKwd = new HashMap<>();

        String[] everyDeleInfo = deleteInfos.split("\r\n");

        for(String str:everyDeleInfo){
            String[] fields = str.split(",|\t");
            List<CampaignEntity> campaignEntityList = findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId).and("name").is(fields[0])));
            CampaignEntity campaignEntity = campaignEntityList==null||campaignEntityList.size()==0?null:campaignEntityList.get(0);

            if(campaignEntity != null){
                List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId).and("cid").is(campaignEntity.getCampaignId()).and("name").is(fields[1])));
                AdgroupEntity adgroupEntity = adgroupList==null||adgroupList.size()==0?null:adgroupList.get(0);

                if(adgroupEntity!=null){
                    List<KeywordEntity> keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId).and("agid").is(adgroupEntity.getAdgroupId())));
                    if(keywordList.size()!=0){
                        deleteKwd.put("campaign",fields[0]);
                        deleteKwd.put("adgroup",fields[1]);
                        deleteKwd.put("list",keywordList);
                    }else{
                        ignoreList.add(setFiledIgnore(fields));
                    }
                }else{
                    ignoreList.add(setFiledIgnore(fields));
                }
            }else{
                ignoreList.add(setFiledIgnore(fields));
            }
        }

        map.put("ignoreList",ignoreList);
        map.put("deleteKwd",deleteKwd);

        return map;
    }


    public AssistantkwdIgnoreDeleDTO setFiledIgnore(String[] fields){
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
     * @param accountId 当前账户id
     * @param isReplace 是否将用户输入的信息替换该单元下相应的内容
     * @param chooseInfos 用户选择的推广计划和退关单元
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    public void batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos){
        String[] everyRow = chooseInfos.split("-");
        String[] everyInfo = keywordInfos.split("\r\n");

        for(String row:everyRow){
            //切割出推广计划和推广单元ID
            String[] fieds = row.split(",");
            for (String info:everyInfo) {
                //若为true，将现在的关键词替换该单元下的所有相应内容,为false时，就将现在输入的关键词添加到数据库
                if(isReplace==true){
                    String[] kwInfo = info.split(",|\t");
                    //删除该单元下的所有相应的关键词
                    keywordDAO.remove(new Query().addCriteria(Criteria.where("aid").is(accountId).and("agid").is(fieds[1])));

                    //开始添加现在用户输入的关键词
                    KeywordEntity keywordEntity = new KeywordEntity();
                    keywordEntity.setAdgroupId(Long.parseLong(fieds[1]));
                    keywordEntity.setKeyword(kwInfo[0]);

                }else{
                    //开始添加现在用户输入的关键词

                }
            }
        }

    }




    /**
     * （输入的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     * @param accountId 当前账户id
     * @param isReplace 是否将用户输入的信息替换该单元下相应的内容
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    @Override
    public void batchAddOrUpdateKeywordByInput(Long accountId, Boolean isReplace, String keywordInfos){
        String[] everyRow = keywordInfos.split("\r\n");

        for(String row:everyRow){
            String[] fileds = row.split(",|\t");

            //根据计划名称得到一个推广计划对象
            List<CampaignEntity> campaignEntityList = campaignDAO.find(new Query().addCriteria(Criteria.where("aid").is(accountId).and("name").is(fileds[0])));
            CampaignEntity campaignEntity = campaignEntityList==null||campaignEntityList.size()==0?null:campaignEntityList.get(0);

            if(campaignEntity!=null){

                //根据单元名称得到一个推广单元对象
                List<AdgroupEntity> adgrounp = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId).and("name").is(fileds[1]).and("cid").is(campaignEntity.getCampaignId())));
                AdgroupEntity adgroupEntity = adgrounp==null||adgrounp.size()==0?null:adgrounp.get(0);

                if(adgroupEntity!=null){

                    //若为true，将现在的关键词替换该单元下的所有相应内容,为false时，就将现在输入的关键词添加到数据库
                    if(isReplace==true){
                        //删除现在该单元下的所有关键词
                        keywordDAO.remove(new Query().addCriteria(Criteria.where("aid").is(accountId).and("agid").is(adgroupEntity.getAdgroupId())));

                        //开始添加现在用户输入的关键词

                    }else{
                        //开始添加现在用户输入的关键词
                    }

                }

            }
        }

    }
}

package com.perfect.service.impl;

import com.perfect.app.assistantKeyword.vo.CampaignTree;
import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.service.AssistantKeywordService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public List<CampaignTree> getCampaignTree(Long accountId){
        List<CampaignTree> campaignTreeList = new ArrayList<>();
        List<CampaignEntity> campaignList = campaignDAO.find(new Query().addCriteria(Criteria.where("aid").is(accountId)));

        for(CampaignEntity campaignEntity:campaignList){
            List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where("cid").is(campaignEntity.getCampaignId()).and("aid").is(accountId)));
            CampaignTree campaignTree = new CampaignTree();
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
    public void deleteKeywordByNamesInput(Long accountId,String deleteInfos) {
        String[] everyDeleInfo = deleteInfos.split("\r\n");

        KeywordInfo keywordInfo;
        for(String str:everyDeleInfo){
            String[] fields = str.split(",|\t");
            List<CampaignEntity> campaignEntityList = findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId).and("name").is(fields[0])));
            CampaignEntity campaignEntity = campaignEntityList==null||campaignEntityList.size()==0?null:campaignEntityList.get(0);

            if(campaignEntity != null){
                List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where("aid").is(accountId).and("cid").is(campaignEntity.getCampaignId()).and("name").is(fields[1])));
                AdgroupEntity adgroupEntity = adgroupList==null||adgroupList.size()==0?null:adgroupList.get(0);

                if(adgroupEntity!=null){
                    keywordDAO.remove(new Query().addCriteria(Criteria.where("aid").is(accountId).and("agid").is(adgroupEntity.getAdgroupId()).and("name").is(fields[2])));
                }
            }

        }

    }
}

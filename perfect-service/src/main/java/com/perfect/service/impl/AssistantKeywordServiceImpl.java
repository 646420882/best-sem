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
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.service.AssistantKeywordService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
     * 根据账户id得到关键词
     * @return
     */

    @Override
    public List<KeywordEntity> getKeyWords(String cid,String aid) {

        String regex = "^\\d+$";

        List<KeywordEntity> keywordEntityList = new ArrayList<>();
        PaginationParam param = new PaginationParam();
        param.setStart(0);
        param.setLimit(Integer.MAX_VALUE);
        param.setAsc(true);
        param.setOrderBy("price");

        //若cid和aid都不为空，就是查询某单元下的关键词,在aid为空的时候就查询该计划下的关键词
        if(cid!=null && !"".equals(cid) && aid!=null && !"".equals(aid)){

            if(aid.matches(regex)==true){
                keywordEntityList.addAll( keywordDAO.getKeywordByAdgroupId(Long.parseLong(aid),null,0,Integer.MAX_VALUE));
            }else{
                keywordEntityList.addAll( keywordDAO.getKeywordByAdgroupId(aid,null,0,Integer.MAX_VALUE));
            }
            return keywordEntityList;
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
                for(AdgroupEntity ad :adgroupEntityList){
                    if(ad.getAdgroupId()==null){
                        keywordEntityList.addAll(keywordDAO.findByAdgroupId(ad.getId(),param));
                    }else{
                        keywordEntityList.addAll(keywordDAO.findByAdgroupId(ad.getAdgroupId(),param));
                    }
                }

            return keywordEntityList;
        }else{
            return keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())));
        }
    }


    /**
     * 根据关键词id删除关键词
     *
     * @param kwids
     */
    @Override
    public void deleteByKwIds(List<String> kwids) {
        String regex = "^\\d+$";

        for(String id:kwids){
            if(id.matches(regex)==true){
                keywordDAO.delete(Long.parseLong(id));
            }else{
                keywordDAO.deleteById(id);
            }
        }
    }


    /**
     * 修改关键词信息
     *
     * @param keywordEntity
     */
    @Override
    public void updateKeyword(KeywordEntity keywordEntity) {
        if(keywordEntity.getKeywordId()==null){
            keywordDAO.updateByMongoId(keywordEntity);
        }else{
            keywordDAO.update(keywordEntity);
        }

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
    public Map<String,Object> validateDeleteKeywordByChoose(Long accountId, String chooseInfos, String keywordNames) {
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
                List<KeywordEntity>  list =  keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(fileds[1]).and("name").is(name)));
                if(list.size()!=0){
                    KeywordDTO keywordDTO = new KeywordDTO();
                    keywordDTO.setCampaignName(fileds[0]);
                    keywordDTO.setAdgroupName(fileds[1]);
                    keywordDTO.setObject(list.get(0));
                    deleteKwd.add(keywordDTO);
                }else{
                    AssistantkwdIgnoreDeleDTO assistantkwdIgnoreDeleDTO = new AssistantkwdIgnoreDeleDTO();
                    assistantkwdIgnoreDeleDTO.setCampaignName(campaignDAO.findOne(Long.parseLong(fileds[0])).getCampaignName());
                    assistantkwdIgnoreDeleDTO.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fileds[1])).getAdgroupName());
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
                List<AdgroupEntity> adgroupList = adgroupDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).is(campaignEntity.getCampaignId()).and("name").is(fields[1])));
                AdgroupEntity adgroupEntity = adgroupList == null || adgroupList.size() == 0 ? null : adgroupList.get(0);

                if (adgroupEntity != null) {
                    List<KeywordEntity> keywordList = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).is(adgroupEntity.getAdgroupId()).and("name").is(fields[2])));
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

                    if(keywordEntity.getMatchType()==null){
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

                    List<KeywordEntity> list = null;
                    if(fieds[1].matches(regex)==true){
                        list = keywordDAO.findByAdgroupId(Long.parseLong(fieds[1]),getBasePagintionParam());
                    }else{
                        list = keywordDAO.findByAdgroupId(fieds[1],getBasePagintionParam());
                    }

                    if(list.size()==0){
                        insertList.add(setFieldToDTO(fieds,keywordEntity));
                    }else{
                        updateList.add(setFieldToDTO(fieds,keywordEntity));
                    }

                } else {
                    KeywordEntity keywordEntity = validateKewword(kwInfo);
                    if(keywordEntity.getMatchType()==null){
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

                    List<KeywordEntity>  list = null;
                    if(fieds[1].matches(regex)==true){
                        list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).is(fieds[1]).and("name").is(keywordEntity.getKeyword())));
                    }else{
                        list = keywordDAO.findByQuery(new Query().addCriteria(Criteria.where(EntityConstants.SYSTEM_ID).is(fieds[1]).and("name").is(keywordEntity.getKeyword())));
                    }
                    //若查询没有数据就添加这条数据,若有就更新这条数据
                    if(list==null||list.size()==0){
                        insertList.add(setFieldToDTO(fieds,keywordEntity));
                    }else{
                        updateList.add(setFieldToDTO(fieds,keywordEntity));
                    }
                }
            }
        }



        List<KeywordEntity> entities = new ArrayList<>();
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
        }


        Map<String,Object> map = new HashMap<>();
        map.put("insertList",insertList);
        map.put("updateList",updateList);
        map.put("igoneList",igoneList);
        map.put("delList",delList);

        return map;
    }

    private KeywordDTO setFieldToDTO(String[] fieds,KeywordEntity keywordEntity){
        KeywordDTO keywordDTO = new KeywordDTO();
        keywordDTO.setCampaignName(campaignDAO.findOne(Long.parseLong(fieds[0])).getCampaignName());
        keywordDTO.setAdgroupName(adgroupDAO.findOne(Long.parseLong(fieds[1])).getAdgroupName());
        keywordDTO.setObject(keywordEntity);
        return keywordDTO;
    }

    private PaginationParam getBasePagintionParam(){
        PaginationParam p = new PaginationParam();
        p.setAsc(true);
        p.setLimit(Integer.MAX_VALUE);
        p.setStart(0);
        p.setOrderBy("name");
        return p;
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
                if("精确".equals(kwInfo[i])){
                    keywordEntity.setMatchType(1);
                }else if("高级短语".equals(kwInfo[i])){
                     keywordEntity.setMatchType(2);
                }else if("广泛".equals(kwInfo[i])){
                     keywordEntity.setMatchType(3);
                }
            }else if(i==2){
                if(kwInfo[i].matches("^\\d+|\\.\\d+$")==true){
                    keywordEntity.setPrice(BigDecimal.valueOf(Double.parseDouble(kwInfo[i])));
                }else{
                    keywordEntity.setPrice(BigDecimal.ZERO);
                }
            }else if(i==3){
                if(kwInfo[i].matches("^([h{1}|H{1}][t{1}|T{1}][t{1}|T{1}][p{1}|P{1}]\\://)?[w{1,3}|W{1,3}]{3}\\.\\w+(\\.[a-zA-Z]+)+$")==true){
                    keywordEntity.setPcDestinationUrl(kwInfo[i]);
                }else{
                    keywordEntity.setPcDestinationUrl("");
                }
            }else if(i==4){
                if(kwInfo[i].matches("^([h{1}|H{1}][t{1}|T{1}][t{1}|T{1}][p{1}|P{1}]\\://)?[w{1,3}|W{1,3}]{3}\\.\\w+(\\.[a-zA-Z]+)+$")==true){
                    keywordEntity.setMobileDestinationUrl(kwInfo[i]);
                }else{
                    keywordEntity.setMobileDestinationUrl("");
                }
            }else if(i==5){
                if("暂停".equals(kwInfo[i])){
                    keywordEntity.setPause(true);
                }else{
                    keywordEntity.setPause(false);
                }
            }
        }
        return keywordEntity;
    }


    /**
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

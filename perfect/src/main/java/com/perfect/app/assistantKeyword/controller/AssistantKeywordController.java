package com.perfect.app.assistantKeyword.controller;

import com.google.gson.Gson;
import com.perfect.core.AppContext;
import com.perfect.dto.CampaignTreeDTO;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.service.AssistantKeywordService;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 2014/8/14.
 */
@RestController
@Scope("prototype")
public class AssistantKeywordController {


    //当前的账户id test
//    Long currentAccountId = 6243012L;


    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private WebContext webContext;


    /**
     * 得到当前账户所有的关键词
     * @return
     */
    @RequestMapping(value = "assistantKeyword/list",method = {RequestMethod.GET,RequestMethod.POST})
    public void getAllKeywordList(HttpServletResponse response){
        Iterable<KeywordEntity>  list = assistantKeywordService.getKeyWords(new Query().addCriteria(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())).limit(20));
        webContext.writeJson(list,response);
    }

    /**
     * 根据一个或者多个关键词id删除关键词
     * @param kwids
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteById" ,method = {RequestMethod.GET,RequestMethod.POST})
    public void deleteKeywordById(Long[] kwids){
        assistantKeywordService.deleteByKwIds(Arrays.asList(kwids));
    }


    /**
     * 修改以下参数的信息
     * @param kwid
     * @param price
     * @param pcDestinationUrl
     * @param mobileDestinationUrl
     * @param matchType
     * @param phraseType
     * @param pause
     * @return
     */
    @RequestMapping(value = "assistantKeyword/edit",method = {RequestMethod.GET,RequestMethod.POST})
    public void updateKeywordName(Long kwid,Double price,String pcDestinationUrl,String mobileDestinationUrl,Integer matchType,Integer phraseType,Boolean pause){
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(kwid);
        keywordEntity.setPrice(price);
        keywordEntity.setPcDestinationUrl(pcDestinationUrl);
        keywordEntity.setMobileDestinationUrl(mobileDestinationUrl);
        keywordEntity.setMatchType(matchType);
        keywordEntity.setPhraseType(phraseType);
        keywordEntity.setPause(pause);
        assistantKeywordService.updateKeyword(keywordEntity);
    }


    /**
     * 得到当前账户的推广计划的树形列表数据
     * @return
     */
    @RequestMapping(value = "assistantKeyword/campaignTree",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getCampaignTree(ModelMap modelMap){
        List<CampaignTreeDTO> treeList = assistantKeywordService.getCampaignTree(AppContext.getAccountId());

        String  gson = new Gson().toJson(treeList);
        System.out.println(gson);

        return new ModelAndView();
    }

    /**
     *(选择计划和单元，只输入关键词名称的方式)
     * 根据用户的选择计划和单元，以及输入的关键词名称进行批量删除关键词
     * @param chooseInfos 用户选择的一个或多个计划和单元
     * @param keywordNames 用户输入的一个或多个的关键词名称
     * @return
     */
   /* @RequestMapping(value = "assistantKeyword/deleteByNameChoose",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleteKeywordByNamesChoose(String chooseInfos,String keywordNames){
        assistantKeywordService.deleteKeywordByNamesChoose(currentAccountId,chooseInfos,keywordNames);
        return new ModelAndView();
    }*/



    /**（输入的方式）(未测试)
     *根据用户输入的删除信息（计划名称，单元名称，关键词名称）批量删除关键词
     * @param deleteInfos 用户输入的一系列的计划名称，单元名称，关键词名称
     * @return
     */
    @RequestMapping(value = "assistantKeyword/validateDeleteByInput",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView validateDeleteByInput(String deleteInfos){
        assistantKeywordService.validateDeleteByInput(AppContext.getAccountId(), deleteInfos);
        return new ModelAndView();
    }


    //未完成
    /**
     * （用户选择计划，单元，输入关键词的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     * @param isReplace 是否将用户输入的信息替换该单元下相应的内容
     * @param chooseInfos 用户选择的推广计划和退关单元
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
   /* @RequestMapping(value = "assistantKeyword/addOrUpdateKeywordByChoose",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView batchAddOrUpdateKeywordByChoose(Boolean isReplace,String chooseInfos,String keywordInfos){
        assistantKeywordService.batchAddOrUpdateKeywordByChoose(currentAccountId,isReplace,chooseInfos,keywordInfos);
        return new ModelAndView();
    }*/


    //未完成
    /**
     * （输入的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     * @param isReplace 是否将用户输入的信息替换该单元下相应的内容
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
   /* @RequestMapping(value = "assistantKeyword/addOrUpdateKeywordByInput",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView batchAddOrUpdateKeywordByInput(Boolean isReplace,String keywordInfos){
        assistantKeywordService.batchAddOrUpdateKeywordByInput(currentAccountId,isReplace,keywordInfos);
        return new ModelAndView();
    }*/

}

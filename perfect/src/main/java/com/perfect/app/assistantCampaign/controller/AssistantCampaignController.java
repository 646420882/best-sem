package com.perfect.app.assistantCampaign.controller;

import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 2014/8/15.
 */
@RestController
@Scope("prototype")
public class AssistantCampaignController {

    //当前的账户id test
    Long currentAccountId = 6243012L;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private WebContext webContext;


    /**
     * 得到当前账户的所有推广计划
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/list" ,method = {RequestMethod.GET,RequestMethod.POST})
    public void getAllCampaignList(HttpServletResponse response){
        List<CampaignEntity> list = campaignDAO.find(new Query().addCriteria(Criteria.where("aid").is(currentAccountId)));
        webContext.writeJson(list,response);
    }


    /**
     * 根据一个或多个cid删除推广计划
     * @param cid
     * @return
     */
    @RequestMapping(value = "assistantCampaign/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleteCampaignById(Long[] cid){
        campaignDAO.deleteByIds(Arrays.asList(cid));
        return new ModelAndView();
    }


    /**
     * 修改以下参数的信息
     * @param cid
     * @param campaignName
     * @param budget
     * @param priceRatio
     * @param regionTarget
     * @param isDynamicCreative
     * @param negativeWords
     * @param exactNegativeWords
     * @param excludeIp
     * @param showProb
     * @param pause
     * @return
     */
    @RequestMapping(value = "assistantCampaign/edit",method = {RequestMethod.GET,RequestMethod.POST})
    public void updateNameOrBudgetOrPriceRatio(Long cid,String campaignName,Double budget,Double priceRatio,Integer[] regionTarget,Boolean isDynamicCreative,
                                                       String[] negativeWords,String[] exactNegativeWords,String[] excludeIp,Integer showProb,Boolean pause
                                                       ) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignId(cid);
        campaignEntity.setCampaignName(campaignName.equals("")?null:campaignName);
        campaignEntity.setBudget(budget);
        campaignEntity.setPriceRatio(priceRatio);
        campaignEntity.setRegionTarget(regionTarget==null||regionTarget.length==0?null:Arrays.asList(regionTarget));
        campaignEntity.setIsDynamicCreative(isDynamicCreative);
        campaignEntity.setNegativeWords(negativeWords==null||negativeWords.length==0?null:Arrays.asList(negativeWords));
        campaignEntity.setExactNegativeWords(exactNegativeWords==null||exactNegativeWords.length==0?null:Arrays.asList(exactNegativeWords));
        campaignEntity.setExcludeIp(excludeIp==null||excludeIp.length==0?null:Arrays.asList(excludeIp));
        campaignEntity.setShowProb(showProb);
        campaignEntity.setPause(pause);

//        campaignDAO.update(campaignEntity);
    }


    /**
     * 添加一条推广计划(!!!)
     * @return
     */
  /*  @RequestMapping(value = "assistantCampaign/add")
    public ModelAndView addCampaign( String campaignName,Double budget,Double priceRatio,Boolean pause, Integer showProb,*//*String[] schedule,*//*Integer[] regionTarget,
                                     String[] negativeWords,String[] excludeIp,
                                     String adgroupName,Double maxPrice,Boolean pause2
                                    ){
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignName(campaignName);
        campaignEntity.setBudget(budget);
        campaignEntity.setPriceRatio(priceRatio);
        campaignEntity.setPause(pause);
        campaignEntity.setShowProb(showProb);
        campaignEntity.setRegionTarget(regionTarget==null?null: Arrays.asList(regionTarget));
        campaignEntity.setNegativeWords(negativeWords==null?null:Arrays.asList(negativeWords));
        campaignEntity.setExcludeIp(excludeIp==null?null:Arrays.asList(excludeIp));

        AdgroupEntity adgroupEntity = new AdgroupEntity();
        adgroupEntity.setAdgroupName(adgroupName);
        adgroupEntity.setMaxPrice(maxPrice);
        adgroupEntity.setPause(pause2);

        return new ModelAndView();
    }*/
}

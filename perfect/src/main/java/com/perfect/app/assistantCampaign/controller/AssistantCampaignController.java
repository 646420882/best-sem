package com.perfect.app.assistantCampaign.controller;

import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by john on 2014/8/15.
 */
@RestController
@Scope("prototype")
public class AssistantCampaignController {

    @Resource
    private CampaignDAO campaignDAO;


    /**
     * 得到所有的推广计划的list
     * @param model
     * @return
     */
    @RequestMapping(value = "assistantCampaign/list" ,method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getAllCampaignList(ModelMap model){
        model.addAttribute("list",campaignDAO.findAll());
        return new ModelAndView();
    }


    /**
     * 根据一个或多个cid删除推广计划
     * @param cid
     * @return
     */
    @RequestMapping(value = "assistantCampaign/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleteCampaignById(@RequestParam(value = "cid" ,required = false) Long[] cid){
        campaignDAO.deleteByIds(Arrays.asList(cid));
        return new ModelAndView();
    }


    /**
     * 修改以下参数的信息
     * @param cid
     * @param adgroupName
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
    @RequestMapping(value = "assistantCampaign/update*",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView updateNameOrBudgetOrPriceRatio(Long cid,String adgroupName,Double budget,Double priceRatio,Integer[] regionTarget,Boolean isDynamicCreative,
                                                       String[] negativeWords,String[] exactNegativeWords,String[] excludeIp,Integer showProb,Boolean pause
                                                       ) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignId(cid);
        campaignEntity.setCampaignName(adgroupName);
        campaignEntity.setBudget(budget);
        campaignEntity.setPriceRatio(priceRatio);
        campaignEntity.setRegionTarget(regionTarget==null?null:Arrays.asList(regionTarget));
        campaignEntity.setIsDynamicCreative(isDynamicCreative);
        campaignEntity.setNegativeWords(negativeWords==null?null:Arrays.asList(negativeWords));
        campaignEntity.setExactNegativeWords(exactNegativeWords==null?null:Arrays.asList(exactNegativeWords));
        campaignEntity.setExcludeIp(excludeIp==null?null:Arrays.asList(excludeIp));
        campaignEntity.setShowProb(showProb);
        campaignEntity.setPause(pause);

        campaignDAO.save(campaignEntity);
        return new ModelAndView();
    }


    /**
     * 修改推广地域
     * @param cid
     * @param regionTarget
     * @return
     */
    @RequestMapping(value = "assistantCampaign/updateRegionTarget")
    public ModelAndView updateRegionTarget(Long cid,Integer[] regionTarget){
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignId(cid);
        campaignEntity.setRegionTarget(Arrays.asList(regionTarget));
        campaignDAO.save(campaignEntity);

        return new ModelAndView();
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

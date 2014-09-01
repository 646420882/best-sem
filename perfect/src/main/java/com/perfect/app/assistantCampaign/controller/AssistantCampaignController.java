package com.perfect.app.assistantCampaign.controller;

import com.perfect.autosdk.sms.v3.ScheduleType;
import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.perfect.mongodb.utils.EntityConstants.ACCOUNT_ID;
/**
 * Created by john on 2014/8/15.
 */
@RestController
@Scope("prototype")
public class AssistantCampaignController {

    //当前的账户id test
//    Long currentAccountId = 6243012L;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private WebContext webContext;


    /**
     * 得到当前账户的所有推广计划
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/list" ,method = {RequestMethod.GET,RequestMethod.POST})
    public void getAllCampaignList(HttpServletResponse response){
        List<CampaignEntity> list = campaignDAO.find(new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())));
        webContext.writeJson(list,response);
    }


    /**
     * 根据推广计划id查找
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/getObject",method = {RequestMethod.GET,RequestMethod.POST})
    public void getCampaignByCid(HttpServletResponse response,Long cid){
        CampaignEntity  campaignEntity = campaignDAO.findOne(cid);
        webContext.writeJson(campaignEntity,response);
    }


    /**
     * 根据一个或多个cid删除推广计划
     * @param cid
     * @return
     */
    @RequestMapping(value = "assistantCampaign/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public void deleteCampaignById(Long[] cid){
        campaignDAO.deleteByIds(Arrays.asList(cid));
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
    public void updateById(Long cid,String campaignName,Double budget,Double priceRatio,Integer[] regionTarget,Boolean isDynamicCreative,
                                                       String negativeWords,String exactNegativeWords,String excludeIp,Integer showProb,Boolean pause,String schedule
                                                       ) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignId(cid);
        campaignEntity.setCampaignName(campaignName);
        campaignEntity.setBudget(budget);
        campaignEntity.setPriceRatio(priceRatio);
        campaignEntity.setRegionTarget(regionTarget == null ? null : "".equals(regionTarget) ? new ArrayList<Integer> (): Arrays.asList(regionTarget));
        campaignEntity.setIsDynamicCreative(isDynamicCreative);
        campaignEntity.setNegativeWords(negativeWords == null ? null : "".equals(negativeWords) ? new ArrayList<String>() : Arrays.asList(negativeWords.split("\n")));
        campaignEntity.setExactNegativeWords(exactNegativeWords == null ? null : "".equals(exactNegativeWords) ? new ArrayList<String>() : Arrays.asList(exactNegativeWords.split("\n")));
        campaignEntity.setExcludeIp(excludeIp == null ? null : "".equals(excludeIp) ? new ArrayList<String>() : Arrays.asList(excludeIp.split("\n")));
        campaignEntity.setShowProb(showProb);
        campaignEntity.setPause(pause);

        List<ScheduleType> scheduleEntityList = null;
        if(schedule!=null&&!"".equals(schedule)){
            scheduleEntityList = new ArrayList<>();
            String[] strSchedule = schedule.split(";");
            for(String str : strSchedule){
                String[] fieds = str.split("-");
                ScheduleType scheduleType = new ScheduleType();
                int i = 0;
                scheduleType.setWeekDay(Long.parseLong(fieds[i++]));
                scheduleType.setStartHour(Long.parseLong(fieds[i++]));
                scheduleType.setEndHour(Long.parseLong(fieds[i++]));
                scheduleEntityList.add(scheduleType);
            }
        }
        campaignEntity.setSchedule(scheduleEntityList);
        campaignDAO.update(campaignEntity);
    }


    /**
     * 添加一条推广计划
     * @return
     */
    @RequestMapping(value = "assistantCampaign/add")
    public void addCampaign( String campaignName,Double budget,Double priceRatio,Boolean pause, Integer showProb,String schedule,Integer[] regionTarget,
                                     String negativeWords,String exactNegativeWords,String excludeIp,
                                     String adgroupName,Double maxPrice,Boolean adgroupPause,Double adgroupPriceRatio
                                    ){

        //推广计划
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignName(campaignName);
        campaignEntity.setBudget(budget);
        campaignEntity.setPriceRatio(priceRatio);
        campaignEntity.setPause(pause);
        campaignEntity.setShowProb(showProb);
        List<ScheduleType> scheduleEntityList = null;
        if(schedule!=null&&!"".equals(schedule)){
            scheduleEntityList = new ArrayList<>();
            String[] strSchedule = schedule.split(";");
            for(String str : strSchedule){
                String[] fieds = str.split("-");
                ScheduleType scheduleType = new ScheduleType();
                int i = 0;
                scheduleType.setWeekDay(Long.parseLong(fieds[i++]));
                scheduleType.setStartHour(Long.parseLong(fieds[i++]));
                scheduleType.setEndHour(Long.parseLong(fieds[i++]));
                scheduleEntityList.add(scheduleType);
            }
        }
        campaignEntity.setSchedule(scheduleEntityList);
        campaignEntity.setRegionTarget(regionTarget == null ? null : "".equals(regionTarget) ? new ArrayList<Integer>() : Arrays.asList(regionTarget));
        campaignEntity.setNegativeWords(negativeWords == null ? null : "".equals(negativeWords) ? new ArrayList<String>() : Arrays.asList(negativeWords.split("\n")));
        campaignEntity.setExactNegativeWords(exactNegativeWords == null ? null : "".equals(exactNegativeWords) ? new ArrayList<String>() : Arrays.asList(exactNegativeWords.split("\n")));
        campaignEntity.setExcludeIp(excludeIp == null ? null : "".equals(excludeIp) ? new ArrayList<String>() : Arrays.asList(excludeIp.split("\n")));

/*
        //推广单元
        AdgroupEntity adgroupEntity = new AdgroupEntity();
        adgroupEntity.setAdgroupName(adgroupName);
        adgroupEntity.setMaxPrice(maxPrice);
        adgroupEntity.setPause(adgroupPause);
        adgroupEntity.setPriceRatio(adgroupPriceRatio);*/

        //开始添加
//        campaignDAO.insert(campaignEntity);
//        adgroupDAO.insert(adgroupEntity);
    }
}

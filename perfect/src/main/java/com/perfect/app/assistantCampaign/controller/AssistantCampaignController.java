package com.perfect.app.assistantCampaign.controller;

import com.perfect.autosdk.sms.v3.OfflineTimeType;
import com.perfect.autosdk.sms.v3.ScheduleType;
import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.CampaignBackUpService;
import com.perfect.utils.web.WebContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.perfect.mongodb.utils.EntityConstants.ACCOUNT_ID;

/**
 * Created by john on 2014/8/15.
 */
@RestController
@Scope("prototype")
public class AssistantCampaignController {

    private static final String RES_SUCCESS = "sucess";
    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private CampaignBackUpService campaignBackUpService;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private WebContext webContext;


    /**
     * 得到当前账户的所有推广计划
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/list", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAllCampaignList(HttpServletResponse response, Integer nowPage, Integer pageSize) {
        if (nowPage == null) {
            nowPage = 1;
        }
        Query query = new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()));
        PagerInfo page = campaignDAO.findByPageInfo(query, pageSize, nowPage);
        webContext.writeJson(page, response);
    }


    /**
     * 根据推广计划id查找
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/getObject", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCampaignByCid(HttpServletResponse response, String cid) {
        String regex = "^\\d+$";
        if (cid.matches(regex) == true) {
            webContext.writeJson(campaignDAO.findOne(Long.parseLong(cid)), response);
        } else {
            webContext.writeJson(campaignDAO.findByObjectId(cid), response);
        }
    }


    /**
     * 根据一个或多个cid删除推广计划
     *
     * @param cid
     * @return
     */
    @RequestMapping(value = "assistantCampaign/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteCampaignById(HttpServletResponse response,String cid) {
        String regex = "^\\d+$";
        String[] cids = cid.split(",");
        for (String id : cids) {
            List<AdgroupEntity> list;
            if(id.matches(regex)==true){
                list = adgroupDAO.findByCampaignId(Long.parseLong(id));
                CampaignEntity campaignEntity = campaignDAO.findOne(Long.parseLong(id));
                campaignEntity.setLocalStatus(3);
                campaignDAO.save(campaignEntity);
            }else{
                list = adgroupDAO.findByCampaignOId(id);
                campaignDAO.deleteByMongoId(id);
            }
            for(AdgroupEntity adgroupEntity : list){
                if (adgroupEntity.getAdgroupId()==null) {
                    adgroupDAO.deleteByObjId(adgroupEntity.getId());
                } else {
                    adgroupDAO.deleteByObjId(adgroupEntity.getAdgroupId());
                }
            }
        }
        webContext.writeJson(RES_SUCCESS,response);
    }


    /**
     * 修改以下参数的信息
     *
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
    @RequestMapping(value = "assistantCampaign/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public void updateById(HttpServletResponse response,String cid, String campaignName, Double budget, Double priceRatio, Integer[] regionTarget, Boolean isDynamicCreative,
                           String negativeWords, String exactNegativeWords, String excludeIp, Integer showProb, Boolean pause, String schedule) {

        String regex = "^\\d+$";

        CampaignEntity newCampaign = null;
        if(cid.matches(regex)==true){
            newCampaign = campaignDAO.findOne(Long.parseLong(cid));
        }else{
            newCampaign = campaignDAO.findByObjectId(cid);
        }
        CampaignEntity campaignEntity = new CampaignEntity();
        BeanUtils.copyProperties(newCampaign,campaignEntity);

        newCampaign.setCampaignName(campaignName == null ? newCampaign.getCampaignName() : campaignName);
        newCampaign.setBudget(budget == null ? newCampaign.getBudget() : budget);
        newCampaign.setPriceRatio(priceRatio == null ? newCampaign.getPriceRatio() : priceRatio);
        newCampaign.setRegionTarget(regionTarget == null ? newCampaign.getRegionTarget() : "".equals(regionTarget) ? new ArrayList<Integer>() : Arrays.asList(regionTarget));
        newCampaign.setIsDynamicCreative(isDynamicCreative == null ? newCampaign.getIsDynamicCreative() : isDynamicCreative);
        newCampaign.setNegativeWords(negativeWords == null ? newCampaign.getNegativeWords() : "".equals(negativeWords) ? new ArrayList<String>() : Arrays.asList(negativeWords.split("\n")));
        newCampaign.setExactNegativeWords(exactNegativeWords == null ? newCampaign.getExactNegativeWords() : "".equals(exactNegativeWords) ? new ArrayList<String>() : Arrays.asList(exactNegativeWords.split("\n")));
        newCampaign.setExcludeIp(excludeIp == null ? newCampaign.getExcludeIp() : "".equals(excludeIp) ? new ArrayList<String>() : Arrays.asList(excludeIp.split("\n")));
        newCampaign.setShowProb(showProb == null ? newCampaign.getShowProb() : showProb);
        newCampaign.setPause(pause == null ? newCampaign.getPause() : pause);

        List<ScheduleType> scheduleEntityList = null;
        if (schedule != null && !"".equals(schedule)) {
            scheduleEntityList = new ArrayList<>();
            String[] strSchedule = schedule.split(";");
            for (String str : strSchedule) {
                String[] fieds = str.split("-");
                ScheduleType scheduleType = new ScheduleType();
                int i = 0;
                scheduleType.setWeekDay(Long.parseLong(fieds[i++]));
                scheduleType.setStartHour(Long.parseLong(fieds[i++]));
                scheduleType.setEndHour(Long.parseLong(fieds[i++]));
                scheduleEntityList.add(scheduleType);
            }
        }
        newCampaign.setSchedule(scheduleEntityList == null ? newCampaign.getSchedule() : scheduleEntityList);

        if(newCampaign.getCampaignId()==null){
            newCampaign.setLocalStatus(1);
        }else{
            newCampaign.setLocalStatus(2);
        }
        campaignDAO.updateByMongoId(newCampaign,campaignEntity);
        webContext.writeJson(newCampaign,response);
    }


    /**
     * 添加一条推广计划
     *
     * @return
     */
    @RequestMapping(value = "assistantCampaign/add")
    public void addCampaign(String campaignName, Double budget, Double priceRatio, Boolean pause, Integer showProb, String schedule, Integer[] regionTarget,
                            String negativeWords, String exactNegativeWords, String excludeIp,
                            String adgroupName, Double maxPrice, Boolean adgroupPause, Double adgroupPriceRatio
    ) {

        //推广计划
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignName(campaignName);
        campaignEntity.setBudget(budget);
        campaignEntity.setPriceRatio(priceRatio);
        campaignEntity.setPause(pause);
        campaignEntity.setShowProb(showProb);
        List<ScheduleType> scheduleEntityList = null;
        if (schedule != null && !"".equals(schedule)) {
            scheduleEntityList = new ArrayList<>();
            String[] strSchedule = schedule.split(";");
            for (String str : strSchedule) {
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
        campaignEntity.setRegionTarget(regionTarget == null ? new ArrayList<Integer>() : Arrays.asList(regionTarget));
        campaignEntity.setNegativeWords(negativeWords == null ? new ArrayList<String>() : Arrays.asList(negativeWords.split("\n")));
        campaignEntity.setExactNegativeWords(exactNegativeWords == null ? new ArrayList<String>() : Arrays.asList(exactNegativeWords.split("\n")));
        campaignEntity.setExcludeIp(excludeIp == null ? new ArrayList<String>() : Arrays.asList(excludeIp.split("\n")));
        campaignEntity.setBudgetOfflineTime(new ArrayList<OfflineTimeType>());
        campaignEntity.setAccountId(AppContext.getAccountId());
        campaignEntity.setStatus(-1);

        //开始添加
        campaignDAO.insert(campaignEntity);
    }

    /**
     * 还原修改的推广计划
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantCampaign/reducUpdate",method = {RequestMethod.GET,RequestMethod.POST})
    public void reducUpdate(HttpServletResponse response,String id){
        CampaignEntity campaignEntity = campaignBackUpService.reducUpdate(id);
        webContext.writeJson(campaignEntity,response);
    }


    /**
     * 还原功能软删除
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantCampaign/reducDel",method = {RequestMethod.GET,RequestMethod.POST})
    public void reducDel(HttpServletResponse response,String id){
        campaignBackUpService .reducDel(id);
        webContext.writeJson(RES_SUCCESS,response);
    }


    /**
     * 弹出快速创建计划窗口
     *
     */
    @RequestMapping(value = "assistantCampaign/showCreatePlanWindow",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showCreatePlanWindow(){
        return new ModelAndView("promotionAssistant/alert/quickCreatePlan");
    }


    /**
     * 添加计划并且添加该计划的推广单元和关键词
     * @return
     */
    @RequestMapping(value = "/assistantCampaign/quickCreateCampaign", method = RequestMethod.POST, produces = "application/json")
    public void quickCreateCampaign(@RequestBody List<KeywordEntity> list,String name,HttpServletResponse response) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //推广计划
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignName("新建计划"+df.format(new Date()));
        campaignEntity.setShowProb(1);
        campaignEntity.setPause(false);
        campaignEntity.setStatus(-1);
        campaignEntity.setPriceRatio(1.0);
        campaignEntity.setIsDynamicCreative(true);
        campaignEntity.setAccountId(AppContext.getAccountId());
        campaignEntity.setLocalStatus(1);

        String campaignObjectId = campaignDAO.insertReturnId(campaignEntity);


        AdgroupEntity adgroupEntity = new AdgroupEntity();
        adgroupEntity.setCampaignObjId(campaignObjectId);
        adgroupEntity.setAdgroupName(name);
        adgroupEntity.setMaxPrice(0d);
        adgroupEntity.setPause(false);
        adgroupEntity.setStatus(-1);
        adgroupEntity.setLocalStatus(1);

        String adgroupObjectId = (String)adgroupDAO.insertOutId(adgroupEntity);

        for(KeywordEntity kwd:list){
            kwd.setAdgroupObjId(adgroupObjectId);
            kwd.setAccountId(AppContext.getAccountId());
        }

        keywordDAO.insertAll(list);
        webContext.writeJson(RES_SUCCESS,response);
    }

}

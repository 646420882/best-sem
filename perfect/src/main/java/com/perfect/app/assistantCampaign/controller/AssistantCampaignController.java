package com.perfect.app.assistantCampaign.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.autosdk.sms.v3.OfflineTimeType;
import com.perfect.autosdk.sms.v3.ScheduleType;
import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.entity.*;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.CampaignBackUpService;
import com.perfect.service.SysRegionalService;
import com.perfect.utils.RegionalCodeUtils;
import com.perfect.utils.web.WebContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.perfect.mongodb.utils.EntityConstants.ACCOUNT_ID;

/**
 * Created by john on 2014/8/15.
 */
@RestController
@Scope("prototype")
public class AssistantCampaignController {

    private static final String RES_SUCCESS = "success";
    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private CampaignBackUpService campaignBackUpService;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private SysRegionalService sysRegionalService;

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
            nowPage = 0;
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
        CampaignEntity campaignEntity = null;

        if (cid.matches(regex) == true) {
            campaignEntity = campaignDAO.findOne(Long.parseLong(cid));
        } else {
            campaignEntity = campaignDAO.findByObjectId(cid);
        }
        webContext.writeJson(campaignEntity, response);
    }


    /**
     * 根据cid查找
     *
     * @param response
     * @param cid
     */
    @RequestMapping(value = "assistantCampaign/getRegion", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCampaign(HttpServletResponse response, String cid) {
        String regex = "^\\d+$";
        Map<String, Object> map = new HashMap<>();
        CampaignEntity campaignEntity = null;

        if (cid.matches(regex) == true) {
            campaignEntity = campaignDAO.findOne(Long.parseLong(cid));
        } else {
            campaignEntity = campaignDAO.findByObjectId(cid);
        }
//        Map<Integer, String> regionMap = RegionalCodeUtils.regionalCode(campaignEntity.getRegionTarget() == null ? new ArrayList<Integer>() : campaignEntity.getRegionTarget());
        List<RegionalCodeDTO> regionList = sysRegionalService.getRegionalId(campaignEntity.getRegionTarget() == null ? new ArrayList<Integer>() : campaignEntity.getRegionTarget());

        map.put("campObj", campaignEntity);
        map.put("regions", regionList);
        webContext.writeJson(map, response);
    }


    /**
     * 根据一个或多个cid删除推广计划
     *
     * @param cid
     * @return
     */
    @RequestMapping(value = "assistantCampaign/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteCampaignById(HttpServletResponse response, String cid) {
        String regex = "^\\d+$";
        String[] cids = cid.split(",");
        for (String id : cids) {
            if (id.matches(regex) == true) {
                campaignDAO.softDel(Long.parseLong(id));
            } else {
                campaignDAO.deleteByMongoId(id);
            }
        }
        webContext.writeJson(RES_SUCCESS, response);
    }


    /**
     * 得到当前登录账户的推广地域
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/getRegionByAcid", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountRegion(HttpServletResponse response) {
        SystemUserEntity currentUser = systemUserDAO.findByAid(AppContext.getAccountId());

        List<BaiduAccountInfoEntity> accounts = currentUser.getBaiduAccountInfoEntities();
        BaiduAccountInfoEntity baiduEntity = null;

        for (BaiduAccountInfoEntity accountInfoEntity : accounts) {
            if (accountInfoEntity.getId().longValue() == AppContext.getAccountId().longValue()) {
                baiduEntity = accountInfoEntity;
                break;
            }
        }

        List<RegionalCodeDTO> regionalCodeDTOs = sysRegionalService.getRegionalByRegionalId(baiduEntity.getRegionTarget());

        webContext.writeJson(regionalCodeDTOs, response);
    }


    /**
     * 使用账户推广地域
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/useAccoutRegion", method = {RequestMethod.GET, RequestMethod.POST})
    public void useAccoutRegion(HttpServletResponse response, String cid) {
        String regex = "^\\d+$";
        CampaignEntity campaignEntity = cid.matches(regex) ? campaignDAO.findOne(Long.parseLong(cid)) : campaignDAO.findByObjectId(cid);
        campaignEntity.setRegionTarget(null);
        campaignDAO.save(campaignEntity);
        webContext.writeJson(RES_SUCCESS, response);
    }


    /**
     * 使用计划推广地域
     *
     * @param
     */
    @RequestMapping(value = "assistantCampaign/usePlanRegion", method = {RequestMethod.GET, RequestMethod.POST})
    public void usePlanRegion(HttpServletResponse response, String regions, String cid) {
        String regex = "^\\d+$";
        CampaignEntity newCampaignEntity = cid.matches(regex) ? campaignDAO.findOne(Long.parseLong(cid)) : campaignDAO.findByObjectId(cid);

        CampaignEntity oldCampaignEntity = new CampaignEntity();
        BeanUtils.copyProperties(newCampaignEntity, oldCampaignEntity);

        String[] regeionArray = "".equals(regions) ? new String[]{} : regions.split(",");

        List<RegionalCodeDTO> regionName = new ArrayList<>();
        if (regeionArray.length != 0) {
            List<String> list = Arrays.asList(regeionArray);
            regionName = sysRegionalService.getRegionalName(list);
        }

        List<Integer> regionId = new ArrayList<>();
        for (RegionalCodeDTO dto : regionName) {
            if (dto.getRegionName() == null || "".equals(dto.getRegionName())) {
                if ("全部区域".equals(dto.getStateName())) {
                    regionId.add(Integer.parseInt(dto.getStateId()));
                } else {
                    regionId.add(Integer.parseInt(dto.getProvinceId()));
                }
            } else {
                regionId.add(Integer.parseInt(dto.getRegionId()));
            }
        }

        newCampaignEntity.setRegionTarget(regionId);

        if (newCampaignEntity.getCampaignId() == null) {
            newCampaignEntity.setLocalStatus(1);
        } else {
            newCampaignEntity.setLocalStatus(2);
        }
        campaignDAO.updateByMongoId(newCampaignEntity, oldCampaignEntity);

        webContext.writeJson(RES_SUCCESS, response);
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
    public void updateById(HttpServletResponse response, String cid, String campaignName, Double budget, Double priceRatio, Integer[] regionTarget, Boolean isDynamicCreative,
                           String negativeWords, String exactNegativeWords, String excludeIp, Integer showProb, Boolean pause, String schedule) {

        String regex = "^\\d+$";

        CampaignEntity newCampaign = null;
        if (cid.matches(regex) == true) {
            newCampaign = campaignDAO.findOne(Long.parseLong(cid));
        } else {
            newCampaign = campaignDAO.findByObjectId(cid);
        }
        CampaignEntity campaignEntity = new CampaignEntity();
        BeanUtils.copyProperties(newCampaign, campaignEntity);

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

        if (schedule != null) {
            Gson gson = new Gson();
            List<ScheduleType> scheduleTypes = gson.fromJson(schedule, new TypeToken<List<ScheduleType>>() {
            }.getType());
            newCampaign.setSchedule(scheduleTypes == null ? new ArrayList<ScheduleType>() : scheduleTypes);
        }

        if (newCampaign.getCampaignId() == null) {
            newCampaign.setLocalStatus(1);
        } else {
            newCampaign.setLocalStatus(2);
        }
        campaignDAO.updateByMongoId(newCampaign, campaignEntity);
        webContext.writeJson(newCampaign, response);
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

        if (schedule != null) {
            Gson gson = new Gson();
            List<ScheduleType> scheduleTypes = gson.fromJson(schedule, new TypeToken<List<ScheduleType>>() {
            }.getType());
            campaignEntity.setSchedule(scheduleTypes == null ? new ArrayList<ScheduleType>() : scheduleTypes);
        }

        campaignEntity.setRegionTarget(regionTarget == null ? new ArrayList<Integer>() : Arrays.asList(regionTarget));
        campaignEntity.setNegativeWords(negativeWords == null ? new ArrayList<String>() : Arrays.asList(negativeWords.split("\n")));
        campaignEntity.setExactNegativeWords(exactNegativeWords == null ? new ArrayList<String>() : Arrays.asList(exactNegativeWords.split("\n")));
        campaignEntity.setExcludeIp(excludeIp == null ? new ArrayList<String>() : Arrays.asList(excludeIp.split("\n")));
        campaignEntity.setBudgetOfflineTime(new ArrayList<OfflineTimeType>());
        campaignEntity.setAccountId(AppContext.getAccountId());
        campaignEntity.setStatus(-1);
        campaignEntity.setLocalStatus(1);

        //开始添加
        String id = campaignDAO.insertReturnId(campaignEntity);

        AdgroupEntity adgroupEntity = new AdgroupEntity();
        adgroupEntity.setCampaignObjId(id);
        adgroupEntity.setAdgroupName(adgroupName);
        adgroupEntity.setMaxPrice(maxPrice);
        adgroupEntity.setPause(adgroupPause);
        adgroupEntity.setStatus(-1);
        adgroupEntity.setLocalStatus(1);
        adgroupEntity.setPriceRatio(adgroupPriceRatio);
        adgroupDAO.insert(adgroupEntity);

    }

    /**
     * 还原修改的推广计划
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantCampaign/reducUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public void reducUpdate(HttpServletResponse response, String id) {
        CampaignEntity campaignEntity = campaignBackUpService.reducUpdate(id);
        webContext.writeJson(campaignEntity, response);
    }


    /**
     * 还原功能软删除
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantCampaign/reducDel", method = {RequestMethod.GET, RequestMethod.POST})
    public void reducDel(HttpServletResponse response, String id) {
        campaignBackUpService.reducDel(id);
        webContext.writeJson(RES_SUCCESS, response);
    }


    /**
     * 弹出快速创建计划窗口
     */
    @RequestMapping(value = "assistantCampaign/showCreatePlanWindow", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showCreatePlanWindow() {
        return new ModelAndView("promotionAssistant/alert/quickCreatePlan");
    }


    /**
     * 弹出设置推广地域窗口
     */
    @RequestMapping(value = "assistantCampaign/showSetPlace", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showSetPlace(ModelMap map, String cid) {
        map.addAttribute("cid", cid);
        return new ModelAndView("promotionAssistant/alert/setRegionTarget", map);
    }


    /**
     * 添加计划并且添加该计划的推广单元和关键词
     *
     * @return
     */
    @RequestMapping(value = "/assistantCampaign/quickCreateCampaign", method = RequestMethod.POST, produces = "application/json")
    public void quickCreateCampaign(@RequestBody List<KeywordEntity> list, String name, String regions, HttpServletResponse response) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        List<String> regionsStr = regions == null || "".equals(regions) ? null : Arrays.asList(regions.split(","));
        Map<Integer, String> regionMap = RegionalCodeUtils.regionalCodeName(regionsStr == null ? new ArrayList<String>() : regionsStr);

        //推广计划
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignName("新建计划" + df.format(new Date()));
        campaignEntity.setShowProb(1);
        campaignEntity.setPause(false);
        campaignEntity.setStatus(-1);
        campaignEntity.setPriceRatio(1.0);
        campaignEntity.setIsDynamicCreative(true);
        campaignEntity.setRegionTarget(new ArrayList<Integer>(regionMap.keySet()));
        campaignEntity.setExcludeIp(new ArrayList<String>());
        campaignEntity.setNegativeWords(new ArrayList<String>());
        campaignEntity.setExactNegativeWords(new ArrayList<String>());
        campaignEntity.setSchedule(new ArrayList<ScheduleType>());
        campaignEntity.setBudgetOfflineTime(new ArrayList<OfflineTimeType>());
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

        String adgroupObjectId = (String) adgroupDAO.insertOutId(adgroupEntity);

        for (KeywordEntity kwd : list) {
            kwd.setAdgroupObjId(adgroupObjectId);
            kwd.setAccountId(AppContext.getAccountId());
        }

        keywordDAO.insertAll(list);
        webContext.writeJson(RES_SUCCESS, response);
    }

}

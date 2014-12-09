package com.perfect.app.assistant.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.autosdk.sms.v3.ScheduleType;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SchedulerDTO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.baidu.OfflineTimeDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.regional.RegionalCodeDTO;
import com.perfect.service.*;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.utils.RegionalCodeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
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

import static com.perfect.commons.constants.MongoEntityConstants.ACCOUNT_ID;

/**
 * Created by john on 2014/8/15.
 * 2014-11-28 refactor XiaoWei
 */
@RestController
@Scope("prototype")
public class AssistantCampaignController extends WebContextSupport {

    private static final String RES_SUCCESS = "success";
    @Resource
    private CampaignService campaignService;

    @Resource
    private CampaignBackUpService campaignBackUpService;

    @Resource
    private AdgroupService adgroupService;

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private KeywordService keywordService;

    @Resource
    private SysRegionalService sysRegionalService;



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
        long acctountId=AppContext.getAccountId();

        PagerInfo page = campaignService.findByPageInfo(acctountId, pageSize, nowPage);
        writeJson(page, response);
    }


    /**
     * 根据推广计划id查找
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/getObject", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCampaignByCid(HttpServletResponse response, String cid) {
        String regex = "^\\d+$";
        CampaignDTO campaignEntity = null;

        if (cid.matches(regex) == true) {
            campaignEntity = campaignService.findOne(Long.parseLong(cid));
        } else {
            campaignEntity = campaignService.findByObjectId(cid);
        }
        writeJson(campaignEntity, response);
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
        CampaignDTO campaignEntity = null;

        if (cid.matches(regex) == true) {
            campaignEntity = campaignService.findOne(Long.parseLong(cid));
        } else {
            campaignEntity = campaignService.findByObjectId(cid);
        }
        List<RegionalCodeDTO> regionList = sysRegionalService.getRegionalId(campaignEntity.getRegionTarget() == null ? new ArrayList<Integer>() : campaignEntity.getRegionTarget());

        map.put("campObj", campaignEntity);
        map.put("regions", regionList);
        writeJson(map, response);
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
                campaignService.softDel(Long.parseLong(id));
            } else {
                campaignService.deleteByMongoId(id);
            }
        }
        writeJson(RES_SUCCESS, response);
    }


    /**
     * 得到当前登录账户的推广地域
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/getRegionByAcid", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountRegion(HttpServletResponse response) {
        SystemUserDTO currentUser = systemUserService.findByAid(AppContext.getAccountId());

        List<BaiduAccountInfoDTO> accounts = currentUser.getBaiduAccounts();
        BaiduAccountInfoDTO baiduEntity = null;

        for (BaiduAccountInfoDTO accountInfoDTO : accounts) {
            if (accountInfoDTO.getId().longValue() == AppContext.getAccountId().longValue()) {
                baiduEntity = accountInfoDTO;
                break;
            }
        }

        List<RegionalCodeDTO> regionalCodeDTOs = sysRegionalService.getProvinceIdByRegionalId(baiduEntity.getRegionTarget());

        writeJson(regionalCodeDTOs, response);
    }


    /**
     * 使用账户推广地域
     *
     * @param response
     */
    @RequestMapping(value = "assistantCampaign/useAccoutRegion", method = {RequestMethod.GET, RequestMethod.POST})
    public void useAccoutRegion(HttpServletResponse response, String cid) {
        String regex = "^\\d+$";
        CampaignDTO campaignDTO = cid.matches(regex) ? campaignService.findOne(Long.parseLong(cid)) : campaignService.findByObjectId(cid);
        campaignDTO.setRegionTarget(null);
        campaignService.save(campaignDTO);
        writeJson(RES_SUCCESS, response);
    }


    /**
     * 使用计划推广地域
     *
     * @param
     */
    @RequestMapping(value = "assistantCampaign/usePlanRegion", method = {RequestMethod.GET, RequestMethod.POST})
    public void usePlanRegion(HttpServletResponse response, String regions, String cid) {
        String regex = "^\\d+$";
        CampaignDTO newCampaignDTO = cid.matches(regex) ? campaignService.findOne(Long.parseLong(cid)) : campaignService.findByObjectId(cid);

        CampaignDTO oldCampaignEntity = new CampaignDTO();
        BeanUtils.copyProperties(newCampaignDTO, oldCampaignEntity);

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

        newCampaignDTO.setRegionTarget(regionId);

        if (newCampaignDTO.getCampaignId() == null) {
            newCampaignDTO.setLocalStatus(1);
        } else {
            newCampaignDTO.setLocalStatus(2);
        }
        campaignService.updateByMongoId(newCampaignDTO, oldCampaignEntity);

        writeJson(RES_SUCCESS, response);
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

        CampaignDTO newCampaign = null;
        if (cid.matches(regex) == true) {
            newCampaign = campaignService.findOne(Long.parseLong(cid));
        } else {
            newCampaign = campaignService.findByObjectId(cid);
        }
        CampaignDTO campaignEntity = new CampaignDTO();
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
            List<SchedulerDTO> scheduleTypes = gson.fromJson(schedule, new TypeToken<List<ScheduleType>>() {
            }.getType());
            newCampaign.setSchedule(scheduleTypes == null ? new ArrayList<SchedulerDTO>() : scheduleTypes);
        }

        if (newCampaign.getCampaignId() == null) {
            newCampaign.setLocalStatus(1);
        } else {
            newCampaign.setLocalStatus(2);
        }
        campaignService.updateByMongoId(newCampaign, campaignEntity);
        writeJson(newCampaign, response);
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
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setCampaignName(campaignName);
        campaignDTO.setBudget(budget);
        campaignDTO.setPriceRatio(priceRatio);
        campaignDTO.setPause(pause);
        campaignDTO.setShowProb(showProb);

        if (schedule != null) {
            Gson gson = new Gson();
            List<SchedulerDTO> scheduleTypes = gson.fromJson(schedule, new TypeToken<List<ScheduleType>>() {
            }.getType());
            campaignDTO.setSchedule(scheduleTypes == null ? new ArrayList<SchedulerDTO>() : scheduleTypes);
        }

        campaignDTO.setRegionTarget(regionTarget == null ? new ArrayList<Integer>() : Arrays.asList(regionTarget));
        campaignDTO.setNegativeWords(negativeWords == null || "".equals(negativeWords) ? new ArrayList<String>() : Arrays.asList(negativeWords.split("\n")));
        campaignDTO.setExactNegativeWords(exactNegativeWords == null || "".equals(exactNegativeWords) ? new ArrayList<String>() : Arrays.asList(exactNegativeWords.split("\n")));
        campaignDTO.setExcludeIp(excludeIp == null || "".equals(excludeIp) ? new ArrayList<String>() : Arrays.asList(excludeIp.split("\n")));
        campaignDTO.setBudgetOfflineTime(new ArrayList<OfflineTimeDTO>());
        campaignDTO.setAccountId(AppContext.getAccountId());
        campaignDTO.setStatus(-1);
        campaignDTO.setLocalStatus(1);

        //开始添加
        String id = campaignService.insertReturnId(campaignDTO);

        AdgroupDTO adgroupDTO = new AdgroupDTO();
        adgroupDTO.setCampaignObjId(id);
        adgroupDTO.setAdgroupName(adgroupName);
        adgroupDTO.setMaxPrice(maxPrice);
        adgroupDTO.setPause(adgroupPause);
        adgroupDTO.setStatus(-1);
        adgroupDTO.setLocalStatus(1);
        adgroupDTO.setPriceRatio(adgroupPriceRatio);
        adgroupDTO.setAccountId(AppContext.getAccountId());
        adgroupService.save(adgroupDTO);

    }

    /**
     * 还原修改的推广计划
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantCampaign/reducUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public void reducUpdate(HttpServletResponse response, String id) {
        CampaignDTO campaignEntity = campaignBackUpService.reducUpdate(id);
        writeJson(campaignEntity, response);
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
        writeJson(RES_SUCCESS, response);
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
    public void quickCreateCampaign(@RequestBody List<KeywordDTO> list, String name, String regions, HttpServletResponse response) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        list.remove(list.size() - 1);

        List<String> regionsStr = regions == null || "".equals(regions) ? null : Arrays.asList(regions.split(","));
        Map<Integer, String> regionMap = RegionalCodeUtils.regionalCodeName(regionsStr == null ? new ArrayList<String>() : regionsStr);

        //推广计划
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setCampaignName("新建计划" + df.format(new Date()));
        campaignDTO.setShowProb(1);
        campaignDTO.setPause(false);
        campaignDTO.setStatus(-1);
        campaignDTO.setPriceRatio(1.0);
        campaignDTO.setIsDynamicCreative(true);
        campaignDTO.setRegionTarget(new ArrayList<Integer>(regionMap.keySet()));
        campaignDTO.setExcludeIp(new ArrayList<String>());
        campaignDTO.setNegativeWords(new ArrayList<String>());
        campaignDTO.setExactNegativeWords(new ArrayList<String>());
        campaignDTO.setSchedule(new ArrayList<SchedulerDTO>());
        campaignDTO.setBudgetOfflineTime(new ArrayList<OfflineTimeDTO>());
        campaignDTO.setAccountId(AppContext.getAccountId());
        campaignDTO.setLocalStatus(1);

        String campaignObjectId = campaignService.insertReturnId(campaignDTO);


        AdgroupDTO adgroupEntity = new AdgroupDTO();
        adgroupEntity.setCampaignObjId(campaignObjectId);
        adgroupEntity.setAdgroupName(name);
        adgroupEntity.setMaxPrice(0d);
        adgroupEntity.setPause(false);
        adgroupEntity.setStatus(-1);
        adgroupEntity.setLocalStatus(1);
        adgroupEntity.setAccountId(AppContext.getAccountId());

        String adgroupObjectId = (String) adgroupService.insertOutId(adgroupEntity);

        for (KeywordDTO kwd : list) {
            kwd.setAdgroupObjId(adgroupObjectId);
            kwd.setAccountId(AppContext.getAccountId());
        }

        keywordService.insertAll(list);
        writeJson(RES_SUCCESS, response);
    }

}

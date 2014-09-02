package com.perfect.app.assistantAdgroup.controller;

import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/assistantAdgroup")
public class AssistantAdgroupController extends WebContextSupport {
    @Resource
    AdgroupDAO adgroupDAO;
    @Resource
    CampaignDAO campaignDAO;

    /**
     * 默认加载单元数据
     *
     * @param request
     * @param response
     * @param cid
     * @return
     */
    @RequestMapping(value = "/getAdgroupList")
    public ModelAndView getList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "cid", required = false) String cid) {
        List<AdgroupEntity> list = new ArrayList<>();
        if (cid.length() > 18) {
            if (cid != "" || !cid.equals("")) {
                Map<String, Object> parms = new HashMap<>();
                parms.put(EntityConstants.OBJ_CAMPAIGN_ID,cid);
                list = adgroupDAO.find(parms, 0, 15);
            } else {
                list = adgroupDAO.find(null, 0, 15);
            }
        } else {
            if (cid != "" || !cid.equals("")) {
                Map<String, Object> parms = new HashMap<>();
                parms.put(EntityConstants.CAMPAIGN_ID, Long.parseLong(cid));
                list = adgroupDAO.find(parms, 0, 15);
            } else {
                list = adgroupDAO.find(null, 0, 15);
            }
            if (list.size() > 0) {
                List<CampaignEntity> campaignEntity = (List<CampaignEntity>) campaignDAO.findAll();
                for (int i = 0; i < campaignEntity.size(); i++) {
                    for (AdgroupEntity a : list) {
                        if (a.getCampaignId().equals(campaignEntity.get(i).getCampaignId())) {
                            a.setCampaignName(campaignEntity.get(i).getCampaignName());
                        }
                    }
                }
            }
        }
        writeJson(list, response);
        return null;
    }

    /**
     * 获取全部的计划供添加选择使用
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPlans", method = RequestMethod.GET)
    public ModelAndView getPlans(HttpServletRequest request, HttpServletResponse response) {
        List<CampaignEntity> campaignEntities = (List<CampaignEntity>) campaignDAO.findAll();
        writeJson(campaignEntities, response);
        return null;
    }

    @RequestMapping(value = "/adAdd", method = RequestMethod.POST)
    public ModelAndView adAdd(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "oid", required = true) String agid,
                              @RequestParam(value = "cid", required = true) String cid,
                              @RequestParam(value = "adgroupName") String name,
                              @RequestParam(value = "maxPrice") Double maxPrice,
                              @RequestParam(value = "negativeWords") List<String> nn,
                              @RequestParam(value = "exactNegativeWords") List<String> ne,
                              @RequestParam(value = "pause") Boolean p,
                              @RequestParam(value = "status") Integer s,
                              @RequestParam(value = "mib") Double mib) {
        try {
            AdgroupEntity adgroupEntity = new AdgroupEntity();
            adgroupEntity.setAccountId(AppContext.getAccountId());
            adgroupEntity.setAdgroupId(Long.parseLong(agid));
            if(cid.length()>18){
                adgroupEntity.setCampaignObjId(cid);
            }else{
                adgroupEntity.setCampaignId(Long.parseLong(cid));
            }
            adgroupEntity.setAdgroupName(name);
            adgroupEntity.setMaxPrice(maxPrice);
            adgroupEntity.setNegativeWords(nn);
            adgroupEntity.setExactNegativeWords(ne);
            adgroupEntity.setPause(p);
            adgroupEntity.setStatus(s);
            adgroupEntity.setMib(mib);
            adgroupDAO.insert(adgroupEntity);
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    @RequestMapping(value = "/del")
    public ModelAndView del(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "oid", required = true) Long oid) {
        try {
            adgroupDAO.delete(oid);
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }

        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletResponse response,
                               @RequestParam(value = "oid", required = true) Long agid,
                               @RequestParam(value = "adgroupName") String name,
                               @RequestParam(value = "maxPrice") Double maxPrice,
                               @RequestParam(value = "negativeWords") List<String> nn,
                               @RequestParam(value = "exactNegativeWords") List<String> ne,
                               @RequestParam(value = "pause") Boolean p,
                               @RequestParam(value = "mib") Double mib) {
        try {
            AdgroupEntity adgroupEntityFind = adgroupDAO.findOne(agid);
            adgroupEntityFind.setAdgroupName(name);
            adgroupEntityFind.setMaxPrice(maxPrice);
            adgroupEntityFind.setMib(mib);
            adgroupEntityFind.setNegativeWords(nn);
            adgroupEntityFind.setExactNegativeWords(ne);
            adgroupEntityFind.setMib(mib);
            adgroupDAO.update(adgroupEntityFind);
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    @RequestMapping(value = "/updateByChange", method = RequestMethod.GET)
    public ModelAndView updateByChange(HttpServletResponse response, @RequestParam(value = "oid", required = true) Long oid,
                                       @RequestParam(value = "pause", required = true) Boolean pause) {
        try {
            AdgroupEntity adgroupEntity = adgroupDAO.findOne(oid);
            adgroupEntity.setPause(pause);
            adgroupDAO.update(adgroupEntity);
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

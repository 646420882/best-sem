package com.perfect.app.assistantCreative.controller;

import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.CreativeDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.CreativeEntity;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/assistantCreative")
public class AssistantCreativeController extends WebContextSupport {

    private static long accountId=6243012L;

    @Resource
    CreativeDAO creativeDAO;
    @Resource
    AdgroupDAO adgroupDAO;
    @Resource
    CampaignDAO campaignDAO;

    @RequestMapping(value = "/getList")
    public ModelAndView getCreativeList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "cid", required = false) String cid,
                                        @RequestParam(value = "aid", required = false) String aid) {
        List<CreativeEntity> creativeEntityList= new ArrayList<>();
        if (aid != "" || !aid.equals("")) {
            creativeEntityList= creativeDAO.getCreativeByAdgroupId(Long.parseLong(aid), null, 0, 10);
        }else if(!cid.equals("")&&aid.equals("")){
            List<Long> adgroupIds=adgroupDAO.getAdgroupIdByCampaignId(Long.parseLong(cid));
            creativeEntityList= (List<CreativeEntity>) creativeDAO.getAllsByAdgroupIds(adgroupIds);

        }else{
            creativeEntityList=creativeDAO.find(null,0,200);
        }

        writeJson(creativeEntityList, response);
        return null;
    }

    /**
     *  返回json数组关于全部计划的
     * @return
     */
    @RequestMapping(value = "/getPlans")
    public ModelAndView getPlans(HttpServletResponse response){
        List<CampaignEntity> list= (List<CampaignEntity>) campaignDAO.findAll();
        writeJson(list,response);
        return null;
    }

    /**
     * 根据计划id获取单元列表
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUnitsByPlanId")
    public ModelAndView getUnitsByPlanId(HttpServletResponse response,@RequestParam(value = "planId",required = true)String planId){
        List<AdgroupEntity> adgroupEntities=adgroupDAO.findByQuery(new Query(Criteria.where("cid").is(Long.parseLong(planId))));
        writeJson(adgroupEntities,response);
    return null;
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ModelAndView insertCreative(HttpServletRequest request,HttpServletResponse response,
                                       @RequestParam(value = "aid",required = true)String aid,
                                       @RequestParam(value = "cacheCativeId",required = true)Long cacheCreativeId,
                                       @RequestParam(value = "title",required = false)String title,
                                       @RequestParam(value="description1",required = false)String de1,
                                       @RequestParam(value = "description2",required = false)String de2,
                                       @RequestParam(value = "pcDestinationUrl",required = false)String pc,
                                       @RequestParam(value = "pcDisplayUrl",required = false)String pcs,
                                       @RequestParam(value = "mobileDestinationUrl",required = false)String mib,
                                       @RequestParam(value = "mobileDisplayUrl",required = false)String mibs,
                                       @RequestParam(value = "pause")Boolean bol,
                                       @RequestParam(value = "status")Integer s,
                                       @RequestParam(value = "d",required = false,defaultValue = "0")Integer d){
        CreativeEntity creativeEntity=new CreativeEntity();
        creativeEntity.setAccountId(AppContext.getAccountId());
        creativeEntity.setTitle(title);
        creativeEntity.setCreativeId(cacheCreativeId);
        creativeEntity.setDescription1(de1);
        creativeEntity.setDescription2(de2);
        creativeEntity.setPcDestinationUrl(pc);
        creativeEntity.setPcDisplayUrl(pcs);
        creativeEntity.setMobileDestinationUrl(mib);
        creativeEntity.setMobileDisplayUrl(mibs);
        creativeEntity.setPause(bol);
        creativeEntity.setStatus(s);
        creativeEntity.setDevicePreference(d);
        creativeEntity.setAdgroupId(Long.parseLong(aid));
        creativeDAO.insert(creativeEntity);
        writeHtml(SUCCESS, response);

        return null;
    }
    @RequestMapping(value = "/del")
    public ModelAndView del(HttpServletResponse response,@RequestParam(value = "oid",required = true)Long oid){
        try {
            creativeDAO.deleteByCacheId(oid);
            writeHtml(SUCCESS,response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

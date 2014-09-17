package com.perfect.app.assistantCreative.controller;

import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.CreativeDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.CreativeEntity;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.CreativeBackUpService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.beans.BeanUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.perfect.mongodb.utils.EntityConstants.*;

/**
 * Created by XiaoWei on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/assistantCreative")
public class AssistantCreativeController extends WebContextSupport {

    private static long accountId = 6243012L;
    private static Integer OBJ_SIZE = 18;

    @Resource
    CreativeDAO creativeDAO;
    @Resource
    AdgroupDAO adgroupDAO;
    @Resource
    CampaignDAO campaignDAO;
    @Resource
    CreativeBackUpService creativeBackUpService;

    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public ModelAndView getCreativeList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "cid", required = false) String cid,
                                        @RequestParam(value = "aid", required = false) String aid,
                                        @RequestParam(value = "nowPage",required = true)Integer nowPage,
                                        @RequestParam(value = "pageSize",required = true)Integer pageSize){
        PagerInfo pagerInfo=null;
        Map<String,Object> map=new HashMap<>();
        if (aid.length() > OBJ_SIZE || cid.length() > OBJ_SIZE) {
            if (aid != "" || !aid.equals("")) {
                map.put(EntityConstants.ADGROUP_ID,aid);
                pagerInfo = creativeDAO.findByPagerInfo(map,nowPage,pageSize);
            } else if (!cid.equals("") && aid.equals("")) {
                List<String> adgroupIds = adgroupDAO.getAdgroupIdByCampaignId(cid);
                pagerInfo = creativeDAO.findByPagerInfoForString(adgroupIds, nowPage, pageSize);
            } else {
                pagerInfo = creativeDAO.findByPagerInfo(map, nowPage, pageSize);
            }
        } else {
            if (aid != "" || !aid.equals("")) {
                pagerInfo = creativeDAO.findByPagerInfo(Long.parseLong(aid), nowPage, pageSize);
            } else if (!cid.equals("") && aid.equals("")) {
                List<Long> adgroupIds = adgroupDAO.getAdgroupIdByCampaignId(Long.parseLong(cid));
                pagerInfo=creativeDAO.findByPagerInfoForLong(adgroupIds,nowPage,pageSize);

            } else {
                pagerInfo = creativeDAO.findByPagerInfo(map, nowPage, pageSize);
            }
        }

        writeJson(pagerInfo, response);
        return null;
    }

    /**
     * 返回json数组关于全部计划的
     *
     * @return
     */
    @RequestMapping(value = "/getPlans")
    public ModelAndView getPlans(HttpServletResponse response) {
        List<CampaignEntity> list = (List<CampaignEntity>) campaignDAO.findAll();
        writeJson(list, response);
        return null;
    }

    /**
     * 根据计划id获取单元列表
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUnitsByPlanId")
    public ModelAndView getUnitsByPlanId(HttpServletResponse response, @RequestParam(value = "planId", required = true) String planId) {
        List<AdgroupEntity> adgroupEntities = new ArrayList<>();
        if (planId.length() > OBJ_SIZE) {
            adgroupEntities = adgroupDAO.findByQuery(new Query(Criteria.where(OBJ_CAMPAIGN_ID).is(planId)));
        } else {
            adgroupEntities = adgroupDAO.findByQuery(new Query(Criteria.where(CAMPAIGN_ID).is(Long.parseLong(planId))));
        }
        writeJson(adgroupEntities, response);
        return null;
    }

    /**
     * 添加方法
     *
     * @param request
     * @param response
     * @param aid
     * @param title
     * @param de1
     * @param de2
     * @param pc
     * @param pcs
     * @param mib
     * @param mibs
     * @param bol
     * @param s
     * @param d
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView insertCreative(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "aid", required = true) String aid,
                                       @RequestParam(value = "title", required = false) String title,
                                       @RequestParam(value = "description1", required = false) String de1,
                                       @RequestParam(value = "description2", required = false) String de2,
                                       @RequestParam(value = "pcDestinationUrl", required = false) String pc,
                                       @RequestParam(value = "pcDisplayUrl", required = false) String pcs,
                                       @RequestParam(value = "mobileDestinationUrl", required = false) String mib,
                                       @RequestParam(value = "mobileDisplayUrl", required = false) String mibs,
                                       @RequestParam(value = "pause") Boolean bol,
                                       @RequestParam(value = "status") Integer s,
                                       @RequestParam(value = "d", required = false, defaultValue = "0") Integer d) {
        try {
            CreativeEntity creativeEntity = new CreativeEntity();
            creativeEntity.setAccountId(AppContext.getAccountId());
            creativeEntity.setTitle(title);

            creativeEntity.setDescription1(de1);
            creativeEntity.setDescription2(de2);
            creativeEntity.setPcDestinationUrl(pc);
            creativeEntity.setPcDisplayUrl(pcs);
            creativeEntity.setMobileDestinationUrl(mib);
            creativeEntity.setMobileDisplayUrl(mibs);
            creativeEntity.setPause(bol);
            creativeEntity.setStatus(s);
            creativeEntity.setDevicePreference(d);
            creativeEntity.setLocalStatus(1);
            if (aid.length() > OBJ_SIZE) {
                creativeEntity.setAdgroupObjId(aid);
                creativeEntity.setCreativeId(null);
            } else {
                creativeEntity.setAdgroupId(Long.parseLong(aid));
            }
            String oid = creativeDAO.insertOutId(creativeEntity);
            writeData(SUCCESS, response, oid);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }


        return null;
    }

    /**
     * 删除方法 根据缓存的creativeId
     *
     * @param response
     * @param oid      缓存的creativeId
     * @return
     */
    @RequestMapping(value = "/del")
    public ModelAndView del(HttpServletResponse response, @RequestParam(value = "oid", required = true) String oid) {
        try {
            if (oid.length() > OBJ_SIZE) {
                creativeDAO.deleteByCacheId(oid);
                writeHtml(SUCCESS, response);
            } else {
                creativeDAO.deleteByCacheId(Long.valueOf(oid));
                writeHtml(SUCCESS, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 修改方法，如果是本地数据，则不进行备份处理，否则进行备份处理
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletResponse response, HttpServletRequest request,
                               @RequestParam(value = "oid", required = true) String oid,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "description1", required = false) String de1,
                               @RequestParam(value = "description2", required = false) String de2,
                               @RequestParam(value = "pcDestinationUrl", required = false) String pc,
                               @RequestParam(value = "pcDisplayUrl", required = false) String pcs,
                               @RequestParam(value = "mobileDestinationUrl", required = false) String mib,
                               @RequestParam(value = "mobileDisplayUrl", required = false) String mibs,
                               @RequestParam(value = "pause") Boolean bol) {
        CreativeEntity creativeEntityFind = null;
        if (oid.length() > OBJ_SIZE) {
            creativeEntityFind = creativeDAO.findByObjId(oid);
            creativeEntityFind.setTitle(title);
            creativeEntityFind.setDescription1(de1);
            creativeEntityFind.setDescription2(de2);
            creativeEntityFind.setPcDestinationUrl(pc);
            creativeEntityFind.setPcDisplayUrl(pcs);
            creativeEntityFind.setMobileDestinationUrl(mib);
            creativeEntityFind.setMobileDisplayUrl(mibs);
            creativeEntityFind.setPause(bol);
            creativeEntityFind.setLocalStatus(1);
            creativeDAO.updateByObjId(creativeEntityFind);
            writeHtml(SUCCESS, response);
        } else {
            creativeEntityFind = creativeDAO.findOne(Long.valueOf(oid));
            CreativeEntity creativeEntity = new CreativeEntity();
            creativeEntityFind.setLocalStatus(2);
            BeanUtils.copyProperties(creativeEntityFind, creativeEntity);
            creativeEntityFind.setTitle(title);
            creativeEntityFind.setDescription1(de1);
            creativeEntityFind.setDescription2(de2);
            creativeEntityFind.setPcDestinationUrl(pc);
            creativeEntityFind.setPcDisplayUrl(pcs);
            creativeEntityFind.setMobileDestinationUrl(mib);
            creativeEntityFind.setMobileDisplayUrl(mibs);
            creativeEntityFind.setPause(bol);

            creativeDAO.update(creativeEntityFind, creativeEntity);
            writeHtml(SUCCESS, response);
        }


        return null;
    }

    /**
     * 普通修改还原方法，这里的oid必须是Long类型，如果不是Long类型的oid，在前端已经判定
     *
     * @param response
     * @param oid
     * @return
     */
    @RequestMapping(value = "/reBack", method = RequestMethod.GET)
    public ModelAndView reBack(HttpServletResponse response, @RequestParam(value = "oid") Long oid) {
        try {
            CreativeBackUpEntity creativeBackUpEntity = creativeBackUpService.reBack(oid);
            writeData(SUCCESS, response, creativeBackUpEntity);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 软删除的还原方法。直接更改掉ls字段
     *
     * @param response
     * @param oid
     * @return
     */
    @RequestMapping(value = "/delBack", method = RequestMethod.GET)
    public ModelAndView delBack(HttpServletResponse response, @RequestParam(value = "oid") Long oid) {
        try {
            creativeDAO.delBack(oid);
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 跳转批量添加页面
     *
     * @return
     */
    @RequestMapping(value = "/updateMulti")
    public ModelAndView convertMulti() {
        return new ModelAndView("promotionAssistant/alert/publicUpdateMulti");
    }

    /**
     * 执行批量添加/修改方法所执行的方法，如果标题和创意能匹配到数据，则执行添加操作
     * @param response
     * @param aid
     * @param title
     * @param de1
     * @param de2
     * @param pc
     * @param pcs
     * @param mib
     * @param mibs
     * @param bol
     * @param s
     * @param d
     * @return
     */
    @RequestMapping(value = "insertOrUpdate", method = RequestMethod.POST)
    public ModelAndView insertOrUpdate(HttpServletResponse response,
                                       @RequestParam(value = "aid", required = true) String aid,
                                       @RequestParam(value = "title", required = false) String title,
                                       @RequestParam(value = "description1", required = false) String de1,
                                       @RequestParam(value = "description2", required = false) String de2,
                                       @RequestParam(value = "pcDestinationUrl", required = false) String pc,
                                       @RequestParam(value = "pcDisplayUrl", required = false) String pcs,
                                       @RequestParam(value = "mobileDestinationUrl", required = false) String mib,
                                       @RequestParam(value = "mobileDisplayUrl", required = false) String mibs,
                                       @RequestParam(value = "pause") Boolean bol,
                                       @RequestParam(value = "status") Integer s,
                                       @RequestParam(value = "d", required = false, defaultValue = "0") Integer d) {
        try{
            //将获取到的标题和创意1在本地数据库中查询
            Map<String,Object> params=new HashMap<>();
            params.put("t",title);
            params.put("desc1",de1);
            if(aid.length()>OBJ_SIZE){
                params.put(EntityConstants.SYSTEM_ID,aid);
            }else{
                params.put(EntityConstants.ADGROUP_ID,Long.valueOf(aid));
            }
            //如果查询到结果
            CreativeEntity creativeEntity = creativeDAO.getAllsBySomeParams(params);
            //如果能查到匹配的数据，则执行修改操作
            if(creativeEntity!=null){
                CreativeEntity creativeEntityFind = null;
                //判断如果该条数据不为已经同步的数据，则视为本地数据，本地数据库数据修改则不需要备份操作
                if (creativeEntity.getCreativeId()==null) {
                    creativeEntityFind = creativeDAO.findByObjId(creativeEntity.getId());
                    creativeEntityFind.setTitle(title);
                    creativeEntityFind.setDescription1(de1);
                    creativeEntityFind.setDescription2(de2);
                    creativeEntityFind.setPcDestinationUrl(pc);
                    creativeEntityFind.setPcDisplayUrl(pcs);
                    creativeEntityFind.setMobileDestinationUrl(mib);
                    creativeEntityFind.setMobileDisplayUrl(mibs);
                    creativeEntityFind.setPause(bol);
                    creativeEntityFind.setLocalStatus(1);
                    creativeDAO.updateByObjId(creativeEntityFind);
                //如果已经是同步到本地的数据，则要执行备份操作，将这条数据备份到备份数据库中
                } else {
                    creativeEntityFind = creativeDAO.findOne(creativeEntity.getCreativeId());
                    CreativeEntity creativeEntityBackUp = new CreativeEntity();
                    creativeEntityFind.setLocalStatus(2);
                    BeanUtils.copyProperties(creativeEntityFind, creativeEntity);
                    creativeEntityFind.setTitle(title);
                    creativeEntityFind.setDescription1(de1);
                    creativeEntityFind.setDescription2(de2);
                    creativeEntityFind.setPcDestinationUrl(pc);
                    creativeEntityFind.setPcDisplayUrl(pcs);
                    creativeEntityFind.setMobileDestinationUrl(mib);
                    creativeEntityFind.setMobileDisplayUrl(mibs);
                    creativeEntityFind.setPause(bol);
                    creativeDAO.update(creativeEntityFind, creativeEntityBackUp);
                }
            //如果没有查到匹配的数据，则执行添加操作
            }else{
                CreativeEntity creativeEntityInsert = new CreativeEntity();
                creativeEntityInsert.setAccountId(AppContext.getAccountId());
                creativeEntityInsert.setTitle(title);
                creativeEntityInsert.setDescription1(de1);
                creativeEntityInsert.setDescription2(de2);
                creativeEntityInsert.setPcDestinationUrl(pc);
                creativeEntityInsert.setPcDisplayUrl(pcs);
                creativeEntityInsert.setMobileDestinationUrl(mib);
                creativeEntityInsert.setMobileDisplayUrl(mibs);
                creativeEntityInsert.setPause(bol);
                creativeEntityInsert.setStatus(s);
                creativeEntityInsert.setDevicePreference(d);
                creativeEntityInsert.setLocalStatus(1);
                if (aid.length() > OBJ_SIZE) {
                    creativeEntityInsert.setAdgroupObjId(aid);
                    creativeEntityInsert.setCreativeId(null);
                } else {
                    creativeEntityInsert.setAdgroupId(Long.parseLong(aid));
                }
                String oid = creativeDAO.insertOutId(creativeEntityInsert);
            }
            writeHtml(SUCCESS,response);
        }catch (Exception e){
            e.printStackTrace();
            writeHtml(EXCEPTION,response);
        }
        return null;
    }
}


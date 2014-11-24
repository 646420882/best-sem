package com.perfect.app.assistant.controller;

import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.dao.mongodb.utils.EntityConstants;
import com.perfect.dao.mongodb.utils.PagerInfo;
import com.perfect.service.AdgroupBackUpService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.beans.BeanUtils;
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
    @Resource
    AdgroupBackUpService adgroupBackUpService;
    private static Integer OBJ_SIZE = 18;

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
                                @RequestParam(value = "cid", required = false)String cid,
                                @RequestParam(value = "nowPage",required = false,defaultValue = "0")int nowPage,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "20")int pageSize) {
        PagerInfo pagerInfo=null;
        Map<String, Object> parms = new HashMap<>();
        if (cid.length() > OBJ_SIZE) {
            if (cid != "" || !cid.equals("")) {
                parms.put(EntityConstants.OBJ_CAMPAIGN_ID, cid);
                pagerInfo = adgroupDAO.findByPagerInfo(parms, nowPage, pageSize);
            } else {
                pagerInfo = adgroupDAO.findByPagerInfo(parms, nowPage, pageSize);
            }
            setCampaignNameByStringObjId((List<AdgroupEntity>) pagerInfo.getList());
        } else {
            if (cid != "" || !cid.equals("")) {
                parms.put(EntityConstants.CAMPAIGN_ID, Long.parseLong(cid));
                pagerInfo = adgroupDAO.findByPagerInfo(parms, nowPage, pageSize);
            } else {
                pagerInfo = adgroupDAO.findByPagerInfo(parms, nowPage, pageSize);
            }
            setCampaignNameByLongId((List<AdgroupEntity>) pagerInfo.getList());
        }
        writeJson(pagerInfo, response);
        return null;
    }

    /**
     * 根据单元获取它的计划名称
     *
     * @param list
     */
    private void setCampaignNameByLongId(List<AdgroupEntity> list) {
        if (list.size() > 0) {
            List<CampaignEntity> campaignEntity = (List<CampaignEntity>) campaignDAO.findAll();
            for (int i = 0; i < campaignEntity.size(); i++) {
                for (AdgroupEntity a : list) {
                    if (a.getCampaignId() != null) {
                        if (a.getCampaignId().equals(campaignEntity.get(i).getCampaignId())) {
                            a.setCampaignName(campaignEntity.get(i).getCampaignName());
                        }
                    } else {
                        if (a.getCampaignObjId().equals(campaignEntity.get(i).getId())) {
                            a.setCampaignName(campaignEntity.get(i).getCampaignName());
                        }
                    }

                }
            }
        }
    }

    /**
     * 根据传入的String类型的Id获取单元的计划名
     *
     * @param list
     */
    private void setCampaignNameByStringObjId(List<AdgroupEntity> list) {
        if (list.size() > 0) {
            List<CampaignEntity> campaignEntity = (List<CampaignEntity>) campaignDAO.findAll();
            for (int i = 0; i < campaignEntity.size(); i++) {
                for (AdgroupEntity a : list) {
                    if (a.getCampaignObjId().equals(campaignEntity.get(i).getId())) {
                        a.setCampaignName(campaignEntity.get(i).getCampaignName());
                    }
                }
            }
        }
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

    /**
     * 添加方法
     *
     * @param request
     * @param response
     * @param cid
     * @param name
     * @param maxPrice
     * @param nn
     * @param ne
     * @param p
     * @param s
     * @param mib
     * @return
     */
    @RequestMapping(value = "/adAdd", method = RequestMethod.POST)
    public ModelAndView adAdd(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "cid", required = true) String cid,
                              @RequestParam(value = "adgroupName") String name,
                              @RequestParam(value = "maxPrice") Double maxPrice,
                              @RequestParam(value = "negativeWords") List<String> nn,
                              @RequestParam(value = "exactNegativeWords") List<String> ne,
                              @RequestParam(value = "pause") Boolean p,
                              @RequestParam(value = "status") Integer s,
                              @RequestParam(value = "mib") Double mib) {
        try {
            ////2014-11-24 refactor
            AdgroupDTO adgroupEntity = new AdgroupDTO();
            adgroupEntity.setAccountId(AppContext.getAccountId());
            if (cid.length() > OBJ_SIZE) {
                adgroupEntity.setCampaignObjId(cid);
            } else {
                adgroupEntity.setCampaignId(Long.parseLong(cid));
            }
            adgroupEntity.setAdgroupName(name);
            adgroupEntity.setMaxPrice(maxPrice);
            adgroupEntity.setNegativeWords(nn);
            adgroupEntity.setExactNegativeWords(ne);
            adgroupEntity.setPause(p);
            adgroupEntity.setStatus(s);
            adgroupEntity.setMib(mib);
            adgroupEntity.setLocalStatus(1);
            Object oid = adgroupDAO.insertOutId(adgroupEntity);
            writeData(SUCCESS, response, oid);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 删除方法，根据传入的id的类型进行判断
     *
     * @param request
     * @param response
     * @param oid
     * @return
     */
    @RequestMapping(value = "/del")
    public ModelAndView del(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "oid", required = true) String oid) {
        try {
            if (oid.length() > OBJ_SIZE) {
                adgroupDAO.deleteByObjId(oid);
            } else {
                adgroupDAO.deleteByObjId(Long.valueOf(oid));
            }
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }

        return null;
    }

    /**
     * 修改方法，基本是修改全部属性,根据传入oid 的类型进行判断
     *
     * @param response
     * @param agid
     * @param name
     * @param maxPrice
     * @param nn
     * @param ne
     * @param p
     * @param mib
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletResponse response,
                               @RequestParam(value = "oid", required = true) String agid,
                               @RequestParam(value = "adgroupName") String name,
                               @RequestParam(value = "maxPrice") Double maxPrice,
                               @RequestParam(value = "negativeWords") List<String> nn,
                               @RequestParam(value = "exactNegativeWords") List<String> ne,
                               @RequestParam(value = "pause") Boolean p,
                               @RequestParam(value = "mib") Double mib) {
        AdgroupEntity adgroupEntityFind = null;
        try {
            if (agid.length() > OBJ_SIZE) {
                adgroupEntityFind = adgroupDAO.findByObjId(agid);
                adgroupEntityFind.setAdgroupName(name);
                adgroupEntityFind.setMaxPrice(maxPrice);
                adgroupEntityFind.setMib(mib);
                adgroupEntityFind.setNegativeWords(nn);
                adgroupEntityFind.setExactNegativeWords(ne);
                adgroupEntityFind.setMib(mib);
                adgroupDAO.updateByObjId(adgroupEntityFind);
                writeHtml(SUCCESS, response);
            } else {
                adgroupEntityFind = adgroupDAO.findOne(Long.valueOf(agid));
                AdgroupEntity adgroupEntity = new AdgroupEntity();
                adgroupEntityFind.setLocalStatus(2);
                BeanUtils.copyProperties(adgroupEntityFind, adgroupEntity);
                adgroupEntityFind.setAdgroupName(name);
                adgroupEntityFind.setMaxPrice(maxPrice);
                adgroupEntityFind.setMib(mib);
                adgroupEntityFind.setNegativeWords(nn);
                adgroupEntityFind.setExactNegativeWords(ne);
                adgroupEntityFind.setMib(mib);
                adgroupDAO.update(adgroupEntityFind, adgroupEntity);
                writeHtml(SUCCESS, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 修改是否启用的下拉列表
     *
     * @param response
     * @param oid
     * @param pause
     * @return
     */
    @RequestMapping(value = "/updateByChange", method = RequestMethod.GET)
    public ModelAndView updateByChange(HttpServletResponse response, @RequestParam(value = "oid", required = true) String oid,
                                       @RequestParam(value = "pause", required = true) Boolean pause) {
        try {
            AdgroupEntity adgroupEntity = null;
            if (oid.length() > OBJ_SIZE) {
                adgroupEntity = adgroupDAO.findByObjId(oid);
                adgroupEntity.setPause(pause);
                adgroupDAO.updateByObjId(adgroupEntity);
                writeHtml(SUCCESS, response);
            } else {
                adgroupEntity = adgroupDAO.findOne(Long.valueOf(oid));
                adgroupEntity.setPause(pause);
                adgroupDAO.update(adgroupEntity);
                writeHtml(SUCCESS, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 如果是localStatus状态为2，则将备份的数据拷贝到正常的数据库中
     *
     * @param response
     * @param oid
     * @return
     */
    @RequestMapping(value = "/agReBack", method = RequestMethod.GET)
    public ModelAndView agReBack(HttpServletResponse response, @RequestParam(value = "oid", required = true) Long oid) {
        try {
            AdgroupBackUpEntity backUpEntity = adgroupBackUpService.agReBack(oid);
            writeData(SUCCESS, response, backUpEntity);
        } catch (Exception e) {
            e.printStackTrace();
            writeData(EXCEPTION, response, null);
        }
        return null;
    }

    /**
     * 删除的数据还原，这里特指同步过后的数据
     *
     * @param response
     * @param oid
     * @return
     */
    @RequestMapping(value = "/agDelBack", method = RequestMethod.GET)
    public ModelAndView agDelBack(HttpServletResponse response, @RequestParam(value = "oid", required = true) Long oid) {
        try {
            adgroupDAO.delBack(oid);
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 弹出批量添加/修改页面
     *
     * @return
     */
    @RequestMapping(value = "/adgroupMutli")
    public ModelAndView converAdgroupMutli() {
        return new ModelAndView("promotionAssistant/alert/adgroupMutli");
    }

    /**
     * 单元批量修改方法，如果
     *
     * @param response
     * @param cid
     * @param name
     * @param maxPrice
     * @param p
     * @param s
     * @return
     */
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ModelAndView insertOrUpdate(HttpServletResponse response, @RequestParam(value = "cid", required = true) String cid,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam(value = "maxPrice") Double maxPrice,
                                       @RequestParam(value = "pause") Boolean p,
                                       @RequestParam(value = "status") Integer s) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", name);
            if (cid.length() > OBJ_SIZE) {
                params.put(EntityConstants.SYSTEM_ID, cid);
            } else {
                params.put(EntityConstants.CAMPAIGN_ID, Long.valueOf(cid));
            }
            AdgroupEntity adgroupEntity = adgroupDAO.fndEntity(params);
            if (adgroupEntity != null) {
                AdgroupEntity adgroupEntityFind = null;
                if (adgroupEntity.getAdgroupId() == null) {
                    adgroupEntityFind = adgroupDAO.findByObjId(adgroupEntity.getId());
                    adgroupEntityFind.setAdgroupName(name);
                    adgroupEntityFind.setMaxPrice(maxPrice);
                    adgroupEntityFind.setPause(p);
                    adgroupDAO.updateByObjId(adgroupEntityFind);
                    writeHtml(SUCCESS, response);
                } else {
                    adgroupEntityFind = adgroupDAO.findOne(adgroupEntity.getAdgroupId());
                    AdgroupEntity adgroupEntityBackUp = new AdgroupEntity();
                    adgroupEntityFind.setLocalStatus(2);
                    BeanUtils.copyProperties(adgroupEntityFind, adgroupEntityBackUp);
                    adgroupEntityFind.setAdgroupName(name);
                    adgroupEntityFind.setMaxPrice(maxPrice);
                    adgroupEntityFind.setPause(p);
                    adgroupDAO.update(adgroupEntityFind, adgroupEntityBackUp);
                    writeHtml(SUCCESS, response);
                }
            } else {
                AdgroupEntity adgroupEntityInsert = new AdgroupEntity();
                adgroupEntityInsert.setAccountId(AppContext.getAccountId());
                if (cid.length() > OBJ_SIZE) {
                    adgroupEntityInsert.setCampaignObjId(cid);
                } else {
                    adgroupEntityInsert.setCampaignId(Long.parseLong(cid));
                }
                adgroupEntityInsert.setAdgroupName(name);
                adgroupEntityInsert.setMaxPrice(maxPrice);
                adgroupEntityInsert.setNegativeWords(new ArrayList<String>(0));
                adgroupEntityInsert.setExactNegativeWords(new ArrayList<String>(0));
                adgroupEntityInsert.setPause(p);
                adgroupEntityInsert.setStatus(s);
                adgroupEntityInsert.setMib(0.0);
                adgroupEntityInsert.setLocalStatus(1);
                adgroupDAO.insertOutId(adgroupEntityInsert);
            }
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }
}

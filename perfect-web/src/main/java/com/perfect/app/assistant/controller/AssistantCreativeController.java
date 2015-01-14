package com.perfect.app.assistant.controller;

import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.autosdk.sms.v3.CreativeService;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.service.*;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.service.AdgroupService;
import com.perfect.service.CampaignService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by XiaoWei on 2014/8/21.
 * 2014-11-26 refactor
 */
@Controller
@RequestMapping(value = "/assistantCreative")
public class AssistantCreativeController extends WebContextSupport {

    private static long accountId = 6243012L;
    private static Integer OBJ_SIZE = 18;

    @Resource
    com.perfect.service.CreativeService creativeService;
    @Resource
    AdgroupService adgroupService;
    @Resource
    CampaignService campaignService;
    @Resource
    CreativeBackUpService creativeBackUpService;
    @Resource
    AccountManageService accountManageService;

    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public ModelAndView getCreativeList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "cid", required = false) String cid,
                                        @RequestParam(value = "aid", required = false) String aid,
                                        @RequestParam(value = "nowPage",required = false,defaultValue = "0")int nowPage,
                                        @RequestParam(value = "pageSize",required = false,defaultValue = "20")int pageSize){
        PagerInfo pagerInfo=null;
        Map<String,Object> map=new HashMap<>();
        if (!aid.equals("-1")) {
        if (aid.length() > OBJ_SIZE || cid.length() > OBJ_SIZE) {
            if (aid != "" || !aid.equals("")) {
                if (aid.length() > OBJ_SIZE) {
                    map.put(MongoEntityConstants.OBJ_ADGROUP_ID, aid);
                    pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize);
                } else {
                    map.put(MongoEntityConstants.ADGROUP_ID,aid);
                    pagerInfo = creativeService.findByPagerInfo(map,nowPage,pageSize);
                }
            } else if (!cid.equals("") && aid.equals("")) {
                List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignId(Long.parseLong(cid));
                pagerInfo = creativeService.findByPagerInfoForLong(adgroupIds, nowPage, pageSize);
            } else {
                pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize);
            }
        } else {
            if (aid != "" || !aid.equals("")) {
                pagerInfo = creativeService.findByPagerInfo(Long.parseLong(aid), nowPage, pageSize);
            } else if (!cid.equals("") && aid.equals("")) {
                List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignId(Long.parseLong(cid));
                pagerInfo=creativeService.findByPagerInfoForLong(adgroupIds,nowPage,pageSize);

            } else {
                pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize);
            }
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
        List<CampaignDTO> list = (List<CampaignDTO>) campaignService.findAll();
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
        List<AdgroupDTO> adgroupEntities = new ArrayList<>();
        if (planId.length() > OBJ_SIZE) {
            adgroupEntities = adgroupService.getAdgroupByCampaignObjId(planId);
        } else {
            adgroupEntities = adgroupService.getAdgroupByCampaignId(Long.parseLong(planId));
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
            UUID uuidRandom=UUID.randomUUID();
           String uuid=uuidRandom.toString().replaceAll("-","");
            CreativeDTO creativeEntity = new CreativeDTO();
            creativeEntity.setAccountId(AppContext.getAccountId());
            creativeEntity.setTitle(title);

            creativeEntity.setDescription1(de1);
            creativeEntity.setDescription2(de2);
            if(pc.indexOf("?")>-1){
                creativeEntity.setPcDestinationUrl(pc+"&pk="+uuid);
            }else{
                creativeEntity.setPcDestinationUrl(pc+"?pk="+uuid);
            }
            creativeEntity.setPcDisplayUrl(pcs);
            if(mib.indexOf("?")>-1){
                creativeEntity.setMobileDestinationUrl(mib+"&pk="+uuid);
            }else{
                creativeEntity.setMobileDestinationUrl(mib+"?pk="+uuid);
            }
            creativeEntity.setMobileDisplayUrl(mibs);
            creativeEntity.setPause(bol);
            creativeEntity.setStatus(s);
            creativeEntity.setDevicePreference(d);
            creativeEntity.setLocalStatus(1);
            creativeEntity.setKey(uuid);
            if (aid.length() > OBJ_SIZE) {
                creativeEntity.setAdgroupObjId(aid);
                creativeEntity.setCreativeId(null);
            } else {
                creativeEntity.setAdgroupId(Long.parseLong(aid));
            }
            String oid = creativeService.insertOutId(creativeEntity);
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
                creativeService.deleteByCacheId(oid);
                writeHtml(SUCCESS, response);
            } else {
                creativeService.deleteByCacheId(Long.valueOf(oid));
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
        CreativeDTO creativeEntityFind = null;
        UUID uuidRandom=UUID.randomUUID();
        String uuid=uuidRandom.toString().replaceAll("-","");
        if (oid.length() > OBJ_SIZE) {
            creativeEntityFind = creativeService.findByObjId(oid);
            creativeEntityFind.setTitle(title);
            creativeEntityFind.setDescription1(de1);
            creativeEntityFind.setDescription2(de2);
            if(pc.indexOf("pk=")==-1){
                if(pc.indexOf("?")>-1){
                    creativeEntityFind.setPcDestinationUrl(pc+"&pk="+uuid);
                }else{
                    creativeEntityFind.setPcDestinationUrl(pc+"?pk="+uuid);
                }
            }
            creativeEntityFind.setPcDisplayUrl(pcs);
            if(mib.indexOf("pk=")==-1){
                if(mib.indexOf("?")>-1){
                    creativeEntityFind.setMobileDestinationUrl(mib+"&pk="+uuid);
                }else{
                    creativeEntityFind.setMobileDestinationUrl(mib+"?pk="+uuid);
                }
            }
            creativeEntityFind.setMobileDisplayUrl(mibs);
            creativeEntityFind.setPause(bol);
            creativeEntityFind.setLocalStatus(1);
            creativeService.updateByObjId(creativeEntityFind);
            writeHtml(SUCCESS, response);
        } else {
            creativeEntityFind = creativeService.findOne(Long.valueOf(oid));
            CreativeDTO creativeEntity = new CreativeDTO();
            creativeEntityFind.setLocalStatus(2);
            BeanUtils.copyProperties(creativeEntityFind, creativeEntity);
            creativeEntityFind.setTitle(title);
            creativeEntityFind.setDescription1(de1);
            creativeEntityFind.setDescription2(de2);
            if(pc.indexOf("pk=")==-1){
                if(pc.indexOf("?")>-1){
                    creativeEntityFind.setPcDestinationUrl(pc+"&pk="+uuid);
                }else{
                    creativeEntityFind.setPcDestinationUrl(pc+"?pk="+uuid);
                }
            }
            creativeEntityFind.setPcDisplayUrl(pcs);
            if(mib.indexOf("pk=")==-1){
                if(mib.indexOf("?")>-1){
                    creativeEntityFind.setMobileDestinationUrl(mib+"&pk="+uuid);
                }else{
                    creativeEntityFind.setMobileDestinationUrl(mib+"?pk="+uuid);
                }
            }
            creativeEntityFind.setMobileDisplayUrl(mibs);
            creativeEntityFind.setPause(bol);

            creativeService.update(creativeEntityFind, creativeEntity);
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
            CreativeBackUpDTO creativeBackUpEntity = creativeBackUpService.reBack(oid);
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
            creativeService.delBack(oid);
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
        return new ModelAndView("promotionAssistant/alert/creativeMutli");
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
                params.put(MongoEntityConstants.SYSTEM_ID,aid);
            }else{
                params.put(MongoEntityConstants.ADGROUP_ID,Long.valueOf(aid));
            }
            //如果查询到结果
            CreativeDTO creativeEntity = creativeService.getAllsBySomeParams(params);
            //如果能查到匹配的数据，则执行修改操作
            if(creativeEntity!=null){
                CreativeDTO creativeEntityFind = null;
                //判断如果该条数据不为已经同步的数据，则视为本地数据，本地数据库数据修改则不需要备份操作
                if (creativeEntity.getCreativeId()==null) {
                    creativeEntityFind = creativeService.findByObjId(creativeEntity.getId());
                    creativeEntityFind.setTitle(title);
                    creativeEntityFind.setDescription1(de1);
                    creativeEntityFind.setDescription2(de2);
                    creativeEntityFind.setPcDestinationUrl(pc);
                    creativeEntityFind.setPcDisplayUrl(pcs);
                    creativeEntityFind.setMobileDestinationUrl(mib);
                    creativeEntityFind.setMobileDisplayUrl(mibs);
                    creativeEntityFind.setPause(bol);
                    creativeEntityFind.setLocalStatus(1);
                    creativeService.updateByObjId(creativeEntityFind);
                    //如果已经是同步到本地的数据，则要执行备份操作，将这条数据备份到备份数据库中
                } else {
                    creativeEntityFind = creativeService.findOne(creativeEntity.getCreativeId());
                    CreativeDTO creativeEntityBackUp = new CreativeDTO();
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
                    creativeService.update(creativeEntityFind, creativeEntityBackUp);
                }
                //如果没有查到匹配的数据，则执行添加操作
            }else{
                CreativeDTO creativeEntityInsert = new CreativeDTO();
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
                String oid = creativeService.insertOutId(creativeEntityInsert);
            }
            writeHtml(SUCCESS,response);
        }catch (Exception e){
            e.printStackTrace();
            writeHtml(EXCEPTION,response);
        }
        return null;
    }


    @RequestMapping(value = "uploadCreative",method = RequestMethod.POST)
    public ModelAndView uploadCreative(HttpServletResponse response,
                                       @RequestParam(value = "cid",required = true) Long cid,
                                       @RequestParam(value = "aid",required = true) Long aid,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestParam(value = "desc1",defaultValue = "") String desc1,
                                       @RequestParam(value = "desc2",defaultValue = "") String desc2,
                                       @RequestParam(value = "pcUrl",defaultValue = "") String pcUrl,
                                       @RequestParam(value = "pcsUrl",defaultValue = "") String pcsUrl){
//        System.out.println("cid:"+cid+"aid"+aid+"title:"+title+"desc1"+desc1+"desc2"+desc2+"pcUrl"+pcUrl+"pcsUrl"+pcsUrl);
        BaiduAccountInfoDTO bad=accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());
        CreativeType creativeTypes=new CreativeType();
        creativeTypes.setTitle(title);
        creativeTypes.setDescription1(desc1);
        creativeTypes.setDescription2(desc2);
        creativeTypes.setPcDestinationUrl(pcUrl);
        creativeTypes.setPcDisplayUrl(pcsUrl);
        creativeTypes.setAdgroupId(aid);
        creativeTypes.setDevicePreference(1);
        CommonService commonService= BaiduServiceSupport.getCommonService(bad.getBaiduUserName(),bad.getBaiduPassword(),bad.getToken());
        try {
            CreativeService service= commonService.getService(CreativeService.class);

            AddCreativeRequest addCreativeRequest=new AddCreativeRequest();
            addCreativeRequest.setCreativeTypes(Arrays.asList(creativeTypes));
            AddCreativeResponse addCreativeResponse=  service.addCreative(addCreativeRequest);
            CreativeType creativeTypeResponse= addCreativeResponse.getCreativeType(0);
            if (creativeTypeResponse.getCreativeId()!=0){
                writeHtml(SUCCESS, response);
            }else{
                writeHtml(FAIL,response);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }
    @RequestMapping(value = "/getDomain",method = RequestMethod.GET)
    public ModelAndView getDomain(HttpServletResponse response){
        BaiduAccountInfoDTO baiduAccountInfoEntity=accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());
        if(baiduAccountInfoEntity!=null){
            writeHtml(baiduAccountInfoEntity.getRegDomain(),response);
        }else{
            writeHtml(FAIL,response);
        }
        return null;
    }
    @RequestMapping(value = "uploadNewCreative",method = RequestMethod.GET)
    public ModelAndView uploadNewCreative(HttpServletResponse response,@RequestParam(value = "title",defaultValue = "")String title,
                                          @RequestParam(value = "desc")String[] desc,
                                          @RequestParam(value = "descUrl")String[] descUrl,
                                          @RequestParam(value = "aid")Long aid){
        SublinkType sublinkType=new SublinkType();
        sublinkType.setAdgroupId(aid);
        List<SublinkInfo> sublinkInfos=new ArrayList<>();
        for (int i=0;i<desc.length;i++){
            SublinkInfo sublinkInfo=new SublinkInfo();
            sublinkInfo.setDescription(desc[i]);
            sublinkInfo.setDestinationUrl(descUrl[i]);
            sublinkInfos.add(sublinkInfo);
        }
        sublinkType.setSublinkInfos(sublinkInfos);
        BaiduAccountInfoDTO bad=accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());
        CommonService commonService= BaiduServiceSupport.getCommonService(bad.getBaiduUserName(),bad.getBaiduPassword(),bad.getToken());
        try {
            NewCreativeService newCreativeService=commonService.getService(NewCreativeService.class);
            AddSublinkRequest addSublinkRequest=new AddSublinkRequest();
            addSublinkRequest.addSublinkType(sublinkType);
            AddSublinkResponse addSublinkResponse=  newCreativeService.addSublink(addSublinkRequest);
            if(addSublinkResponse.getSublinkType(0).getSublinkId()!=0){
                writeHtml(SUCCESS,response);
            }else{
                writeHtml(FAIL,response);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return  null;
    }
}


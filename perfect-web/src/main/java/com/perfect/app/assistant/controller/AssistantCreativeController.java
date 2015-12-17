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
import com.perfect.param.SearchFilterParam;
import com.perfect.service.*;
import com.perfect.web.suport.WebContextSupport;
import com.perfect.service.AdgroupService;
import com.perfect.service.CampaignService;
import com.perfect.utils.CsvReadUtil;
import com.perfect.utils.csv.UploadHelper;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.vo.CsvImportResponseVO;
import com.perfect.vo.ValidateCreativeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private BaiduAccountService baiduAccountService;

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public ModelAndView getCreativeList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "cid", required = false) String cid,
                                        @RequestParam(value = "aid", required = false) String aid,
                                        @RequestParam(value = "nowPage", required = false, defaultValue = "0") int nowPage,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        PagerInfo pagerInfo = null;
        Map<String, Object> map = new HashMap<>();
        if (!aid.equals("-1")) {
            if (aid.length() > OBJ_SIZE || cid.length() > OBJ_SIZE) {
                if (!aid.equals("")) {
                    if (aid.length() > OBJ_SIZE) {
                        map.put(MongoEntityConstants.OBJ_ADGROUP_ID, aid);
                        pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, null);
                    } else {
                        map.put(MongoEntityConstants.ADGROUP_ID, aid);
                        pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, null);
                    }
                } else if (!cid.equals("") && aid.equals("")) {
                    List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignObj(cid);
                    pagerInfo = creativeService.findByPagerInfoForLong(adgroupIds, nowPage, pageSize, null);
                } else {
                    pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, null);
                }
            } else {
                if (!aid.equals("")) {
                    pagerInfo = creativeService.findByPagerInfo(Long.parseLong(aid), nowPage, pageSize, null);
                } else if (!cid.equals("") && aid.equals("")) {
                    List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignId(Long.parseLong(cid));
                    pagerInfo = creativeService.findByPagerInfoForLong(adgroupIds, nowPage, pageSize, null);

                } else {
                    pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, null);
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
            UUID uuidRandom = UUID.randomUUID();
            String uuid = uuidRandom.toString().replaceAll("-", "");
            CreativeDTO creativeEntity = new CreativeDTO();
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
     *
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
                               @RequestParam(value = "pause") Boolean bol,
                               @RequestParam(value = "devicePreference") Integer device) {
        CreativeDTO creativeEntityFind = null;
        if (oid.length() > OBJ_SIZE) {
            creativeEntityFind = creativeService.findByObjId(oid);
            creativeEntityFind.setTitle(title);
            creativeEntityFind.setDescription1(de1);
            creativeEntityFind.setDescription2(de2);
            creativeEntityFind.setPcDestinationUrl(pc);
            creativeEntityFind.setPcDisplayUrl(pcs);
            creativeEntityFind.setMobileDestinationUrl(mib);
            creativeEntityFind.setMobileDisplayUrl(mibs);
            creativeEntityFind.setPause(bol);
            creativeEntityFind.setDevicePreference(device);
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
            creativeEntityFind.setPcDestinationUrl(pc);
            creativeEntityFind.setPcDisplayUrl(pcs);
            creativeEntityFind.setMobileDestinationUrl(mib);
            creativeEntityFind.setMobileDisplayUrl(mibs);
            creativeEntityFind.setPause(bol);
            creativeEntityFind.setDevicePreference(device);
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
     *
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
     * @return
     */
    @RequestMapping(value = "insertOrUpdate", method = RequestMethod.POST)
    public ModelAndView insertOrUpdate(HttpServletResponse response,
                                       @RequestParam(value = "cid", required = false) String cid,
                                       @RequestParam(value = "aid", required = false) String aid,
                                       @RequestParam(value = "isReplace") Boolean isReplace,
                                       @RequestParam(value = "title", required = false) String title,
                                       @RequestParam(value = "description1", required = false) String de1,
                                       @RequestParam(value = "description2", required = false) String de2,
                                       @RequestParam(value = "pcDestinationUrl", required = false) String pc,
                                       @RequestParam(value = "pcDisplayUrl", required = false) String pcs,
                                       @RequestParam(value = "mobileDestinationUrl", required = false) String mib,
                                       @RequestParam(value = "mobileDisplayUrl", required = false) String mibs,
                                       @RequestParam(value = "pause") String bol,
                                       @RequestParam(value = "selected") Integer selected,
                                       @RequestParam(value = "device") String device) {
        try {
            //将获取到的标题和创意1在本地数据库中查询
            if (aid.contains("\n")) {
                String[] cidStr = cid.split("\n");
                String[] aidStr = aid.split("\n");
                String[] titleStr = title.split("\n");
                String[] de1Str = de1.split("\n");
                String[] de2Str = de2.split("\n");
                String[] pcStr = pc.split("\n");
                String[] pcsStr = pcs.split("\n");
                String[] mibStr = mib.split("\n");
                String[] mibsStr = mibs.split("\n");
                String[] bolStr = bol.split("\n");
                String[] devStr = device.split("\n");
                for (int i = 0; i < aidStr.length; i++) {
                    innerInsert(selected, isReplace, cidStr[i], aidStr[i], titleStr[i], de1Str[i], de2Str[i], pcStr[i], pcsStr[i], mibStr[i], mibsStr[i], bolStr[i], devStr[i]);
                }
            } else {
                innerInsert(selected, isReplace, cid, aid, title, de1, de2, pc, pcs, mib, mibs, bol, device);
            }
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    private void innerInsert(Integer selected, Boolean isReplace, String cid, String aid, String title, String de1, String de2, String pc, String pcs, String mib, String mibs, String bol, String device) {
        Map<String, Object> params = new HashMap<>();
        params.put("t", title);
        params.put("desc1", de1);
        params.put("desc2", de2);
        if (selected == 0) {
            if (aid.length() > OBJ_SIZE) {
                params.put(MongoEntityConstants.OBJ_ADGROUP_ID, aid);
            } else {
                params.put(MongoEntityConstants.ADGROUP_ID, Long.valueOf(aid));
            }
        } else {
            AdgroupDTO adgroupDTO = adgroupService.autoBAG(cid, aid);
            if (adgroupDTO.getAdgroupId() != null) {
                params.put(MongoEntityConstants.ADGROUP_ID, adgroupDTO.getAdgroupId());
                aid = adgroupDTO.getAdgroupId().toString();
            } else {
                params.put(MongoEntityConstants.OBJ_ADGROUP_ID, adgroupDTO.getId());
                aid = adgroupDTO.getId();
            }
        }
        //如果查询到结果
        CreativeDTO creativeEntity = creativeService.getAllsBySomeParams(params);
        //如果能查到匹配的数据，则执行修改操作
        if (creativeEntity != null) {
            if (isReplace) {
                CreativeDTO creativeEntityFind = null;
                //判断如果该条数据不为已经同步的数据，则视为本地数据，本地数据库数据修改则不需要备份操作
                if (creativeEntity.getCreativeId() == null) {
                    creativeEntityFind = creativeService.findByObjId(creativeEntity.getId());
                    creativeEntityFind.setTitle(title);
                    creativeEntityFind.setDescription1(de1);
                    creativeEntityFind.setDescription2(de2);
                    creativeEntityFind.setPcDestinationUrl(pc);
                    creativeEntityFind.setPcDisplayUrl(pcs);
                    creativeEntityFind.setMobileDestinationUrl(mib);
                    creativeEntityFind.setMobileDisplayUrl(mibs);
                    creativeEntityFind.setPause(Boolean.parseBoolean(bol));
                    creativeEntityFind.setLocalStatus(1);
                    creativeEntityFind.setDevicePreference(Integer.parseInt(device));
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
                    creativeEntityFind.setPause(Boolean.parseBoolean(bol));
                    creativeEntityFind.setDevicePreference(Integer.parseInt(device));
                    creativeService.update(creativeEntityFind, creativeEntityBackUp);
                }
            }
            //如果没有查到匹配的数据，则执行添加操作
        } else {
            CreativeDTO creativeEntityInsert = new CreativeDTO();
            creativeEntityInsert.setAccountId(AppContext.getAccountId());
            creativeEntityInsert.setTitle(title);
            creativeEntityInsert.setDescription1(de1);
            creativeEntityInsert.setDescription2(de2);
            creativeEntityInsert.setPcDestinationUrl(pc);
            creativeEntityInsert.setPcDisplayUrl(pcs);
            creativeEntityInsert.setMobileDestinationUrl(mib);
            creativeEntityInsert.setMobileDisplayUrl(mibs);
            creativeEntityInsert.setPause(Boolean.parseBoolean(bol));
            creativeEntityInsert.setStatus(-1);
            creativeEntityInsert.setDevicePreference(Integer.parseInt(device));
            creativeEntityInsert.setLocalStatus(1);
            if (aid.length() > OBJ_SIZE) {
                creativeEntityInsert.setAdgroupObjId(aid);
                creativeEntityInsert.setCreativeId(null);
            } else {
                creativeEntityInsert.setAdgroupId(Long.parseLong(aid));
            }
            String oid = creativeService.insertOutId(creativeEntityInsert);
        }
    }

    @RequestMapping(value = "/validateCreative")
    public ModelAndView validateCreative(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "cid", required = true) String cid,
                                         @RequestParam(value = "aid", required = true) String aid,
                                         @RequestParam(value = "title", required = false) String title,
                                         @RequestParam(value = "desc1", required = false) String de1,
                                         @RequestParam(value = "desc2", required = false) String de2,
                                         @RequestParam(value = "pc", required = false) String pc,
                                         @RequestParam(value = "pcs", required = false) String pcs,
                                         @RequestParam(value = "mib", required = false) String mib,
                                         @RequestParam(value = "mibs", required = false) String mibs,
                                         @RequestParam(value = "pause") String bol,
                                         @RequestParam(value = "device", required = false, defaultValue = "0") String device,
                                         @RequestParam(value = "selected", required = false) String selected) {
        if (aid.contains("\n")) {
            String[] cidStr = cid.split("\n");
            String[] aidStr = aid.split("\n");
            String[] titleStr = title.split("\n");
            String[] de1Str = de1.split("\n");
            String[] de2Str = de2.split("\n");
            String[] pcStr = pc.split("\n");
            String[] pcsStr = pcs.split("\n");
            String[] mibStr = mib.split("\n");
            String[] mibsStr = mibs.split("\n");
            String[] bolStr = bol.split("\n");
            String[] devStr = device.split("\n");
            List<CreativeDTO> baseDTOs = new ArrayList<>();
            for (int i = 0; i < cidStr.length; i++) {
                CreativeDTO creativeDTO = new CreativeDTO();
                creativeDTO.setCampaignName(cidStr[i]);
                creativeDTO.setAdgroupName(aidStr[i]);
                creativeDTO.setTitle(titleStr[i]);
                creativeDTO.setDescription1(de1Str[i]);
                creativeDTO.setDescription2(de2Str[i]);
                creativeDTO.setPcDestinationUrl(pcStr[i]);
                creativeDTO.setPcDisplayUrl(pcsStr[i]);
                creativeDTO.setMobileDestinationUrl(mibStr[i]);
                creativeDTO.setMobileDisplayUrl(mibsStr[i]);
                creativeDTO.setPause(Boolean.valueOf(bolStr[i]));
                creativeDTO.setDevicePreference(Integer.valueOf(devStr[i]));
                baseDTOs.add(creativeDTO);
            }
            ValidateCreativeVO vc = new ValidateCreativeVO();
            List<CreativeDTO> selfListCreative = new ArrayList<>();
            List<CreativeDTO> dbExistCreative = null;
            Map<String, List<CreativeDTO>> selfDTO = baseDTOs
                    .stream()
                    .collect(Collectors.groupingBy(creativeDTO -> {
                        return creativeDTO.getTitle() + creativeDTO.getDescription1() + creativeDTO.getDescription2();
                    }));
            for (Map.Entry<String, List<CreativeDTO>> s : selfDTO.entrySet()) {
                List<CreativeDTO> tmpList = s.getValue();
                selfListCreative.add(tmpList.get(0));
            }
            vc.setEndGetCount(baseDTOs.size() - selfListCreative.size());

            if (selfListCreative.size() > 0) {
                vc.setSafeCreativeDTOList(selfListCreative);
                dbExistCreative = creativeService.findExistCreative(selfListCreative);
            }
            vc.setDbExistCreativeDTOList(dbExistCreative);
            writeJson(vc, response);
            return null;
        } else {
            ValidateCreativeVO vc = new ValidateCreativeVO();
            List<CreativeDTO> safeList = new ArrayList<>();
            CreativeDTO creativeDTO = new CreativeDTO();
            creativeDTO.setCampaignName(cid);
            creativeDTO.setAdgroupName(aid);
            creativeDTO.setTitle(title);
            creativeDTO.setDescription1(de1);
            creativeDTO.setDescription2(de2);
            creativeDTO.setPcDestinationUrl(pc);
            creativeDTO.setPcDisplayUrl(pcs);
            creativeDTO.setMobileDestinationUrl(mib);
            creativeDTO.setMobileDisplayUrl(mibs);
            creativeDTO.setPause(Boolean.valueOf(bol));
            creativeDTO.setDevicePreference(Integer.valueOf(device));
            safeList.add(creativeDTO);
            vc.setSafeCreativeDTOList(safeList);

            List<CreativeDTO> dbExistList = creativeService.findExistCreative(safeList);
            vc.setDbExistCreativeDTOList(dbExistList);
            writeJson(vc, response);
        }

        return null;
    }

    @RequestMapping(value = "uploadCreative", method = RequestMethod.POST)
    public ModelAndView uploadCreative(HttpServletResponse response,
                                       @RequestParam(value = "cid", required = true) Long cid,
                                       @RequestParam(value = "aid", required = true) Long aid,
                                       @RequestParam(value = "title", defaultValue = "") String title,
                                       @RequestParam(value = "desc1", defaultValue = "") String desc1,
                                       @RequestParam(value = "desc2", defaultValue = "") String desc2,
                                       @RequestParam(value = "pcUrl", defaultValue = "") String pcUrl,
                                       @RequestParam(value = "pcsUrl", defaultValue = "") String pcsUrl) {
//        System.out.println("cid:"+cid+"aid"+aid+"title:"+title+"desc1"+desc1+"desc2"+desc2+"pcUrl"+pcUrl+"pcsUrl"+pcsUrl);
        BaiduAccountInfoDTO bad = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());
        CreativeType creativeTypes = new CreativeType();
        creativeTypes.setTitle(title);
        creativeTypes.setDescription1(desc1);
        creativeTypes.setDescription2(desc2);
        creativeTypes.setPcDestinationUrl(pcUrl);
        creativeTypes.setPcDisplayUrl(pcsUrl);
        creativeTypes.setAdgroupId(aid);
        creativeTypes.setDevicePreference(0);
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            CreativeService service = commonService.getService(CreativeService.class);

            AddCreativeRequest addCreativeRequest = new AddCreativeRequest();
            addCreativeRequest.setCreativeTypes(Arrays.asList(creativeTypes));
            AddCreativeResponse addCreativeResponse = service.addCreative(addCreativeRequest);
            CreativeType creativeTypeResponse = addCreativeResponse.getCreativeType(0);
            if (creativeTypeResponse.getCreativeId() != 0) {
                writeHtml(SUCCESS, response);
            } else {
                writeHtml(FAIL, response);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    @RequestMapping(value = "/getDomain", method = RequestMethod.GET)
    public ModelAndView getDomain(HttpServletResponse response) {
        BaiduAccountInfoDTO baiduAccountInfoEntity = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());
        if (baiduAccountInfoEntity != null) {
            writeHtml(baiduAccountInfoEntity.getRegDomain(), response);
        } else {
            writeHtml(FAIL, response);
        }
        return null;
    }

    @RequestMapping(value = "uploadNewCreative", method = RequestMethod.GET)
    public ModelAndView uploadNewCreative(HttpServletResponse response, @RequestParam(value = "title", defaultValue = "") String title,
                                          @RequestParam(value = "desc") String[] desc,
                                          @RequestParam(value = "descUrl") String[] descUrl,
                                          @RequestParam(value = "aid") Long aid) {
        SublinkType sublinkType = new SublinkType();
        sublinkType.setAdgroupId(aid);
        List<SublinkInfo> sublinkInfos = new ArrayList<>();
        for (int i = 0; i < desc.length; i++) {
            SublinkInfo sublinkInfo = new SublinkInfo();
            sublinkInfo.setDescription(desc[i]);
            sublinkInfo.setDestinationUrl(descUrl[i]);
            sublinkInfos.add(sublinkInfo);
        }
        sublinkType.setSublinkInfos(sublinkInfos);
        BaiduAccountInfoDTO bad = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(bad.getBaiduUserName(), bad.getBaiduPassword(), bad.getToken());
        try {
            NewCreativeService newCreativeService = commonService.getService(NewCreativeService.class);
            AddSublinkRequest addSublinkRequest = new AddSublinkRequest();
            addSublinkRequest.addSublinkType(sublinkType);
            AddSublinkResponse addSublinkResponse = newCreativeService.addSublink(addSublinkRequest);
            if (addSublinkResponse.getSublinkType(0).getSublinkId() != 0) {
                writeHtml(SUCCESS, response);
            } else {
                writeHtml(FAIL, response);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "uploadOperate")
    public ModelAndView uploadOperate(@RequestParam(value = "crid", required = true) String crid, @RequestParam(value = "ls") Integer ls) {
        if (crid.length() > OBJ_SIZE) {
            List<CreativeDTO> creativeDTOs = creativeService.uploadAdd(new ArrayList<String>() {{
                add(crid);
            }});
            if (creativeDTOs.size() > 0) {
                int error = 0;
                for (CreativeDTO c : creativeDTOs) {
                    if (c.getCreativeId() != 0) {
                        creativeService.update(crid, c);
                    } else {
                        error++;
                    }
                }
                if (error > 0) {
                    return writeMapObject(MSG, "部分创意上传失败，请检查上传数据的合法性，是否重复，域名是否一致等条件...");
                } else {
                    return writeMapObject(MSG, SUCCESS);
                }

            } else {
                return writeMapObject(MSG, "noUp");
            }
        } else {
            switch (ls) {
                case 2:
                    List<CreativeDTO> dtos = creativeService.uploadUpdate(new ArrayList<Long>() {{
                        add(Long.valueOf(crid));
                    }});
                    if (dtos.size() > 0) {
                        dtos.stream().forEach(s -> {
                            creativeService.updateLs(Long.valueOf(crid), s);
                        });
                        return writeMapObject(MSG, SUCCESS);
                    } else {
                        return writeMapObject(MSG, "修改失败");
                    }
                case 3:
                    Integer result = creativeService.uploadDel(Long.valueOf(crid));
                    if (result != 0) {
                        creativeService.deleteByLongId(Long.valueOf(crid));
                        return writeMapObject(MSG, SUCCESS);
                    } else {
                        return writeMapObject(MSG, "删除失败");
                    }

            }
        }

        return writeMapObject(MSG, "上传失败");
    }

    @RequestMapping(value = "uploadAddByUp")
    public ModelAndView uploadAddByUp(@RequestParam(value = "crid") String crid) {
        List<CreativeDTO> returnKeywordDTO = creativeService.uploadAddByUp(crid);
        if (returnKeywordDTO.size() > 0) {
            returnKeywordDTO.stream().forEach(s -> {
                creativeService.update(crid, s);
            });
            return writeMapObject(MSG, SUCCESS);
        }
        return writeMapObject(MSG, "级联上传失败");
    }

    @RequestMapping(value = "filterSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView filterSearch(@RequestBody SearchFilterParam sp, HttpServletResponse response) {
        Integer nowPage = 1;
        Integer pageSize = 1000;
        String aid = sp.getAid();
        String cid = sp.getCid();
        PagerInfo pagerInfo = null;
        Map<String, Object> map = new HashMap<>();
        if (!aid.equals("-1")) {
            if (aid.length() > OBJ_SIZE || cid.length() > OBJ_SIZE) {
                if (!aid.equals("")) {
                    if (aid.length() > OBJ_SIZE) {
                        map.put(MongoEntityConstants.OBJ_ADGROUP_ID, aid);
                        pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, sp);
                    } else {
                        map.put(MongoEntityConstants.ADGROUP_ID, aid);
                        pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, sp);
                    }
                } else if (!cid.equals("") && aid.equals("")) {
                    List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignObj(cid);
                    pagerInfo = creativeService.findByPagerInfoForLong(adgroupIds, nowPage, pageSize, sp);
                } else {
                    pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, sp);
                }
            } else {
                if (!aid.equals("")) {
                    pagerInfo = creativeService.findByPagerInfo(Long.parseLong(aid), nowPage, pageSize, sp);
                } else if (!cid.equals("") && aid.equals("")) {
                    List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignId(Long.parseLong(cid));
                    pagerInfo = creativeService.findByPagerInfoForLong(adgroupIds, nowPage, pageSize, sp);

                } else {
                    pagerInfo = creativeService.findByPagerInfo(map, nowPage, pageSize, sp);
                }
            }
        }
        writeJson(pagerInfo, response);
        return null;
    }

    @RequestMapping(value = "importByFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("upload");
        CsvImportResponseVO cr = new CsvImportResponseVO();
        String fileName = file.getOriginalFilename();
        UploadHelper upload = new UploadHelper();
        String ext = upload.getExt(fileName);
        String fileNameUpdateAgo = new Date().getTime() + "." + ext;
        if (ext.equals("csv")) {
            File targetFile = new File(path, fileNameUpdateAgo);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BaiduAccountInfoDTO accountInfoDTO = baiduAccountService.getBaiduAccountInfoBySystemUserNameAndAcId(AppContext.getUser(), AppContext.getAccountId());
            CsvReadUtil csvReadUtil = new CsvReadUtil(path + File.separator + fileNameUpdateAgo, "UTF-8", accountInfoDTO);
            List<CreativeDTO> getList = csvReadUtil.getImportCreativeList();
            targetFile.delete();

            ValidateCreativeVO vc = new ValidateCreativeVO();
            List<CreativeDTO> selfListCreative = new ArrayList<>();
            List<CreativeDTO> dbExistCreative = null;
            Map<String, List<CreativeDTO>> selfDTO = getList
                    .stream()
                    .collect(Collectors.groupingBy(creativeDTO -> {
                        return creativeDTO.getTitle() + creativeDTO.getDescription1() + creativeDTO.getDescription2();
                    }));
            for (Map.Entry<String, List<CreativeDTO>> s : selfDTO.entrySet()) {
                List<CreativeDTO> tmpList = s.getValue();
                selfListCreative.add(tmpList.get(0));
            }
            vc.setEndGetCount(getList.size() - selfListCreative.size());

            if (selfListCreative.size() > 0) {
                vc.setSafeCreativeDTOList(selfListCreative);
                dbExistCreative = creativeService.findExistCreative(selfListCreative);
                vc.setDbExistCreativeDTOList(dbExistCreative);
                cr.setMsg("Ok");
            } else {
                cr.setMsg("没有检测到csv文件有正确的数据");
            }
            cr.setVc(vc);
            writeJson(cr, response);

        } else if (ext.equals("xls") || ext.equals("xlsx")) {
            cr.setMsg("目前只支持csv格式的文件！");
            writeJson(cr, response);
        } else {
            cr.setMsg("请选择正确的文件格式！");
            writeJson(cr, response);
            return;
        }
    }
}


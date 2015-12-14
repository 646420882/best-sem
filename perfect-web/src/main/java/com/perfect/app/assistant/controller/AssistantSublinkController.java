package com.perfect.app.assistant.controller;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.core.AppContext;
import com.perfect.dto.creative.MobileSublinkDTO;
import com.perfect.dto.creative.MobileSublinkInfoDTO;
import com.perfect.dto.creative.SublinkDTO;
import com.perfect.dto.creative.SublinkInfoDTO;
import com.perfect.service.AdgroupService;
import com.perfect.service.MobileSublinkService;
import com.perfect.service.SublinkService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2015/2/25.
 */
@Controller
@RequestMapping(value = "/sublink")
public class AssistantSublinkController extends WebContextSupport {
    public static final int OBJ_SIZE = 18;
    @Resource
    private SublinkService sublinkService;
    @Resource
    private MobileSublinkService mobileSublinkService;
    @Resource
    private AdgroupService adgroupService;

    @RequestMapping(value = "/getList")
    public void getList(HttpServletResponse response,
                        @RequestParam(value = "cid", required = false) String cid,
                        @RequestParam(value = "aid", required = false) String aid,
                        @RequestParam(value = "nowPage", required = false, defaultValue = "0") int nowPage,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PagerInfo pagerInfo = null;
        Map<String, Object> map = new HashMap<>();
        if (!aid.equals("-1")) {
            if (aid.length() > OBJ_SIZE || cid.length() > OBJ_SIZE) {
                if (aid != "" || !aid.equals("")) {
                    if (aid.length() > OBJ_SIZE) {
                        map.put(MongoEntityConstants.OBJ_ADGROUP_ID, aid);
                        pagerInfo = sublinkService.findByParams(map, nowPage, pageSize);
                    } else {
                        map.put(MongoEntityConstants.ADGROUP_ID, aid);
                        pagerInfo = sublinkService.findByParams(map, nowPage, pageSize);
                    }
                } else if (!cid.equals("") && aid.equals("")) {
                    List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignObj(cid);
                    pagerInfo = sublinkService.findByPagerInfoForLongs(adgroupIds, nowPage, pageSize);
                } else {
                    pagerInfo = sublinkService.findByPagerInfo(map, nowPage, pageSize);
                }
            } else {
                if (aid != "" || !aid.equals("")) {
                    pagerInfo = sublinkService.findByPagerInfo(Long.parseLong(aid), nowPage, pageSize);
                } else if (!cid.equals("") && aid.equals("")) {
                    List<Long> adgroupIds = adgroupService.getAdgroupIdByCampaignId(Long.parseLong(cid));
                    pagerInfo = sublinkService.findByPagerInfoForLongs(adgroupIds, nowPage, pageSize);
                } else {
                    pagerInfo = sublinkService.findByPagerInfo(map, nowPage, pageSize);
                }
            }
        }
        writeJson(pagerInfo, response);
    }

    @RequestMapping(value = "/customSave")
    public ModelAndView customSave(
            @RequestParam(value = "names", required = true) String names,
            @RequestParam(value = "links", required = true) String links,
            @RequestParam(value = "subPause", required = true) boolean pause,
            @RequestParam(value = "mType", required = true) Integer mType,
            @RequestParam(value = "adgroupId", required = true) String adgroupId) {
        if (mType == 0) {
            String[] subNames = names.split("\n");
            String[] subLinks = links.split("\n");
            List<SublinkInfoDTO> sublinkInfoDTOs = new ArrayList<>();
            for (int i = 0; i < subNames.length; i++) {
                SublinkInfoDTO sublinkInfoDTO = new SublinkInfoDTO();
                sublinkInfoDTO.setDescription(subNames[i]);
                sublinkInfoDTO.setDestinationUrl(subLinks[i]);
                sublinkInfoDTOs.add(sublinkInfoDTO);
            }
            SublinkDTO sublinkDTO = new SublinkDTO();
            sublinkDTO.setStatus(-1);
            if (adgroupId.length() > OBJ_SIZE) {
                sublinkDTO.setAdgroupObjId(adgroupId);
            } else {
                sublinkDTO.setAdgroupId(Long.valueOf(adgroupId));
            }
            sublinkDTO.setPause(pause);
            sublinkDTO.setAccountId(AppContext.getAccountId());
            sublinkDTO.setSublinkInfos(sublinkInfoDTOs);
            String result = sublinkService.customSave(sublinkDTO);
            if (result != null) {
                return writeMapObject(MSG, SUCCESS);
            } else {
                return writeMapObject(MSG, "一个单元只能存在一个计算机蹊径子链");
            }
        } else {
            String[] subNames = names.split("\n");
            String[] subLinks = links.split("\n");
            List<MobileSublinkInfoDTO> mobileSublinkInfoDTOs = new ArrayList<>();
            for (int i = 0; i < subNames.length; i++) {
                MobileSublinkInfoDTO mobileSublinkInfoDTO = new MobileSublinkInfoDTO();
                mobileSublinkInfoDTO.setDescription(subNames[i]);
                mobileSublinkInfoDTO.setDestinationUrl(subLinks[i]);
                mobileSublinkInfoDTOs.add(mobileSublinkInfoDTO);
            }
            MobileSublinkDTO sublinkDTO = new MobileSublinkDTO();
            sublinkDTO.setStatus(-1);
            if (adgroupId.length() > OBJ_SIZE) {
                sublinkDTO.setAdgroupObjId(adgroupId);
            } else {
                sublinkDTO.setAdgroupId(Long.valueOf(adgroupId));
            }
            sublinkDTO.setPause(pause);
            sublinkDTO.setAccountId(AppContext.getAccountId());
            sublinkDTO.setMobileSublinkInfos(mobileSublinkInfoDTOs);
            String result = mobileSublinkService.customSave(sublinkDTO);
            if (result != null) {
                return writeMapObject(MSG, SUCCESS);
            } else {
                return writeMapObject(MSG, "一个单元只能存在一个移动端蹊径子链");
            }
        }
    }
}

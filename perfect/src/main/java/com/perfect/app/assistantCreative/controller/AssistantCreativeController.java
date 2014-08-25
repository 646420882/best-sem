package com.perfect.app.assistantCreative.controller;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CreativeDAO;
import com.perfect.entity.CreativeEntity;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @Resource
    CreativeDAO creativeDAO;
    @Resource
    AdgroupDAO adgroupDAO;

    @RequestMapping(value = "/getList")
    public ModelAndView getCreativeList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "cid", required = false) String cid,
                                        @RequestParam(value = "aid", required = false) String aid) {
        List<CreativeEntity> creativeEntityList= new ArrayList<>();
        if (aid != "" || !aid.equals("")) {
            creativeEntityList= creativeDAO.getCreativeByAdgroupId(Long.parseLong(aid), null, 0, 20);
        }else if(!cid.equals("")&&aid.equals("")){
            List<Long> adgroupIds=adgroupDAO.getAdgroupIdByCampaignId(Long.parseLong(cid));
            creativeEntityList= (List<CreativeEntity>) creativeDAO.getAllsByAdgroupIds(adgroupIds);

        }else{
            creativeEntityList=creativeDAO.find(null,0,20);
        }
        writeJson(creativeEntityList, response);
        return null;
    }
}

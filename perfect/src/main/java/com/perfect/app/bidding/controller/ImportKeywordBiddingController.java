package com.perfect.app.bidding.controller;

import com.perfect.core.AppContext;
import com.perfect.entity.CustomGroupEntity;
import com.perfect.service.CustomGroupService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 */
@Controller
@RequestMapping("/importBid")
public class ImportKeywordBiddingController extends WebContextSupport {

    @Resource
    private CustomGroupService customGroupSerivice;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ModelAndView insertCustomGroup(@RequestParam(value = "gname")String groupName,HttpServletResponse response){
        CustomGroupEntity customGroupEntity=new CustomGroupEntity();
        customGroupEntity.setAccountId(AppContext.getAccountId());
        customGroupEntity.setGroupName(groupName);
        customGroupSerivice.insert(customGroupEntity);
        String oid=customGroupEntity.getId();
        writeData(SUCCESS,response,oid);
        return null;
    }
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public ModelAndView findCustomGroupAll(HttpServletResponse response){
      List<CustomGroupEntity> list=  customGroupSerivice.findAll(AppContext.getAccountId());
        writeJson(list,response);
        return null;
    }
    @RequestMapping(value = "/getCustomTree",method = RequestMethod.GET)
    public ModelAndView getzTree(HttpServletResponse response){
        Map<String,Object> map=customGroupSerivice.getCustomGroupTree();
        if(map.size()>0){
            writeJson(map,response);
        }
        return null;
    }
}

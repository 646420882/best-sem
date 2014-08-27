package com.perfect.app.assistantAdgroup.controller;

import com.perfect.dao.AdgroupDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/adgroup")
public class AssistantAdgroupController extends WebContextSupport{
    @Resource
    AdgroupDAO adgroupDAO;
    @RequestMapping(value = "/getList")
    public ModelAndView getList(HttpServletRequest request,HttpServletResponse response){
        List<AdgroupEntity> list=adgroupDAO.find(null,0,15);
        writeJson(list,response);
        return null;
    }
}

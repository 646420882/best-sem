package com.perfect.app.count.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.count.ConstantsDTO;
import com.perfect.service.CensusService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/6.
 */
@RestController
@Scope("prototype")
@RequestMapping("/pftstis")
public class PerfectStatistics extends WebContextSupport {
    @Resource
    private CensusService censusService;
    /**
     * 统计页面
     *
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ModelAndView Count(ModelMap model) {
        return new ModelAndView("count/count", model);
    }
    /**
     * test
     *
     * @return
     */
    @RequestMapping(value = "/gettest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView gettest() {
        return new ModelAndView("avatar/avatar");
    }

    /**
     * test-XiaoWei
     * @return
     */
    @RequestMapping(value = "/getPager")
    public ModelAndView getPager() {
        return new ModelAndView("homePage/aaa");
    }
    /**
     * cookie
     *
     * @return
     */
    @RequestMapping(value = "/getCookie", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getCookie() {
        return new ModelAndView("avatar/cookie");
    }

    /**
     * 获取用户IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 参数说明：
     * a Cookie中的UUID
     * b 电脑系统
     * c 客户端浏览器信息
     * d 客户端屏幕分辨率信息
     * e 客户端是否支持cookie（true为支持）
     * f 客户端是否支持java（true为支持）
     * g 客户端屏幕颜色渲染bit
     * h 客户端flash版本 （如果此参数为空则表明不支持flash插件,数据库标识为  Deny）
     * i 客户端访问目标地址时间
     * j 客户端网页的访问来源（如果此参数为空则表明访问来远为：“直接访问”,数据库标识为  direct）
     * k 用户IP地址
     * l 用户所在地区
     * m 用户浏览网站使用设备(0 PC端浏览  1 移动端浏览)
     * n 用户自定义数组
     * Url：客户端网页目标地址
     * ip: java获取客户端IP地址
     * TODO 后期更换参数处理
     * @return
     */

    @RequestMapping(value = "/saveParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAvg(HttpServletResponse response,HttpServletRequest request,
                               @RequestParam(value = "osAnBrowser", required = true) String[] osAnBrowser) {
        List<String> list=new ArrayList<>();
        for (int i = 0; i <osAnBrowser.length ; i++) {
            list.add(osAnBrowser[i]);
        }
        list.add(request.getHeader("referer"));
        list.add(request.getSession().getId());
        String[] newStr=list.toArray(new String[1]);
        String sid = censusService.saveParams(newStr);
        return null;
    }
    @RequestMapping(value = "/getTodayConstants",method = RequestMethod.GET)
    public  ModelAndView getTodayConstants(HttpServletResponse response,@RequestParam(value = "url",required = true)String url){
       Map<String,ConstantsDTO> constantsDTO= censusService.getTodayTotal(url);
        writeJson(constantsDTO,response);
        return null;
    }

}

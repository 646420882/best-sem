package com.perfect.app.statistics.controller;

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
import java.io.*;
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
     * osAnBrowser数组参数说明：
     * 0 Cookie中的UUID
     * 1 客户端电脑系统信息
     * 2 客户端浏览器信息
     * 3 客户端屏幕分辨率信息
     * 4 客户端是否支持cookie（true为支持）
     * 5 客户端是否支持java（true为支持）
     * 6 客户端屏幕颜色渲染bit
     * 7 客户端flash版本 （如果此参数为空则表明不支持flash插件,数据库标识为  Deny）
     * 8 客户端访问目标地址时间
     * 9 客户端网页的访问来源（如果此参数为空则表明访问来远为：“直接访问”,数据库标识为  direct）
     * 10 用户IP地址
     * 11 用户所在地区
     * 12 用户浏览网站使用设备(0 PC端浏览  1 移动端浏览)
     * Url：客户端网页目标地址
     * ip: java获取客户端IP地址
     * @return
     */
    @RequestMapping(value = "/statistics", method = {RequestMethod.GET, RequestMethod.POST})
    public void gettests(HttpServletRequest request, HttpServletResponse response,
                         String[] osAnBrowser) throws UnsupportedEncodingException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("F://statistics.log"), true));
            for (int i = 0;i<osAnBrowser.length; i++){
                writer.write(Parameters(i,osAnBrowser)+osAnBrowser[i]+"\r\n");
            }

            String Url = request.getHeader("referer");
            //获取用户IP地址
            String ip = getIpAddr(request);
            System.out.println(ip);
            writer.write(Url+"\r\n\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    //
    private String Parameters(int a,String[] osAnBrowser){
        switch (a){
            case 0:
                return "Cookie中的UUID：";
            case 1:
                return "电脑系统信息：";
            case 2:
                return "浏览器信息：";
            case 3:
                return "屏幕分辨率信息：";
            case 4:
                return "是否支持cookie：";
            case 5:
                return "是否支持java：";
            case 6:
                return "屏幕颜色渲染bit：";
            case 7:
                return "flash版本：";
            case 8:
                return "访问目标地址时间：";
            case 9:
                return "网页的访问来源：";
            case 10:
                return "IP：";
            case 11:
                if(osAnBrowser[11] == null || osAnBrowser[11] == ""){
                    return "地区：未知区域";
                }
                return "地区：";
            case 12:
                if(osAnBrowser[12].equals("0")){
                    return "使用设备：PC端->";
                }else if(osAnBrowser[12].equals("1")){
                    return "使用设备：移动端->";
                }else{
                    return "使用设备：其他";
                }
        }
        return "未知";
    }

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

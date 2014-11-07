package com.perfect.app.perfectStatistics.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by SubDong on 2014/11/6.
 */
@RestController
@Scope("prototype")
@RequestMapping("/pftstis")
public class PerfectStatistics {
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
     * 7 客户端flash版本
     * 8 客户端访问目标地址时间
     * 9 客户端网页的访问来源（如果此参数为空则表明访问来远为：“直接访问”）
     * Url：客户端网页目标地址
     * @return
     */
    @RequestMapping(value = "/statistics", method = {RequestMethod.GET, RequestMethod.POST})
    public void gettests(HttpServletRequest request, HttpServletResponse response,
                         String[] osAnBrowser) throws UnsupportedEncodingException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("F://statistics.log"), true));
            for (int i = 0;i<osAnBrowser.length; i++){
                writer.write(Parameters(i)+osAnBrowser[i]+"\r\n");
            }
            String Url = request.getHeader("referer");
            writer.write(Url+"\r\n\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String Parameters(int a){
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
        }
        return "未知";
    }
}

package com.perfect.app.census.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by XiaoWei on 2014/11/10.
 */
@Controller
@RequestMapping("/census")
public class CensusController  {

    @RequestMapping(value = "/getPager")
    public ModelAndView getPager() {
        return new ModelAndView("homePage/aaa");
    }

    @RequestMapping(value = "/SaveParams",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAvg(HttpServletResponse response,
                               @RequestParam(value = "osAnBrowser",required = true)String[] osAnBrowser){
        String uuid=osAnBrowser[0];
        String windows=osAnBrowser[1];
        String browser=osAnBrowser[2];
        String resolution=osAnBrowser[3];
        boolean supportCookie= Boolean.parseBoolean(osAnBrowser[4]);
        boolean supportJava= Boolean.parseBoolean(osAnBrowser[5]);
        String bit=osAnBrowser[6];
        String flash=osAnBrowser[7];
        String time=osAnBrowser[8];
        String lastPage=osAnBrowser[9];
        String ip=osAnBrowser[10];
        String area=osAnBrowser[11];
        String operate=osAnBrowser[12];

        return null;
    }
    @RequestMapping(value = "/getTotal",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getTotal(HttpServletResponse response,
                                 @RequestParam(value = "session",required = true,defaultValue = "")String session,
                                 @RequestParam(value = "cookie",required = true,defaultValue = "")String cookie,
                                 @RequestParam(value = "url",required = false)String url){

        return null;
    }
}

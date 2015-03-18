package com.perfect.app.count.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.dao.CensusDAO;
import com.perfect.dao.report.CensusEveryDayReportDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by john on 2014/11/19.
 * 趋势分析
 */
@Controller
@RequestMapping("/pftstis")
public class TrendAnalysisController extends WebContextSupport {

    @Resource
    private CensusEveryDayReportDao censusEveryDayReportDao ;



    /**
     * 显示实时访客页面
     * @return
     */
    @RequestMapping(value = "/realTimeVisitorPage")
    public ModelAndView getRealTimeVisitorPage(){
        return new ModelAndView("census/trend/realtimevisitor");
    }

    /**
     * 显示今日，昨日，最近30天的页面
     * @return
     */
    @RequestMapping(value = "/dayTotalPage")
    public ModelAndView getDayTotalPage(){
        return new ModelAndView("census/trend/dayTotal");
    }

    @RequestMapping(value = "/getTrendAnalysisData",method = {RequestMethod.GET, RequestMethod.POST})
    public void getTrendAnalysisData(HttpServletResponse response){
        writeJson(censusEveryDayReportDao.getCensus(), response);
        System.out.println();
    }

}

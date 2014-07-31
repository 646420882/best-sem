package com.perfect.app.homePage.controller;

import com.google.gson.Gson;
import com.mongodb.Cursor;
import com.mongodb.DBCursor;
import com.perfect.app.homePage.service.PerformanceService;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/7/25.
 */
@RestController
@Scope("prototype")
public class PerformanceController {
    @Resource
    public PerformanceService performanceService;

    /**
     * 账户表现
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/account/performance", method =  RequestMethod.GET)
    public void test(HttpServletResponse response) {
        String[] date={"2014-01-25","2014-01-26","2014-01-27","2014-01-28","2014-01-29","2014-01-30","2014-01-31","2014-02-01","2014-02-02"
                ,"2014-02-03","2014-02-04","2014-02-05","2014-02-06","2014-02-07","2014-02-08","2014-02-09","2014-02-12","2014-02-11","2014-02-12","2014-02-13"};
        List<KeywordRealTimeDataVOEntity> jsonMapList = performanceService.performance("Perfect", date);
        Gson gson = new Gson();
        String ddd = gson.toJson(jsonMapList);

        try {
            response.setContentType("text/json; charset=UTF-8");
            response.getWriter().print(ddd);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

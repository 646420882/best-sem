package com.perfect.app.homePage.controller;


import com.perfect.api.baidu.PromotionMonitoring;
import com.perfect.autosdk.sms.v3.FolderMonitor;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.KeywordReportService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by XiaoWei on 2014/7/29.
 */
@Controller
public class ImportKeywordManager extends WebContextSupport {

    @Resource
    private KeywordReportService keywordReportService;

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    @RequestMapping(value = "/import/getImportKeywordList")
    public void getImportKeywordList(HttpServletResponse response,
                                     @RequestParam(value = "startDate", required = true) String startDate,
                                     @RequestParam(value = "endDate", required = true) String endDate,
                                     @RequestParam(value = "limt", required = false) Integer limit,
                                     @RequestParam(value = "sort", required = false) String orderBy,
                                     @RequestParam(value = "nowPage")Integer nowPage,
                                     @RequestParam(value = "pageSize")Integer pageSize) {
//       List<KeywordRealTimeDataVOEntity> list=importKeywordService.getMap(request);
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("limit", limit);
        map.put("sort", orderBy);
        map.put("kwdIds",getMonitorIds());
        map.put("nowPage",nowPage);
        map.put("pageSize",pageSize);
        map.put("orderBy",orderBy);
        PagerInfo p = keywordReportService.findByPagerInfo(map);



        writeJson(p,response);

    }

    /**
     * 获取当前登陆用户基本信息和对应API
     *
     * @return
     */
    public PromotionMonitoring getUserInfo() {
        Long accid = AppContext.getAccountId();
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(accid);
        PromotionMonitoring Monitoring = new PromotionMonitoring(entity);
        return Monitoring;
    }
    private List<Long> getMonitorIds(){
        List<Long> kwdIds=new ArrayList<>();
        PromotionMonitoring monitoring = getUserInfo();
        List<FolderMonitor> monitors = monitoring.getMonitorWordByFolderIdAll();
        for (FolderMonitor fm : monitors) {
            for (Monitor mt : fm.getMonitors()) {
                kwdIds.add(mt.getId());
            }
        }
        return kwdIds;
    }


}

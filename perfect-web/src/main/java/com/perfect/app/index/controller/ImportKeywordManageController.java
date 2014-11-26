package com.perfect.app.index.controller;


import com.perfect.api.baidu.PromotionMonitoring;
import com.perfect.autosdk.sms.v3.FolderMonitor;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.dao.mongodb.utils.DateUtils;
import com.perfect.dao.mongodb.utils.PagerInfo;
import com.perfect.service.KeywordReportService;
import com.perfect.commons.web.WebContextSupport;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by XiaoWei on 2014/7/29.
 */
@Controller
public class ImportKeywordManageController extends WebContextSupport {

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
                                     @RequestParam(value = "nowPage") Integer nowPage,
                                     @RequestParam(value = "pageSize") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("limit", limit);
        map.put("sort", orderBy);
        if (getMonitorIds() != null) {
            map.put("kwdIds", getMonitorIds());
        }
        map.put("nowPage", nowPage);
        map.put("pageSize", pageSize);
        map.put("orderBy", orderBy);
        PagerInfo p = keywordReportService.findByPagerInfo(map);
        writeJson(p, response);
    }

    /**
     * 获取当前登陆用户基本信息和对应API
     *
     * @return
     */
    public PromotionMonitoring getUserInfo() {
        Long accid = AppContext.getAccountId();
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(accid);
        PromotionMonitoring Monitoring = new PromotionMonitoring(entity.getBaiduUserName(),entity.getBaiduPassword(),entity.getToken());;
        return Monitoring;
    }

    /**
     * 获取到所有被监控的重点关键词id组
     *
     * @return
     */
    private List<Long> getMonitorIds() {
        List<Long> kwdIds = new ArrayList<>();
        PromotionMonitoring monitoring = getUserInfo();
        List<FolderMonitor> monitors = monitoring.getMonitorWordByFolderIdAll();
        if (monitors.size() > 0) {
            for (FolderMonitor fm : monitors) {
                for (Monitor mt : fm.getMonitors()) {
                    kwdIds.add(mt.getId());
                }
            }
        }
        return kwdIds;
    }

    @RequestMapping(value = "import/getCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView getImportCSV(HttpServletResponse response,
                                     @RequestParam(value = "startDate", required = true) String startDate,
                                     @RequestParam(value = "endDate", required = true) String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("kwdIds", getMonitorIds());
        List<KeywordReportEntity> keywordReportEntityList = keywordReportService.getAll(params);
        String filename = DateUtils.getYesterdayStr() + "-ImportKeyword.csv";
        OutputStream os = null;
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("UTF-8"), "ISO8859-1"));
            os = response.getOutputStream();
            keywordReportService.downAccountCSV(os, keywordReportEntityList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

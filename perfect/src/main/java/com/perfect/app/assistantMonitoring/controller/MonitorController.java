package com.perfect.app.assistantMonitoring.controller;

import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.entity.FolderEntity;
import com.perfect.service.MonitorSynchronizedService;
import com.perfect.service.MonitoringService;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/10.
 */
@RestController
@Scope("prototype")
public class MonitorController {
    @Resource
    private MonitoringService monitoringService;
    @Resource
    private MonitorSynchronizedService synchronizedService;
    @Resource
    private WebContext webContext;

    /**
     * 同步当前用户下的所有监控信息
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/synchronous", method = {RequestMethod.GET, RequestMethod.POST})
    public void synchronous(HttpServletResponse response) {
        int i = synchronizedService.getSynchronized();
        webContext.writeJson(i, response);
    }


    /**
     * 同步当前用户下的所有监控信息
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/getFolder", method = {RequestMethod.GET, RequestMethod.POST})
    public void getFolder(HttpServletResponse response) {
        List<FolderEntity> folder = monitoringService.getFolder();
        Map<String,List<FolderEntity>> listMap = new HashMap<>();
            listMap.put("rows",folder);
        webContext.writeJson(listMap, response);
    }

}

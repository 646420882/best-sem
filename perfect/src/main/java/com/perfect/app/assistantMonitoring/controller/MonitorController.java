package com.perfect.app.assistantMonitoring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.FolderEntity;
import com.perfect.service.MonitorSynchronizedService;
import com.perfect.service.MonitoringService;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
     * 获取树
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/getTree", method = {RequestMethod.GET, RequestMethod.POST})
    public void getTree(HttpServletResponse response) {
        List<FolderEntity> folder = monitoringService.getFolder();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode jsonNodes = null;
        if(folder.size() > 0){
            for(FolderEntity entity : folder){
                jsonNodes = mapper.createObjectNode();
                jsonNodes.put("id", entity.getFolderId());
                jsonNodes.put("pid", 0);
                jsonNodes.put("name", entity.getFolderName());
                arrayNode.add(jsonNodes);
            }
        }
        Map<String,Object> listMap = new HashMap<>();
        listMap.put("tree",arrayNode);
        webContext.writeJson(listMap, response);
    }

    /**
     * 得到当前用户下的所有监控文件夹信息
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

    /**
     * 得到当前用户下的所有监控对象信息
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/getMonitor", method = {RequestMethod.GET, RequestMethod.POST})
    public void getMonitor(HttpServletResponse response) {
        List<KeywordDTO> keywordDTOs = monitoringService.getMonitor();

        Map<String,List<KeywordDTO>> listMap = new HashMap<>();
        listMap.put("rows",keywordDTOs);
        webContext.writeJson(listMap, response);
    }

    /**
     * 通过监控文件夹ID得到当前用户下的所有监控对象信息
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/getMonitorId", method = {RequestMethod.GET, RequestMethod.POST})
    public void getMonitorId(HttpServletResponse response,
                             @RequestParam(value = "forlder", required = false) Long forlder) {
        List<KeywordDTO> keywordDTOs = monitoringService.getMonitorId(forlder);

        Map<String,List<KeywordDTO>> listMap = new HashMap<>();
        listMap.put("rows",keywordDTOs);
        webContext.writeJson(listMap, response);
    }

}

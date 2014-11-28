package com.perfect.app.assistant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.commons.web.WebContext;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.service.MonitorSynchronizedService;
import com.perfect.service.MonitoringService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/10.
 */
@RestController
@Scope("prototype")
public class AssistantMonitorController {
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
        List<FolderDTO> folder = monitoringService.getFolder();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode jsonNodes = null;
        if (folder.size() > 0) {
            for (FolderDTO entity : folder) {
                jsonNodes = mapper.createObjectNode();
                jsonNodes.put("id", entity.getFolderId());
                jsonNodes.put("pid", 0);
                jsonNodes.put("name", entity.getFolderName());
                arrayNode.add(jsonNodes);
            }
        }
        Map<String, Object> listMap = new HashMap<>();
        listMap.put("tree", arrayNode);
        webContext.writeJson(listMap, response);
    }

    /**
     * 得到当前用户下的所有监控文件夹信息
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/getFolder", method = {RequestMethod.GET, RequestMethod.POST})
    public void getFolder(HttpServletResponse response) {
        List<FolderDTO> folder = monitoringService.getFolder();

        Map<String, List<FolderDTO>> listMap = new HashMap<>();
        listMap.put("rows", folder);
        webContext.writeJson(listMap, response);
    }

    /**
     * 得到当前用户下的所有监控对象信息
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/getMonitor", method = {RequestMethod.GET, RequestMethod.POST})
    public void getMonitor(HttpServletResponse response) {
        List<KeywordInfoDTO> keywordDTOs = monitoringService.getMonitor();

        Map<String, List<KeywordInfoDTO>> listMap = new HashMap<>();
        listMap.put("rows", keywordDTOs);
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
        List<KeywordInfoDTO> keywordDTOs = monitoringService.getMonitorId(forlder);

        Map<String, List<KeywordInfoDTO>> listMap = new HashMap<>();
        listMap.put("rows", keywordDTOs);
        webContext.writeJson(listMap, response);
    }

    /**
     * 修改监控文件夹名称
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/updateFolderName", method = {RequestMethod.GET, RequestMethod.POST})
    public void updateFolderName(HttpServletResponse response,
                                 @RequestParam(value = "forlderId", required = false) Long forlderId,
                                 @RequestParam(value = "forlderName", required = false) String forlderName) {
        boolean writeResult = monitoringService.updateFolderName(forlderId, forlderName);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(String.valueOf(writeResult));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加监控文件夹
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/addFolder", method = {RequestMethod.GET, RequestMethod.POST})
    public void updateFolderName(HttpServletResponse response,
                                 @RequestParam(value = "forlderName", required = false) String forlderName) {
        int folder = monitoringService.addFolder(forlderName);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(String.valueOf(folder));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除监控文件夹
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/removeFolder", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteFolder(HttpServletResponse response,
                             @RequestParam(value = "forlderId", required = false) Long forlderId) {

        boolean folder = monitoringService.deleteFolder(forlderId);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(String.valueOf(folder));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除监控对象
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/removeMonitor", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteMonitor(HttpServletResponse response,
                              @RequestParam(value = "monitorId", required = false) Long monitorId) {

        boolean folder = monitoringService.deleteMonitorId(monitorId);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(String.valueOf(folder));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 添加监控对象
     *
     * @param response
     */
    @RequestMapping(value = "/monitoring/addMonitor", method = {RequestMethod.GET, RequestMethod.POST})
    public void addMonitor(HttpServletResponse response,
                           @RequestParam(value = "folderID", required = false) Long folderID,
                           @RequestParam(value = "campaignId", required = false) Long campaignId,
                           @RequestParam(value = "adgroupId", required = false) Long adgroupId,
                           @RequestParam(value = "acliId", required = false) Long acliId) {

        int folder = monitoringService.addMonitorId(folderID, campaignId, adgroupId, acliId);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(String.valueOf(folder));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

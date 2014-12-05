package com.perfect.app.statistics.controller;

import com.mongodb.util.JSON;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.CensusCfgDTO;
import com.perfect.dto.CountDTO;
import com.perfect.service.CensusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/12/1.
 */
@Controller
@RequestMapping("/pftstis")
public class VisitPageController extends WebContextSupport {

    @Resource
    private CensusService censusService;

    @RequestMapping("/saveUrl")
    public ModelAndView saveUrlConfig(HttpServletRequest request,
                                      @RequestParam(value = "url", required = true, defaultValue = "") String url,
                                      @RequestParam(value = "ip", defaultValue = "0.0.0.1", required = true) String ip,
                                      @RequestParam(value = "urlDesc", defaultValue = "暂无说明", required = false) String urlDesc) {
        try {
            CensusCfgDTO censusCfgDTO=new CensusCfgDTO();
            censusCfgDTO.setUrl(url);
            censusCfgDTO.setIp(ip);
            censusCfgDTO.setUrlDesc(urlDesc);
            censusCfgDTO.setStatus(0);
            int result=censusService.saveConfig(censusCfgDTO);
            return writeMapObject("result", result);
        }catch (Exception e){
            e.printStackTrace();
            return writeMapObject("result", 3);
        }
    }

    @RequestMapping(value = "/getCfgList")
    public ModelAndView getConfigList(@RequestParam(value = "ip",required = true,defaultValue = "0.0.0.1")String ip) {
        List<CensusCfgDTO> cfgDTOList = censusService.getCfgList(ip);
        return writeMapObject("rows", cfgDTOList);
    }

    @RequestMapping(value = "/delete")
    public  ModelAndView delete(@RequestParam(value = "id",required = true)String id){
        try {
            censusService.delete(id);
            return writeMapObject("result",SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return writeMapObject("result",EXCEPTION);
        }
    }

    @RequestMapping(value = "/getVisitPageCount")
    public ModelAndView getVisitPageCount(@RequestParam(value = "ip",defaultValue = "0.0.0.1",required = true)String ip){
        CountDTO countDTO=censusService.getVisitPage(ip,-1);
        return writeMapObject("result",countDTO);
    }
}

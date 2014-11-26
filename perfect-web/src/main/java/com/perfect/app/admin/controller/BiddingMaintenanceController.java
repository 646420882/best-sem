package com.perfect.app.admin.controller;

import com.perfect.dto.UrlDTO;
import com.perfect.service.BiddingMaintenanceService;
import com.perfect.utils.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by baizz on 2014-9-26.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/biddingUrl")
public class BiddingMaintenanceController {

    @Resource
    private BiddingMaintenanceService biddingMaintenanceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView listRequestUrl() {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<UrlDTO> list = biddingMaintenanceService.findAll();
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addRequestUrl(@RequestParam(value = "url", required = true) String requestUrl)
            throws UnsupportedEncodingException {
        if (requestUrl == null)
            return null;

        requestUrl = java.net.URLDecoder.decode(requestUrl, "UTF-8");
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setRequest(requestUrl);
        urlDTO.setIdle(true);
        urlDTO.setFinishTime(0l);
        biddingMaintenanceService.saveUrlEntity(urlDTO);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(new TreeMap<String, Object>() {{
            put("status", "success");
        }});
        return new ModelAndView(jsonView);
    }

}

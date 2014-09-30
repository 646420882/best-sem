package com.perfect.app.bidding.controller;

import com.perfect.entity.UrlEntity;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by baizz on 2014-9-26.
 */
@RestController
@Scope("prototype")
public class BiddingMaintenanceController {

    @Resource
    private BiddingMaintenanceService biddingMaintenanceService;

    @RequestMapping(value = "/biddingUrl/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView listRequestUrl(HttpServletRequest request, HttpServletResponse response) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<UrlEntity> list = biddingMaintenanceService.findAll();
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/biddingUrl/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addRequestUrl(@RequestParam(value = "url", required = true) String requestUrl)
            throws UnsupportedEncodingException {
        if (requestUrl == null)
            return null;

        requestUrl = java.net.URLDecoder.decode(requestUrl, "UTF-8");
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setRequest(requestUrl);
        urlEntity.setIdle(true);
        urlEntity.setFinishTime(0l);
        biddingMaintenanceService.saveUrlEntity(urlEntity);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(new TreeMap<String, Object>() {{
            put("status", "success");
        }});
        return new ModelAndView(jsonView);
    }

}

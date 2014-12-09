package com.perfect.app.admin.controller;

import com.perfect.dto.CookieDTO;
import com.perfect.utils.json.JSONUtils;
import com.perfect.service.CookieService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-9-26.
 * 2014-11-29 refactor
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/biddingUrl")
public class BiddingMaintenanceController implements Controller {

    @Resource
    private CookieService cookieService;

    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<CookieDTO> list = cookieService.findAll();
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }
}

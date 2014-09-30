package com.perfect.app.regional.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.perfect.dto.RedisRegionalDTO;
import com.perfect.service.SysRegionalService;
import com.perfect.utils.JSONUtils;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/30.
 */
@RestController
@Scope("prototype")
public class SysRegionalController {
    @Resource
    private SysRegionalService sysRegionalService;

    @RequestMapping(value = "/regional/getProvince", method = {RequestMethod.GET,RequestMethod.POST})
    public void register(HttpServletResponse response) {
        Map<String, List<Object>> dtos = sysRegionalService.getProvince();
        String gson = new Gson().toJson(dtos);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(gson);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

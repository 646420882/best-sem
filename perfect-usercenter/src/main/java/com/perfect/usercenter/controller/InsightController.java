package com.perfect.usercenter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.huiyan.InsightWebsiteDTO;
import com.perfect.service.UserAccountService;
import com.perfect.utils.JsonViews;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by subdong on 15-12-21.
 */
@RestController
@Scope("prototype")
public class InsightController {


    @Resource
    private UserAccountService userAccountService;


    @RequestMapping(value = "/insightAdd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView insightAdd(HttpServletRequest request, HttpServletResponse response,
                                   InsightWebsiteDTO websiteDTO) {

        String s = userAccountService.insertInsight(websiteDTO);
        return JsonViews.generate(JsonResultMaps.successMap(s));
    }

    @RequestMapping(value = "/insightQuery/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView insightQuery(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable String userid) {

        List<InsightWebsiteDTO> insightWebsiteDTO = userAccountService.queryInfo(userid);

        return JsonViews.generate(JsonResultMaps.successMap(insightWebsiteDTO));
    }

    @RequestMapping(value = "/insightDel/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView insightDel(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable String id) {

        String del = userAccountService.del(id);

        return JsonViews.generate(JsonResultMaps.successMap(del));
    }

    @RequestMapping(value = "/insightupdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView insightupdate(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam String uid,
                                      @RequestParam String rname,
                                      @RequestParam String url,
                                      @RequestParam String webName) {

        String update = userAccountService.huiyanUpdate(uid, rname, url, webName);

        return JsonViews.generate(JsonResultMaps.successMap(update));
    }

    @RequestMapping(value = "/enableOrPause", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView enableOrPause(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam String uid,
                                      @RequestParam int enable) {

        String update = userAccountService.huiyanEnableOrPause(uid, enable);

        return JsonViews.generate(JsonResultMaps.successMap(update));
    }
}

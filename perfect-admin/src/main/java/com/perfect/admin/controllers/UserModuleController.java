package com.perfect.admin.controllers;

import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.service.SystemUserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 15/12/15.
 */
@RestController
public class UserModuleController {

    @Resource
    private SystemUserService systemUserService;

    @RequestMapping(value = "/users/{userid}/modules", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addModule(@PathVariable("userid") String userid, @RequestParam("moduleid") String moduleId) {

        boolean success = systemUserService.addModule(userid, moduleId);
        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generateFailedNoData();

    }

    @RequestMapping(value = "/users/{userid}/modules", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteModule(@PathVariable("userid") String userid, @RequestParam("moduleid") String moduleId) {

        boolean success = systemUserService.deleteModule(userid, moduleId);
        if (success) {
            return JsonViews.generateSuccessNoData();
        }
        return JsonViews.generateFailedNoData();
    }



    @RequestMapping(value = "/users/{userid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView userModules(@PathVariable("userid") String userid) {

        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserService.getUserModules(userid);

        if (systemUserModuleDTOs == null || systemUserModuleDTOs.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }
        return JsonViews.generate(JsonResultMaps.successMap(systemUserModuleDTOs));

    }

    @RequestMapping(value = "/users/{userid}/modules/{modulename}/rights", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView updateUserModuleMenus(@PathVariable("userid") String id, @PathVariable("modulename") String
            modulename, @RequestParam("menus") String menus) {

        System.out.println("id = " + id);
        boolean success = systemUserService.updateModuleMenus(id, modulename, menus.split("|"));

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }
}

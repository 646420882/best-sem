package com.perfect.admin.controllers;

import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.param.UserMenuParams;
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

    @RequestMapping(value = "/users/{userid}/modules/{moduleid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteModule(@PathVariable("userid") String userid, @PathVariable("moduleid") String moduleId) {

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

    @RequestMapping(value = "/users/{userid}/modules/{moduleid}/submenus", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView updateUserModuleSubMenus(@PathVariable("userid") String id, @PathVariable("moduleid") String
            moduleid, @RequestBody UserMenuParams menus) {

        if (menus == null) {
            return JsonViews.generateFailedNoData();
        }

        boolean success = systemUserService.updateUserModuleMenus(id, moduleid, menus);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }

    @RequestMapping(value = "/users/{userid}/modules/{moduleid}/submenus/{submenuid}", method = RequestMethod.DELETE, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView deleteUserModuleSubMenus(@PathVariable("userid") String id, @PathVariable("moduleid") String
            modulename, @PathVariable("submenuid") String submenuid, @RequestParam("menus") String menus) {

        boolean success = systemUserService.updateUserModuleMenus(id, modulename, null);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }

//    @RequestMapping(value = "/users/{userid}/modules/{modulename}/submenus", method = RequestMethod.GET, produces = MediaType
//            .APPLICATION_JSON_VALUE)
//    public ModelAndView getUserModuleSubMenus(@PathVariable("userid") String id, @PathVariable("modulename") String
//            modulename, @RequestParam("menus") String menus) {
//
//        System.out.println("id = " + id);
//        boolean success = systemUserService.updateUserModuleMenus(id, modulename, null);
//
//        if (success) {
//            return JsonViews.generateSuccessNoData();
//        }
//
//        return JsonViews.generate(-1);
//    }
}

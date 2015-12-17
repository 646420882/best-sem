package com.perfect.admin.controllers;

import com.google.common.base.Strings;
import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.service.SystemModuleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 * TODO 子模块的增删改查
 */
@RestController
public class SystemModuleController {

    @Resource
    private SystemModuleService systemModuleService;

    /**
     * 获取所有系统模块
     *
     * @return
     */
    @RequestMapping(value = "/sysmodules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView list() {
        List<SystemModuleDTO> systemModuleDTOs = systemModuleService.list();

        if (systemModuleDTOs == null || systemModuleDTOs.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(JsonResultMaps.successMap(systemModuleDTOs));
    }


    /**
     * 生成系统模块
     *
     * @param modulename
     * @param moduleurl
     * @return
     */
    @RequestMapping(value = "/sysmodules", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView createModule(@RequestParam("modulename") String modulename, @RequestParam(value = "moduleurl", required = false) String moduleurl) {

        SystemModuleDTO systemModuleDTO = new SystemModuleDTO();
        systemModuleDTO.setModuleName(modulename);
        systemModuleDTO.setModuleUrl(moduleurl);

        systemModuleDTO = systemModuleService.createModule(systemModuleDTO);

        if (systemModuleDTO != null) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1, "创建系统模块失败");
    }


    /**
     * 删除系统模块
     *
     * @return
     */
    @RequestMapping(value = "/sysmodules/{moduleId}", method = RequestMethod.DELETE, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView deleteModule(@RequestParam("moduleId") String moduleId) {

        boolean success = systemModuleService.deleteModule(moduleId);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1, "删除系统模块失败");
    }


    /**
     * 更新系统模块
     *
     * @param moduleName
     * @param moduleurl
     * @return
     */
    @RequestMapping(value = "/sysmodules/{moduleId}", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView updateModule(@PathVariable("moduleId") String moduleId, @RequestParam(value = "moduleurl",
            required = false) String moduleurl, @RequestParam(value = "modulename", required = false) String moduleName) {

        if (Strings.isNullOrEmpty(moduleName)) {
            return JsonViews.generate(-1, "更新模块参数模块名称非法");
        }

        if (Strings.isNullOrEmpty(moduleurl)) {
            return JsonViews.generate(-1, "更新模块参数模块url非法");
        }


        SystemModuleDTO systemModuleDTO = new SystemModuleDTO();
        systemModuleDTO.setModuleName(moduleName);
        systemModuleDTO.setId(moduleId);
        systemModuleDTO.setModuleUrl(moduleurl);

        boolean success = systemModuleService.updateModule(systemModuleDTO);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1, "更新系统模块失败");
    }

    /**
     * 查询某个系统模块
     *
     * @param moduleid
     * @return
     */
    @RequestMapping(value = "/sysmodules/{moduleid}", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView list(@PathVariable("moduleid") String moduleid) {
        SystemModuleDTO systemModuleDTO = systemModuleService.findByModuleId(moduleid);

        if (systemModuleDTO == null) {
            return JsonViews.generate(-1, "没有对应模块Id:" + moduleid);
        }

        return JsonViews.generate(JsonResultMaps.successMap(systemModuleDTO));
    }

    /**
     * 生成菜单
     *
     * @param moduleName
     * @param menus
     * @return
     */
    @RequestMapping(value = "/sysmodules/{moduelid}/menus", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView createMenu(@PathVariable("moduelid") String moduelid,
                                   @RequestParam("menuname") String menuname, @RequestParam("order") int order) {
        SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
        systemMenuDTO.setMenuName(menuname);
        systemMenuDTO.setOrder(order);

        boolean success = systemModuleService.insertMenu(moduelid, systemMenuDTO);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }


    /**
     * 删除菜单
     *
     * @param moduleId
     * @param menuid
     * @return
     */
    @RequestMapping(value = "/sysmodules/{moduleid}/menus/{menuid}", method = RequestMethod.DELETE, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView deleteMenu(@PathVariable("moduleid") String moduleId, @PathVariable("menuid") String
            menuid) {
        boolean success = systemModuleService.deleteMenu(moduleId, menuid);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }


    /**
     * 更新菜单数据
     *
     * @param moduleId
     * @param menuid
     * @param menuname
     * @param order
     * @return
     */

    @RequestMapping(value = "/sysmodules/{moduleid}/menus/{menuid}", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView updateMenu(@PathVariable("moduleid") String moduleId, @PathVariable("menuid") String
            menuid, @RequestParam(value = "menuname", required = false) String menuname, @RequestParam(value = "order",
            required = false) Integer order, @RequestParam(value = "menuurl", required = false) String menuUrl) {

        if (Strings.isNullOrEmpty(menuname) && order == null) {
            return JsonViews.generate(-1, "参数为空");
        }

        boolean success = systemModuleService.updateMenu(moduleId, menuid, menuname, order, menuUrl);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }


}

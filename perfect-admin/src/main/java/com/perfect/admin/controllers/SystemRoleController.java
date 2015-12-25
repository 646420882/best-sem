package com.perfect.admin.controllers;

import com.google.common.collect.Lists;
import com.perfect.admin.utils.JsonViews;
import com.perfect.admin.utils.SuperUserUtils;
import com.perfect.core.AppContext;
import com.perfect.core.SystemRoleInfo;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import com.perfect.utils.paging.BootStrapPagerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 系统角色控制器
 * <p>
 * 需要判断用户是否是超级管理员
 * Created by yousheng on 15/12/17.
 */
@RestController
public class SystemRoleController {

    @Resource
    private SystemRoleService systemRoleService;

    @RequestMapping(value = "/sysroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BootStrapPagerInfo list(
            @RequestParam(value = "search", required = false) String queryName,
            @RequestParam(value = "super", required = false) Boolean superUser,
            @RequestParam(value = "offset", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "true") String order) {

        boolean isSuper = SuperUserUtils.isLoginSuper();
        BootStrapPagerInfo bootStrapPagerInfo = new BootStrapPagerInfo();

        if (!isSuper) {
            SystemRoleInfo systemRoleInfo = AppContext.getSystemRoleInfo();
            SystemRoleDTO systemUserDTO = systemRoleService.findByUserName(systemRoleInfo.getRoleName());
            bootStrapPagerInfo.setTotal(1);
            bootStrapPagerInfo.setRows(Lists.newArrayList(systemUserDTO));
            return bootStrapPagerInfo;
        }

        if (page < 0 || size < 0) {
            return null;
        }
        Boolean asc = order.equals("asc");
        bootStrapPagerInfo = systemRoleService.listPagable(queryName, superUser, page, size, sort, asc);
        return bootStrapPagerInfo;
    }

    @RequestMapping(value = "/sysroles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView createSystemRole(@RequestBody SystemRoleDTO systemRoleDTO) {


        ModelAndView modelAndView = checkUserSuperAdmin("当前用户没有新增管理员权限");
        if (modelAndView != null) {
            return modelAndView;
        }

        systemRoleService.addSystemRole(systemRoleDTO);
        return JsonViews.generateSuccessNoData();
    }

    @RequestMapping(value = "/sysroles/{roleid}/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateRolePassword(@PathVariable("roleid") String roleid,
                                           @RequestParam(value = "password", required = false, defaultValue = "123456") String password) {

        SystemRoleInfo systemRoleInfo = AppContext.getSystemRoleInfo();

        if (systemRoleInfo == null) {
            return JsonViews.generate(-1, "请先登录.");
        }

        boolean isSuper = systemRoleInfo.isSuper();
        if (!isSuper) {
            // 非超级管理员只能更新自己的密码
            String currentUserId = systemRoleInfo.getRoleId();
            if (!roleid.equals(currentUserId)) {
                return JsonViews.generate(-1, "只能更新当前用户密码.");
            }
        }

        boolean success = systemRoleService.updateRolePassword(roleid, password);

        if (success) {
            return JsonViews.generateSuccessNoData();
        } else {
            return JsonViews.generateFailedNoData();
        }


    }

    @RequestMapping(value = "/sysroles/{roleid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteSystemRole(@PathVariable("roleid") String roleid) {

        ModelAndView modelAndView = checkUserSuperAdmin("当前用户没有删除管理员权限");
        if (modelAndView != null) {
            return modelAndView;
        }


        boolean success = systemRoleService.deleteSystemRole(roleid);
        if (!success) {
            return JsonViews.generateFailedNoData();
        }
        return JsonViews.generateSuccessNoData();
    }

    private ModelAndView checkUserSuperAdmin(String msg) {
        boolean isSuper = SuperUserUtils.isLoginSuper();
        if (!isSuper) {
            return JsonViews.generate(-1, msg);
        }
        return null;
    }

    @RequestMapping(value = "/sysroles/{roleid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateSystemRole(@PathVariable("roleid") String roleid, @RequestBody SystemRoleDTO systemRoleDTO) {

        ModelAndView modelAndView = checkUserSuperAdmin("当前用户没有修改管理员权限");
        if (modelAndView != null) {
            SystemRoleInfo systemRoleInfo=AppContext.getSystemRoleInfo();

            if (systemRoleDTO.getId().equals(systemRoleInfo.getRoleId())) {
                systemRoleService.addSystemRole(systemRoleDTO);
                return JsonViews.generateSuccessNoData();
            }
            return modelAndView;
        }

        systemRoleService.update(roleid, systemRoleDTO);
        return JsonViews.generateSuccessNoData();
    }

}

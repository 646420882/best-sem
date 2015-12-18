package com.perfect.admin.controllers;

import com.google.common.collect.Lists;
import com.perfect.admin.utils.JsonViews;
import com.perfect.admin.utils.SuperUserUtils;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.core.AppContext;
import com.perfect.core.SystemUserInfo;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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
    public ModelAndView list(
            @RequestParam(value = "name", required = false) String queryName,
            @RequestParam(value = "super", required = false) Boolean superUser,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "true") Boolean asc) {

        boolean isSuper = SuperUserUtils.isLoginSuper();

        if (!isSuper) {
            SystemUserInfo systemUserInfo = AppContext.getSystemUserInfo();
            SystemRoleDTO systemUserDTO = systemRoleService.findByUserName(systemUserInfo.getUser());

            return JsonViews.generate(JsonResultMaps.successMap(Lists.newArrayList(systemUserDTO)));
        }

        if (page < 1 || size < 0) {
            return JsonViews.generate(-1, "分页参数错误.");
        }

        List<SystemRoleDTO> systemRoleDTOList = systemRoleService.list(queryName, superUser, page, size, sort, asc);
        return JsonViews.generate(JsonResultMaps.successMap(systemRoleDTOList));
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

    @RequestMapping(value = "/sysroles/{roleid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteSystemRole(@PathVariable("roleid") String roleid) {

        ModelAndView modelAndView = checkUserSuperAdmin("当前用户没有删除管理员权限");
        if (modelAndView != null) {
            return modelAndView;
        }


        boolean success = systemRoleService.deleteSystemRole(roleid);
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
            return modelAndView;
        }

        systemRoleService.update(roleid, systemRoleDTO);
        return JsonViews.generateSuccessNoData();
    }

}

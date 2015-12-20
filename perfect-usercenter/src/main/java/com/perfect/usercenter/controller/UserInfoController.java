package com.perfect.usercenter.controller;

import com.perfect.commons.web.JsonResultMaps;
import com.perfect.core.AppContext;
import com.perfect.core.UserInfo;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.SystemUserService;
import com.perfect.utils.JsonViews;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by yousheng on 15/12/19.
 */
@RestController
public class UserInfoController {

    @Resource
    private SystemUserService systemUserService;

    /**
     * 从SESSION获取用户信息来查询，避免用户越权访问其他用户数据
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView userInfo() {

        UserInfo systemUserInfo = AppContext.getUserInfo();

        if (systemUserInfo == null) {
            return JsonViews.generate(-1, "请登陆系统!");
        }
        SystemUserDTO systemUserDTO = systemUserService.findByUserName(systemUserInfo.getUserName());

        if (systemUserDTO == null) {
            return JsonViews.generate(-1, "用户信息失效,请重新登陆!");
        }

        return JsonViews.generate(JsonResultMaps.successMap(systemUserDTO));
    }

    /**
     * 只能更新基本信息
     *
     * @param userid
     * @param systemUserDTO
     * @return
     */
    @RequestMapping(value = "/user/{userid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateUserBaseInfo(@PathVariable String userid, SystemUserDTO systemUserDTO) {

        UserInfo userInfo = AppContext.getUserInfo();

        if (userInfo == null) {
            return JsonViews.generate(-1, "请登陆系统!");
        }

        SystemUserDTO latest = systemUserService.findByUserId(userid);

        if (latest == null) {
            return JsonViews.generate(-1, "用户信息失效,请重新登陆!");
        }

        //登陆用户ID与更新用户ID不匹配
        if (!latest.getId().equals(userInfo.getUserId())) {
            return JsonViews.generate(-1, "当前用户没有更改该用户的权限!");
        }

        boolean updated = systemUserService.updateUserBaseInfo(userid, systemUserDTO);

        if (updated) {
            return JsonViews.generate(JsonResultMaps.successMap(systemUserDTO));
        }

        return JsonViews.generateFailedNoData();
    }


}

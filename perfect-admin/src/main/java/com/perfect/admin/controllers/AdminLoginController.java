package com.perfect.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yousheng on 15/12/14.
 */
@Controller
public class AdminLoginController {


    public AdminLoginController() {
        System.out.println("true = " + true);
    }

    /**
     * 登陆页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getLoginPage(HttpServletRequest request,
                                     ModelMap model,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "error", required = false) boolean error) {
        if (error) {
//            int badCredentialsNum = CustomUserDetailsService.getPasswdBadCredentialsNum();
//            if (CustomUserDetailsService.isUsernameNotFound()) {
//                if (CustomUserDetailsService.isVerifyNotPass())
//                    model.put("invalidUserName", "正在审核中");
//                else if (CustomUserDetailsService.isForbidden())
//                    model.put("invalidUserName", "帐号已禁用");
//                else
//                    model.put("invalidUserName", "用户名不存在");
//            } else if (badCredentialsNum > 0) {
//                if (badCredentialsNum == 3)
//                    model.put("invalidPassword", "帐号已被锁定");
//                else
//                    model.put("invalidPassword", "密码错误, 剩余" + (3 - badCredentialsNum) + "次");
//            }
        } else {
            model.put("error", "");
        }
        model.put("redirect_url", url);
        return new ModelAndView("/login", model);
    }
}

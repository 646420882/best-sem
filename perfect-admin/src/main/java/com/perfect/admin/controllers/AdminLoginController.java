package com.perfect.admin.controllers;

import com.perfect.admin.filter.AdminPriviledgeFilter;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限中心登陆控制器
 * <p>
 * logout直接在filter中判断
 * Created by yousheng on 15/12/14.
 */
@Controller
public class AdminLoginController {


    @Resource
    private SystemRoleService systemRoleService;

    public AdminLoginController() {
        System.out.println("true = " + true);
    }

    /**
     * 登陆页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public ModelAndView getLoginPage(HttpServletRequest request,
                                     ModelMap model,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "error", required = false) boolean error) {


        return new ModelAndView("loginOrReg/login", model);
    }


    @RequestMapping(value = "/loginaction", method = {RequestMethod.POST})
    public ModelAndView login(HttpServletRequest request,
                              ModelMap model,
                              @RequestParam(value = "url", required = false) String url,
                              @RequestParam(value = "error", required = false) boolean error) {

        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");


        SystemRoleDTO systemRoleDTO = systemRoleService.login(username, password);
        if (systemRoleDTO == null) {

            ModelAndView modelAndView = new ModelAndView("loginOrReg/login");
//            modelAndView.addObject("invalidUserName", "用户名或者密码错误!");

            modelAndView.getModel().put("invalidUserName", "用户名或者密码错误");
            return modelAndView;
        }
        request.getSession().setAttribute(AdminPriviledgeFilter.SESSION_USER, systemRoleDTO);

        return new ModelAndView("redirect:/");
    }

}

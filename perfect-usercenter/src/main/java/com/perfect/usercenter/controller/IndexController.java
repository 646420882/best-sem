package com.perfect.usercenter.controller;

import com.google.common.base.Strings;
import com.perfect.commons.SessionContext;
import com.perfect.commons.constants.UserConstants;
import com.perfect.core.AppContext;
import com.perfect.service.UserAccountService;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created on 2015-12-14.
 *
 * @author dolphineor
 */
@RestController
@Scope("prototype")
public class IndexController {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private UserAccountService userAccountService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/loginOrReg/login");
    }

    @RequestMapping(value = "/platform")
    public ModelAndView platform(HttpServletRequest request, HttpServletResponse response, final ModelMap modelMap) {
        StringBuilder redirect = new StringBuilder();

        Arrays.asList(request.getCookies()).stream().filter(cookie -> cookie.getName().equals(UserConstants.TOKEN_USER)).findFirst().ifPresent((cookie1 -> {
            String token = cookie1.getValue();
            if (Strings.isNullOrEmpty(token)) {
                redirect.append("/login");
            } else {
                modelMap.put(UserConstants.TOKEN_USER, token);
            }
        }));

        return new ModelAndView("/bestPage/bestIndex", modelMap);
    }


    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public void logout(HttpServletResponse response) {
        Cookie cookies = new Cookie("userToken", null);
        cookies.setMaxAge(0);
        response.addCookie(cookies);
    }

    @RequestMapping(value = "/toUserCenter", method = RequestMethod.GET)
    public void toUserCenter() {
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView reg() {
        return new ModelAndView("/loginOrReg/register");
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView account(HttpServletRequest request, ModelMap modelMap) {
        // 获取搜客模块的使用期限
        long[] serviceLife = userAccountService.getSemModuleServiceLife(SessionContext.getUser(request).getUserName());
        modelMap.put("bestSemStartTime", DATE_FORMAT.format(new Date(serviceLife[0])));
        modelMap.put("bestSemEndTime", DATE_FORMAT.format(new Date(serviceLife[1])));
        return new ModelAndView("/account/account").addAllObjects(modelMap);
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ModelAndView password() {
        return new ModelAndView("/password/password");
    }

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public ModelAndView forget() {
        return new ModelAndView("/password/forget");
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView reset(HttpServletRequest request,
                              ModelMap model) {
        String userId = request.getParameter("u");
        String userTokenId = request.getParameter("t");
        Jedis jc = JRedisUtils.get();
        try {
            boolean jedisKey = jc.exists(userTokenId);
            if (jedisKey) {
                model.put("userid", userId);
                model.put("userToken", userTokenId);
                return new ModelAndView("/password/reset", model);
            } else {
                model.put("invalidMsg", "验证连接已失效,请重新验证！");
                return new ModelAndView("/password/forget", model);
            }
        } finally {
            jc.close();
        }
    }

    @RequestMapping(value = "/safetyTool", method = RequestMethod.GET)
    public ModelAndView safetyTool(ModelMap modelMap) {
        String email = userAccountService.getUserEmail(AppContext.getUserInfo().getUserName());
        modelMap.put("userEmail", email);
        return new ModelAndView("/safe/safetool").addAllObjects(modelMap);
    }
}



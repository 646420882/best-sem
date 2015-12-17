package com.perfect.usercenter.controller;

import com.perfect.commons.CustomUserDetails;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.entity.sys.SystemUserEntity;
import com.perfect.service.SystemUserService;
import com.perfect.utils.MD5;
import com.perfect.utils.json.JSONUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by subdong on 15-12-16.
 */
@RestController
@Scope("prototype")
public class LoginController {

    protected Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private SystemUserService systemUserService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "j_username") String account,
                              @RequestParam(value = "j_password") String pwd,
                              ModelMap model) {

        String a = response.getHeader("redirect");
        SystemUserDTO systemUserDTO = systemUserService.getSystemUser(account);
        if (systemUserDTO != null) {
            MD5.Builder md5Builder = new MD5.Builder();
            MD5 md5 = md5Builder.password(pwd).salt(account).build();
            String pwdKey = md5.getMD5();

            if (systemUserDTO.getAccountState() == 0) {
                model.put("invalidUserName", "帐号已禁用");
            } else if (systemUserDTO.getState() == 0) {
                model.put("invalidUserName", "正在审核中");
            }

            if (account.equals(systemUserDTO.getUserName()) && pwdKey.equals(systemUserDTO.getPassword())) {
                ModelAndView view = new ModelAndView("/bestPage/bestIndex");
                //登录成功
                loginSuccessHandler(request, response, systemUserDTO, view);
                return view;
            } else {
                int s = loginFailureHandler(request, response, systemUserDTO);
                if (s == 3) {
                    model.put("invalidPassword", "帐号已被锁定");
                } else if (s == -1) {
                    model.put("invalidPassword", "登录错误");
                } else {
                    model.put("invalidPassword", "密码错误, 剩余" + (3 - s) + "次");
                }
            }
        } else {
            model.put("invalidUserName", "用户名不存在");
        }

        return new ModelAndView("/loginOrReg/login", model);
    }


    private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, SystemUserDTO systemUserDTO, ModelAndView modelAndView) {

        String url = request.getParameter("redirect");
        if (url.equals("") || url == null) {
            url = request.getParameter("url");
        }

        String uuid = UUID.randomUUID().toString();
        //systemUserDTO.setImg(null);
        systemUserDTO.setImg(null);
        systemUserDTO.setImgBytes(null);
        String json = JSONUtils.getJsonString(systemUserDTO);
        Jedis jedis = JRedisUtils.get();
        jedis.setex(uuid, 60, json);

        if (url != null && !url.isEmpty() && !url.equals("null")) {
            String target;
            if (url.lastIndexOf("/") != -1) {
                target = "http://" + url + "token?tokenid=" + uuid;
            } else {
                target = "http://" + url + "/token?tokenid=" + uuid;
            }

            try {
                response.sendRedirect(target);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
            return;
        }

        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.password(systemUserDTO.getUserName()).salt("passwd").build();
        String pwdKey = md5.getMD5();

        try {
            jedis = JRedisUtils.get();
            if (jedis.exists(pwdKey)) {
                Integer value = Integer.valueOf(jedis.get(pwdKey));
                if (value == 3) {
                    response.sendRedirect("/login");
                    return;
                } else {
                    jedis.expire(pwdKey, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to /platformPage");
            return;
        }
        Cookie cookie = new Cookie("userToken", uuid);
        cookie.setMaxAge(30 * 60 * 60);
        response.addCookie(cookie);
        request.getSession().setAttribute("user", systemUserDTO);

        // token settings
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("userToken", uuid);
    }


    private int loginFailureHandler(HttpServletRequest request, HttpServletResponse response, SystemUserDTO systemUserDTO) {
        String redirect = request.getParameter("redirect");
        if (redirect != null && !redirect.isEmpty()) {
            try {
                response.sendRedirect("/login?error=true&url=" + redirect);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (systemUserDTO != null) {
                //密码验证失败
                MD5.Builder md5Builder = new MD5.Builder();
                MD5 md5 = md5Builder.password(systemUserDTO.getUserName()).salt("passwd").build();
                String key = md5.getMD5();
                Jedis jedis = null;
                try {
                    jedis = JRedisUtils.get();
                    if (!jedis.exists(key)) {
                        jedis.incr(key);
                        jedis.expire(key, 10800);
                        return 1;
                    } else {
                        Integer oldValue = Integer.valueOf(jedis.get(key));
                        if (oldValue < 3) {
                            Integer newValue = oldValue + 1;
                            jedis.incr(key);
                            jedis.expire(key, 10800);
                            return newValue;
                        }
                        if (oldValue == 3) {
                            return 3;
                        }
                    }
                } finally {
                    if (jedis != null) {
                        JRedisUtils.returnJedis(jedis);
                    }
                }
            }
        }
        return -1;
    }
}

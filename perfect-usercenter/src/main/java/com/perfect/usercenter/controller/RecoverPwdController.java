package com.perfect.usercenter.controller;

import com.perfect.commons.deduplication.Md5Helper;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.SystemUserService;
import com.perfect.utils.email.EmailUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by subdong on 15-12-30.
 */
@RestController
@Scope("prototype")
public class RecoverPwdController {

    @Resource
    private SystemUserService systemUserService;

    /**
     * 密码重置  邮箱验证
     */
    @RequestMapping(value = "/recoverPwd", method = RequestMethod.POST)
    public ModelAndView recoverPwd(HttpServletRequest request,
                                   @RequestParam(value = "username", required = true) String username,
                                   @RequestParam(value = "email", required = true) String email,
                                   ModelMap model) {

        SystemUserDTO byUserName = systemUserService.findByUserName(username);

        if (Objects.isNull(byUserName)) {
            model.put("invalidMsg", "用户不存在");
            return new ModelAndView("/password/forget", model);
        }
        if (!byUserName.getEmail().equals(email)) {
            model.put("invalidMsg", "输入邮箱不正确");
            return new ModelAndView("/password/forget", model);
        }

        String md5 = Md5Helper.MD5.getMD5(username);
        Jedis jc = JRedisUtils.get();
        try {
            jc.set(md5, "");
            jc.expire(md5, 600);
        } finally {
            jc.close();
        }

        String uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String html = "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\"> </head>" +
                "<body> <div> <span>此验证码10分钟内有效</span> </br> <span>" + uri + "/reset?u=" + byUserName.getId() + "&t=" + md5 + "</span> </div> </body> </html>";


        EmailUtils.sendHtmlEmail("百思平台密码修改验证", html, byUserName.getEmail());

        model.put("invalidMsg", "密码修改验证已发送到你的邮箱当中！请进入" + byUserName.getEmail() + "邮箱查看");
        return new ModelAndView("/password/forget", model);
    }

    /**
     * 密码重置  密码修改
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public ModelAndView resetPwd(HttpServletRequest request,
                                 @RequestParam(value = "pwd", required = true) String pwd,
                                 @RequestParam(value = "userid", required = true) String userId,
                                 ModelMap model) {

        boolean b = systemUserService.updateUserPassword(userId, pwd);
        if (b) {
            model.put("resetsMsg", "OK");
        } else {
            model.put("resetsMsg", "fild");
        }

        return new ModelAndView("/password/reset", model);
    }
}

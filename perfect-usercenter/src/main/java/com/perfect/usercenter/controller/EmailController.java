package com.perfect.usercenter.controller;

import com.perfect.usercenter.email.EmailHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2015-12-18.
 *
 * @author dolphineor
 */
@RestController
@RequestMapping("/email")
@Scope("prototype")
public class EmailController {

    private static String captchaHtmlTemplate = "";

    @RequestMapping(value = "/sendCaptcha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmailCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        // TODO 生成验证码
        EmailHelper.sendHtmlEmail("", "", email);
    }
}

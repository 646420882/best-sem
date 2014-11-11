package com.perfect.app.bdlogin.controller;

import com.perfect.app.bdlogin.core.BaiduHttpLogin;
import com.perfect.app.bdlogin.core.CaptchaHandler;
import com.perfect.entity.CookieEntity;
import com.perfect.service.CookieService;
import com.perfect.utils.web.ServletContextUtils;
import org.apache.http.client.CookieStore;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baizz on 2014-11-10.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin")
public class BaiduHttpLoginController implements Controller {

    @Resource
    private CookieService cookieService;

    @Override
    @RequestMapping(value = "/bdLogin/checkImageCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String imageCode = request.getParameter("code");

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, String> map = new HashMap<>();

        String sessionId = ServletContextUtils.getSession().getId();
        String cookies = ServletContextUtils.getSession().getAttribute(sessionId + "bdLogin").toString();
        boolean isSuccess = cookieService.simulateLogin("baidu-bjtthunbohui2134115", "Bjhunbohui7", imageCode, cookies);
        if (isSuccess) {
            map.put("status", "success");

            CookieStore cookieStore = BaiduHttpLogin.getSSLCookies();
            CookieEntity cookieEntity = new CookieEntity();
            cookieEntity.setCookie(cookieStore);
            cookieEntity.setIdle(true);
            cookieService.saveCookie(cookieEntity);

            ServletContextUtils.getSession().removeAttribute(sessionId + "bdLogin");
        } else {
            map.put("status", "fail");
        }

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/bdLogin/getCaptcha", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaHandler.handle(BaiduHttpLogin.phantomJSPath);
        byte[] captchaBytes = CaptchaHandler.getCaptchaBytes();
        String cookies = CaptchaHandler.getCookies();
        String sessionId = ServletContextUtils.getSession().getId();
        ServletContextUtils.getSession().setAttribute(sessionId + "bdLogin", cookies);
        if (captchaBytes != null) {
            response.getOutputStream().write(captchaBytes);
        }
    }
}

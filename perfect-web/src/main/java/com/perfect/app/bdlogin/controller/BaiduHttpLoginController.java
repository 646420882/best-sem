package com.perfect.app.bdlogin.controller;

import com.perfect.commons.bdlogin.BaiduHttpLogin;
import com.perfect.commons.bdlogin.CaptchaHandler;
import com.perfect.commons.web.ServletContextUtils;
import com.perfect.dto.CookieDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.AccountManageService;
import com.perfect.service.CookieService;
import com.perfect.utils.json.JSONUtils;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.perfect.commons.bdlogin.BaiduHttpLogin.getBaiduLoginJSPath;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-29 refactor
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin")
@SuppressWarnings("unchecked")
public class BaiduHttpLoginController implements Controller {

    @Resource
    private AccountManageService accountManageService;

    @Resource
    private CookieService cookieService;

    @Override
    @RequestMapping(value = "/bdLogin/checkImageCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer number = Integer.valueOf(request.getParameter("number"));
        String imageCode = request.getParameter("code");

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        if (number > 0) {
            HttpSession session = ServletContextUtils.getSession();
            BaiduAccountInfoDTO dto;
            if (session.getAttribute(session.getId() + "-baiduAccountInfo") == null) {
                List<BaiduAccountInfoDTO> list = accountManageService.getAllBaiduAccount();
                int index = list.size() - 1;
                dto = list.get(index);
                list.remove(index);
                session.setAttribute(session.getId() + "-baiduAccountInfo", list);
            } else {
                List<BaiduAccountInfoDTO> list = (List<BaiduAccountInfoDTO>) session.getAttribute(session.getId() + "-baiduAccountInfo");
                if (list.isEmpty()) {
                    map.put("number", number);
                    map.put("status", "fail");
                    jsonView.setAttributesMap(map);
                    return new ModelAndView(jsonView);
                }
                int index = list.size() - 1;
                dto = list.get(index);
                list.remove(index);
                session.setAttribute(session.getId() + "-baiduAccountInfo", list);
            }

            String cookies = session.getAttribute(session.getId() + "-bdLogin").toString();
            boolean isSuccess = BaiduHttpLogin.execute(dto.getBaiduUserName(), dto.getBaiduPassword(), imageCode, cookies);
            if (isSuccess) {
                number--;
                session.setAttribute(session.getId() + "-number", number);
                map.put("status", "success");
                map.put("number", number);

                CookieStore cookieStore = BaiduHttpLogin.getSSLCookies();
                CookieDTO cookieDTO = new CookieDTO();
                cookieDTO.setCookie(JSONUtils.getJsonString(cookieStore));
                cookieDTO.setIdle(true);
                cookieService.saveCookie(cookieDTO);

                session.removeAttribute(session.getId() + "-bdLogin");
            } else {
                List<BaiduAccountInfoDTO> list = (List<BaiduAccountInfoDTO>) session.getAttribute(session.getId() + "-baiduAccountInfo");
                list.add(dto);
                session.setAttribute(session.getId() + "-baiduAccountInfo", list);
                map.put("status", "fail");
            }
        }

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/bdLogin/getCaptcha", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaHandler.handle(getBaiduLoginJSPath());
        byte[] captchaBytes = CaptchaHandler.getCaptchaBytes();
        String cookies = CaptchaHandler.getCookies();
        HttpSession session = ServletContextUtils.getSession();
        session.setAttribute(session.getId() + "-bdLogin", cookies);
        if (captchaBytes != null) {
            response.getOutputStream().write(captchaBytes);
        }
    }
}

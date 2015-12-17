package com.perfect.app.bdlogin.controller;

import com.perfect.commons.bdlogin.BaiduHttpLoginHandler;
import com.perfect.commons.bdlogin.BaiduSearchPageUtils;
import com.perfect.commons.bdlogin.CaptchaHandler;
import com.perfect.web.suport.ServletContextUtils;
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
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.perfect.commons.bdlogin.BaiduHttpLoginHandler.getBaiduLoginJSPath;

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

    @Resource
    private BaiduHttpLoginHandler baiduLoginHandler;

    @Resource
    private CaptchaHandler captchaHandler;


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
            boolean isSuccess = baiduLoginHandler.execute(dto.getBaiduUserName(), dto.getBaiduPassword(), imageCode, cookies);
            if (isSuccess) {
                number--;
                session.setAttribute(session.getId() + "-number", number);
                map.put("status", "success");
                map.put("number", number);

                CookieStore cookieStore = BaiduHttpLoginHandler.getSSLCookies();
                CookieDTO cookieDTO = new CookieDTO();
                cookieDTO.setCookie(JSONUtils.getJsonString(cookieStore));
                cookieDTO.setCastk(baiduLoginHandler.getCastk());
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
        captchaHandler.handle(getBaiduLoginJSPath());
        byte[] captchaBytes = captchaHandler.getCaptchaBytes();
        HttpSession session = ServletContextUtils.getSession();
        session.removeAttribute("imgError");
        if (captchaBytes.length == 0) {
            session.setAttribute("imgError", captchaHandler.getExceptionMsg());
            return;
        }

        String cookies = captchaHandler.getCaptchaCookies();
        session.setAttribute(session.getId() + "-bdLogin", cookies);
        response.getOutputStream().write(captchaBytes);
    }

    @RequestMapping(value = "/bdLogin/saveCurl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView saveCurl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String curl = request.getParameter("curl");
        curl = curl.replace("test", "%KEYWORD%").replace("%3A0%2C%22pageNo", "%3A%AREA_ID%%2C%22pageNo");
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        CookieDTO cookieDTO = new CookieDTO();
        cookieDTO.setCookie(curl);
        cookieDTO.setIdle(true);
        cookieService.saveCookie(cookieDTO);
        map.put("status", "success");
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/bdLogin/heartbeat", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void cookieHeartbeat() {
        String[] arr = {"雪地靴", "冬装", "时尚女包", "旅游攻略", "贷款", "风衣", "打底裤", "男鞋品牌"};

        while (true) {
            CookieDTO cookieDTO = cookieService.takeOne();
            if (cookieDTO == null) {
                break;
            } else {
                Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
                    String keyword = arr[new Random().nextInt(8)];
                    String html = BaiduSearchPageUtils.getBaiduSearchPage(cookieDTO.getCookie(), keyword, 1000);
                    if (html.length() > 200) {
                        System.out.println(Thread.currentThread().getName() + ". keyword: " + keyword + ", cookieId: " + cookieDTO.getId() + ", html length: " + html.length() + ", heartbeat time: " + Instant.now().atZone(ZoneId.of("Asia/Shanghai")));
                    } else if (html.length() == 185) {
                        System.out.println(cookieDTO.getId() + "-cookie expired!");
                        cookieService.delete(cookieDTO.getId());
                    } else {
                        System.out.println("sleep : " + cookieDTO.getId());
                        sleep(1);
                    }
                }, 0, 5, TimeUnit.SECONDS);

                sleep(5);
            }
        }
    }

    private void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

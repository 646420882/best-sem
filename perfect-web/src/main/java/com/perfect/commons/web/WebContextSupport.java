package com.perfect.commons.web;


import com.perfect.dto.web.MessageResultDTO;
import com.perfect.utils.json.JSONUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/7/28.
 * 2014-11-28 refactor
 */
public class WebContextSupport   {
    public static final String SUCCESS = "1";//成功
    public static final String FAIL = "0";//失败
    public static final String EXCEPTION = "3";//异常;
    public static final String NOLOGIN = "4";//未登陆;

    public void writeHtml(String html, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(html);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeObject(Object obj, HttpServletResponse res) {
        writeJson(obj, res);
    }

    public void writeJson(Object obj, HttpServletResponse res) {
        writeHtml(JSONUtils.getJsonObject(obj).toString(), res);
    }
    public void writeData(Object message, HttpServletResponse response, Object data) {
        writeJson(new MessageResultDTO(message, data), response);
    }

//    protected ModelAndView writeJson(String key, Object obj) {
//        AbstractView jsonView = new MappingJackson2JsonView();
//        Map<String, Object> returnMap = new HashMap<String, Object>() {{
//            put(key, JSONUtils.getJsonObject(obj));
//        }};
//        jsonView.setAttributesMap(returnMap);
//        return new ModelAndView(jsonView);
//    }
}

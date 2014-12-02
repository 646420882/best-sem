package com.perfect.commons.web;


import com.perfect.dto.web.MessageResultDTO;
import com.perfect.utils.json.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XiaoWei on 2014/7/28.
 * 2014-11-28 refactor
 */
public class WebContextSupport implements WebContext {
    public static final String SUCCESS = "1";//成功
    public static final String FAIL = "0";//失败
    public static final String EXCEPTION = "3";//异常;
    public static final String NOLOGIN = "4";//未登陆;

    @Override
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

    @Override
    public void writeObject(Object obj, HttpServletResponse res) {
        writeObject(obj, res);
    }

    @Override
    public void writeJson(Object obj, HttpServletResponse res) {
        writeHtml(JSONUtils.getJsonObject(obj).toString(), res);
    }


    @Override
    public void writeData(Object message, HttpServletResponse response, Object data) {
        writeJson(new MessageResultDTO(message, data), response);
    }
}

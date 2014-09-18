package com.perfect.utils.web;


import com.perfect.utils.JSONUtils;
import com.perfect.utils.web.dto.MessageResult;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XiaoWei on 2014/7/28.
 */
@Repository(value = "webContext")
public class WebContextSupport implements WebContext {
    public static final String SUCCESS = "1";
    public static final String FAIL = "0";
    public static final String EXCEPTION = "3";
    public static final String NOLOGIN = "4";

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
        writeJson(new MessageResult(message, data), response);
    }
}

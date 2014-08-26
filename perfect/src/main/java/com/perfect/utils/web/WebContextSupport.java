package com.perfect.utils.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.utils.JSONUtils;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XiaoWei on 2014/7/28.
 */
@Repository(value = "webContext")
public class WebContextSupport implements WebContext {
    public static final String SUCCESS="1";
   public static final String FAIL="0";
    public static final String EXCEPTION="3";
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
        ObjectMapper objectMapper = new ObjectMapper();
            writeHtml(JSONUtils.getJsonObject(obj).toString(), res);
    }
}

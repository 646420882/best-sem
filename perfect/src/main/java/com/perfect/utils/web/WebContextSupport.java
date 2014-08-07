package com.perfect.utils.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XiaoWei on 2014/7/28.
 */
@Repository(value = "webContext")
public class WebContextSupport implements WebContext {
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
    public void wirteJson(Object obj, HttpServletResponse res) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            writeHtml(objectMapper.writeValueAsString(obj), res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

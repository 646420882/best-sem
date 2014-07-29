package com.perfect.utils.ajax;

import net.sf.json.JSONSerializer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XiaoWei on 2014/7/25.
 */
public class WebContexSupport implements WebContext {
    @Override
    public void writeHtml(String html,HttpServletResponse response) {
        try
        {

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
    public void writeObject(Object obj,HttpServletResponse res) {
        wirteJson(obj,res);

    }

    @Override
    public void wirteJson(Object obj,HttpServletResponse res) {
        writeHtml(JSONSerializer.toJSON(obj).toString(),res);
    }
}

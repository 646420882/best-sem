package com.perfect.utils.ajax;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
 * Created by XiaoWei on 2014/7/25.
 */
public interface WebContext {
    /**
     * 写出Html对象
     * @param html
     */
    public void writeHtml(String html,HttpServletResponse response);

    /**
     * 写出Object对象
     * @param obj
     */
    public void writeObject(Object obj,HttpServletResponse response);

    /**
     * 写出 json对象
     * @param obj
     */
    public void wirteJson(Object obj,HttpServletResponse response);
}

package com.perfect.utils.web;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by XiaoWei on 2014/7/28.
 */
public interface WebContext {

    /**
     * @param html     返回页面的字符串
     * @param response
     */
    public void writeHtml(String html, HttpServletResponse response);

    /**
     * @param obj 返回Object 对象
     * @param res
     */
    public void writeObject(Object obj, HttpServletResponse res);

    /**
     * @param obj 返回Json对象
     * @param res
     */
    public void writeJson(Object obj, HttpServletResponse res);

    /**
     * 返回html消息并带数据
     * @param response
     * @param data
     */
    public void writeData(Object message,HttpServletResponse response, Object data);


}

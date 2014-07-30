package com.perfect.utils.web;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by XiaoWei on 2014/7/28.
 */
public interface WebContext {

    /**
     *
     * @param html 返回页面的字符串
     * @param response
     */
        public void writeHtml(String html,HttpServletResponse response) ;

    /**
     *
     * @param obj 返回Object 对象
     * @param res
     */
        public void writeObject(Object obj,HttpServletResponse res);

    /**
     *
     * @param obj 返回Json对象
     * @param res
     */
        public void wirteJson(Object obj,HttpServletResponse res);



}

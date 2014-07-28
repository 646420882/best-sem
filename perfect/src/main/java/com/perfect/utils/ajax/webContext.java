package com.perfect.utils.ajax;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by XiaoWei on 2014/7/28.
 */
public interface WebContext {

    public void writeHtml(String html,HttpServletResponse response) ;

    public void writeObject(Object obj,HttpServletResponse res);

    public void wirteJson(Object obj,HttpServletResponse res);

}

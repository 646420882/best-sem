package com.perfect.app.batchUpload.controller;

import com.perfect.utils.web.WebContextSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by XiaoWei on 2014/8/4.
 */
@RestController
@Scope("prototype")
public class uploadManager extends WebContextSupport{
    /**
     * 跳转至批量上传页面
     * @return
     */
    @RequestMapping(value = "/upload/uploadIndex")
    public ModelAndView convertUploadPage(){
        return new ModelAndView("upload/uploadMain");
    }
}

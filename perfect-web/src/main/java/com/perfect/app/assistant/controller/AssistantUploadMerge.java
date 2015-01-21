package com.perfect.app.assistant.controller;

import com.perfect.commons.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by XiaoWei on 2015/1/19.
 */
@Controller
@RequestMapping(value = "/uploadMerge")
public class AssistantUploadMerge extends WebContextSupport {

    @RequestMapping(value = "/upload")
    public ModelAndView uploadMerge(){

        return writeMapObject("msg",SUCCESS);
    }

}

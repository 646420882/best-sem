package com.perfect.app.assistant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by john on 2015/12/4.
 */
@RestController
@RequestMapping("/assistant")
public class AssistantOperationLogController {

    @RequestMapping("/operationLog")
    public ModelAndView toOperationLogPage() {
        return new ModelAndView("log/operationlog");
    }
}

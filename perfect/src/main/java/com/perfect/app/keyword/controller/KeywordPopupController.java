package com.perfect.app.keyword.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by baizz on 2014-9-1.
 */
@RestController
@Scope("prototype")
public class KeywordPopupController {

    @RequestMapping(value = "/toAddPage", method = RequestMethod.GET)
    public ModelAndView getAddKeywordIframePage() {
        return new ModelAndView("popup/keyword/addKeyword");
    }

    @RequestMapping(value = "/addplan", method = RequestMethod.GET)
    public ModelAndView getAddKeywordIframePage1() {
        return new ModelAndView("popup/keyword/addplan");
    }

    @RequestMapping(value = "/newkeyword", method = RequestMethod.GET)
    public ModelAndView toNewKeywordIframePage() {
        return new ModelAndView("popup/keyword/newkeyword");
    }

    @RequestMapping(value = "/deletekeyword", method = RequestMethod.GET)
    public ModelAndView todeleteKeywordIframePage() {
        return new ModelAndView("popup/keyword/deletekeyword");
    }

    @RequestMapping(value = "/searchword", method = RequestMethod.GET)
    public ModelAndView toSearchWordIframePage() {
        return new ModelAndView("popup/keyword/searchword");
    }

}

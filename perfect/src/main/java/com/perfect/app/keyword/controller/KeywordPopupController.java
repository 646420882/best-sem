package com.perfect.app.keyword.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "/toAddPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAddKeywordIframePage() {
        return new ModelAndView("popup/keyword/addKeyword");
    }

    @RequestMapping(value = "/addplan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAddKeywordIframePage1() {
        return new ModelAndView("popup/keyword/addplan");
    }

    @RequestMapping(value = "/newkeyword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView toNewKeywordIframePage
            () {
        return new ModelAndView("popup/keyword/newkeyword");
    }

    /*@RequestMapping(value = "/toAddPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAddKeywordIframePage2() {
        return new ModelAndView("popup/keyword/addKeyword");
    }

    @RequestMapping(value = "/toAddPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAddKeywordIframePage3() {
        return new ModelAndView("popup/keyword/addKeyword");
    }

    @RequestMapping(value = "/toAddPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAddKeywordIframePage4() {
        return new ModelAndView("popup/keyword/addKeyword");*/

}

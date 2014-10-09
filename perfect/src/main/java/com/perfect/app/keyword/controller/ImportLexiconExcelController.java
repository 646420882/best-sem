package com.perfect.app.keyword.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

/**
 * Created by baizz on 2014-10-9.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/lexicon")
public class ImportLexiconExcelController {

    @RequestMapping(value = "/import", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView importLexicon() {
        return new ModelAndView();
    }

    @RequestMapping(value = "/delete/{trade}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteLexicon(@PathVariable("trade") String trade) throws UnsupportedEncodingException {
        trade = java.net.URLDecoder.decode(trade, "UTF-8");
        return new ModelAndView();
    }
}

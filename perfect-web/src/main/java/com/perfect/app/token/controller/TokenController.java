package com.perfect.app.token.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2015-12-17.
 *
 * @author dolphineor
 */
@RestController
@Scope("prototype")
public class TokenController {

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        // Do nothing
    }
}

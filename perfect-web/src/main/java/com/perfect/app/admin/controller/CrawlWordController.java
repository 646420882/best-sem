package com.perfect.app.admin.controller;

import com.perfect.commons.crawl.CrawlURLHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baizz on 2014-12-4.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin")
public class CrawlWordController {

    @RequestMapping(value = "/crawl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void handleRequest(@RequestBody String[] sites) {
        CrawlURLHandler.build().setSites(sites).run();
    }
}

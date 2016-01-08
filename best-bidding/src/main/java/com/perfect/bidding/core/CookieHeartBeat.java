package com.perfect.bidding.core;

import com.perfect.commons.bdlogin.BaiduSearchPageUtils;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dto.CookieDTO;
import com.perfect.service.CookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by vbzer_000 on 2014/9/25.
 */
public class CookieHeartBeat {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieHeartBeat.class);

    private static final int COOKIE_HEARTBEAT_WORKER = 2;


    public void start() {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Cookie heartbeat started...");
        }

        CookieService cookieService = (CookieService) ApplicationContextHelper.getBeanByName("cookieService");
        Executors.newScheduledThreadPool(COOKIE_HEARTBEAT_WORKER).scheduleWithFixedDelay(() -> {
            CookieDTO cookieDTO = cookieService.takeOne();

            if (cookieDTO != null) {
                String html = BaiduSearchPageUtils.getBaiduSearchPage(cookieDTO.getCookie(), "test", 1000);
                if (html.length() < 200)    // cookie expired
                    cookieService.delete(cookieDTO.getId());
                else
                    cookieService.returnOne(cookieDTO.getId());
            }

        }, 0, 15, TimeUnit.SECONDS);

    }
}

package com.perfect.web.auth;

import com.google.common.collect.Maps;
import com.perfect.commons.constants.AuthConstants;
import com.perfect.utils.http.HttpClientUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created on 2015-12-09.
 * <p>Token心跳设置
 *
 * @author dolphineor
 */
public class TokenHeartBeat implements Runnable, AuthConstants {

    private final HttpServletRequest request;

    private TokenHeartBeat(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        Thread t = new Thread(this);
        try {
            if (Optional.ofNullable(request.getCookies()).isPresent()) {
                Optional<Cookie> cookieOptional = Arrays.stream(request.getCookies())
                        .filter(c -> Objects.equals(TOKEN, c.getName()))
                        .findFirst();

                if (cookieOptional.isPresent()) {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put("token", cookieOptional.get().getValue());

                    HttpClientUtils.postRequest(TOKEN_HEART_BEAT_URL, params);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        t.start();
    }

    public static void refreshToken(HttpServletRequest request) {
        new TokenHeartBeat(request).run();
    }
}

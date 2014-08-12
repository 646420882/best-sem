/**
 * JsonProxy.java
 *
 * Copyright 2011 Baidu, Inc.
 *
 * Baidu licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.perfect.autosdk.core;

import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.util.PrintUtil;
import com.perfect.redis.JRedisUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author @author@ (@author-email@)
 * @version @version@, $Date: 2011-5-10$
 */
public class JsonProxy<I> implements InvocationHandler {
    protected static final Log log = LogFactory.getLog(JsonProxy.class);

    private Class<I> interfaces;
    private CommonService service;

    /**
     * @param interfaces
     */
    public JsonProxy(Class<I> interfaces, CommonService service) {
        super();
        this.interfaces = interfaces;
        this.service = service;
    }

    /**
     * Create the proxy instance of api client stub. Proxied by JsonProxy.
     *
     * @param <T>        The proxy instannce type.
     * @param interfaces The proxy instannce type class.
     * @param service    The original object.
     * @return The proxied object.
     * @throws ApiException
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaces, CommonService service) throws ApiException {
        JsonProxy<T> proxy = new JsonProxy<T>(interfaces, service);
        service.generateHeader();
        return (T) Proxy.newProxyInstance(JsonProxy.class.getClassLoader(), new Class<?>[]{interfaces},
                proxy);
    }

    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String addr = service.serverUrl + AddressUtil.getJsonAddr(interfaces) + '/' + method.getName();
        log.info("Current Calling URL: " + addr);
        PrintUtil.println("Current Calling URL: " + addr);
        JsonConnection conn = new GZIPJsonConnection(addr);
        conn.setConnectTimeout(service.connectTimeoutMills);
        conn.setReadTimeout(service.readTimeoutMills);
        conn.sendRequest(makeRequest(args[0]));
        // JRedis example
        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            long v = jedis.incr("PEI_E");
            if (v == 1)
                jedis.expire("PEI_E", 7 * 24 * 60 * 60);
        } catch (Exception e) {

        } finally {
            JRedisUtils.returnJedis(jedis);
        }

        JsonEnvelop<ResHeader, ?> response = conn.readResponse(ResHeader.class, method.getReturnType());
        ResHeaderUtil.resHeader.set(response.getHeader());
        return response.getBody();
    }

    private <K> JsonEnvelop<?, ?> makeRequest(Object args) {
        JsonEnvelop<AuthHeader, Object> body = new JsonEnvelop<AuthHeader, Object>();
        body.setHeader(service.authHeader);
        body.setBody(args);
        return body;
    }


}

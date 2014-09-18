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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
        if (log.isDebugEnabled())
            log.debug("Current Calling URL: " + addr);

        JsonConnection conn = new GZIPJsonConnection(addr);
        conn.setConnectTimeout(service.connectTimeoutMills);
        conn.setReadTimeout(service.readTimeoutMills);
        JsonEnvelop request = makeRequest(args[0]);
        conn.sendRequest(request);
        JsonEnvelop<ResHeader, ?> response = conn.readResponse(ResHeader.class, method.getReturnType());
        ResHeader resHeader = response.getHeader();
        if (!resHeader.getFailures().isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("Call Error: Head info = " + resHeader + "\n" +
                        "account info = " + service.getUsername() + "\n" +
                        "request info = " + addr + "\n" +
                        "request param = " + args[0]
                );
            }


        }
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

/**
 * JsonConnection.java
 * <p>
 * Copyright 2011 Baidu, Inc.
 * <p>
 * Baidu licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.perfect.autosdk.core;

import com.perfect.autosdk.exception.ApiException;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonConnection {

    private HttpURLConnection connection;
    private int connectTimeout = 30000; // 默认连接超时30秒
    private int readTimeout = 60000; // 默认连接超时60秒
    private int retry = 3;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * @param url The request URL
     * @throws IOException
     * @throws MalformedURLException
     */
    public JsonConnection(String url) throws MalformedURLException, IOException {
        super();
        connection = (HttpURLConnection) new URL(url).openConnection();
    }

    /**
     * 向服务器发送信息
     *
     * @return
     * @throws ApiException
     */

    protected OutputStream sendRequest() throws ApiException {
        OutputStream out = null;
        try {
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/json; charset=utf-8");

            connection.connect();
            out = connection.getOutputStream();
            return out;
        } catch (IOException e) {
            connection.disconnect();
            throw new ApiException(e);
        } catch (Exception e) {
            connection.disconnect();
            throw new ApiException(e);
        }
    }


    /**
     * 向服务器发送信息
     *
     * @param body 向服务器发送的信封对象
     * @throws ApiException
     */
    public void sendRequest(JsonEnvelop<?, ?> body) throws ApiException {
        OutputStream out = sendRequest();
        try {
            JacksonUtil.writeObj(out, body);
        } catch (Exception e) {
            throw new ApiException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new ApiException(e);
                }
            }
        }

    }

    /**
     * 读取服务器返回的信息
     *
     * @return 读取到的数据
     * @throws ApiException
     */
    protected InputStream readResponse() throws ApiException {
        InputStream in = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = connection.getInputStream();
            } else {
                if (connection.getErrorStream() != null) {
                    in = connection.getErrorStream();
                } else {
                    in = connection.getInputStream();
                }
            }
            return in;
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * 读取服务器返回的信息
     *
     * @param <T> 返回头类型。
     * @param <K> 返回body类型。
     * @param t   返回头类型的class
     * @param k   返回body类型的class
     * @return 服务器返回的信封
     * @throws ApiException
     */
    public <T, K> JsonEnvelop<T, K> readResponse(Class<T> t, Class<K> k) throws ApiException {
        InputStream in = readResponse();
        try {
            TypeFactory instance = TypeFactory.defaultInstance();
            return JacksonUtil.readObj(in, instance.constructParametricType(JsonEnvelop.class, t, k));
        } catch (IOException e) {
            throw new ApiException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new ApiException(e);
                }
            }
        }
    }

    /**
     * @param <T>
     * @param t
     * @return
     * @throws ApiException
     * @description 读取服务器返回的信息，转换为类型 T
     */
    public <T> T readResponse(Class<T> t) throws ApiException {
        InputStream in = readResponse();
        try {
            return JacksonUtil.readObj(in, t);
        } catch (IOException e) {
            throw new ApiException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new ApiException(e);
                }
            }
        }
    }

    /**
     * 将服务器返回的信息转为String
     *
     * @return
     */
    public String readResponseAsString() throws ApiException {

        InputStream in = readResponse();
        String responseStr = null;

        if (in != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuffer buf = new StringBuffer();
                String line;
                while (null != (line = br.readLine())) {
                    buf.append(line).append("\n");
                }
                responseStr = buf.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new ApiException(e);
                    }
                }
            }
        }
        return responseStr;
    }
}

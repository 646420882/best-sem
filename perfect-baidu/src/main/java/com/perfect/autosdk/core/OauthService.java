/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 * work 2014-2-14
 */
package com.perfect.autosdk.core;

import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.util.ApiConfig;
import com.perfect.autosdk.util.BrowserUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * @author wangbin14
 * @version 1.0
 * @title OauthService
 * @description Oauth2.0 related apis
 * @date 2014-2-14
 */
public class OauthService {

    protected static final Log log = LogFactory.getLog(OauthService.class);
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String state;

    /**
     * @description default constructor
     */
    public OauthService() {
        this.clientId = trimNotNullStr(ApiConfig.getValue("client_id"));
        this.clientSecret = trimNotNullStr(ApiConfig.getValue("client_secret"));
        this.redirectUrl = trimNotNullStr(ApiConfig.getValue("redirect_url"));
    }

    /**
     * @param clientId
     * @param clientSecret
     * @param redirectUrl
     * @description build OauthService with client's parameters
     */
    public OauthService(String clientId, String clientSecret, String redirectUrl) {
        this.clientId = trimNotNullStr(clientId);
        this.clientSecret = trimNotNullStr(clientSecret);
        this.redirectUrl = trimNotNullStr(redirectUrl);
    }

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @param redirectUrl: the redirectUrl of an APP
     * @description set user config: redirectUrl
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = trimNotNullStr(redirectUrl);
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId: the unique id of an APP
     * @description set user config: clientId
     */
    public void setClientId(String clientId) {
        this.clientId = trimNotNullStr(clientId);
    }

    /**
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * @param clientSecret: the secret of an APP
     * @description set user config: clientSecret
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = trimNotNullStr(clientSecret);
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state: a customized random string
     * @description set user config: state
     */
    public void setState(String state) {
        this.state = trimNotNullStr(state);
    }

    /**
     * @param ori
     * @return
     * @description return trimmed value of a not-null-string
     */
    public static String trimNotNullStr(String ori) {
        return ori == null ? null : ori.trim();
    }

    /**
     * @param responseType: Client & Server Flow: responseType=code; Implicit Flow: responseType=token
     * @param state:        customized random string
     * @description run native browser, redirect to the authorization page
     */
    public void authorize(String responseType, String state) {
        if (state != null && state.length() > 0) {
            this.setState(state.trim());
        }
        BrowserUtils.runBrowser(buildAuthorizeUrl(responseType));
    }

    /**
     * @param code
     * @description Client & Server Flow: send authcode to open2 in exchange for accesstoken
     */
    @SuppressWarnings("unchecked")
    public AccessToken getAccesstokenByCode(String code) {

        JsonConnection conn;
        AccessToken accessToken = new AccessToken();
        try {
            // build connection with open2 authorization server
            conn = new JsonConnection(buildAccesstokenUrl(code));
            conn.setConnectTimeout(Integer.valueOf(ApiConfig.getValue("connectTimeoutMills").trim()));
            conn.setReadTimeout(Integer.valueOf(ApiConfig.getValue("readTimeoutMills").trim()));

            // post null data to auth-server
            conn.sendRequest(null);

            // read response and cast it to a MAP (conn is closed in readResponse).
            Map<String, Object> tokenMap = conn.readResponse(Map.class);

            // extract values and put them in AccessToken
            if (tokenMap != null) {
                if (tokenMap.get("error") == null) {
                    accessToken.setAccessToken((String) tokenMap.get("access_token"));
                    // NOTICE: the type of expires_in was Long in OPEN2, but json converted it to Integer
                    //         automatically. But since Integer.MAX_VALUE is more than 68 years 
                    //         (68 * 365 * 24 * 3600), we think it is a safe conversion.
                    accessToken.setExpiredIn((Integer) tokenMap.get("expires_in"));
                    accessToken.setRefreshToken((String) tokenMap.get("refresh_token"));
                    accessToken.setSessionKey((String) tokenMap.get("username"));
                    accessToken.setUsername((String) tokenMap.get("identifier"));
                    log.info("Successed in getting AccessToken: " + tokenMap.get("access_token"));
                } else {
                    log.error("Failed in getting AccessToken with returned message: " + tokenMap.get("error"));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * @return
     * @description Client & Server Flow: build the url which is used to get authorization code
     */
    public String buildAuthorizeUrl(String responseType) {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(ApiConfig.getValue("open2OauthServerUrl").trim())
                .append(ApiConfig.getValue("authorizeUrl").trim()).append("?client_id=").append(this.getClientId())
                .append("&response_type=").append(responseType).append("&redirect_uri=").append(this.getRedirectUrl());
        if (this.getState() != null) {
            urlBuilder.append("&state=").append(this.getState());
        }
        return urlBuilder.toString();
    }

    /**
     * @param authcode
     * @return
     * @description Client & Server Flow: build the url which is used to exchange authorization code for accesstoken
     */
    public String buildAccesstokenUrl(String authcode) {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(ApiConfig.getValue("open2OauthServerUrl").trim()).append("/oauth2/token?client_id=")
                .append(this.getClientId()).append("&client_secret=").append(this.getClientSecret())
                .append("&grant_type=authorization_code&code=").append(authcode).append("&redirect_uri=")
                .append(this.getRedirectUrl());
        if (this.getState() != null) {
            urlBuilder.append("&state=").append(this.getState());
        }
        return urlBuilder.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // OauthService oauth = new OauthService("your_client_id", "your_client_secret", "your_redirect_url");

        //        OauthService oauth = new OauthService();
        //        oauth.setClientId("your_client_id");
        //        oauth.setClientSecret("your_client_secret");
        //        oauth.setRedirectUrl("your_redirect_url");
        //        oauth.authorize("code", "sdk");
        System.out.println(ApiConfig.getValue("client_id"));

    }

}

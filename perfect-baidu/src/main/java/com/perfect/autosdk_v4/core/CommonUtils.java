package com.perfect.autosdk_v4.core;

import java.util.ArrayList;
import java.util.List;

import com.perfect.autosdk_v4.exception.ApiException;

public class CommonUtils {
	public static final String NAMESPACE = "http://10.48.59.45:8080/api-gateway/json/sms/";
	public static final String PACKAGE = "com.baidu.drapi.autosdk.sms.";
	private static final String DOT = ".";
	public static final String SLASH = "/";
	public static final String V2 = "v2";
	public static final String V3 = "v3";
	public static List<String> METHODV2;
	static {
		METHODV2 = new ArrayList<String>();
		METHODV2.add("getKRFileState");
		METHODV2.add("getKRByMultiSeedWord");
		METHODV2.add("getKRFileIdbySeedWord");
		METHODV2.add("getKRbySeedUrl");
		METHODV2.add("getKRbySeedWord");
		METHODV2.add("getSeedWord");
		METHODV2.add("getAdInfo");
		METHODV2.add("updateAccountInfo");
	}

	public static String getURL(String service, String method)
			throws ApiException {
		StringBuilder url = new StringBuilder();
		url.append(NAMESPACE);
		if (METHODV2.contains(method)) {
			url.append(V2);
		} else {
			url.append(V3);
		}
		url.append(SLASH).append(service).append(SLASH);
		url.append(method);
		return url.toString();
	}

	public static AuthHeader getAuthHeader(String username, String password,
			String token, String target) {
		AuthHeader authHeader = new AuthHeader();
		authHeader.setUsername(username);
		authHeader.setPassword(password);
		authHeader.setToken(token);
		authHeader.setTarget(target);
		return authHeader;
	}

	public static String getCls(String service, String method) {
		StringBuilder cls = new StringBuilder();
		cls.append(PACKAGE);
		if(METHODV2.contains(method)){
			cls.append(V2);
		}else{
			cls.append(V3);
		}
		cls.append(DOT).append(service);
		return cls.toString();
	}

}

package com.perfect.autosdk.core;

import com.perfect.autosdk.util.PrintUtil;


public class AddressUtil {

	private static final String NAMESPACE_COMMON = "http://api.baidu.com/sem/common/v2";


	public static <T> String getAddr(Class<T> cls) {
	    String pkgName = cls.getPackage().getName();
	    int dot = pkgName.indexOf("autosdk");
	    String tempAddr = pkgName.substring(dot+8).replace(".", "/");
	    return "/sem/" + tempAddr + "/" + cls.getSimpleName();
	}
	
	public static <T> String getJsonAddr(Class<T> cls) {
		String pkgName = cls.getPackage().getName();
	    int dot = pkgName.indexOf("autosdk");
	    String tempAddr = pkgName.substring(dot+8).replace(".", "/");
	    return "/json/" + tempAddr + "/" + cls.getSimpleName();
	}


	public static String getHeaderNameSpace() {
		return NAMESPACE_COMMON;
	}
	public static <T> String getVersion(Class<T> cls){
		String pkgName = cls.getPackage().getName();
		return pkgName.substring(pkgName.lastIndexOf(".")+1);
	}

	public static void main(String[] args){
		Class<AddressUtil> cls = AddressUtil.class;
		PrintUtil.println(AddressUtil.getAddr(cls));
		PrintUtil.println(AddressUtil.getVersion(cls));
	}
}

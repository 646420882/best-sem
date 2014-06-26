package com.perfect.autosdk.core;

public class AsyncProxy implements Runnable {

	private JsonProxy proxy;
	private Object req;
	private Callback<JsonEnvelop<ResHeader, ?>> callback;

	public AsyncProxy(JsonProxy proxy, Object req,
			Callback<JsonEnvelop<ResHeader, ?>> callback) {
		this.proxy = proxy;
		this.req = req;
		this.callback = callback;
	}

	public void run() {
		try {
//			JsonEnvelop<ResHeader, ?> res = proxy.execute(this.req);
//			this.callback.execResult(res);
		} catch (Throwable e) {
			e.printStackTrace();
			callback.execError(e);
		}
	}

}

package com.perfect.autosdk_v4.core;

public interface Callback<T> {
	
	void execResult(T result);

	void execError(Throwable error);
}

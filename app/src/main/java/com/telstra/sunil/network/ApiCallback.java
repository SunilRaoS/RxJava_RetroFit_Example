package com.telstra.sunil.network;

/**
 * Common Callback to call API request response.
 * @param <P> : response Type
 */
public interface ApiCallback<P> {
    void onSuccess(P t);

    void onFailed(Throwable throwable);
}
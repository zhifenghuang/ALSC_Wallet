package com.alsc.alsc_wallet.http;

public interface SubscriberOnNextListener<T> {
    void onNext(T t, String msg);
}

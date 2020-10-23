package com.wallet.retrofit;

public interface HttpInfoRequest<T> {
    void onSuccess(T model);

    void onError(int eCode);
}

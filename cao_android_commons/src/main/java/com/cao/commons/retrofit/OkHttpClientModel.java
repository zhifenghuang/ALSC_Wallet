package com.cao.commons.retrofit;


import com.cao.commons.SPConstants;
import com.cao.commons.base.PoliceApplication;
import com.cao.commons.util.DataKeeper;
import com.cao.commons.util.Network;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * 请求网络头部封装
 *
 * @author Ycc
 * @version v1.0
 * @Time 2018-8-7
 */

public class OkHttpClientModel {
    private static final int DEFAULT_TIMEOUT = 20;
    private static final int CONNECT_TIMEOUT = 20;

    public static OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(new File(PoliceApplication.newInstance().getCacheDir(), SPConstants.WEFACEAPP_CACHE),
                1024 * 1024 * 10);

        OkHttpClient okHttpClient;
        //      if (SPConstants.DEBUG_MODE) {
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CacheInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
//        } else {
//            okHttpClient = new OkHttpClient.Builder()
//                    .cache(cache)
//                    .addNetworkInterceptor(new CacheInterceptor())
//                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                    .build();
//        }

        return okHttpClient;
    }


    public static OkHttpClient getOkHttpClientLocal() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(new File(PoliceApplication.newInstance().getCacheDir(), SPConstants.WEFACEAPP_CACHE),
                1024 * 1024 * 20);

        OkHttpClient okHttpClient;
        //      if (SPConstants.DEBUG_MODE) {
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new CacheInterceptorLocal())
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
//        } else {
//            okHttpClient = new OkHttpClient.Builder()
//                    .cache(cache)
//                    .addInterceptor(new CacheInterceptorLocal())
//                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                    .build();
//        }

        return okHttpClient;
    }


}

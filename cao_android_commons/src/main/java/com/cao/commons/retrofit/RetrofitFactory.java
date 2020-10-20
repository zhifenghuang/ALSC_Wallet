package com.cao.commons.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by LiYang on 2018/8/7.
 */

public class RetrofitFactory {
    public static class V1 {
        public static Retrofit V1;
    }
    public static class V2 {
        public static Retrofit V2;
    }

    @SuppressWarnings("unchecked")
    public static <T> T GetApiService(Class synClass, Class apiServiceClass, Retrofit retrofit, String url) {
        if (retrofit == null) {
            synchronized (synClass) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder().setLenient().create();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .client(OkHttpClientModel.getOkHttpClient())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return (T) retrofit.create(apiServiceClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T GetColdService(Class synClass, Class apiServiceClass, Retrofit retrofit, String url) {
        if (retrofit == null) {
            synchronized (synClass) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder().setLenient().create();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .client(OkHttpClientModel.getOkHttpClientLocal())

                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return (T) retrofit.create(apiServiceClass);
    }
}

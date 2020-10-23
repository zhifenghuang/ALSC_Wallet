package com.common.http;

import android.util.Log;

import com.cao.commons.manager.ConfigManager;
import com.cao.commons.manager.DataManager;
import com.common.utils.NetUtil;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gigabud on 17-5-3.
 */

public class HttpMethods {

    private static final String TAG = "HttpMethods";
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 20;
    private static HttpMethods INSTANCE;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //    if (SPConstants.DEBUG_MODE) {
                Log.i(TAG, message);
                //     }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
    }

    public static HttpMethods getInstance() {
        if (INSTANCE == null) {
            synchronized (TAG) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpMethods();
                }
            }
        }
        return INSTANCE;
    }

    private String getBaseUrl() {
        return "http://mining.test.uwyoo.com/";
    }


    public void register(int type, String userName, String code, String account, String pwd,
                         String nick, String firstName, String lastName, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable;
        if (type == 1) {
            observable = httpService.registerWithMobile(userName, code, account, pwd, nick, firstName, lastName);
        } else {
            observable = httpService.registerWithEmail(userName, code, account, pwd, nick, firstName, lastName);
        }
        toSubscribe(observable, observer);
    }

    public void login(String name, String password, String code, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.login(name, password, code);
        toSubscribe(observable, observer);
    }

    public void assets(HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.assets(DataManager.getInstance().getToken());
        toSubscribe(observable, observer);
    }

    public void getNewsArticle(int type, int page_index, int page_size, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.newsArticle(DataManager.getInstance().getToken(), type, page_index, page_size);
        toSubscribe(observable, observer);
    }


    private <T> void toSubscribe(Observable<T> o, HttpObserver s) {
        o.retry(2, new Predicate<Throwable>() {
            @Override
            public boolean test(@NonNull Throwable throwable) throws Exception {
                return NetUtil.isConnected(ConfigManager.getInstance().getContext()) &&
                        (throwable instanceof SocketTimeoutException ||
                                throwable instanceof ConnectException ||
                                throwable instanceof ConnectTimeoutException ||
                                throwable instanceof TimeoutException);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}

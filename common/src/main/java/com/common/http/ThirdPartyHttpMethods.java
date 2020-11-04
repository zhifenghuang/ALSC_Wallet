package com.common.http;

import android.text.TextUtils;
import android.util.Log;

import com.cao.commons.manager.ConfigManager;
import com.cao.commons.manager.DataManager;
import com.common.utils.Constants;
import com.common.utils.NetUtil;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gigabud on 17-5-3.
 */

public class ThirdPartyHttpMethods {

    private static final String TAG = "HttpMethods";
    private Retrofit mRetrofit1, mRetrofit2;
    private static final int DEFAULT_TIMEOUT = 20;
    private static ThirdPartyHttpMethods INSTANCE;


    private ThirdPartyHttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        mRetrofit1 = new Retrofit.Builder()
                .client(getUnsafeOkHttpClient(true))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl1())
                .build();

        mRetrofit2 = new Retrofit.Builder()
                .client(getUnsafeOkHttpClient(true))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl2())
                .build();
    }

    private OkHttpClient getUnsafeOkHttpClient(boolean isNeedSSL) {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //    if (SPConstants.DEBUG_MODE) {
                    Log.i(TAG, message);
                    //     }
                }
            });

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request;
                    String ticket = DataManager.getInstance().getTicket();
                    if (!TextUtils.isEmpty(ticket)) {
                        request = chain.request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer [" + ticket + "]")
                                .build();
                    } else {
                        request = chain.request()
                                .newBuilder()
                                .build();
                    }
                    return chain.proceed(request);
                }
            };

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(loggingInterceptor);
            if (isNeedSSL) {
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ThirdPartyHttpMethods getInstance() {
        if (INSTANCE == null) {
            synchronized (TAG) {
                if (INSTANCE == null) {
                    INSTANCE = new ThirdPartyHttpMethods();
                }
            }
        }
        return INSTANCE;
    }

    private String getBaseUrl1() {
        return Constants.THIRD_PARTY_BASE_URL_1;
    }

    private String getBaseUrl2() {
        return Constants.THIRD_PARTY_BASE_URL_2;
    }

    public void getVerCode(HashMap<String, Object> map, HttpObserver observer) {
        ThirdPartyService httpService = mRetrofit1.create(ThirdPartyService.class);
        Random random = new Random();
        String randomStr = "";
        for (int i = 0; i < 4; ++i) {
            randomStr += (1 + random.nextInt(9));
        }
        Observable observable = httpService.getVerCode(Constants.THIRD_PARTY_ID, Constants.THIRD_PARTY_APPID, randomStr, map);
        toSubscribe(observable, observer);
    }

    public void register(HashMap<String, Object> map, HttpObserver observer) {
        ThirdPartyService httpService = mRetrofit1.create(ThirdPartyService.class);
        Observable observable = httpService.register(Constants.THIRD_PARTY_ID, Constants.THIRD_PARTY_APPID, map);
        toSubscribe(observable, observer);
    }

    public void login(HashMap<String, Object> map, HttpObserver observer) {
        ThirdPartyService httpService = mRetrofit2.create(ThirdPartyService.class);
        Observable observable = httpService.login(Constants.THIRD_PARTY_ID, Constants.THIRD_PARTY_APPID, map);
        toSubscribe(observable, observer);
    }

    public void getLoginVerCode(HashMap<String, Object> map, HttpObserver observer) {
        ThirdPartyService httpService = mRetrofit2.create(ThirdPartyService.class);
        Random random = new Random();
        String randomStr = "";
        for (int i = 0; i < 4; ++i) {
            randomStr += (1 + random.nextInt(9));
        }
        Observable observable = httpService.getLoginVerCode(Constants.THIRD_PARTY_ID, Constants.THIRD_PARTY_APPID, randomStr, map);
        toSubscribe(observable, observer);
    }

    public void bindWallet(HashMap<String, Object> map, HttpObserver observer) {
        ThirdPartyService httpService = mRetrofit2.create(ThirdPartyService.class);
        Random random = new Random();
        String randomStr = "";
        for (int i = 0; i < 4; ++i) {
            randomStr += (1 + random.nextInt(9));
        }
        Observable observable = httpService.bindWallet(Constants.THIRD_PARTY_ID, Constants.THIRD_PARTY_APPID, randomStr, map);
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

package com.alsc.chat.http;

import android.content.Context;
import android.util.Log;

import com.alsc.chat.manager.ChatManager;
import com.cao.commons.SPConstants;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.NetUtil;
import com.common.http.HttpObserver;
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
    private static final int DEFAULT_TIMEOUT = 10;
    private static HttpMethods INSTANCE;
    private Context mContext;

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


//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request request;
//                if (!TextUtils.isEmpty(DataManager.getInstance().getToken())) {
//                    request = chain.request()
//                            .newBuilder()
//                            .addHeader("Authorization", DataManager.getInstance().getToken())
//                            .build();
//                } else {
//                    request = chain.request()
//                            .newBuilder()
//                            .build();
//                }
//                return chain.proceed(request);
//            }
//        };

        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(interceptor)
//                .addInterceptor(new AddCookiesInterceptor()) //这部分
//                .addInterceptor(new ReceivedCookiesInterceptor()) //这部分
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

//    class ReceivedCookiesInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response originalResponse = chain.proceed(chain.request());
//
//            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//                HashSet<String> cookies = new HashSet<>();
//
//                for (String header : originalResponse.headers("Set-Cookie")) {
//                    cookies.add(header);
//                }
//
//                SharedPreferences.Editor config = mContext.getSharedPreferences("config", mContext.MODE_PRIVATE)
//                        .edit();
//                config.putStringSet("cookie", cookies);
//                config.commit();
//            }
//
//            return originalResponse;
//        }
//    }
//
//    class AddCookiesInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request.Builder builder = chain.request().newBuilder();
//            HashSet<String> preferences = (HashSet) mContext.getSharedPreferences("config",
//                    mContext.MODE_PRIVATE).getStringSet("cookie", null);
//            if (preferences != null) {
//                for (String cookie : preferences) {
//                    builder.addHeader("Cookie", cookie);
//                    Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
//                }
//            }
//            return chain.proceed(builder.build());
//        }
//    }

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

    public void setContent(Context content) {
        this.mContext = content;
    }

    private String getBaseUrl() {
        return Constants.BASE_URL + "/";
    }


    /**
     * @param pwd
     * @param token
     * @param observer
     */
    public void checkPwd(String pwd, String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.checkPwd(pwd, token);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void getBalance(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.getBalance(token);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void isIncome(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.isIncome(token);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void settle(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.settle(token);
        toSubscribe(observable, observer);
    }

    /**
     * @param observer
     */
    public void checkVersion(HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.checkVersion(1, SPConstants.CURRENT_VERSION_CODE);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void getPop(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_pop(token);
        toSubscribe(observable, observer);
    }


    private <T> void toSubscribe(Observable<T> o, HttpObserver s) {
        o.retry(2, new Predicate<Throwable>() {
            @Override
            public boolean test(@NonNull Throwable throwable) throws Exception {
                return NetUtil.isConnected(ChatManager.getInstance().getContext()) &&
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

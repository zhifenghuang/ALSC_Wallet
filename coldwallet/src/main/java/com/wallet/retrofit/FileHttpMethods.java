package com.wallet.retrofit;

import android.content.Context;
import android.util.Log;

import com.alsc.chat.http.HttpObserver;
import com.alsc.chat.manager.ConfigManager;
import com.alsc.chat.utils.NetUtil;
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

public class FileHttpMethods {

    private static final String TAG = "FileHttpMethods";
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 10;
    private static FileHttpMethods INSTANCE;
    private Context mContext;

    private FileHttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //   if (SPConstants.DEBUG_MODE) {
                Log.i(TAG, message);
                //    }
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

    public static FileHttpMethods getInstance() {
        if (INSTANCE == null) {
            synchronized (TAG) {
                if (INSTANCE == null) {
                    INSTANCE = new FileHttpMethods();
                }
            }
        }
        return INSTANCE;
    }

    public void setContent(Context content) {
        this.mContext = content;
    }

    private String getBaseUrl() {
        return "http://zlk.gogopipe.xyz/";
    }


    /**
     * @param observer
     */
    public void getAds(HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getAds("cn");
        toSubscribe(observable, observer);
    }

    /**
     * @param observer
     */
    public void getVideo(HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getVideo("cn");
        toSubscribe(observable, observer);
    }

    /**
     * @param observer
     */
    public void getTjSchool(HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getTjSchool("cn");
        toSubscribe(observable, observer);
    }

    /**
     * @param observer
     */
    public void getBook(HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getBook("cn");
        toSubscribe(observable, observer);
    }

    /**
     * @param observer
     */
    public void getDownloads(HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getDownloads("cn");
        toSubscribe(observable, observer);
    }

    /**
     * @param id
     * @param observer
     */
    public void getArticle(int id, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getArticle(id, "cn");
        toSubscribe(observable, observer);
    }


    /**
     * @param page
     * @param observer
     */
    public void getLive(int page, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getLive("cn", page);
        toSubscribe(observable, observer);
    }

    /**
     * @param page
     * @param observer
     */
    public void businessSchoolVideo(int page, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.businessSchoolVideo("cn", page);
        toSubscribe(observable, observer);
    }

    /**
     * @param page
     * @param type
     * @param observer
     */
    public void getBook(int page, int type, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getBookFile("cn", page, type);
        toSubscribe(observable, observer);
    }

    /**
     * @param page
     * @param observer
     */
    public void getDownloadFile(int page, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getDownloadFile("cn", page);
        toSubscribe(observable, observer);
    }

    /**
     * @param page
     * @param observer
     */
    public void getPdfs(int page, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getPdfs("cn", page);
        toSubscribe(observable, observer);
    }

    /**
     * @param page
     * @param keyword
     * @param observer
     */
    public void search(int page, String keyword, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.search("cn", keyword, page);
        toSubscribe(observable, observer);
    }

    /**
     * @param id
     * @param observer
     */
    public void getVinfo(long id, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getVinfo("cn", id);
        toSubscribe(observable, observer);
    }

    /**
     * @param id
     * @param observer
     */
    public void getPinfo(long id, HttpObserver observer) {
        FileApiService httpService = mRetrofit.create(FileApiService.class);
        Observable observable = httpService.getPinfo("cn", id);
        toSubscribe(observable, observer);
    }


    private <T> void toSubscribe(Observable<T> o, HttpObserver s) {
        o.retry(3, new Predicate<Throwable>() {
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

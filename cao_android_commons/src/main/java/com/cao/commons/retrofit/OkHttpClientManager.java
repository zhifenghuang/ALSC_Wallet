package com.cao.commons.retrofit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpClientManager {

    private static final String TAG = "OkHttpClientManager";

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public void post(String url, RequestBody body, final Callback callback) {
        //RequestBody body = new FormBody.Builder().add("useName", "addd").add("pwd", "123").build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void post(String url, String json, final Callback callback) {
        //RequestBody body = new FormBody.Builder().add("useName", "addd").add("pwd", "123").build();
        RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void postXml(String url, String xmlStr, final Callback callback) {
        //RequestBody body = new FormBody.Builder().add("useName", "addd").add("pwd", "123").build();
        RequestBody body = RequestBody.create(XML, xmlStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void get(String url, final Callback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

}

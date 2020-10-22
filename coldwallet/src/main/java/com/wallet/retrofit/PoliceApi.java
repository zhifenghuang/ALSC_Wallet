package com.wallet.retrofit;


import com.cao.commons.retrofit.PoliceBaseUrl;
import com.cao.commons.retrofit.RetrofitFactory;

/**
 * Created by tfwin2 on 2018/11/10.
 */

public class PoliceApi {

    private static final String TAG = "PoliceApi";

    private static V1ApiService mV1ApiService;
    private static ColdApiService mColdApiService;
    private static ColdApiService mColdApiService1;
    private static ColdApiService mColdApiService2;

    public static V1ApiService getV1ApiService() {
        if (mV1ApiService == null) {
            synchronized (TAG) {
                if (mV1ApiService == null) {
                    mV1ApiService = RetrofitFactory.GetApiService(RetrofitFactory.V1.class, V1ApiService.class, RetrofitFactory.V1.V1, PoliceBaseUrl.getBaseUrl());
                }
            }
        }
        return mV1ApiService;
    }


    public static ColdApiService getColdApiService() {
        if (mColdApiService == null) {
            mColdApiService = RetrofitFactory.GetApiService(RetrofitFactory.V1.class, ColdApiService.class, RetrofitFactory.V1.V1, PoliceBaseUrl.getBaseUrl());
        }
        return mColdApiService;
    }


    public static ColdApiService getColdApiService1() {
        if (mColdApiService1 == null) {
            mColdApiService1 = RetrofitFactory.GetColdService(RetrofitFactory.V1.class, ColdApiService.class, RetrofitFactory.V1.V1, "https://ethgasstation.info/");
        }
        return mColdApiService1;
    }

    public static ColdApiService getColdService() {
        if (mColdApiService2 == null) {
            mColdApiService2 = RetrofitFactory.GetColdService(RetrofitFactory.V1.class, ColdApiService.class, RetrofitFactory.V1.V1, PoliceBaseUrl.V1_TEXT_COLD);
        }
        return mColdApiService2;
    }

    public static void clearHotService() {
        mV1ApiService = null;
        getV1ApiService();
    }
}

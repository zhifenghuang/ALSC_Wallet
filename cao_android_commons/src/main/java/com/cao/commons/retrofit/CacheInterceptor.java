package com.cao.commons.retrofit;


import android.text.TextUtils;

import com.cao.commons.SPConstants;
import com.cao.commons.base.PoliceApplication;
import com.cao.commons.util.DataKeeper;
import com.cao.commons.util.Network;
import com.cao.commons.util.log.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * okhttp拦截器，请求头参数封装在此
 *
 * @author Y
 * @version v1.0
 * @Time 2018-11-10
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        // true 使用缓存 反之 不使用缓存
        String cache = request.cacheControl().toString();
        Boolean isNoCache = cache.equals("");

        boolean netAvailable = Network.isConnected(PoliceApplication.newInstance().getApplicationContext());
        String token = DataKeeper.get(PoliceApplication.newInstance(), SPConstants.WEFACE_TOKEN, "");
        /**此处封装请求头*/
        request = request.newBuilder()
                //.removeHeader(SPConstants.ANDROID_DID)
//                .removeHeader(SPConstants.WEFACE_TOKEN)
                //.addHeader(SPConstants.ANDROID_DID, TelephoneUtil.getUID())
//                .addHeader(SPConstants.WEFACE_USER_ID, "24fd019a246d4feda46b486de2a63193")
                //.addHeader(SPConstants.WEFACE_USER_ID, model != null ? model.userId : "")
                .cacheControl(netAvailable ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                .addHeader(SPConstants.WEFACE_COOKIE, SPConstants.WEFACE_TOKEN + "=" + token)
                .build();
        if (netAvailable) {
            HttpUrl url = request.url();
//            Log.e("----", url.toString());
            Response build = chain.proceed(request).newBuilder()
                    .removeHeader(HttpConstants.PRAGMA)
                    .removeHeader(HttpConstants.CACHE_CONTROL)
                    .header(HttpConstants.CACHE_CONTROL, isNoCache ? HttpConstants.INTERNET : cache)
                    .build();
            return build;

        } else {

            return chain.proceed(request).newBuilder()
                    .removeHeader(HttpConstants.PRAGMA)
                    .removeHeader(HttpConstants.CACHE_CONTROL)
                    // 默认设置网络不可用时设置缓存1周后超时，反之使用设置好的参数
                    .header(HttpConstants.CACHE_CONTROL, isNoCache ? HttpConstants.INTERNET_NO : cache)
                    .build();
        }

    }
}

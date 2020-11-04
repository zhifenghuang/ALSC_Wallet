package com.common.http;

import com.common.bean.ThirdPartyUserBean;
import com.common.bean.ThirdPartyResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ThirdPartyService {

    /**
     * 获取第三方验证码
     *
     * @return
     */
    @POST("v1/agapi")
    Observable<ThirdPartyResponse<Object>> getVerCode(@Query("partid") String partid,
                                                      @Query("appid") String appid,
                                                      @Query("random") String random,
                                                      @Body HashMap<String, Object> map);


    /**
     * 获取第三方验证码
     *
     * @return
     */
    @POST("v2/agapi/userregister")
    Observable<ThirdPartyResponse<ThirdPartyUserBean>> register(@Query("partid") String partid,
                                                                @Query("appid") String appid,
                                                                @Body HashMap<String, Object> map);

    /**
     * 登录
     *
     * @return
     */
    @POST("v1/eapi/user/login")
    Observable<ThirdPartyResponse<ThirdPartyUserBean>> login(@Query("partid") String partid,
                                                             @Query("appid") String appid,
                                                             @Body HashMap<String, Object> map);

    /**
     * 获取登录验证码
     *
     * @return
     */
    @POST("v1/eapi/user/applycode")
    Observable<ThirdPartyResponse<Object>> getLoginVerCode(@Query("partid") String partid,
                                                           @Query("appid") String appid,
                                                           @Query("random") String random,
                                                           @Body HashMap<String, Object> map);

    /**
     * 获取登录验证码
     *
     * @return
     */
    @POST("v1/eapi/user/bindwallet")
    Observable<ThirdPartyResponse<Object>> bindWallet(@Query("partid") String partid,
                                                           @Query("appid") String appid,
                                                           @Query("random") String random,
                                                           @Body HashMap<String, Object> map);

}

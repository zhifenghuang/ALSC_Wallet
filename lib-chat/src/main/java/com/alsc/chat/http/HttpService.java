package com.alsc.chat.http;


import com.cao.commons.bean.ScaleBean;
import com.cao.commons.bean.VersionBean;
import com.cao.commons.bean.chat.BasicResponse;
import com.cao.commons.bean.user.ActivityTimeBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpService {

    /*
验证支付密码
*/
    @FormUrlEncoded
    @POST("api/check_pay")
    Observable<BasicResponse> checkPwd(@Field("pwd") String pwd, @Field("token") String token);

    /*
可用资产
*/
    @FormUrlEncoded
    @POST("api/game_balance")
    Observable<BasicResponse<HashMap<String, Double>>> getBalance(@Field("token") String token);

    /*
检测是否有收益
*/
    @FormUrlEncoded
    @POST("api/is_income")
    Observable<BasicResponse> isIncome(@Field("token") String token);

    /*
检测是否有收益
*/
    @FormUrlEncoded
    @POST("api/settle")
    Observable<BasicResponse<ArrayList<ScaleBean>>> settle(@Field("token") String token);

    /*
检测是否需要弹出活动弹窗
*/
    @FormUrlEncoded
    @POST("api/get_pop")
    Observable<BasicResponse<ActivityTimeBean>> get_pop(@Field("token") String token);

    /*
检测app新版本
*/
    @FormUrlEncoded
    @POST("api/check_app")
    Observable<BasicResponse<VersionBean>> checkVersion(@Field("type") int type, @Field("banbenhao") int banbenhao);
}

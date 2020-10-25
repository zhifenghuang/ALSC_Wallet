package com.common.http;

import com.cao.commons.bean.BaseListBean;
import com.cao.commons.bean.chat.BasicResponse;
import com.cao.commons.bean.ArticleBean;
import com.cao.commons.bean.AssetsBean;
import com.common.bean.LoginBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpService {

    /**
     * 手机号注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/register")
    Observable<BasicResponse<HashMap<String, String>>> registerWithMobile(@Field("username") String username,
                                                                          @Field("verify_code") String verify_code,
                                                                          @Field("phone") String phone,
                                                                          @Field("pwd") String pwd,
                                                                          @Field("nickname") String nickname,
                                                                          @Field("firstname") String firstname,
                                                                          @Field("lastname") String lastname);


    /**
     * 邮箱注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/register")
    Observable<BasicResponse<HashMap<String, String>>> registerWithEmail(@Field("username") String username,
                                                                         @Field("verify_code") String verify_code,
                                                                         @Field("email") String email,
                                                                         @Field("pwd") String pwd,
                                                                         @Field("nickname") String nickname,
                                                                         @Field("firstname") String firstname,
                                                                         @Field("lastname") String lastname);

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/login")
    Observable<BasicResponse<LoginBean>> login(@Field("name") String name,
                                               @Field("password") String password,
                                               @Field("code") String code);

    /**
     * 资产
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/assets/index")
    Observable<BasicResponse<AssetsBean>> assets(@Field("token") String token);

    /**
     * 转账记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/logs/transfer_list")
    Observable<BasicResponse<HashMap<String, String>>> transfer_list(@Field("token") String token,
                                                                     @Field("type") String type,
                                                                     @Field("symbol") String symbol,
                                                                     @Field("page_index") int page_index,
                                                                     @Field("page_size") int page_size);

    /**
     * 转账记录详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/logs/transfer_info")
    Observable<BasicResponse<HashMap<String, String>>> transfer_info(@Field("token") String token,
                                                                     @Field("id") String id);

    /**
     * 转账
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/assets/transfer")
    Observable<BasicResponse<HashMap<String, String>>> transfer(@Field("token") String token,
                                                                @Field("amount") String amount,
                                                                @Field("symbol") String symbol,
                                                                @Field("fee") String fee,
                                                                @Field("receive_url") String receive_url,
                                                                @Field("send_url") String send_url);

    /**
     * 资讯
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/news/article")
    Observable<BasicResponse<BaseListBean<ArrayList<ArticleBean>>>> newsArticle(@Field("token") String token,
                                                                                @Field("type") int type,
                                                                                @Field("page_index") int page_index,
                                                                                @Field("page_size") int page_size);

}

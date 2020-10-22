package com.wallet.retrofit;


import com.cao.commons.bean.AbNormalBean;
import com.cao.commons.bean.BaseListBean;
import com.cao.commons.bean.BaseResultBean;
import com.cao.commons.bean.KongtouAwardBean;
import com.cao.commons.bean.VersionBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.disc.DiscoverHangBean;
import com.cao.commons.bean.disc.DiscoverNoticeBean;
import com.cao.commons.bean.disc.GameAddressBean;
import com.cao.commons.bean.disc.HangQingBean;
import com.cao.commons.bean.login.PhoneCodeResultEntity;
import com.cao.commons.bean.login.PicCodeResultBean;
import com.cao.commons.bean.login.RegisterStepBean;
import com.cao.commons.bean.user.AlscPriceBean;
import com.cao.commons.bean.user.AreaHeightBean;
import com.cao.commons.bean.user.ArticleDetailBean;
import com.cao.commons.bean.user.AwardBean;
import com.cao.commons.bean.user.CreditDetailListBean;
import com.cao.commons.bean.user.ExchangeDetailBean;
import com.cao.commons.bean.user.FeeBean;
import com.cao.commons.bean.user.FrequencyBean;
import com.cao.commons.bean.user.HighAddressBean;
import com.cao.commons.bean.user.HighTransferBean;
import com.cao.commons.bean.user.LogDetailBean;
import com.cao.commons.bean.user.MyUserInfoBean;
import com.cao.commons.bean.user.RecommmendDetailBean;
import com.cao.commons.bean.user.SettlePoolListBean;
import com.cao.commons.bean.user.ShareTogetherBean;
import com.cao.commons.bean.user.TransferListBean;
import com.cao.commons.bean.user.TransferListDetailBean;
import com.cao.commons.bean.user.WallerInfoBean;
import com.cao.commons.model.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 接口请求封装
 */
public interface V1ApiService {

    /*
    获取图片验证码
    */
    @Headers("Cache-Control:no-cache,max-age=0")
    @GET("api/captcha")
    Observable<ApiModel<PicCodeResultBean>> getPicCode();

    /*
    获取手机验证码
     */
    @FormUrlEncoded
    @POST("api/get_mobile_code")
    Observable<ApiModel> getMobileCode(@Query("lang") String lang, @Field("phone") String phone, @Field("phone_code") String phone_code, @Field("sid") String sid);

    /*
    获取邮箱验证码
     */
    @FormUrlEncoded
    @POST("api/get_code")
    Observable<ApiModel> getEmailCode(@Query("lang") String lang, @Field("email") String email, @Field("sid") String sid);

    /*
    注册第一步
    */
    @FormUrlEncoded
    @POST("api/register1")
    Observable<ApiModel<RegisterStepBean>> register1(@Query("lang") String lang, @Field("username") String username,
                                                     @Field("pic_code") String pic_code,
                                                     @Field("invite_code") String invite_code,
                                                     @Field("sid") String sid);

    /*
    注册第二步
    */
    @FormUrlEncoded
    @POST("api/register2")
    Observable<ApiModel<RegisterStepBean>> register2(@Query("lang") String lang, @Field("phone") String phone,
                                                     @Field("phone_area") String phone_area,
                                                     @Field("email") String email,
                                                     @Field("captcha") String captcha,
                                                     @Field("uid") String uid,
                                                     @Field("sid") String sid);

    /*
        注册第三步
        */
    @FormUrlEncoded
    @POST("api/register3")
    Observable<ApiModel<UserBean>> register3(@Query("lang") String lang, @Field("uid") String uid,
                                             @Field("pwd") String pwd,
                                             @Field("pwd2") String pwd2,
                                             @Field("pay_pwd") String pay_pwd,
                                             @Field("pay_pwd2") String pay_pwd2);

    /*
    获取手机区号列表
    */
    @GET("api/phone_codes")
    Observable<ApiModel<PhoneCodeResultEntity>> getPhoneCode(@Query("lang") String lang, @Query("lan") int lan);

    /*
    获取行情接口
   */
    @FormUrlEncoded
    @POST("api/hangq")
    Observable<ApiModel<BaseListBean<ArrayList<DiscoverHangBean>>>> getHangq(@Query("lang") String lang, @Field("token") String token);
//
//    /*
//    获取行情接口
//   */
//    @POST("api/hot_hq")
//    Observable<ApiModel<ArrayList<DiscoverHangBean>>> getHotHangq();
//
//    /*
//    行情列表
//   */
//    @POST("api/hq")
//    Observable<ApiModel<ArrayList<DiscoverHangBean>>> getHangQList();

    /*
     公告列表
    */
    @FormUrlEncoded
    @POST("api/notice")
    Observable<ApiModel<BaseListBean<ArrayList<DiscoverNoticeBean>>>> getNotice(@Query("lang") String lang, @Field("type") int type,
                                                                                @Field("page_size") int page_size,
                                                                                @Field("page_index") int page_index,
                                                                                @Field("token") String token);

    /*
    公告列表冷钱包
   */
    @FormUrlEncoded
    @POST("api/cold_notice")
    Observable<ApiModel<BaseListBean<ArrayList<DiscoverNoticeBean>>>> getColdNotice(@Query("lang") String lang, @Field("type") int type,
                                                                                    @Field("page_size") int page_size,
                                                                                    @Field("page_index") int page_index);

    /*
         读取公告
        */
    @FormUrlEncoded
    @POST("api/read")
    Observable<ApiModel> readNotice(@Query("lang") String lang, @Field("id") String id, @Field("token") String token);


    /*
     用户首页信息
    */
    @Multipart
    @POST("api/index")
    Observable<ApiModel<MyUserInfoBean>> getUserIndex(@Query("lang") String lang, @Part("token") String token);


    /*
    登录
     */
    @FormUrlEncoded
    @POST("api/login")
    Observable<ApiModel<UserBean>> login(@Query("lang") String lang, @Field("name") String name,
                                         @Field("password") String password,
                                         @Field("code") String code,
                                         @Field("sid") String sid);

    /**
     * 转账记录
     *
     * @param symbol 币种 1.alsc 2.eth 3.eth的usdt 4.btc的usdt 5.btc,19系统兑换
     * @param type   类型 0全部 1收款 2转账
     * @return
     */
    @FormUrlEncoded
    @POST("api/transfer_list")
    Observable<ApiModel<BaseListBean<List<TransferListBean>>>> getTransferList(@Query("lang") String lang, @Field("symbol") int symbol,
                                                                               @Field("type") int type,
                                                                               @Field("page_size") int page_size,
                                                                               @Field("page_index") int page_index,
                                                                               @Field("token") String token);

    /**
     * 转账记录
     *
     * @param symbol 币种 1.alsc 2.eth 3.eth的usdt 4.btc的usdt 5.btc,19系统兑换
     * @param type   类型 0全部 1收款 2转账
     * @return
     */
    @FormUrlEncoded
    @POST("api/transfer_list2")
    Observable<ApiModel<BaseListBean<List<TransferListBean>>>> getTransferList2(@Query("lang") String lang, @Field("symbol") int symbol,
                                                                                @Field("type") int type,
                                                                                @Field("page_size") int page_size,
                                                                                @Field("page_index") int page_index,
                                                                                @Field("token") String token);

    /**
     * 转账记录详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/transfer_info")
    Observable<ApiModel<TransferListDetailBean>> getTransferListDetail(@Query("lang") String lang, @Field("id") String id,
                                                                       @Field("token") String token);

    /**
     * 忘记密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/find_password")
    Observable<ApiModel> findPassword(@Query("lang") String lang, @Field("email") String email, @Field("mobile") String mobile,
                                      @Field("phone_code") String phone_code, @Field("code") String code,
                                      @Field("pwd") String pwd, @Field("pwd2") String pwd2,
                                      @Field("sid") String sid, @Field("token") String token);

    /**
     * 获取区块高度
     *
     * @return
     */
    @GET("api/area_height")
    Observable<ApiModel<AreaHeightBean>> getAreaHeight(@Query("lang") String lang);

    /**
     * 转账
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/transfer")
    Observable<ApiModel> transfer(@Query("lang") String lang, @Field("amount") String amount, @Field("receive_url") String receive_url,
                                  @Field("send_url") String send_url, @Field("symbol") int symbol,
                                  @Field("fee") String fee, @Field("token") String token);

    /**
     * 获取实时价格
     *
     * @return
     */
    @GET("api/alsc_price")
    Observable<ApiModel<BaseResultBean<AlscPriceBean>>> getAlscPrice(@Query("lang") String lang);

    /**
     * 资产信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/waller_info")
    Observable<ApiModel<WallerInfoBean>> getWallerInfo(@Query("lang") String lang, @Field("token") String token);

    /**
     * 一键共识
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/fund")
    Observable<ApiModel> fund(@Query("lang") String lang, @Field("token") String token);

    /*
     奖池明细
    */
    @FormUrlEncoded
    @POST("api/jackpot")
    Observable<ApiModel<SettlePoolListBean>> getPoolDetail(@Query("lang") String lang, @Field("type") int type,
                                                           @Field("tag") String tag,
                                                           @Field("page_size") int page_size,
                                                           @Field("page_index") int page_index,
                                                           @Field("token") String token);

    /*
     信誉明细
    */
    @FormUrlEncoded
    @POST("api/xingyu_info")
    Observable<ApiModel<CreditDetailListBean>> getCreditDetail(@Query("lang") String lang, @Field("page_size") int page_size,
                                                               @Field("page_index") int page_index,
                                                               @Field("token") String token);


    @FormUrlEncoded
    @POST("api/high_tranfers")
    Observable<ApiModel> highTransfers(@Query("lang") String lang, @Field("token") String token,
                                       @Field("eid") String eid,
                                       @Field("amount") String amount,
                                       @Field("type") String type);

    @FormUrlEncoded
    @POST("api/high_add")
    Observable<ApiModel> addHighAddress(@Query("lang") String lang, @Field("token") String token,
                                        @Field("eid") String eid);

    @FormUrlEncoded
    @POST("api/high_del")
    Observable<ApiModel> delHighAddress(@Query("lang") String lang, @Field("token") String token,
                                        @Field("id") String id);

    @FormUrlEncoded
    @POST("api/high_list")
    Observable<ApiModel<ArrayList<HighTransferBean>>> highTransferList(@Query("lang") String lang, @Field("token") String token);

    @FormUrlEncoded
    @POST("api/high_address")
    Observable<ApiModel<ArrayList<HighAddressBean>>> highAddressList(@Query("lang") String lang, @Field("token") String token);


    /*
     信誉明细
    */
    @FormUrlEncoded
    @POST("api/xy_two")
    Observable<ApiModel<CreditDetailListBean>> getCreditDetails(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                @Field("page_index") int page_index,
                                                                @Field("cid") String cid,
                                                                @Field("token") String token);

    /*
     共享明细
    */
    @FormUrlEncoded
    @POST("api/team_log")
    Observable<ApiModel<BaseListBean<List<ShareTogetherBean>>>> getShareTogetherDetails(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                                        @Field("page_index") int page_index,
                                                                                        @Field("token") String token);

    /*
     共识明细
    */
    @FormUrlEncoded
    @POST("api/frequency")
    Observable<ApiModel<BaseListBean<List<FrequencyBean>>>> getFrequencyDetail(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                               @Field("page_index") int page_index,
                                                                               @Field("token") String token);

    /*
     分享明细
    */
    @FormUrlEncoded
    @POST("api/recommend")
    Observable<ApiModel<BaseListBean<List<RecommmendDetailBean>>>> getRecommmendDetail(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                                       @Field("page_index") int page_index,
                                                                                       @Field("token") String token);

    /*
     参与贡献
    */
    @FormUrlEncoded
    @POST("api/create_heyue")
    Observable<ApiModel<HashMap<String, String>>> joinContribute(@Query("lang") String lang, @Field("usdt") String usdt, @Field("token") String token);

    /*
    参与贡献
   */
    @FormUrlEncoded
    @POST("api/create_heyue")
    Observable<ApiModel<HashMap<String, String>>> joinContribute(@Query("lang") String lang, @Field("usdt") String usdt, @Field("type") String type, @Field("token") String token);

    /*
    验证支付密码
   */
    @FormUrlEncoded
    @POST("api/check_pay")
    Observable<ApiModel> checkPwd(@Query("lang") String lang, @Field("pwd") String pwd, @Field("token") String token);

    /*
    超级节点
   */
    @FormUrlEncoded
    @POST("api/super")
    Observable<ApiModel> joinSuper(@Query("lang") String lang, @Field("price") String price, @Field("token") String token);

    /*
    资产兑换
   */
    @FormUrlEncoded
    @POST("api/turn")
    Observable<ApiModel> exchangeMoney(@Query("lang") String lang, @Field("usdt") String usdt, @Field("alsc") String alsc, @Field("price") String price,
                                       @Field("token") String token);

    /*
     收益记录
    */
    @FormUrlEncoded
    @POST("api/log")
    Observable<ApiModel<BaseListBean<List<LogDetailBean>>>> getLogDetail(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                         @Field("page_index") int page_index,
                                                                         @Field("type") int type,
                                                                         @Field("token") String token);


    /*
 收益记录
*/
    @FormUrlEncoded
    @POST("api/log2")
    Observable<ApiModel<BaseListBean<List<LogDetailBean>>>> getLogDetail2(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                          @Field("page_index") int page_index,
                                                                          @Field("type") int type,
                                                                          @Field("token") String token);

    /*
     兑换记录
    */
    @FormUrlEncoded
    @POST("api/exchange_list")
    Observable<ApiModel<BaseListBean<List<ExchangeDetailBean>>>> getExchangeList(@Query("lang") String lang, @Field("page_size") int page_size,
                                                                                 @Field("page_index") int page_index,
                                                                                 @Field("token") String token);


    /*
    修改昵称
   */
    @FormUrlEncoded
    @POST("api/up_uname")
    Observable<ApiModel> updateUserName(@Query("lang") String lang, @Field("uname") String uname, @Field("token") String token);

    /*
    修改头像（上传图片地址）
   */
    @FormUrlEncoded
    @POST("api/up_url")
    Observable<ApiModel> updateUserAvatar(@Query("lang") String lang, @Field("url") String url, @Field("token") String token);

    /*
    文章详情
   */
    @FormUrlEncoded
    @POST("api/article")
    Observable<ApiModel<ArticleDetailBean>> getArticleDetail(@Query("lang") String lang, @Field("id") String id);

    /*
    修改登录密码
   */
    @FormUrlEncoded
    @POST("api/up_password")
    Observable<ApiModel> updatePassword(@Query("lang") String lang, @Field("old_pwd") String old_pwd, @Field("new_pwd") String new_pwd, @Field("new_pwd2") String new_pwd2,
                                        @Field("code") String code, @Field("type") int type,
                                        @Field("sid") String sid, @Field("token") String token);

    /*
    修改支付密码
   */
    @FormUrlEncoded
    @POST("api/up_pay")
    Observable<ApiModel> updatePayPassword(@Query("lang") String lang, @Field("old_pwd") String old_pwd, @Field("new_pwd") String new_pwd,
                                           @Field("new_pwd2") String new_pwd2, @Field("token") String token);

    /*
    找回支付密码
   */
    @FormUrlEncoded
    @POST("api/find_paypwd")
    Observable<ApiModel> findPassword(@Query("lang") String lang, @Field("new_pwd") String new_pwd, @Field("new_pwd2") String new_pwd2,
                                      @Field("code") String code, @Field("type") int type,
                                      @Field("sid") String sid, @Field("token") String token);

    /*
   绑定/更换手机
    */
    @FormUrlEncoded
    @POST("api/bang_phone")
    Observable<ApiModel> bindPhone(@Query("lang") String lang, @Field("phone") String phone, @Field("code") String code,
                                   @Field("phone_code") String phone_code,
                                   @Field("sid") String sid, @Field("token") String token);

    /*
   绑定/更换邮箱
    */
    @FormUrlEncoded
    @POST("api/bang_email")
    Observable<ApiModel> bindEmail(@Query("lang") String lang, @Field("email") String email, @Field("code") String code,
                                   @Field("sid") String sid, @Field("token") String token);

    /*
    获取游戏地址
        */
    @FormUrlEncoded
    @POST("api/get_addr")
    Observable<ApiModel<GameAddressBean>> getAddress(@Query("lang") String lang, @Field("lan") int lan);

    /*
    获取A13行情
    */
    @GET("api/a13_price")
    Observable<ApiModel<HangQingBean>> getA13Price(@Query("lang") String lang);


    /*
   切换账号时登录接口
    */
    @FormUrlEncoded
    @POST("api/qh_login")
    Observable<ApiModel<UserBean>> hotLogin(@Query("lang") String lang, @Field("name") String name, @Field("password") String password);

    /*
   获取手续费
    */
    @FormUrlEncoded
    @POST("api/get_fee")
    Observable<ApiModel<FeeBean>> getFee(@Query("lang") String lang, @Field("token") String token);

    /*
   检测app新版本
    */
    @FormUrlEncoded
    @POST("api/check_app")
    Observable<ApiModel<VersionBean>> checkVersion(@Query("lang") String lang, @Field("type") int type, @Field("banbenhao") int banbenhao);

    /*
   找回密码验证验证码
    */
    @FormUrlEncoded
    @POST("api/check_code")
    Observable<ApiModel> checkCode(@Query("lang") String lang, @Field("email") String email, @Field("mobile") String mobile,
                                   @Field("phone_code") String phone_code,
                                   @Field("code") String code,
                                   @Field("sid") String sid);

    /*
   冻结记录
    */
    @FormUrlEncoded
    @POST("api/freeze")
    Observable<ApiModel<BaseListBean<List<TransferListBean>>>> getFreezeList(@Query("lang") String lang, @Field("type") int type,
                                                                             @Field("page_size") int page_size,
                                                                             @Field("page_index") int page_index,
                                                                             @Field("token") String token);

    /*
   修改密码验证旧密码
    */
    @FormUrlEncoded
    @POST("api/check_oldpwd")
    Observable<ApiModel> checkOldPwd(@Query("lang") String lang, @Field("old_pwd") String old_pwd,
                                     @Field("new_pwd") String new_pwd,
                                     @Field("new_pwd2") String new_pwd2,
                                     @Field("token") String token);

    /*
   获取获奖公告
    */
    @FormUrlEncoded
    @POST("api/get_award")
    Observable<ApiModel<AwardBean>> getAward(@Query("lang") String lang, @Field("token") String token);


    /*
空投获奖公告
*/
    @FormUrlEncoded
    @POST("api/win_notice")
    Observable<ApiModel<KongtouAwardBean>> winNotice(@Query("lang") String lang, @Field("token") String token, @Field("lan") String lan);


    @FormUrlEncoded
    @POST("api/abnormal")
    Observable<ApiModel<AbNormalBean>> abnormal(@Query("lang") String lang, @Field("token") String token, @Field("lan") String lan);


//    @FormUrlEncoded
//    @GET("api/is_close")
//    Observable<ApiModel<HashMap<String,String>>> isCloseApp(@Query("lan") String lan);


}

package com.wallet.retrofit;

import com.cao.commons.bean.BaseListBean;
import com.cao.commons.bean.cold.ColdBtcBean;
import com.cao.commons.bean.cold.ColdHqBean;
import com.cao.commons.bean.cold.EthgasBean;
import com.cao.commons.bean.cold.RequestWalletBean;
import com.cao.commons.bean.user.TransferListBean;
import com.cao.commons.bean.user.TransferListDetailBean;
import com.cao.commons.model.ApiModel;
import com.wallet.wallet.bean.UtxoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ColdApiService {

    /*
    冷钱包实时价格接口
     */
    @FormUrlEncoded
    @POST("api/cold_hq")
    Observable<ApiModel<ColdHqBean>> getColdHq(@Field("symbol[]") String[] symbol, @Field("tag") String tag);

    /*
    冷钱包实时价格接口
     */
    @GET("api/cold/get_huilv2")
    Observable<ApiModel<ColdHqBean>> getColdHq();


    /*
    查询旷工费范围的接口,可有fastest、fast、average、safeLow选择
     */
    @GET("json/ethgasAPI.json")
    Observable<EthgasBean> ethgasAPI();

    /**
     * 创建或导入冷钱包
     *
     * @param ask
     * @return
     */
    @POST("api/cold/create")
    //Post请求发送数据
    Call<Object> requestColdCreate(@Body RequestWalletBean ask);//@body即非表单请求体，被@Body注解的ask将会被Gson转换成json发送到服务器，返回到Take。 // 其中返回类型为Call<*>，*是接收数据的类

    /**
     * 获取btc余额
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/cold/getBtc")
    Call<ApiModel<ColdBtcBean>> getBtc(@Field("address") String address);

    /**
     * 获取utxo
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/cold/getUtxo")
    Observable<ApiModel<BaseListBean<List<UtxoBean>>>> getUtxo(@Field("address") String address);

    /**
     * 获取交易记录
     *
     * @return
     */
    @GET("api/cold/getList")
    Observable<ApiModel<List<TransferListBean>>> getTradeList(@Query("address") String address, @Query("page_size") int page_size
            , @Query("page_index") int page_index, @Query("symbol") String symbol, @Query("type") int type);


    /**
     * 获取交易记录详情
     *
     * @return
     */
    @GET("api/cold/getInfo")
    Observable<ApiModel<TransferListDetailBean>> getInfo(@Query("id") String id, @Query("addr") String addr);



    /**
     * 导入eth提币交易记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/cold/createETH")
    Observable<ApiModel> createETH(@Field("fromAddress") String fromAddress,
                                   @Field("toAddress") String toAddress,
                                   @Field("txid") String txid,
                                   @Field("symbol") String symbol,
                                   @Field("amount") String amount,
                                   @Field("fee") String fee);

    /**
     * 导入BTC提币交易记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/cold/createBTC")
    Observable<ApiModel> createBTC(@Field("fromAddress") String fromAddress,
                                   @Field("toAddress") String toAddress,
                                   @Field("txid") String txid,
                                   @Field("amount") String amount,
                                   @Field("fee") String fee);

    /**
     * 导入BTC提币交易记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/cold/createOMNI")
    Observable<ApiModel> createOMNI(@Field("fromAddress") String fromAddress,
                                    @Field("toAddress") String toAddress,
                                    @Field("txid") String txid,
                                    @Field("amount") String amount,
                                    @Field("fee") String fee);
}

package com.wallet.retrofit;

import android.content.Context;

import com.cao.commons.bean.BaseListBean;
import com.cao.commons.bean.cold.ColdHqBean;
import com.cao.commons.bean.cold.EthgasBean;
import com.cao.commons.bean.cold.RequestWalletBean;
import com.cao.commons.bean.user.TransferListBean;
import com.cao.commons.bean.user.TransferListDetailBean;
import com.cao.commons.model.ApiModel;
import com.cao.commons.retrofit.RxSchedulers;
import com.cao.commons.util.log.Log;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.bean.ColdWallet;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.wallet.bean.UtxoBean;
import com.wallet.wallet.bean.WalletType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColdInterface {

    /**
     * 冷钱包实时价格接口
     */
    public static void getColdHq(Context context, String Tag, HttpInfoRequest<ColdHqBean> request) {
//        String string = arrayToString(symbol);
        PoliceApi.getColdService().getColdHq()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<ColdHqBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(ColdHqBean commentFlagModel) {
                        if (request != null)
                            request.onSuccess(commentFlagModel);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }




    /**
     * 创建或导入冷钱包
     */
    public static void requestColdCreates(RequestWalletBean bean) {
        PoliceApi.getColdService().requestColdCreate(bean)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e("ColdInterface", "requestColdCreate success");
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e("ColdInterface", t.getMessage());
                    }
                });
    }

    /**
     * 获取btc余额
     */
    public static BigDecimal getBtc(String address) throws IOException {
//        ApiModel<ColdBtcBean> bean = PoliceApi.getColdService().getBtc(address).execute().body();
//        if (bean != null && bean.getData() != null && TextUtils.isEmpty(bean.getData().getFinal_balance())) {
//            return new BigDecimal(bean.getData().getFinal_balance());
//        }
        return new BigDecimal(0);
    }

    /**
     * 获取utxo
     */
    public static void getUtxo(String address, Context context, String Tag, HttpInfoRequest<BaseListBean<List<UtxoBean>>> request) {
        PoliceApi.getColdService().getUtxo(address)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<UtxoBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<UtxoBean>> commentFlagModel) {
                        if (request != null)
                            request.onSuccess(commentFlagModel);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /**
     * 获取交易记录
     */
    public static void getTradeList(String address,int page_size,int page_index,String symbol,int type, Context context, String Tag, HttpInfoRequest<List<TransferListBean>> request) {
        PoliceApi.getColdService().getTradeList(address,page_size,page_index,getWalletType(symbol),type)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<List<TransferListBean>>(context, Tag, false) {
                    @Override
                    public void onSuccess(List<TransferListBean> commentFlagModel) {
                        if (request != null)
                            request.onSuccess(commentFlagModel);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /**
     * 获取交易记录
     */
    public static void getInfo(String id,String addr, Context context, String Tag, HttpInfoRequest<TransferListDetailBean> request) {
        PoliceApi.getColdService().getInfo(id,addr)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<TransferListDetailBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(TransferListDetailBean commentFlagModel) {
                        if (request != null)
                            request.onSuccess(commentFlagModel);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /**
     * 导入BTC提币交易记录
     */
    public static void createBTC(String fromAddress,String toAddress,String txid,String amount,String fee, Context context, String Tag) {
        PoliceApi.getColdService().createBTC(fromAddress,toAddress,txid,amount,fee)
                .compose(RxSchedulers.io_main())
                .subscribe(new Observer<ApiModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiModel apiModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 导入OMNI提币交易记录
     */
    public static void createOMNI(String fromAddress,String toAddress,String txid,String amount,String fee, Context context, String Tag) {
        PoliceApi.getColdService().createOMNI(fromAddress,toAddress,txid,amount,fee)
                .compose(RxSchedulers.io_main())
                .subscribe(new Observer<ApiModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiModel apiModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 导入eth提币交易记录
     */
    public static void createETH(String fromAddress,String toAddress,String txid,String symbol,String amount,String fee, Context context, String Tag) {
        PoliceApi.getColdService().createETH(fromAddress,toAddress,txid,getWalletType(symbol),amount,fee)
                .compose(RxSchedulers.io_main())
                .subscribe(new Observer<ApiModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiModel apiModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 查询旷工费范围的接口,可有fastest、fast、average、safeLow选择
     * https://docs.ethgasstation.info/
     * 注意:要将提供的值转换为gwei,请除以10
     *
     * @param request //     * @param response
     *                //     * @return
     *                //     * @throws InvalidAlgorithmParameterException
     *                //     * @throws NoSuchAlgorithmException
     *                //     * @throws NoSuchProviderException
     *                //     * @throws CipherException
     *                //     * @throws JsonProcessingException
     */
    public static void queryAbsenteeism(HttpInfoRequest<EthgasBean> request) {
        PoliceApi.getColdApiService1().ethgasAPI().compose(RxSchedulers.io_main())
                .subscribe(new Observer<EthgasBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EthgasBean ethgasBean) {
                        if (ethgasBean != null) {
                            if (request != null)
                                request.onSuccess(ethgasBean);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.toast(e.toString());
                        if (request != null)
                            request.onError(-1);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
//        /**
//     * 查询旷工费范围的接口,可有fastest、fast、average、safeLow选择
//     * https://docs.ethgasstation.info/
//     * 注意:要将提供的值转换为gwei,请除以10
//     * @param request
//     * @param response
//     * @return
//     * @throws InvalidAlgorithmParameterException
//     * @throws NoSuchAlgorithmException
//     * @throws NoSuchProviderException
//     * @throws CipherException
//     * @throws JsonProcessingException
//     */
////    @ApiOperation(value = "查询旷工费范围的接口,可有fastest、fast、average、safeLow选择", notes = "响应数据说明：{\"fastest\":\"最快\",\"fast\":\"快\",\"average\":\"平均\",\"safeLow\":\"慢\"}")
//    public Object queryAbsenteeism() {
//        String kk = "https://ethgasstation.info/json/ethgasAPI.json";
//        String ethstring = HttpUtil.get(kk, Charset.defaultCharset());
//        JSONObject ethkjson = JSONObject.fromObject(ethstring);
//        BigDecimal fastest = new BigDecimal(ethkjson.get("fastest").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
//        BigDecimal fast = new BigDecimal(ethkjson.get("fast").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
//        BigDecimal average = new BigDecimal(ethkjson.get("average").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
//        BigDecimal safeLow = new BigDecimal(ethkjson.get("safeLow").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("fastest", fastest);//最快
//        map.put("fast", fast);//快
//        map.put("average", average);//平均
//        map.put("safeLow", safeLow);//慢
//        return ResponseBuilder.custom().success("success", 0).data(map).build();
//    }


    private static String arrayToString(String[] symbol) {
        StringBuilder builder = new StringBuilder();
        if (symbol.length > 1) {
            builder.append("[");
            for (int i = 0; i < symbol.length; i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append("\"");
                builder.append(symbol[i]);
                builder.append("\"");
            }
            builder.append("]");
        } else if (symbol.length == 1) {
            builder.append(symbol[0]);
        }
        return builder.toString();
    }

    public static RequestWalletBean getRequstWallet(int type, String address, String symbol) {
        RequestWalletBean bean = new RequestWalletBean();
        bean.setType(type);
        List<RequestWalletBean.ListBean> list = new ArrayList<>();
        if ("ETH".equals(symbol) || "A13".equals(symbol) || "USDT-ERC20".equals(symbol)) {
            RequestWalletBean.ListBean listBean = new RequestWalletBean.ListBean();
            listBean.setSymbol(getWalletType("ETH"));
            listBean.setAddress(address);
            list.add(listBean);

            RequestWalletBean.ListBean listBean1 = new RequestWalletBean.ListBean();
            listBean1.setSymbol(getWalletType("A13"));
            listBean1.setAddress(address);
            list.add(listBean1);

            RequestWalletBean.ListBean listBean2 = new RequestWalletBean.ListBean();
            listBean2.setSymbol(getWalletType("USDT-ERC20"));
            listBean2.setAddress(address);
            list.add(listBean2);
        }else {
            RequestWalletBean.ListBean listBean = new RequestWalletBean.ListBean();
            listBean.setSymbol(getWalletType("BTC"));
            listBean.setAddress(address);
            list.add(listBean);

            RequestWalletBean.ListBean listBean1 = new RequestWalletBean.ListBean();
            listBean1.setSymbol(getWalletType("USDT-OMNI"));
            listBean1.setAddress(address);
            list.add(listBean1);
        }
        bean.setList(list);
        return bean;
    }

    public static RequestWalletBean getRequstWallet(ColdWallet coldWallet) {
        RequestWalletBean bean = new RequestWalletBean();
        bean.setType(1);
        List<RequestWalletBean.ListBean> list = new ArrayList<>();
        for (JnWallet wallet:coldWallet.getJnWallets()){
            RequestWalletBean.ListBean listBean = new RequestWalletBean.ListBean();
            listBean.setSymbol(getWalletType(wallet.getWalletType()));
            listBean.setAddress(wallet.getAddress());
            list.add(listBean);
        }
        bean.setList(list);
        return bean;
    }

    private static String getWalletType(String mSymbol) {
        if ("ETH".equals(mSymbol)) {
            return "ETH";
        } else if ("USDT-OMNI".equals(mSymbol)) {
            return "USDT";
        } else if ("USDT-ERC20".equals(mSymbol)) {
            return "ETHUSDT";
        } else if ("A13".equals(mSymbol)) {
            return "A13";
        }
        return "BTC";
    }

    private static String getWalletType(WalletType mSymbol) {
        if (WalletType.ETH == mSymbol) {
            return "ETH";
        } else if (WalletType.USDT_OMNI == mSymbol) {
            return "USDT";
        } else if (WalletType.USDT_ERC20 == mSymbol) {
            return "ETHUSDT";
        } else if (WalletType.A13 == mSymbol) {
            return "A13";
        }
        return "BTC";
    }

}

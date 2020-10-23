package com.wallet.retrofit;

import android.content.Context;

import com.cao.commons.SPConstants;
import com.cao.commons.base.PoliceApplication;
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
import com.cao.commons.bean.login.SettingPasswordRequest;
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
import com.cao.commons.bean.user.RegisterRequest;
import com.cao.commons.bean.user.SettlePoolListBean;
import com.cao.commons.bean.user.ShareTogetherBean;
import com.cao.commons.bean.user.TransferListBean;
import com.cao.commons.bean.user.TransferListDetailBean;
import com.cao.commons.bean.user.WallerInfoBean;
import com.cao.commons.manager.DataManager;
import com.cao.commons.retrofit.RxSchedulers;
import com.common.utils.NetUtil;
import com.common.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class HttpInterface {

    /**
     * 获取图片验证码
     */
    public static void getPicCode(Context context, String Tag, boolean isShowDialog, HttpInfoRequest<PicCodeResultBean> request) {
        PoliceApi.getV1ApiService().getPicCode()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<PicCodeResultBean>(context, Tag, isShowDialog) {
                    @Override
                    public void onSuccess(PicCodeResultBean commentFlagModel) {
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

    /*
    获取手机验证码
     */
    public static void getMobileCode(String phone, String phone_code, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().getMobileCode(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), phone, phone_code, sid)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
    获取邮箱验证码
     */
    public static void getEmailCode(String email, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().getEmailCode(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), email, sid)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
        注册第一步
        */
    public static void register1(String username, String invite_code, String pic_code, String sid, Context context, String Tag, HttpInfoRequest<RegisterStepBean> request) {
        PoliceApi.getV1ApiService().register1(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), username, pic_code, invite_code, sid)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<RegisterStepBean>(context, Tag, true) {
                    @Override
                    public void onSuccess(RegisterStepBean commentFlagModel) {
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

    /*
        注册第二步
        */
    public static void register2(RegisterRequest registerRequest, Context context, String Tag, HttpInfoRequest<RegisterStepBean> request) {
        PoliceApi.getV1ApiService().register2(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), registerRequest.getPhone(), registerRequest.getPhone_area(),
                registerRequest.getEmail(), registerRequest.getCaptcha(), registerRequest.getUser_id(), registerRequest.getSid())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<RegisterStepBean>(context, Tag, true) {
                    @Override
                    public void onSuccess(RegisterStepBean commentFlagModel) {
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

    /*
    注册第三步
    */
    public static void register3(RegisterRequest registerRequest, Context context, String Tag, HttpInfoRequest<UserBean> request) {
        PoliceApi.getV1ApiService().register3(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), registerRequest.getUser_id(), registerRequest.getPwd(),
                registerRequest.getPwd2(), registerRequest.getPay_pwd(), registerRequest.getPay_pwd2())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<UserBean>(context, Tag, true) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        if (request != null)
                            request.onSuccess(userBean);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
   获取手机区号列表
   */
    public static void getPhoneCode(Context context, String Tag, HttpInfoRequest<PhoneCodeResultEntity> request) {
        int lan = 2;
        if (DataManager.getInstance().getLanguage() == 1) {
            lan = 1;
        } else if (DataManager.getInstance().getLanguage() == 2) {
            lan = 3;
        } else if (DataManager.getInstance().getLanguage() == 3) {
            lan = 4;
        } else if (DataManager.getInstance().getLanguage() == 4) {
            lan = 5;
        }
        PoliceApi.getV1ApiService().getPhoneCode(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), lan)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<PhoneCodeResultEntity>(context, Tag, true) {
                    @Override
                    public void onSuccess(PhoneCodeResultEntity commentFlagModel) {
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

    /*
   获取行情接口
   */
    public static void getHangq(Context context, String Tag, HttpInfoRequest<BaseListBean<ArrayList<DiscoverHangBean>>> request) {
        PoliceApi.getV1ApiService().getHangq(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<ArrayList<DiscoverHangBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<ArrayList<DiscoverHangBean>> commentFlagModel) {
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

//    /*
//  获取行情接口
//  */
//    public static void getHotHangq(Context context, String Tag, HttpInfoRequest<ArrayList<DiscoverHangBean>> request) {
//        PoliceApi.getV1ApiService().getHotHangq()
//                .compose(RxSchedulers.io_main())
//                .subscribe(new RxObserver<ArrayList<DiscoverHangBean>>(context, Tag, false) {
//                    @Override
//                    public void onSuccess(ArrayList<DiscoverHangBean> commentFlagModel) {
//                        if (request != null)
//                            request.onSuccess(commentFlagModel);
//                    }
//
//                    @Override
//                    public void onErrors(int eCode) {
//                        super.onErrors(eCode);
//                        if (request != null)
//                            request.onError(eCode);
//                    }
//                });
//    }
//
//    /*
//  行情列表
//  */
//    public static void getHangQList(Context context, String Tag, HttpInfoRequest<ArrayList<DiscoverHangBean>> request) {
//        PoliceApi.getV1ApiService().getHangQList()
//                .compose(RxSchedulers.io_main())
//                .subscribe(new RxObserver<ArrayList<DiscoverHangBean>>(context, Tag, false) {
//                    @Override
//                    public void onSuccess(ArrayList<DiscoverHangBean> commentFlagModel) {
//                        if (request != null)
//                            request.onSuccess(commentFlagModel);
//                    }
//
//                    @Override
//                    public void onErrors(int eCode) {
//                        super.onErrors(eCode);
//                        if (request != null)
//                            request.onError(eCode);
//                    }
//                });
//    }

    /*
   公告列表
   */
    public static void getNotice(int type, int pageSize, int page, Context context, String Tag, HttpInfoRequest<BaseListBean<ArrayList<DiscoverNoticeBean>>> request) {
        PoliceApi.getV1ApiService().getNotice(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), type, pageSize, page, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<ArrayList<DiscoverNoticeBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<ArrayList<DiscoverNoticeBean>> commentFlagModel) {
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

    /*
  公告列表冷钱包
  */
    public static void getColdNotice(int type, int pageSize, int page, Context context, String Tag, HttpInfoRequest<BaseListBean<ArrayList<DiscoverNoticeBean>>> request) {
        PoliceApi.getV1ApiService().getColdNotice(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), type, pageSize, page)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<ArrayList<DiscoverNoticeBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<ArrayList<DiscoverNoticeBean>> commentFlagModel) {
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

    /*
   登录
   */
    public static void login(String account, String password, String code, String sid, Context context, String Tag, HttpInfoRequest<UserBean> request) {
        PoliceApi.getV1ApiService().login(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), account, password, code, sid)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<UserBean>(context, Tag, true) {
                    @Override
                    public void onSuccess(UserBean commentFlagModel) {
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

    /*
  登录
  */
    public static void hotLogin(String account, String password, Context context, String Tag, HttpInfoRequest<UserBean> request) {
        PoliceApi.getV1ApiService().hotLogin(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), account, password)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<UserBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(UserBean commentFlagModel) {
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

    /*
   登录
   */
    public static void getUserIndex(Context context, String Tag, HttpInfoRequest<MyUserInfoBean> request) {
        PoliceApi.getV1ApiService().getUserIndex(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<MyUserInfoBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(MyUserInfoBean commentFlagModel) {
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

    /*
   转账记录
   */
    public static void getTransferList(int symbol, int type, int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<TransferListBean>>> request) {
        PoliceApi.getV1ApiService().getTransferList(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), symbol, type, page_size, page_index, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<TransferListBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<TransferListBean>> commentFlagModel) {
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

    /*
转账记录
*/
    public static void getTransferList2(int symbol, int type, int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<TransferListBean>>> request) {
        PoliceApi.getV1ApiService().getTransferList2(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), symbol, type, page_size, page_index, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<TransferListBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<TransferListBean>> commentFlagModel) {
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

    /*
   转账记录
   */
    public static void getFreezeList(int type, int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<TransferListBean>>> request) {
        PoliceApi.getV1ApiService().getFreezeList(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), type, page_size, page_index, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<TransferListBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<TransferListBean>> commentFlagModel) {
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

    /*
   转账记录详情
   */
    public static void getTransferListDetail(String id, Context context, String Tag, HttpInfoRequest<TransferListDetailBean> request) {
        PoliceApi.getV1ApiService().getTransferListDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), id, DataManager.getInstance().getToken())
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

    /*
    忘记密码
     */
    public static void findPassword(SettingPasswordRequest requests, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().findPassword(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), requests.getEmail(), requests.getMobile(), requests.getPhoneCode(),
                requests.getCode(), requests.getPwd(), requests.getPwd2(), requests.getSid(), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
   获取区块高度
   */
    public static void getAreaHeight(Context context, String Tag, HttpInfoRequest<AreaHeightBean> request) {
        PoliceApi.getV1ApiService().getAreaHeight(Utils.getLanguageStr(DataManager.getInstance().getLanguage()))
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<AreaHeightBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(AreaHeightBean commentFlagModel) {
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

    /*
   转账
   */
    public static void transfer(String amount, String receive_url, String send_url, int symbol, String fee, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().transfer(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), amount, receive_url, send_url, symbol, fee, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
   获取实时价格
   */
    public static void getAlscPrice(Context context, String Tag, HttpInfoRequest<BaseResultBean<AlscPriceBean>> request) {
        PoliceApi.getV1ApiService().getAlscPrice(Utils.getLanguageStr(DataManager.getInstance().getLanguage()))
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseResultBean<AlscPriceBean>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseResultBean<AlscPriceBean> commentFlagModel) {
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

    /*
  资产信息
  */
    public static void getWallerInfo(Context context, String Tag, HttpInfoRequest<WallerInfoBean> request) {
        PoliceApi.getV1ApiService().getWallerInfo(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<WallerInfoBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(WallerInfoBean commentFlagModel) {
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

    /*
一键共识
*/
    public static void fund(Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().fund(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object o) {
                        if (request != null)
                            request.onSuccess(o);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
高频交易
*/
    public static void highTransfers(Context context, String eid, String amount, String type, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().highTransfers(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken(), eid, amount, type)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object o) {
                        if (request != null)
                            request.onSuccess(o);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
高频交易记录
*/
    public static void highTransferList(Context context, String Tag, HttpInfoRequest<ArrayList<HighTransferBean>> request) {
        PoliceApi.getV1ApiService().highTransferList(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .subscribe(new RxObserver<ArrayList<HighTransferBean>>(context, Tag, false) {
                    @Override
                    public void onSuccess(ArrayList<HighTransferBean> list) {
                        if (request != null)
                            request.onSuccess(list);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
高频交易地址薄
*/
    public static void highAddressList(Context context, String Tag, HttpInfoRequest<ArrayList<HighAddressBean>> request) {
        PoliceApi.getV1ApiService().highAddressList(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .subscribe(new RxObserver<ArrayList<HighAddressBean>>(context, Tag, false) {
                    @Override
                    public void onSuccess(ArrayList<HighAddressBean> list) {
                        if (request != null)
                            request.onSuccess(list);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
添加高频交易地址
*/
    public static void addHighAddress(Context context, String eid, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().addHighAddress(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken(), eid)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object o) {
                        if (request != null)
                            request.onSuccess(o);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
删除添加高频交易地址
*/
    public static void deleteHighAddress(Context context, String id, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().delHighAddress(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken(), id)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object o) {
                        if (request != null)
                            request.onSuccess(o);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
   奖池明细
   */
    public static void getPoolDetail(int type, String tag, int page_size, int page_index, Context context, String Tag, HttpInfoRequest<SettlePoolListBean> request) {
        PoliceApi.getV1ApiService().getPoolDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), type, tag, page_size, page_index, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<SettlePoolListBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(SettlePoolListBean commentFlagModel) {
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

    /*
   信誉明细
   */
    public static void getCreditDetail(int page_size, int page_index, Context context, String Tag, HttpInfoRequest<CreditDetailListBean> request) {
        PoliceApi.getV1ApiService().getCreditDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<CreditDetailListBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(CreditDetailListBean commentFlagModel) {
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

    /*
  信誉明细
  */
    public static void getCreditDetails(int page_size, int page_index, String cid, Context context, String Tag, HttpInfoRequest<CreditDetailListBean> request) {
        PoliceApi.getV1ApiService().getCreditDetails(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, cid, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<CreditDetailListBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(CreditDetailListBean commentFlagModel) {
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

    /*
  共享明细
  */
    public static void getShareTogetherDetails(int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<ShareTogetherBean>>> request) {
        PoliceApi.getV1ApiService().getShareTogetherDetails(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<ShareTogetherBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<ShareTogetherBean>> commentFlagModel) {
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

    /*
   共识明细
   */
    public static void getFrequencyDetail(int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<FrequencyBean>>> request) {
        PoliceApi.getV1ApiService().getFrequencyDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<FrequencyBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<FrequencyBean>> commentFlagModel) {
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

    /*
  分享明细
  */
    public static void getRecommmendDetail(int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<RecommmendDetailBean>>> request) {
        PoliceApi.getV1ApiService().getRecommmendDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<RecommmendDetailBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<RecommmendDetailBean>> commentFlagModel) {
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


    /*
   参与贡献
   */
    public static void joinContribute(String amount, Context context, String Tag, HttpInfoRequest<HashMap<String, String>> request) {
        PoliceApi.getV1ApiService().joinContribute(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), amount, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<HashMap<String, String>>(context, Tag, true) {
                    @Override
                    public void onSuccess(HashMap<String, String> map) {
                        if (request != null)
                            request.onSuccess(map);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    public static void joinContribute(String amount, String type, Context context, String Tag, HttpInfoRequest<HashMap<String, String>> request) {
        PoliceApi.getV1ApiService().joinContribute(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), amount, type, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<HashMap<String, String>>(context, Tag, true) {
                    @Override
                    public void onSuccess(HashMap<String, String> map) {
                        if (request != null)
                            request.onSuccess(map);
                    }

                    @Override
                    public void onErrors(int eCode) {
                        super.onErrors(eCode);
                        if (request != null)
                            request.onError(eCode);
                    }
                });
    }

    /*
   验证支付密码
   */
    public static void checkPwd(String pwd, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().checkPwd(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), pwd, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, false) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
   超级节点
   */
    public static void joinSuper(String price, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().joinSuper(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), price, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
  资产兑换
  */
    public static void exchangeMoney(String usdt, String alsc, String price, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().exchangeMoney(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), usdt, alsc, price, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
   收益记录
   */
    public static void getLogDetail(int page_size, int page_index, int type, Context context, String Tag, HttpInfoRequest<BaseListBean<List<LogDetailBean>>> request) {
        PoliceApi.getV1ApiService().getLogDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, type, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<LogDetailBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<LogDetailBean>> commentFlagModel) {
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

    /*
收益记录
*/
    public static void getLogDetail2(int page_size, int page_index, int type, Context context, String Tag, HttpInfoRequest<BaseListBean<List<LogDetailBean>>> request) {
        PoliceApi.getV1ApiService().getLogDetail2(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, type, DataManager.getInstance().getToken())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        return NetUtil.isConnected(PoliceApplication.getInstance()) &&
                                (throwable instanceof IOException ||
                                        throwable instanceof TimeoutException);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<LogDetailBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<LogDetailBean>> commentFlagModel) {
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

    /*
   收益记录
   */
    public static void getExchangeList(int page_size, int page_index, Context context, String Tag, HttpInfoRequest<BaseListBean<List<ExchangeDetailBean>>> request) {
        PoliceApi.getV1ApiService().getExchangeList(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), page_size, page_index, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<BaseListBean<List<ExchangeDetailBean>>>(context, Tag, false) {
                    @Override
                    public void onSuccess(BaseListBean<List<ExchangeDetailBean>> commentFlagModel) {
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

    /*
  修改昵称
  */
    public static void updateUserName(String uname, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().updateUserName(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), uname, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
  修改头像（上传图片地址）
  */
    public static void updateUserAvatar(String url, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().updateUserAvatar(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), url, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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


    /*
   文章详情
   */
    public static void getArticleDetail(String id, Context context, String Tag, HttpInfoRequest<ArticleDetailBean> request) {
        PoliceApi.getV1ApiService().getArticleDetail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), id)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<ArticleDetailBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(ArticleDetailBean commentFlagModel) {
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

    /*
  修改登录密码
  */
    public static void updatePassword(String old_pwd, String new_pwd, String code, int type, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().updatePassword(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), old_pwd, new_pwd, new_pwd, code, type, sid, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
 修改支付密码
 */
    public static void updatePayPassword(String old_pwd, String new_pwd, String new_pwd2, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().updatePayPassword(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), old_pwd, new_pwd, new_pwd2, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
 修改登录密码
 */
    public static void findPassword(String new_pwd, String new_pwd2, String code, int type, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().findPassword(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), new_pwd, new_pwd2, code, type, sid, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
    绑定/更换手机
    */
    public static void bindPhone(String phone, String code, String phone_code, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().bindPhone(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), phone, code, phone_code, sid, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
    绑定/更换邮箱
    */
    public static void bindEmail(String phone, String code, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().bindEmail(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), phone, code, sid, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
  读取公告
  */
    public static void readNotice(String id, Context context, String Tag, boolean showDialog, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().readNotice(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), id, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, showDialog) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
   获取游戏地址
   */
    public static void getAddress(Context context, String Tag, HttpInfoRequest<GameAddressBean> request) {
        int lan = DataManager.getInstance().getLanguage() == 0 ? 1 : 2;
        PoliceApi.getV1ApiService().getAddress(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), lan)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<GameAddressBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(GameAddressBean commentFlagModel) {
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

    public static void getA13Price(Context context, String Tag, HttpInfoRequest<HangQingBean> request) {
        int lan = DataManager.getInstance().getLanguage() == 0 ? 1 : 2;
        PoliceApi.getV1ApiService().getA13Price(Utils.getLanguageStr(DataManager.getInstance().getLanguage()))
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<HangQingBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(HangQingBean commentFlagModel) {
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

    /*
       获取手续费
       */
    public static void getFee(Context context, String Tag, HttpInfoRequest<FeeBean> request) {
        PoliceApi.getV1ApiService().getFee(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<FeeBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(FeeBean commentFlagModel) {
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

    /*
       检测app新版本
       */
    public static void checkVersion(Context context, String Tag, boolean isShowDialog, HttpInfoRequest<VersionBean> request) {
        PoliceApi.getV1ApiService().checkVersion(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), 1, SPConstants.CURRENT_VERSION_CODE)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<VersionBean>(context, Tag, isShowDialog) {
                    @Override
                    public void onSuccess(VersionBean commentFlagModel) {
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


    /*
  验证支付密码
  */
    public static void checkCode(String email, String mobile, String phone_code, String code, String sid, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().checkCode(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), email, mobile, phone_code, code, sid)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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


    /*
     修改密码验证旧密码
     */
    public static void checkOldPwd(String old_pwd, String new_pwd, Context context, String Tag, HttpInfoRequest request) {
        PoliceApi.getV1ApiService().checkOldPwd(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), old_pwd, new_pwd, new_pwd, DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver(context, Tag, true) {
                    @Override
                    public void onSuccess(Object commentFlagModel) {
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

    /*
       获取获奖公告
       */
    public static void getAward(Context context, String Tag, HttpInfoRequest<AwardBean> request) {
        PoliceApi.getV1ApiService().getAward(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<AwardBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(AwardBean commentFlagModel) {
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

    /*
   空投获奖公告
   */
    public static void winNotice(Context context, String Tag, HttpInfoRequest<KongtouAwardBean> request) {
        PoliceApi.getV1ApiService().winNotice(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken(), DataManager.getInstance().getLanguage() == 0 ? "cn" : "en")
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<KongtouAwardBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(KongtouAwardBean commentFlagModel) {
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

    public static void abnormal(Context context, String Tag, HttpInfoRequest<AbNormalBean> request) {
        PoliceApi.getV1ApiService().abnormal(Utils.getLanguageStr(DataManager.getInstance().getLanguage()), DataManager.getInstance().getToken(), DataManager.getInstance().getLanguage() == 0 ? "cn" : "en")
                .compose(RxSchedulers.io_main())
                .subscribe(new RxObserver<AbNormalBean>(context, Tag, false) {
                    @Override
                    public void onSuccess(AbNormalBean commentFlagModel) {
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

//    public static void isCloseApp(Context context, String Tag, HttpInfoRequest<HashMap<String,String>> request) {
//        PoliceApi.getV1ApiService().isCloseApp(DataManager.getInstance().getLanguage() == 0 ? "cn" : "en")
//                .compose(RxSchedulers.io_main())
//                .subscribe(new RxObserver<HashMap<String,String>>(context, Tag, false) {
//                    @Override
//                    public void onSuccess(HashMap<String,String> map) {
//                        if (request != null)
//                            request.onSuccess(map);
//                    }
//
//                    @Override
//                    public void onErrors(int eCode) {
//                        super.onErrors(eCode);
//                        if (request != null)
//                            request.onError(eCode);
//                    }
//                });
//    }
}

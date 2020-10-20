package com.cao.commons.retrofit;

import android.content.Context;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cao.commons.R;
import com.cao.commons.model.ApiModel;
import com.cao.commons.util.Utils;
import com.cao.commons.widget.svprogresshud.SVProgressHUD;

import org.greenrobot.eventbus.EventBus;

import java.io.EOFException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * 处理统一返回结果，回调到界面层
 *
 * @author Y
 * @version v1.0
 * @Time 2018-11-10
 */

public abstract class RxObserver<T> implements Observer<ApiModel<T>> {
    private static final String TAG = "RxObserver";
    private String mKey;
    private RxManager mRxManager;

    private boolean isShowDialog;
    private boolean isShowDialogError = true;
    private SVProgressHUD mDialog;
    private Context mContext;

    public static int RETRY_TIME = 0;

    public RxObserver(Context context, String key, boolean isShowDialog) {
        this.mContext = context;
        this.mKey = key;
        this.isShowDialog = isShowDialog;
        mDialog = new SVProgressHUD(context);
        mRxManager = RxManager.getInstance();
    }

    public RxObserver(Context context, String key, boolean isShowDialog, boolean isShowDialogError) {
        this.mContext = context;
        this.mKey = key;
        this.isShowDialogError = isShowDialogError;
        this.isShowDialog = isShowDialog;
        mDialog = new SVProgressHUD(context);
        mRxManager = RxManager.getInstance();
    }

    @Override
    public void onNext(ApiModel<T> value) {
        Log.e(TAG, "onNext: " + value.toString());
        if (mDialog != null)
            mDialog.dismiss();
        if (value.getCode() == 4200) {
            HashMap<String, String> map = new HashMap<>();
            map.put("login_out", "");
            EventBus.getDefault().post(map);
        } else if (value.getCode() == 1)
            onSuccess(value.getData());
        else {
            onErrors(value.getCode());
//            if (value.getCode() != SPConstants.ERROR_CODE_INSUFFICIENT_BALANCE && value.getCode() != SPConstants.ERROR_USER_PAYMENT_PASSWORD_NUll
//                    && value.getCode() != SPConstants.ERROR_USER_PASSWORD_WRONG)
//                showInfoWithStatus(value.getMsg());
            Toast.makeText(mContext, Utils.getCodeMessage(mContext, value.getCode()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mRxManager.add(mKey, d);
        if (isShowDialog) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (mDialog != null)
                        mDialog.showWithStatus(mContext.getString(R.string.network_loading), SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            });
        }
    }

    @Override
    public void onComplete() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (e instanceof EOFException ||
                e instanceof SocketException ||
                e instanceof SocketTimeoutException ||
                e instanceof UnknownHostException) {
            e.printStackTrace();
            onErrors(HttpConstants.NETWORK_EXCPTION);
            if (null != mContext) {
                showInfoWithStatus(mContext.getResources().getString(R.string.network_excption));
            }
//            ++RxObserver.RETRY_TIME;
//            if (RxObserver.RETRY_TIME > 3) {
//                PoliceBaseUrl.resetCurrentV();
//            }
        } else if (e instanceof HttpException) {
            //自定义错误请求方式
            //onHttpError(((HttpException) e).code(), ApiModel.handle(e));
            if (null != mContext) {
                showInfoWithStatus(mContext.getResources().getString(R.string.network_excption));
            }
        } else {
            e.printStackTrace();
            onErrors(HttpConstants.UNKNOWN_EXCPTION);
            if (null != mContext) {
                showInfoWithStatus(mContext.getResources().getString(R.string.network_excption));
            }
        }
    }

    public void onErrors(int eCode) {
    }

    public abstract void onSuccess(T t);

    private void showInfoWithStatus(final String msg) {
        if (isShowDialog) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
                    View view = View
                            .inflate(mContext, R.layout.custom_dialog, null);
                    mDialog.setView(view);

                    TextView mTvDialogContent = view
                            .findViewById(R.id.mTvDialogContent);
                    TextView mTvDialogDismm = view
                            .findViewById(R.id.mTvDialogDismm);
                    mTvDialogContent.setText(msg);

                    final AlertDialog dialog = mDialog.setCancelable(false).create();
                    dialog.show();
                    mTvDialogDismm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }, 500);
        }
    }
}

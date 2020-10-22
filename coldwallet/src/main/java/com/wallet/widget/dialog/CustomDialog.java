package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cao.commons.bean.VersionBean;
import com.cao.commons.util.Utils;
import com.cold.wallet.R;
import com.wallet.retrofit.PoliceApi;
import com.wallet.utils.DensityUtil;
import com.wallet.utils.ToastUtil;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private TextView mOkButton;
    private TextView mCancelButton;
    private TextView tv_message;
    private Context mContext;
    private int switchType;
    private VersionBean mVersionBean;

    public CustomDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_custom);
        mContext = context;
        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(66); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.CENTER);

        tv_message = findViewById(R.id.tv_message);
        mOkButton = findViewById(R.id.id_textview_popup_ok);
        mCancelButton = findViewById(R.id.id_textview_popup_cancel);
        findViewById(R.id.id_textview_popup_ok).setOnClickListener(this);
        findViewById(R.id.id_textview_popup_cancel).setOnClickListener(this);
    }


    public void showDialog(int walletType) {
        if (walletType == 0) {
            switchType = 0;
            tv_message.setText(mContext.getString(R.string.wv_create_cold_wallet));
        } else if (walletType == 1) {
            switchType = 1;
            tv_message.setText(mContext.getString(R.string.wv_create_hot_wallet));
        } else if (walletType == 101) {
            switchType = walletType;
            tv_message.setText(mContext.getString(R.string.wv_delete_cold_wallet));
            mOkButton.setText(mContext.getString(R.string.wh_ok));
        } else if (walletType == 102) {
            switchType = walletType;
            tv_message.setText(mContext.getString(R.string.wv_login_out));
            mOkButton.setText(mContext.getString(R.string.wh_ok));
        }
        show();
    }

    public void showDialog(VersionBean bean) {
        switchType = 200;
        mVersionBean = bean;
        String content = "";
        if (bean.getDesc().contains("&")) {
            String[] strs = bean.getDesc().split("&");
            int length = strs.length;
            for (int i = 0; i < length; ++i) {
                content += strs[i];
                if (i < length - 1) {
                    content += "\n";
                }
            }
        } else {
            content = bean.getDesc();
        }
        tv_message.setText(content);
        mOkButton.setText(mContext.getString(R.string.wh_ok));
        if (bean.getType() == 2) {
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            mCancelButton.setVisibility(View.GONE);
        }
        show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_textview_popup_ok) {
            if (switchType == 0 || switchType == 1) {

                Utils.loginOutAndGoToType(mContext);
                dismiss();
            } else if (switchType == 101) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                dismiss();
            } else if (switchType == 102) {
                PoliceApi.clearHotService();
                Utils.loginOutAndGoToType(mContext);
                dismiss();
            } else if (switchType == 200) {
                if (!TextUtils.isEmpty(mVersionBean.getAzUrl())) {
                    Uri uri = Uri.parse(mVersionBean.getAzUrl());    //设置跳转的网站
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                    if (mVersionBean.getType() == 1) {
                        dismiss();
                    }
                } else {
                    ToastUtil.toast(mContext.getString(R.string.new_version_url_empty));
                    dismiss();
                }
            }
        } else if (id == R.id.id_textview_popup_cancel) {
            dismiss();
        }
    }

    public View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

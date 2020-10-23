package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cold.wallet.R;
import com.wallet.utils.DensityUtil;

public class ColdWalletTextDialog extends Dialog {
    private TextView mOkButton;
    private TextView mCancelButton;
    private TextView tv_message;
    private Context mContext;

    public ColdWalletTextDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public ColdWalletTextDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_cold_wallet_text);
        mContext = context;
        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(30); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.CENTER);

        tv_message = findViewById(R.id.tv_message);
        mOkButton = findViewById(R.id.id_textview_popup_ok);
        mCancelButton = findViewById(R.id.id_textview_popup_cancel);
    }

    public void showDialog(String content ){
        tv_message.setText(content);
        show();
    }

    public TextView getmOkButton() {
        return mOkButton;
    }

    public TextView getmCancelButton() {
        return mCancelButton;
    }
}

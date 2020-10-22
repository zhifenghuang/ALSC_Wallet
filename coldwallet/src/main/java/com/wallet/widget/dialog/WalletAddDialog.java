package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cold.wallet.R;
import com.wallet.activity.cold.ColdImportActivity;
import com.wallet.activity.cold.ColdWalletCreateActivity;
import com.wallet.utils.DensityUtil;

public class WalletAddDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private String mSymbol;

    public WalletAddDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public WalletAddDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.dialog_wallet_add);


        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(26 * 2); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.BOTTOM);
        view.setWindowAnimations(R.style.dialog_slide_up_down); //设置窗口弹出动画

        findViewById(R.id.tv_create).setOnClickListener(this);
        findViewById(R.id.tv_import).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    public void showDialog(String symbol){
        this.mSymbol = symbol;
        show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_create) {
            ColdWalletCreateActivity.startActivity(mContext, mSymbol);
            dismiss();
        } else if (id == R.id.tv_import) {
            ColdImportActivity.startActivity(mContext, mSymbol);
            dismiss();
        } else if (id == R.id.tv_cancel) {
            dismiss();
        }
    }
}
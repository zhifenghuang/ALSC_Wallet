package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cold.wallet.R;
import com.wallet.utils.DensityUtil;

public class WalletPathDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private String mSymbol;

    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;

    public WalletPathDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public WalletPathDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.dialog_wallet_path);


        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(26 * 2); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.BOTTOM);
        view.setWindowAnimations(R.style.dialog_slide_up_down); //设置窗口弹出动画

        tv_item1 = findViewById(R.id.tv_item1);
        tv_item2 = findViewById(R.id.tv_item2);
        tv_item3 = findViewById(R.id.tv_item3);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    public void showDialog(String symbol) {
        this.mSymbol = symbol;
        show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }
    }

    public TextView getTv_item1() {
        return tv_item1;
    }

    public TextView getTv_item2() {
        return tv_item2;
    }

    public TextView getTv_item3() {
        return tv_item3;
    }
}
package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cold.wallet.R;
import com.wallet.utils.DensityUtil;

public class AddressDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public AddressDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public AddressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.dialog_address);


        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(26 * 2); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.BOTTOM);
        view.setWindowAnimations(R.style.dialog_slide_up_down); //设置窗口弹出动画

        findViewById(R.id.tv_btc).setOnClickListener(this);
        findViewById(R.id.tv_eth).setOnClickListener(this);
        findViewById(R.id.tv_omni).setOnClickListener(this);
        findViewById(R.id.tv_erc).setOnClickListener(this);
        findViewById(R.id.tv_alsc).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_btc) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick("BTC");
            }
            dismiss();
        } else if (id == R.id.tv_eth) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick("ETH");
            }
            dismiss();
        } else if (id == R.id.tv_omni) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick("USDT-OMNI");
            }
            dismiss();
        } else if (id == R.id.tv_erc) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick("USDT-ERC20");
            }
            dismiss();
        } else if (id == R.id.tv_alsc) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick("A13");
            }
            dismiss();
        } else if (id == R.id.tv_cancel) {
            dismiss();
        }
    }

}
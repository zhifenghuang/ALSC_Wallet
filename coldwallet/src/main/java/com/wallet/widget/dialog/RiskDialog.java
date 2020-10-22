package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cold.wallet.R;
import com.wallet.utils.DensityUtil;

/**
 * 描述结果
 *
 * @author xyx on 2020/4/2 0002
 * @e-mail 384744573@qq.com
 * @see [相关类/方法](可选)
 */
public class RiskDialog extends Dialog implements View.OnClickListener {
    private Context mContext;

    public RiskDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public RiskDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_risk);
        mContext = context;
        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(45*2); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.CENTER);

        findViewById(R.id.iv_close).setOnClickListener(this);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close) {
            dismiss();
        }
    }

    public void showDialog() {

        show();
    }
}

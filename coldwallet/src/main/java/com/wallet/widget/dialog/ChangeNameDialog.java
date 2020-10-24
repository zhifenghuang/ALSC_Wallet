package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cold.wallet.R;
import com.wallet.utils.DensityUtil;

public class ChangeNameDialog extends Dialog {
    private TextView mOkButton;
    private TextView mCancelButton;
    public EditText et_content;
    private Context mContext;

    public ChangeNameDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public ChangeNameDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_change_name);
        mContext = context;
        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width =  DensityUtil.dip2px(260); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.CENTER);

        et_content = findViewById(R.id.et_content);
        mOkButton = findViewById(R.id.id_textview_popup_ok);
        mCancelButton = findViewById(R.id.id_textview_popup_cancel);
    }


    public TextView getmOkButton() {
        return mOkButton;
    }

    public TextView getmCancelButton() {
        return mCancelButton;
    }

    public void showDialog(String toString) {
        et_content.setText(toString);
        et_content.setSelection(toString.length());
        show();
    }
}

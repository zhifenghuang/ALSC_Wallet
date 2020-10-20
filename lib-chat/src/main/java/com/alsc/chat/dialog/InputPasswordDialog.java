package com.alsc.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alsc.chat.R;
import com.alsc.chat.activity.ChatBaseActivity;
import com.alsc.chat.http.HttpMethods;
import com.alsc.chat.http.HttpObserver;
import com.alsc.chat.http.OnHttpErrorListener;
import com.alsc.chat.http.SubscriberOnNextListener;
import com.alsc.chat.utils.Utils;
import com.cao.commons.manager.DataManager;

public class InputPasswordDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private EditText etPayPassword;

    private OnInputPasswordListener mOnInputPasswordListener;

    public InputPasswordDialog(Context context, float amount, String title, String content) {
        this(context, R.style.LoadingDialog, amount, title, content);
    }

    public InputPasswordDialog(Context context, int themeResId, float amount, String title, String content) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.layout_input_pay_password_dialog);

        etPayPassword = findViewById(R.id.etPayPassword);
        if (amount < 0.0) {
            findViewById(R.id.tvValue).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.tvValue)).setText(String.format("%.2f", amount));
        }
        if (TextUtils.isEmpty(title)) {
            findViewById(R.id.tvType).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.tvType)).setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            findViewById(R.id.tvContent).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.tvContent)).setText(content);
        }

        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = ((ChatBaseActivity) context).getDisplaymetrics().widthPixels - Utils.dip2px(context, 52); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.BOTTOM);

        findViewById(R.id.ivClose).setOnClickListener(this);
        findViewById(R.id.tvOk).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideSoftInput();
        int id = v.getId();
        if (id == R.id.tvOk) {
            next();
        } else if (id == R.id.ivClose) {
            dismiss();
        }
    }

    private void next() {
        String password = etPayPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        HttpMethods.getInstance().checkPwd(password,
                DataManager.getInstance().getToken(), new HttpObserver(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o, String msg) {
                        if (mOnInputPasswordListener != null) {
                            mOnInputPasswordListener.afterCheckPassword();
                        }
                        dismiss();
                    }
                }, mContext, new OnHttpErrorListener() {
                    @Override
                    public void onConnectError(Throwable e) {
                        Toast.makeText(mContext, com.cao.commons.util.Utils.getCodeMessage(mContext, 0), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onServerError(int errorCode, String errorMsg) {
                        Toast.makeText(mContext, com.cao.commons.util.Utils.getCodeMessage(mContext, errorCode), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        if (etPayPassword != null) {
            InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(etPayPassword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setOnInputPasswordListener(OnInputPasswordListener onInputPasswordListener) {
        mOnInputPasswordListener = onInputPasswordListener;
    }


    public interface OnInputPasswordListener {
        public void afterCheckPassword();
    }
}


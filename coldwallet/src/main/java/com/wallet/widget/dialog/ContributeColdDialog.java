package com.wallet.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.cold.wallet.R;
import com.wallet.event.CheckCPwdEvent;
import com.wallet.utils.DensityUtil;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;

import org.greenrobot.eventbus.EventBus;

public class ContributeColdDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private EditText et_content;

    private String mTag;
    private String mPassword;
    // 软键盘
    private InputMethodManager manager = null;

    public ContributeColdDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    public ContributeColdDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.dialog_contribute_cold);

        et_content = findViewById(R.id.et_content);

        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = DensityUtil.getScreenWidth() - DensityUtil.dip2px(26 * 2); // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.BOTTOM);
        view.setWindowAnimations(R.style.dialog_slide_up_down); //设置窗口弹出动画

        findViewById(R.id.fl_close).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideSoftInput();
        int id = v.getId();
        if (id == R.id.tv_commit) {
            if (Utils.isFastDoubleClick()) {
                return;
            }
            next();
        } else if (id == R.id.fl_close) {
            dismiss();
        }
    }

    private void next() {
        String password = et_content.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(mContext.getString(R.string.dialog_input_your_pay_password));
            return;
        }
        String truePass;
        if (!TextUtils.isEmpty(mPassword)) {
            truePass = mPassword;
        } else {
            UserBean userBean = DataManager.getInstance().getUser();
            truePass = userBean.getPassword();
        }

        if (password.equals(truePass)) {
            EventBus.getDefault().post(new CheckCPwdEvent(mTag));
            dismiss();
        } else {
            ToastUtil.toast(mContext.getString(R.string.input_password_failure));
            et_content.setText("");
        }
    }


    public void showDialog(String tag) {
        this.mTag = tag;
        et_content.setText("");
        show();
    }

    public void showDialog(String tag, String password) {
        this.mTag = tag;
        this.mPassword = password;
        et_content.setText("");
        show();
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        if (et_content != null) {
            InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(et_content.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

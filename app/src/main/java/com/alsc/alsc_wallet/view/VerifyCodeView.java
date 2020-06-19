package com.alsc.alsc_wallet.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class VerifyCodeView extends RelativeLayout {

    private TextView[] tvCodes;
    //   private View[] lines;
    private EditText etCode;
    private List<String> mCodes = new ArrayList<>();
    private OnInputListener mOnInputListener;

    private static final int MAX_CODE_SIZE = 6;

    public VerifyCodeView(Context context) {
        super(context);
        initView();
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadView();
    }

    private void loadView() {
        LayoutInflater.from(getContext()).inflate(R.layout.verify_code_view, this);
        initView();
        initEvent();
    }

    private void initView() {
        tvCodes = new TextView[MAX_CODE_SIZE];
//        lines = new View[MAX_CODE_SIZE];
        int id;
        for (int i = 0; i < MAX_CODE_SIZE; ++i) {
            id = getResources().getIdentifier("tvCode" + i, "id", getContext().getPackageName());
            tvCodes[i] = findViewById(id);
//            id = getResources().getIdentifier("line" + i, "id", getContext().getPackageName());
//            lines[i] = findViewById(id);
        }
        etCode = findViewById(R.id.etCode);
    }

    private void initEvent() {
        //验证码输入
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    etCode.setText("");
                    if (mCodes.size() < MAX_CODE_SIZE) {
                        mCodes.add(editable.toString());
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && mCodes.size() > 0) {
                    mCodes.remove(mCodes.size() - 1);
                    showCode();
                    return true;
                }
                return false;
            }
        });
    }

    public void showKeybord() {
//        postDelayed(Runnable {
//            etCode.setFocusable(true)
//            etCode.setFocusableInTouchMode(true)
//            etCode.requestFocus()
//            (context as BaseActivity<*>).showKeyBoard(etCode)
//        }, 500)
        postDelayed(new Runnable() {
            @Override
            public void run() {
                etCode.setFocusable(true);
                etCode.setFocusableInTouchMode(true);
                etCode.requestFocus();
                ((BaseActivity) getContext()).showKeyBoard(etCode);
            }
        }, 400);
    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        int size = mCodes.size();
//        int color_default = getResources().getColor(R.color.user_color_404552);
//        int color_focus = getResources().getColor(R.color.user_color_dabc86);
        for (int i = 0; i < MAX_CODE_SIZE; ++i) {
            tvCodes[i].setText(size > i ? mCodes.get(i) : "");
//            tvCodes[i].setTextColor(i == size-1 ? color_focus : Color.WHITE);
//            lines[i].setBackgroundColor(i == size-1 ? color_focus : color_default);
        }
        callBack();//回调
    }


    /**
     * 回调
     */
    private void callBack() {
        if (mOnInputListener == null) {
            return;
        }
        if (mCodes.size() == MAX_CODE_SIZE) {
            mOnInputListener.onSucess(getPhoneCode());
        } else {
            mOnInputListener.onInput();
        }
    }

    //定义回调
    public interface OnInputListener {
        void onSucess(String code);

        void onInput();
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.mOnInputListener = onInputListener;
    }

    /**
     * 获得手机号验证码
     *
     * @return 验证码
     */
    public String getPhoneCode() {
        StringBuilder sb = new StringBuilder();
        for (String code : mCodes) {
            sb.append(code);
        }
        return sb.toString();
    }
}

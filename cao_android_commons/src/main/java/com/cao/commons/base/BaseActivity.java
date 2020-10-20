package com.cao.commons.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cao.commons.R;
import com.cao.commons.manager.ActivityManager;
import com.cao.commons.manager.DataManager;
import com.cao.commons.retrofit.OkHttpClientManager;
import com.cao.commons.retrofit.PoliceBaseUrl;
import com.cao.commons.retrofit.RxManager;
import com.cao.commons.widget.svprogresshud.SVProgressHUD;
import com.gyf.barlibrary.ImmersionBar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 基类baseActivity，勿动，后期有需求可调整
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-11-9
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 每个页面唯一标识tag，不需要子页面做优化处理
     */
    public String Tag;
    /**
     * 上下wen
     */
    public Context mContext;
    /**
     * 后面会在此类加曾经是状态栏
     */
    public ImmersionBar mImmersionBar;
    /**
     * 新增longding
     */
    private SVProgressHUD mDialog;
    /**
     * 防止页面多次点击多次创建
     */
    private static long lastClickTime;
    // 软键盘
    private InputMethodManager manager = null;
    /**
     * 是否可以关闭软键盘
     */
    protected boolean canHideSoftKeyBoard = true;

    protected boolean isDarkFont = true;

    protected boolean mIsActivityFinish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsActivityFinish = false;
        Tag = getPackageName() + "." + getClass().getSimpleName();
        ActivityManager.getInstance().addActivity(this);
        init(savedInstanceState);
        if (!getLocalClassName().contains("SplashActivity")) {
            isCloseApp();
        }
    }


    private void isCloseApp() {
        String lan = DataManager.getInstance().getLanguage() == 0 ? "cn" : "en";
        OkHttpClientManager.getInstance().get(PoliceBaseUrl.getBaseUrl() + "api/is_close?lan=" + lan, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() == null && !mIsActivityFinish) {
                    return;
                }
                try {
                    String str = response.body().string();
                    JSONObject jb = new JSONObject(str);
                    if (jb.optInt("code", 0) != 1) {
                        return;
                    }
                    jb = jb.optJSONObject("data");
                    if (jb.optInt("status", 0) != 1) {
                        return;
                    }
                    final String msg = jb.optString("msg");
                    final String title = jb.optString("title", "标题");
                    if (TextUtils.isEmpty(msg)) {
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CloseAppDialog(BaseActivity.this, title, msg).show();
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
    }

    public void onBackClick(View view) {
        finish();
    }

    /**
     * baseActivity销毁会绑定rxjava，清空子页面Tag
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Jzvd.releaseAllVideos();
        mIsActivityFinish = true;
        RxManager.getInstance().clear(Tag);
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 初始化，注意看方法先后顺序，避免子类空指针
     */
    private void init(Bundle bundle) {
        mContext = this;
        initIntentData();
        initViews(bundle);
        initTitleData();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        setListener();
    }

    /**
     * 获取点击事件
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (canHideSoftKeyBoard) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View view = getCurrentFocus();
                if (isShouldHideKeyBord(view, ev)) {
                    hideSoftInput(view.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void showSoftInput(EditText view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isFastDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    /**
     * 初始化状态栏
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.with(this)
                .statusBarDarkFont(isDarkFont)   //状态栏字体是深色，不写默认为亮色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .init();
    }

    /**
     * 子类接受跳转操作，可放此方法
     */
    protected void initIntentData() {
    }

    /**
     * 取消网络
     */
    protected void cancleRequest() {
    }

    /**
     * 初始化view,再initIntentData之后
     */
    protected abstract void initViews(Bundle bundle);

    /**
     * 初始化事件
     */
    protected void setListener() {
    }

    /**
     * 是否需要沉浸式状态栏
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化通用标题栏
     */
    protected void initTitleData() {
    }

    /**
     * 调用方法
     */
    public void showWithStatus() {
        if (mDialog == null)
            mDialog = new SVProgressHUD(this);
        mDialog.showWithStatus(mContext.getResources().getString(R.string.network_loading), SVProgressHUD.SVProgressHUDMaskType.Clear);
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 防止页面多次点击
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {

            return true;
        }
        lastClickTime = time;
        return false;

    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        try {
            if (manager == null) {
                manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            if (getCurrentFocus() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class CloseAppDialog extends Dialog {
        private Context mContext;

        public CloseAppDialog(Context context, String title, String msg) {
            this(context, R.style.LoadingDialog, title, msg);
        }

        public CloseAppDialog(Context context, int themeResId, String title, String msg) {
            super(context, themeResId);
            setContentView(R.layout.dialog_close_app);
            mContext = context;
            Window view = getWindow();
            WindowManager.LayoutParams lp = view.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 设置宽度充满屏幕
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            view.setGravity(Gravity.CENTER);

            ((TextView) findViewById(R.id.tv_content)).setText("" + msg);
            ((TextView) findViewById(R.id.tv_title)).setText("" + title);

            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }


}

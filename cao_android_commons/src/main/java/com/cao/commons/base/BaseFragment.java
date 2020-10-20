package com.cao.commons.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cao.commons.R;
import com.cao.commons.retrofit.RxManager;
import com.cao.commons.widget.svprogresshud.SVProgressHUD;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Field;


/**
 * 基类BaseFragment，勿动，后期有需求可调整,基类都勿动，有需求我会加上去,baseFragment已经做了fragment可见的加载方式
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-11-9
 */

public abstract class BaseFragment extends Fragment {
    protected String Tag;
    protected View mRootView;
    /**
     * Fragment当前状态是否可见
     */
    private boolean isFragmentVisible;
    /**
     * 标识组件是否初始化完毕
     */
    private boolean isReuseView;
    /**
     * 是否是第一次开启网络加载
     */
    private boolean isFirstVisibles;
    /**
     * 主线程，云信更新ui需要
     */
    private static final Handler handler = new Handler();
    protected Context mContext;
    // 软键盘
    private InputMethodManager manager = null;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mRootView == null) {
            return;
        }
        if (isFirstVisibles && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisibles = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    protected void setTopStatusBarStyle(View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topView.setPadding(0, getStatusBarHeight() + topView.getPaddingTop(), 0, 0);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    private int getStatusBarHeight() {
        // 获得状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        try {
//            //参数是固定写法
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxManager.getInstance().clear(Tag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        getBnudleValue();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateFragmentView(inflater, container);
        mContext = getContext();
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (mRootView == null) {
            mRootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisibles) {
                    setTag(getClass().getSimpleName());
                    onFragmentFirstVisible();
                    isFirstVisibles = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView && mRootView != null ? mRootView : view, savedInstanceState);
    }

    private void initVariable() {
        isFirstVisibles = true;
        isFragmentVisible = false;
        isReuseView = true;
        mRootView = null;
    }

    private void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible)
            onFragmentVisible();
        else
            onFragmentInvisible();
    }

    protected final Handler getHandler() {
        return handler;
    }

    protected final void postRunnable(final Runnable runnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        });
    }

    protected final void postDelayed(final Runnable runnable, long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        }, delay);
    }

    /**
     * 加载fragment布局
     */
    public abstract View onCreateFragmentView(LayoutInflater inflater, ViewGroup viewGroup);

    /**
     * 添加事件
     */
    protected void setListener() {
    }

    /**
     * 获取bundel
     */
    protected void getBnudleValue() {
    }

    /**
     * fragment可见
     */
    protected void onFragmentVisible() {
    }

    /**
     * fragment不可见
     */
    protected void onFragmentInvisible() {
    }

    /**
     * 在fragment首次可见时回调，可用于加载数据，防止每次进入都重复加载数据
     */
    protected abstract void onFragmentFirstVisible();

    public String getTags() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        try {
            if (manager == null) {
                manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (getActivity().getCurrentFocus() != null) {
                    manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增longding
     */
    private SVProgressHUD mDialog;

    /**
     * 调用方法
     */
    public void showWithStatus() {
        if (mDialog == null)
            mDialog = new SVProgressHUD(mContext);
        mDialog.showWithStatus(mContext.getResources().getString(R.string.network_loading), SVProgressHUD.SVProgressHUDMaskType.Clear);
    }

    public void dismissDialog() {
        if (mDialog!=null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}

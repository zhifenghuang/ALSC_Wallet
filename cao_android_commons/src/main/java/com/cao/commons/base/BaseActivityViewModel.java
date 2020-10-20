package com.cao.commons.base;

import android.view.View;
import android.view.ViewStub;

import androidx.databinding.ViewDataBinding;

import com.cao.commons.manager.DefaultLayoutManager;
import com.cao.commons.util.KeyboardStatusUtil;
import com.cao.commons.widget.PermissionsDialog;
import com.cao.commons.widget.svprogresshud.SVProgressHUD;

/**
 * 基类BaseActivityViewModel，勿动，后期有需求可调整
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-11-9
 */

public abstract class BaseActivityViewModel<ACT extends BaseActivity, DB extends ViewDataBinding> {
    /**
     * 必备属性
     */
    private BaseActivity mActivity;
    private ViewDataBinding mBinding;
    /**
     * 基类loading
     */
    protected SVProgressHUD mDialog;
    /**
     * 可选属性
     */
    //protected FaceTitleManager mTitleManager;
    protected DefaultLayoutManager mDefaultLayoutManager;
    /* 使用之前要初始化 */
    protected PermissionsDialog mPermissionsDialog;
    /**
     * 控制软键盘
     */
    protected KeyboardStatusUtil mKeyboardStatusUtil;

    public BaseActivityViewModel(BaseActivity activity, ViewDataBinding binding) {
        init(activity, binding);
        initView();
        initData();
        setListener();
    }

    public BaseActivityViewModel(BaseActivity activity, ViewDataBinding binding, View view) {
        init(activity, binding);
        initView();
        initData();
        setListener();
    }

    public BaseActivityViewModel(BaseActivity activity, ViewDataBinding binding, ViewStub viewStub) {
        init(activity, binding);
        initView();
        initData();
        setListener();
    }

    public BaseActivityViewModel(BaseActivity activity, ViewDataBinding binding, View view, ViewStub viewStub) {
        init(activity, binding);
        this.mDefaultLayoutManager = new DefaultLayoutManager(activity, viewStub);
        initView();
        initData();
        setListener();
    }

    private void init(BaseActivity activity, ViewDataBinding binding) {
        this.mActivity = activity;
        this.mBinding = binding;
        this.mDialog = new SVProgressHUD(activity);
    }

    public void initKeyboardStatus() {
        mKeyboardStatusUtil = new KeyboardStatusUtil();
        mKeyboardStatusUtil.registerActivity(getActivity());
        mKeyboardStatusUtil.setmVisibilityListener(new KeyboardStatusUtil.KeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean keyboardVisible) {
                keyBoardStatusChanged(keyboardVisible);
            }
        });
    }


    public void showTimeDialog() {
        PermissionsDialog.builder(getActivity())
                .setTitle("提示")
                .setContent("您的结束日期不能大于开始日期")
                .setLeftButName("取消")
                .setRightButName("确定")
                .setListener(new PermissionsDialog.OnPermissionsDialogListener() {
                    @Override
                    public void cancleOnClick(PermissionsDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void determineOnClick(PermissionsDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onDismiss() {

                    }
                }).show();
    }


    protected void keyBoardStatusChanged(boolean keyboardVisible) {
    }

    protected abstract void initView();

    protected void initData() {
    }

    ;

    protected void setListener() {
    }

    protected DB getBinding() {
        return (DB) mBinding;
    }

    protected ACT getActivity() {
        return (ACT) mActivity;
    }

}

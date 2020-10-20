package com.cao.commons.base;

import android.content.Context;
import android.os.Handler;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.cao.commons.util.KeyboardStatusUtil;
import com.cao.commons.widget.PermissionsDialog;
import com.cao.commons.widget.svprogresshud.SVProgressHUD;

/**
 * BaseFragmentViewModel  基类
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-11-11
 */

public class BaseFragmentViewModel <F extends BaseFragment,DB extends ViewDataBinding>{
    /** 必备属性 */
    private BaseFragment mFragment;
    private ViewDataBinding mBinding;
    private SVProgressHUD mDialog;
    /* 使用之前要初始化 */
    protected PermissionsDialog mPermissionsDialog;
    protected KeyboardStatusUtil mKeyboardStatusUtil;

    public BaseFragmentViewModel(BaseFragment fragment, ViewDataBinding binding) {
        init(fragment,binding);
        initView();
        initData();
        setListener();
    }

    private void init(BaseFragment fragment, ViewDataBinding binding){
        this.mFragment = fragment;
        this.mBinding = binding;
        this.mDialog = new SVProgressHUD(fragment.getActivity());
    }

    public void initKeyboardStatus(){
        mKeyboardStatusUtil = new KeyboardStatusUtil();
        mKeyboardStatusUtil.registerFragment(getFragments());
        mKeyboardStatusUtil.setmVisibilityListener(new KeyboardStatusUtil.KeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean keyboardVisible) {
                keyBoardStatusChanged(keyboardVisible);
            }
        });
    }

    public void showInfoWithStatus(final String msg) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.showWithStatus(msg);
            }
        }, 500);
    }
    public void dismissDialog(){
        if (mDialog == null )
            mDialog = new SVProgressHUD(getContexts());
        mDialog.dismiss();
    }
    protected void initView(){}

    protected void initData(){}

    protected void setListener(){}

    protected void keyBoardStatusChanged(boolean keyboardVisible){}

    protected DB getBinding(){
        return (DB) mBinding;
    }

    protected F getFragments(){
        return (F) mFragment;
    }

    protected FragmentActivity getActivity(){
        return  mFragment.getActivity();
    }

    protected Context getContexts(){
        return getFragments().getActivity();
    }
}

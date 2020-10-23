package com.alsc.alsc_wallet.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alsc.alsc_wallet.BaseApp;
import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.MainActivity;
import com.alsc.chat.http.ChatHttpMethods;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.common.activity.BaseActivity;
import com.common.bean.LoginBean;
import com.common.fragment.BaseFragment;
import com.common.http.HttpMethods;
import com.common.http.HttpObserver;
import com.common.http.OnHttpErrorListener;
import com.common.http.SubscriberOnNextListener;
import com.common.utils.Constants;
import com.common.utils.MD5Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class LoginFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        setViewsOnClickListener(R.id.tvRight, R.id.tvLogin);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvRight:
                gotoPager(RegisterFragment.class);
                break;
            case R.id.tvLogin:
                String name = getTextById(R.id.etAccount);
                if (TextUtils.isEmpty(name)) {
                    showToast(R.string.app_please_input_account);
                    return;
                }
                String password = getTextById(R.id.etPassword);
                if (TextUtils.isEmpty(password)) {
                    showToast(R.string.app_please_input_password);
                    return;
                }
                String code = getTextById(R.id.etVerCode);
                if (TextUtils.isEmpty(code)) {
                    showToast(R.string.app_please_input_ver_code);
                    return;
                }
                HttpMethods.getInstance().login(name, MD5Utils.encryptMD5(password), code,
                        new HttpObserver(new SubscriberOnNextListener<LoginBean>() {
                            @Override
                            public void onNext(LoginBean bean, String msg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                refreshUserInfo(bean.getToken(), String.valueOf(bean.getUserId()));
                            }
                        }, getActivity(), new OnHttpErrorListener() {
                            @Override
                            public void onConnectError(Throwable e) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                showToast(R.string.app_net_work_error);
                            }

                            @Override
                            public void onServerError(int errorCode, String errorMsg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                if (!TextUtils.isEmpty(errorMsg)) {
                                    ((BaseActivity) getActivity()).errorCodeDo(errorCode, errorMsg);
                                }
                            }
                        }));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (str.equals(Constants.FINISH_ACTIVITY)) {
            goBack();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void refreshUserInfo(final String token, String userId) {
        ChatHttpMethods.getInstance().setToken(token);
        ChatHttpMethods.getInstance().getUserProfile(userId, new HttpObserver(new SubscriberOnNextListener<UserBean>() {
            @Override
            public void onNext(UserBean bean, String msg) {
                if (getActivity() == null || getView() == null) {
                    return;
                }
                ChatHttpMethods.getInstance().setToken(null);
                DataManager.getInstance().saveUser(bean);
                DataManager.getInstance().saveToken(token);
                ((BaseApp) getActivity().getApplication()).initWebSocket(token);
                gotoPager(MainActivity.class);
                ((BaseActivity) getActivity()).finishAllActivity();
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }
}

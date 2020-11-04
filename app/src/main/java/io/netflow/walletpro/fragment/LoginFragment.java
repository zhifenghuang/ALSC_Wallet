package io.netflow.walletpro.fragment;

import android.text.TextUtils;
import android.view.View;

import io.netflow.walletpro.BaseApp;
import io.netflow.walletpro.R;
import io.netflow.walletpro.activity.MainActivity;
import com.alsc.chat.http.ChatHttpMethods;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.common.activity.BaseActivity;
import com.common.bean.LoginBean;
import com.common.bean.ThirdPartyUserBean;
import com.common.fragment.BaseFragment;
import com.common.http.HttpMethods;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.common.http.ThirdPartyHttpMethods;
import com.common.utils.Constants;
import com.common.utils.CountDownUtil;
import com.common.utils.MD5Utils;
import com.common.utils.SecuritySHA1Utils;

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
        setViewsOnClickListener(R.id.tvRight, R.id.tvLogin, R.id.tvGetCode);
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
            case R.id.tvGetCode:
                HashMap<String, Object> map = new HashMap<>();
                String account = getTextById(R.id.etAccount);
                if (TextUtils.isEmpty(account)) {
                    showToast(R.string.app_please_input_phone_or_email);
                    return;
                }
                map.put("loginname", account);
                ThirdPartyHttpMethods.getInstance().getLoginVerCode(map, new HttpObserver(new SubscriberOnNextListener<Object>() {
                    @Override
                    public void onNext(Object o, String msg) {
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startCountDown();
                            }
                        });
                    }
                }, getActivity(), (BaseActivity) getActivity()));

                break;
            case R.id.tvLogin:
                map = new HashMap<>();
                String name = getTextById(R.id.etAccount);
                if (TextUtils.isEmpty(name)) {
                    showToast(R.string.app_please_input_phone_or_email);
                    return;
                }
                map.put("loginname", name);
                String code = getTextById(R.id.etVerCode);
                if (TextUtils.isEmpty(code)) {
                    showToast(R.string.app_please_input_ver_code);
                    return;
                }
                map.put("verifycode", code);
                String password = getTextById(R.id.etPassword);
                if (TextUtils.isEmpty(password)) {
                    showToast(R.string.app_please_input_password);
                    return;
                }
                try {
                    map.put("loginpwd", SecuritySHA1Utils.getSha1(MD5Utils.encryptMD5(password)));
                    ThirdPartyHttpMethods.getInstance().login(map, new HttpObserver(new SubscriberOnNextListener<ThirdPartyUserBean>() {
                        @Override
                        public void onNext(ThirdPartyUserBean bean, String msg) {
                            if (getView() == null || bean == null || TextUtils.isEmpty(bean.getTicket())) {
                                return;
                            }
                            login(bean.getTicket());
                        }
                    }, getActivity(), (BaseActivity) getActivity()));
                } catch (Exception e) {

                }
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

    private void login(final String ticket) {
        HttpMethods.getInstance().login(ticket, new HttpObserver(new SubscriberOnNextListener<LoginBean>() {
            @Override
            public void onNext(LoginBean bean, String msg) {
                if (getActivity() == null || getView() == null || bean == null) {
                    return;
                }
                refreshUserInfo(ticket, bean.getToken(), String.valueOf(bean.getUserId()));
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }

    private void refreshUserInfo(final String ticket, final String token, String userId) {
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
                DataManager.getInstance().saveTicket(ticket);
                ((BaseApp) getActivity().getApplication()).initWebSocket(token);
                gotoPager(MainActivity.class);
                ((BaseActivity) getActivity()).finishAllActivity();
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }

    private void startCountDown() {
        new CountDownUtil(fv(R.id.tvGetCode))
                .setCountDownMillis(60_000L)//倒计时60000ms
                .setHintAfterText("s")
                .setCountDownHint(getString(R.string.app_message_resend))
                .setCountDownColor(R.color.color_00_cf_7c, R.color.color_b2_b2_b2)

                .setCountDownBackgroundColor(R.drawable.shape_stroke_00cf7c_12, R.drawable.shape_stroke_b2b2b2_12)
                .setOnTimeListener(new CountDownUtil.OnTimeFinishListener() {
                    @Override
                    public void onTimeFinish() {
                    }
                })
                .start();
    }
}

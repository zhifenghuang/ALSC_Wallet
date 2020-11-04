package io.netflow.walletpro.fragment;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import io.netflow.walletpro.R;
import com.common.activity.BaseActivity;
import com.common.bean.ThirdPartyUserBean;
import com.common.fragment.BaseFragment;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.common.http.ThirdPartyHttpMethods;
import com.common.utils.CountDownUtil;

import java.util.HashMap;

public class RegisterFragment extends BaseFragment {

    private int mRegisterType = 1;  //1是手机号注册，2是邮箱注册

    private String mPhoneCode = "86";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight, R.id.tvGetCode, R.id.tvOk);

        ((RadioGroup) view.findViewById(R.id.rGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMobile) {
                    mRegisterType = 1;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    setViewVisible(R.id.llPhone);
                    setViewGone(R.id.etEmail);
                } else {
                    mRegisterType = 2;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    setViewGone(R.id.llPhone);
                    setViewVisible(R.id.etEmail);
                }
            }
        });
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvGetCode:
                HashMap<String, Object> map = new HashMap<>();
                if (mRegisterType == 1) {
                    String phone = getTextById(R.id.etMobile);
                    if (TextUtils.isEmpty(phone)) {
                        showToast(R.string.app_please_input_mobile);
                        return;
                    }
                    map.put("phone", phone);
                    map.put("countrycode", "86");
                } else {
                    String email = getTextById(R.id.etEmail);
                    if (TextUtils.isEmpty(email)) {
                        showToast(R.string.app_please_input_email);
                        return;
                    }
                    map.put("email", email);
                }
                map.put("action", "requestauxcode");
                ThirdPartyHttpMethods.getInstance().getVerCode(map, new HttpObserver(new SubscriberOnNextListener<Object>() {
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
            case R.id.tvRight:
                goBack();
                break;
            case R.id.tvOk:
                map = new HashMap<>();
                String account;
                if (mRegisterType == 1) {
                    account = getTextById(R.id.etMobile);
                    if (TextUtils.isEmpty(account)) {
                        showToast(R.string.app_please_input_mobile);
                        return;
                    }
                    map.put("phone", account);
                } else {
                    account = getTextById(R.id.etEmail);
                    if (TextUtils.isEmpty(account)) {
                        showToast(R.string.app_please_input_email);
                        return;
                    }
                    map.put("email", account);
                }
                String verCode = getTextById(R.id.etVerCode);
                if (TextUtils.isEmpty(verCode)) {
                    showToast(R.string.app_please_input_ver_code);
                    return;
                }
                map.put("verifycode", verCode);
                String userName = getTextById(R.id.etUserName);
                if (TextUtils.isEmpty(userName)) {
                    showToast(R.string.app_please_input_user_name);
                    return;
                }
                map.put("loginname", userName);
                String nick = getTextById(R.id.etNick);
                if (TextUtils.isEmpty(nick)) {
                    showToast(R.string.app_please_input_nick);
                    return;
                }
                map.put("nickname", nick);
                String password1 = getTextById(R.id.etPassword);
                if (TextUtils.isEmpty(password1)) {
                    showToast(R.string.app_please_input_password);
                    return;
                }
                String password2 = getTextById(R.id.etCheckPassword);
                if (TextUtils.isEmpty(password2)) {
                    showToast(R.string.app_please_input_check_password);
                    return;
                }
                if (!password1.equals(password2)) {
                    showToast(R.string.app_password_not_equal_check_password);
                    return;
                }
                map.put("password", password1);
                String firstName = getTextById(R.id.etFirstName);
                if (TextUtils.isEmpty(firstName)) {
                    showToast(R.string.app_please_input_first_name);
                    return;
                }
                map.put("firstname", firstName);
                String secondName = getTextById(R.id.etSecondName);
                if (TextUtils.isEmpty(secondName)) {
                    showToast(R.string.app_please_input_second_name);
                    return;
                }
                map.put("lastname", secondName);
                ThirdPartyHttpMethods.getInstance().register(map, new HttpObserver(new SubscriberOnNextListener<ThirdPartyUserBean>() {
                    @Override
                    public void onNext(ThirdPartyUserBean bean, String msg) {
                        showToast(R.string.app_register_success);
                        goBack();
                    }
                }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
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

package com.alsc.alsc_wallet.fragment.identity;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.alsc.alsc_wallet.view.VerifyCodeView;

public class GoogleVerifyCodeFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_google_verify_code;
    }

    @Override
    protected void onViewCreated(View view) {
        VerifyCodeView verifyCodeView = view.findViewById(R.id.verifyCodeView);
        verifyCodeView.showKeybord();
        verifyCodeView.setOnInputListener(new VerifyCodeView.OnInputListener() {
            @Override
            public void onSucess(String code) {
                gotoPager(GoogleIdentityMachineFragment.class);
            }

            @Override
            public void onInput() {

            }
        });
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }


}

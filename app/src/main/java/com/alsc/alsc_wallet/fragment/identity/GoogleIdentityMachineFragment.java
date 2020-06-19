package com.alsc.alsc_wallet.fragment.identity;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;

public class GoogleIdentityMachineFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_google_identity_machine;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.wallet_google_identity_machine);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }


}

package com.alsc.alsc_wallet.fragment.identity;

import android.os.Bundle;
import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.alsc.alsc_wallet.utils.Constants;

public class OpenIdentity3Fragment extends BaseFragment {

    private int mType;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_open_identity3;
    }

    @Override
    protected void onViewCreated(View view) {
        mType = getArguments().getInt(Constants.BUNDLE_EXTRA, 0);
        setViewsOnClickListener(R.id.tvNextStep);
        setEditTextHint(R.id.etNumber, getString(mType == 0 ?
                R.string.wallet_credentials_number :
                R.string.wallet_identity_credentials_number));

    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvNextStep:
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BUNDLE_EXTRA, mType);
                gotoPager(OpenIdentity4Fragment.class, bundle);
                break;
        }
    }

}

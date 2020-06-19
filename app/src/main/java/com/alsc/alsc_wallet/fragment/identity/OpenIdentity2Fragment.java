package com.alsc.alsc_wallet.fragment.identity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.alsc.alsc_wallet.utils.Constants;

public class OpenIdentity2Fragment extends BaseFragment {

    private int mSelectType = -1;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_open_identity2;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvOk);
        setViewsOnClickListener(R.id.llPassport, R.id.llIdentity, R.id.tvNextStep);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.llPassport:
                mSelectType = 0;
                resetBtns((LinearLayout) v, fv(R.id.llIdentity));
                break;
            case R.id.llIdentity:
                mSelectType = 1;
                resetBtns((LinearLayout) v, fv(R.id.llPassport));
                break;
            case R.id.tvNextStep:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BUNDLE_EXTRA, mSelectType);
                gotoPager(OpenIdentity3Fragment.class, bundle);
                break;
        }
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2) {
        ll1.setBackgroundResource(R.drawable.bg_wallet_credentials_select);
        ll2.setBackgroundResource(R.drawable.bg_wallet_credentials_unselect);
        fv(R.id.tvNextStep).setBackgroundResource(R.drawable.bg_wallet_buy);
    }

}

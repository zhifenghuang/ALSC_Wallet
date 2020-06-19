package com.alsc.alsc_wallet.fragment.identity;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;

public class OpenIdentity1Fragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_open_identity1;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvOk);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvOk:
                gotoPager(OpenIdentity2Fragment.class);
                break;
        }
    }

}

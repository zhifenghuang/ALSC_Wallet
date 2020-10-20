package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.common.fragment.BaseFragment;

public class AliPaySettingFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_alipay_setting;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvSave);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvSave:
                gotoPager(BuyDetailFragment.class);
                break;
        }
    }


}
